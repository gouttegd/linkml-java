/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.incenp.linkml.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.incenp.linkml.core.annotations.ExtensionHolder;
import org.incenp.linkml.core.annotations.Identifier;
import org.incenp.linkml.core.annotations.Inlining;
import org.incenp.linkml.core.annotations.Requirement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a “slot” (that is, a field) on a specific LinkML object.
 * <p>
 * This is <em>not</em> a “slot definition” in a LinkML schema! This class is
 * mostly intended to encapsulate the code needed to (1) obtain information
 * about a slot at runtime, (2) get or set the value of a slot.
 */
public class Slot {

    private static final String SET_VALUE_ERROR = "Cannot set value of slot '%s': %s";
    private static final String GET_VALUE_ERROR = "Cannot get value of slot '%s': %s";

    private Field field;
    private Method writeAccessor;
    private Method readAccessor;
    private Class<?> outerType;
    private InliningMode inliningMode;

    /**
     * Creates a new instance.
     * 
     * @param field The Java field that represents the slot.
     * @throws LinkMLRuntimeException If the class the field belongs to does not
     *                                declare accessor methods for the slot (which
     *                                should not happen if the class is a <em>bona
     *                                fide</em> LinkML object).
     */
    public Slot(Field field) throws LinkMLRuntimeException {
        this.field = field;
        outerType = field.getType();

        Class<?> klass = field.getDeclaringClass();
        try {
            writeAccessor = klass.getDeclaredMethod(getWriteAccessorName(field), new Class<?>[] { outerType });
            readAccessor = klass.getDeclaredMethod(getReadAccessorName(field), (Class<?>[]) null);
        } catch ( NoSuchMethodException | SecurityException e ) {
            throw new LinkMLInternalError(String.format("Missing accessor for slot '%s'", field.getName()), e);
        }

        Inlining inliningAnnotation = field.getAnnotation(Inlining.class);
        inliningMode = inliningAnnotation != null ? inliningAnnotation.value() : InliningMode.NO_INLINING;
    }

    /**
     * Gets the name of the slot as originally declared in LinkML.
     * <p>
     * This may not be the same as the name of the underlying Java field because the
     * LinkML name might have been transformed during the code generation process,
     * for example if the original name was not a suitable Java name.
     * 
     * @return The original LinkML name.
     */
    public String getLinkMLName() {
        JsonProperty propertyAnnotation = field.getAnnotation(JsonProperty.class);
        if ( propertyAnnotation != null ) {
            return propertyAnnotation.value();
        } else {
            return field.getName();
        }
    }

    /**
     * Indicates whether the slot holds an identifier for the class it belongs to.
     * 
     * @return <true> if the slot is an identifier slot, otherwise
     *         <code>false</code>.
     */
    public boolean isIdentifier() {
        return field.isAnnotationPresent(Identifier.class);
    }

    /**
     * Indicates whether the slot is expected to hold multiple values.
     * 
     * @return <code>true</code> if the slot is multi-valued, otherwise
     *         <code>false</code>.
     */
    public boolean isMultivalued() {
        return outerType == List.class;
    }

    /**
     * Gets the type of the slot.
     * <p>
     * For single-valued slot, this is the same as the type of the underlying Java
     * field. But for multi-valued slots, the underlying field is of type
     * {@link List}; this method will return the parameter type of the List object.
     * 
     * @return The real type of the slot.
     */
    public Class<?> getInnerType() {
        if ( isMultivalued() ) {
            ParameterizedType pt = (ParameterizedType) field.getGenericType();
            return (Class<?>) pt.getActualTypeArguments()[0];
        }
        return outerType;
    }

    /**
     * Indicates how the slot value(s) is(are) inlined.
     * 
     * @return <code>null</code> if the slot expects an object reference (no
     *         inlining); otherwise, a {@link InliningMode} value specifying how the
     *         value(s) should be inlined.
     */
    public InliningMode getInliningMode() {
        return inliningMode;
    }

    /**
     * Indicates whether the slot is required, recommended, or optional.
     * 
     * @return A {@link RequirementLevel} value for the slot.
     */
    public RequirementLevel getRequirementLevel() {
        if ( isIdentifier() ) {
            return RequirementLevel.MANDATORY;
        }
        Requirement reqAnnotation = field.getAnnotation(Requirement.class);
        return reqAnnotation != null ? reqAnnotation.value() : RequirementLevel.OPTIONAL;
    }

    /**
     * Indicates whether this slot is intended to store “extended data” (unknown
     * properties) for its object.
     * 
     * @return <code>true</code> if the slot is the holder slot for all unknown
     *         properties.
     */
    public boolean isExtensionStore() {
        return field.isAnnotationPresent(ExtensionHolder.class);
    }

    /**
     * Assigns a value to the slot for the given object.
     * 
     * @param target The LinkML object holding the slot.
     * @param value  The value to assign.
     * @throws LinkMLRuntimeException If any error occurs when attempting to set the
     *                                value. Likely causes would be (1)
     *                                <code>target</code> is not a proper LinkML
     *                                object, or not an object for which the present
     *                                slot is valid; (2) the value itself is
     *                                invalid.
     */
    public void setValue(Object target, Object value) throws LinkMLRuntimeException {
        try {
            writeAccessor.invoke(target, new Object[] { value });
        } catch ( IllegalArgumentException e ) {
            // Either the target object is not valid (especially, it does not declare the
            // expected accessor method), or the value object is not valid (not of the
            // correct type).
            throw new LinkMLInternalError(String.format(SET_VALUE_ERROR, getLinkMLName(), "invalid object"), e);
        } catch ( InvocationTargetException e ) {
            // An error occurred within the accessor method (most likely, it didn't like the
            // value it got passed).
            throw new LinkMLValueError(String.format(SET_VALUE_ERROR, getLinkMLName(), "invalid value"), e);
        } catch ( IllegalAccessException e ) {
            // Should not happen if the target object is a valid LinkML object
            throw new LinkMLInternalError(String.format(SET_VALUE_ERROR, getLinkMLName(), "illegal access"), e);
        }
    }

    /**
     * Gets the value of the slot for the given object.
     * 
     * @param source The LinkML object holding the slot.
     * @return The value of the slot in the given object.
     * @throws LinkMLRuntimeException If any error occurs when attempting to get the
     *                                value. A likely cause would be that
     *                                <code>source</code> is not a proper LinkML
     *                                object, or not an object for which the slot is
     *                                valid.
     */
    public Object getValue(Object source) throws LinkMLRuntimeException {
        try {
            return readAccessor.invoke(source, (Object[]) null);
        } catch ( IllegalArgumentException e ) {
            // The target object is not valid (especially, it does not declare the expected
            // accessor method).
            throw new LinkMLInternalError(String.format(GET_VALUE_ERROR, getLinkMLName(), "invalid object"), e);
        } catch ( InvocationTargetException e ) {
            // An error occurred within the accessor method. That is not really expected to
            // ever happen: why would a read accessor throw an exception?
            throw new LinkMLInternalError(String.format(GET_VALUE_ERROR, getLinkMLName(), "invalid state"), e);
        } catch ( IllegalAccessException e ) {
            // Should not happen if the target object is a valid LinkML object
            throw new LinkMLInternalError(String.format(GET_VALUE_ERROR, getLinkMLName(), "illegal access"), e);
        }
    }

    /**
     * Finds the slot that holds the identifier for a given LinkML object.
     * <p>
     * The “identifier slot” is the slot that is tagged with
     * <code>identifier: true</code> in its definition (or in the
     * <code>slot_usage</code> block that overrides the global definition for a
     * given class). In LinkML Java objects, the identifier slot is tagged with a
     * dedicated runtime annotation.
     * 
     * @param type The Java class representing the LinML class for which to get the
     *             identifier slot.
     * @return The Slot object representing the identifier slot, or
     *         <code>null</code> if the class does not have any identifier slot.
     */
    public static Slot getIdentifierSlot(Class<?> type) {
        Class<?> current = type;
        do {
            for ( Field field : current.getDeclaredFields() ) {
                if ( field.isAnnotationPresent(Identifier.class) ) {
                    try {
                        return new Slot(field);
                    } catch ( LinkMLRuntimeException e ) {
                    }
                }
            }
            current = current.getSuperclass();
        } while ( current != null );

        return null;
    }

    /**
     * Finds the primary slot for the given type.
     * <p>
     * The “primary” slot of a LinkML class is the slot to which assign the value of
     * a dict entry when using the “SimpleDict” inlining mode. As per the rules set
     * forth in LinkML’s documentation, the primary slot is either:
     * <ul>
     * <li>the one non-identifier (or more generally, non-<em>key</em>, but we do
     * not support key slots for the time being) slot, if the class has only one
     * such slot beyond the identifier slot;
     * <li>the one <em>mandatory</em> non-identifier slot, if the class has several
     * non-identifier slots but only one marked as mandatory.
     * </ul>
     * If the class has more than one non-identifier slot without any single one of
     * them being mandatory, or more than one mandatory non-identifier slot, then
     * the class has <em>no</em> primary slot (and is not eligible for inlining in
     * SimpleDict mode).
     * 
     * @param type The type for which to retrieve the primary slot.
     * @return The primary slot for the type, or <code>null</code> if the type has
     *         no primary slot.
     * 
     * @see <a href=
     *      "https://linkml.io/linkml/schemas/inlining.html#inlining-as-simple-dictionaries">LinkML
     *      documentation about inlining as simple dictionaries</a>
     */
    public static Slot getPrimaryValueSlot(Class<?> type) {
        Slot mandatorySlot = null;
        Slot nonIdentifierSlot = null;
        int nMandatorySlots = 0;
        int nNonIdentifierSlots = 0;
        for ( Slot slot : Slot.getSlots(type) ) {
            if ( slot.isIdentifier() ) {
                continue;
            }
            if ( slot.getRequirementLevel() == RequirementLevel.MANDATORY ) {
                mandatorySlot = slot;
                nMandatorySlots += 1;
            }
            nonIdentifierSlot = slot;
            nNonIdentifierSlots += 1;
        }
        return nNonIdentifierSlots == 1 ? nonIdentifierSlot : nMandatorySlots == 1 ? mandatorySlot : null;
    }

    /**
     * Gets the Slot object corresponding to a given field in a given class.
     * <p>
     * This is mostly a convenience method for
     * <code>new Slot(klass.getDeclaredField(name))</code>, except that this method
     * takes care of looking up the field in the superclass(es) of the given class
     * if needed.
     * 
     * @param klass The class for which we want to retrieve a slot.
     * @param name  The name of the field representing the slot (this is
     *              <em>not</em> the LinkML name of the slot).
     * @return The requested slot, or <code>null</code> if the class does not
     *         contain such a slot.
     * @throws LinkMLRuntimeException If the class has a field with the requested
     *                                name, but no corresponding accessors (which
     *                                should not happen if the class is a proper
     *                                LinkML Java object).
     */
    public static Slot getSlot(Class<?> klass, String name) throws LinkMLRuntimeException {
        Class<?> current = klass;
        do {
            try {
                Field f = current.getDeclaredField(name);
                return new Slot(f);
            } catch ( NoSuchFieldException e ) {
            }

            current = current.getSuperclass();
        } while ( current != null );

        return null;
    }

    /**
     * Gets all the slots on a given class.
     * 
     * @param klass The class for which to retrieve the slots.
     * @return The list of all slots on the class (including the slots that belong
     *         to the superclass(es), if any).
     */
    public static Collection<Slot> getSlots(Class<?> klass) {
        ArrayList<Slot> slots = new ArrayList<>();
        Class<?> current = klass;
        do {
            for ( Field f : current.getDeclaredFields() ) {
                try {
                    slots.add(new Slot(f));
                } catch ( LinkMLRuntimeException e ) {
                    // Assume this is not a LinkML field
                }
            }
            current = current.getSuperclass();
        } while ( current != null );

        return slots;
    }

    /*
     * Given a field name, obtain the name of the write accessor. We assume that
     * Java classes representing LinkML objects will be generated using Lombok, or
     * at least using the same logic as Lombok. That is, a field foo is expected to
     * yield an write accessor named setFoo, unless the field is a boolean and is
     * named isFoo, in which case the accessor is setFoo (not setIsFoo!).
     */
    private static String getWriteAccessorName(Field field) {
        String fieldName = field.getName();
        String noun = null;
        if ( field.getType() == Boolean.TYPE && fieldName.length() > 2 && fieldName.charAt(0) == 'i'
                && fieldName.charAt(1) == 's'
                && Character.isUpperCase(fieldName.charAt(2)) ) {
            noun = fieldName.substring(2);
        } else {
            noun = Character.toString(Character.toUpperCase(fieldName.charAt(0))) + fieldName.substring(1);
        }
        return "set" + noun;
    }

    /*
     * Given a field name, obtain the name of the read accessor. As above, we assume
     * the "Lombok logic" is used: a field foo is expected to have a read accessor
     * named getFoo, or isFoo if the field is a boolean (and if the field is already
     * named isFoo, then the accessor is also isFoo, not isIsFoo).
     */
    private static String getReadAccessorName(Field field) {
        String fieldName = field.getName();
        String verb = null;
        String noun = null;
        if ( field.getType() == Boolean.TYPE ) {
            verb = "is";
            if ( fieldName.length() > 2 && fieldName.charAt(0) == 'i' && fieldName.charAt(1) == 's'
                    && Character.isUpperCase(fieldName.charAt(2)) ) {
                noun = fieldName.substring(2);
            } else {
                noun = Character.toString(Character.toUpperCase(fieldName.charAt(0))) + fieldName.substring(1);
            }
        } else {
            verb = "get";
            noun = Character.toString(Character.toUpperCase(fieldName.charAt(0))) + fieldName.substring(1);
        }
        return verb + noun;
    }
}
