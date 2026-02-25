/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   (1) Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *
 *   (2) Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.  
 *
 *   (3)The name of the author may not be used to endorse or promote
 *   products derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.incenp.linkml.core.annotations.Converter;
import org.incenp.linkml.core.annotations.ExtensionHolder;
import org.incenp.linkml.core.annotations.Identifier;
import org.incenp.linkml.core.annotations.Inlined;
import org.incenp.linkml.core.annotations.LinkURI;
import org.incenp.linkml.core.annotations.Required;
import org.incenp.linkml.core.annotations.SlotName;
import org.incenp.linkml.core.annotations.TypeDesignator;

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
    private InliningMode inlining;

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
        SlotName propertyAnnotation = field.getAnnotation(SlotName.class);
        if ( propertyAnnotation != null ) {
            return propertyAnnotation.value();
        } else {
            return field.getName();
        }
    }

    /**
     * Indicates whether the slot holds an identifier for the class it belongs to.
     * 
     * @return <code>true</code> if the slot is an identifier slot, otherwise
     *         <code>false</code>.
     */
    public boolean isIdentifier() {
        Identifier annot = field.getAnnotation(Identifier.class);
        return annot != null && annot.isGlobal();
    }

    /**
     * Indicates whether the slot holds a key for the class it belongs to.
     * 
     * @return <code>true</code> if the slot is a key slot, otherwise
     *         <code>false</code>.
     */
    public boolean isKey() {
        Identifier annot = field.getAnnotation(Identifier.class);
        return annot != null && !annot.isGlobal();
    }

    /**
     * Indicates whether the slot acts as the designator for its class.
     * 
     * @return <code>true</code> is the slot is the class’ type designator,
     *         otherwise <code>false</code>.
     */
    public boolean isTypeDesignator() {
        return field.isAnnotationPresent(TypeDesignator.class);
    }

    /**
     * Gets the URI that identifies the slot.
     * <p>
     * The “Link URI” is especially intended to be used for import/export from/to
     * RDF, but it may have other uses as well.
     * <p>
     * Since we do not have access to the defining schema, we are entirely dependent
     * on the presence of a {@link LinkURI} annotation, which should have been
     * inserted by the code generator.
     * 
     * @return The link URI for the slot.
     */
    public String getLinkedURI() {
        LinkURI annot = field.getAnnotation(LinkURI.class);
        return annot != null ? annot.value() : null;
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
     * <p>
     * Note that this returns the <em>effectively expected</em> inlining mode, that
     * takes into account both (1) the presence of the {@link Inlined} annotation on
     * the field representing the slot, (2) whether the slot is single- or
     * multi-valued, and (3) the type of the slot.
     * <p>
     * For example, if a multi-valued slot has for type a class that has no
     * identifier slot of any kind, then the slot is necessarily expected to be
     * inlined as a list, regardless of whether it has been explicitly marked as
     * such.
     * 
     * @return The expected inlining mode for the slot.
     */
    public InliningMode getInliningMode() {
        if ( inlining != null ) {
            return inlining;
        }

        ClassInfo ci = ClassInfo.get(getInnerType());
        if ( ci == null ) {
            inlining = InliningMode.IRRELEVANT;
        } else if ( !ci.hasIdentifier() ) {
            // No identifier of any kind -- necessarily inlined
            inlining = isMultivalued() ? InliningMode.LIST : InliningMode.DICT;
        } else if ( !ci.isGloballyUnique() ) {
            // Locally unique object -- necessarily inlined
            if ( !isMultivalued() ) {
                // Can only be serialised as a dict
                inlining = InliningMode.DICT;
            } else {
                // Eligible for list and dict inlining
                Inlined annot = field.getAnnotation(Inlined.class);
                if ( annot == null ) {
                    // It's not very clear from the LinkML spec what the default should be in that
                    // case (if neither inlined nor inlined_as_list has been specified in the
                    // schema), but apparently the LinkML validator expects a dict.
                    inlining = InliningMode.DICT;
                } else {
                    inlining = annot.asList() ? InliningMode.LIST : InliningMode.DICT;
                }
            }
        } else {
            // Globally unique object -- eligible for all forms of serialisation
            Inlined annot = field.getAnnotation(Inlined.class);
            if ( annot == null ) {
                inlining = InliningMode.NO_INLINING;
            } else if ( isMultivalued() ) {
                inlining = annot.asList() ? InliningMode.LIST : InliningMode.DICT;
            } else {
                inlining = InliningMode.DICT;
            }
        }

        return inlining;
    }

    /**
     * Indicates whether the slot is required, recommended, or optional.
     * 
     * @return A {@link RequirementLevel} value for the slot.
     */
    public RequirementLevel getRequirementLevel() {
        if ( isIdentifier() || isKey() ) {
            return RequirementLevel.MANDATORY;
        }
        Required reqAnnotation = field.getAnnotation(Required.class);
        return reqAnnotation != null
                ? reqAnnotation.isRecommended() ? RequirementLevel.RECOMMENDED : RequirementLevel.MANDATORY
                : RequirementLevel.OPTIONAL;
    }

    /**
     * Gets the custom converter class to use to convert values intended for this
     * slot, if any.
     * <p>
     * A slot can indicate that it needs a custom converter by mean of a
     * {@link Converter} annotation.
     * 
     * @return The custom converter for the slot, or <code>null</code> if the code
     *         does not mandate a custom converter.
     */
    public Class<?> getCustomConverter() {
        Converter annot = field.getAnnotation(Converter.class);
        return annot != null ? annot.value() : null;
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
     * Gets the class in which the slot is declared.
     * <p>
     * If the slot has been inherited from a parent class, this will return that
     * parent class.
     * 
     * @return The highest level class in which the slot is originally declared.
     */
    public Class<?> getDeclaringClass() {
        return field.getDeclaringClass();
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
     * Finds the slot that holds the identifier (local or global) for a given LinkML
     * object.
     * <p>
     * The “identifier slot” is the slot that is tagged with
     * <code>identifier: true</code> or <code>key: true</code> in its definition (or
     * in the <code>slot_usage</code> block that overrides the global definition for
     * a given class). In LinkML Java objects, the identifier slot is tagged with a
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
