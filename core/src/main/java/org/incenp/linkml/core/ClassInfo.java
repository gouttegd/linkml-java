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
 *   (3) Neither the name of the copyright holder nor the names its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds informations about a LinkML class and its corresponding Java class.
 * <p>
 * This class is intended merely as a container of various informations about a
 * given LinkML class, as obtained at runtime from the Java class that
 * represents it.
 */
public class ClassInfo {

    private static final String CREATE_ERROR = "Cannot create global object '%s' of type '%s'";

    private static Map<Class<?>, ClassInfo> cache = new HashMap<>();

    private Class<?> type;
    private Map<String, Slot> slots = new HashMap<>();
    private Map<String, Slot> slotsByURI = new HashMap<>();
    private Slot identifierSlot;
    private Slot designatorSlot;
    private Slot extensionSlot;
    private Slot primarySlot;
    private boolean identifierIsLocal;
    private boolean primarySlotChecked;

    /**
     * Creates a new instance from the specified Java class.
     * <p>
     * This constructor is not intended for public use. Obtain a
     * <code>ClassInfo</code> from the {@link #get(Class)} static method instead.
     * 
     * @param klass The Java class that represents the LinkML class.
     */
    private ClassInfo(Class<?> klass) {
        type = klass;
        for ( Slot slot : Slot.getSlots(klass) ) {
            slots.put(slot.getLinkMLName(), slot);
            if ( slot.isIdentifier() ) {
                identifierSlot = slot;
                identifierIsLocal = identifierSlot.isLocalIdentifier();
            } else if ( slot.isExtensionStore() ) {
                extensionSlot = slot;
            } else if ( slot.isTypeDesignator() ) {
                designatorSlot = slot;
            }

            String uri = slot.getLinkedURI();
            if ( uri != null ) {
                slotsByURI.put(uri, slot);
            }
        }
    }

    /**
     * Indicates whether the class represents objects that are globally unique.
     * <p>
     * A LinkML class represents globally unique objects iff it has a <em>identifier
     * slot</em>, that is a slot marked with <code>identifier: true</code>.
     */
    public boolean isGloballyUnique() {
        return identifierSlot != null && !identifierIsLocal;
    }

    /**
     * Indicates whether the class represents objects that are locally unique.
     * <p>
     * A LinkML class represents locally unique objects iff it has a <em>key
     * slot</em>, that is a slot marked with <code>key: true</code>.
     */
    public boolean isLocallyUnique() {
        return identifierSlot != null && identifierIsLocal;
    }

    /**
     * Indicates whether the class has any kind of identifier slot, whether it is a
     * proper LinkML <em>identifier</em> slot (globally unique) or a <em>key
     * slot</em> (locally unique).
     */
    public boolean hasIdentifier() {
        return identifierSlot != null;
    }

    /**
     * Indicates whether the class is eligible for <em>SimpleDict</em> inlining.
     * <p>
     * When deserialising, the class is eligible for this type of inlining if it has
     * both an identifier slot (globally or locally unique, it does not matter) and
     * a primary value slot.
     * <p>
     * When serialising, the class is eligible for SimpleDict inlining only if, in
     * addition to the previous criterion, it has <em>no other slots</em> beyond the
     * identifier slot and the primary value slot.
     * 
     * @param write If <code>true</code>, check for eligibility for SimpleDict
     *              serialisation; otherwise, check for eligibility for SimpleDict
     *              deserialisation.
     * @return <code>true</code> if the class can be (de)serialised as a SimpleDict,
     *         otherwise <code>false</code>.
     */
    public boolean isEligibleForSimpleDict(boolean write) {
        if ( identifierSlot == null || getPrimarySlot() == null ) {
            return false;
        } else if ( write ) {
            return slots.size() == 2;
        } else {
            return true;
        }
    }

    /**
     * Indicates whether the class has a <em>type designator slot</em>, used to
     * unambiguously specify the runtime type of an object.
     */
    public boolean hasTypeDesignator() {
        return designatorSlot != null;
    }

    /**
     * Indicates whether the class has an additional, non-LinkML-defined slot
     * intended to store non-LinkML-defined attributes.
     */
    public boolean hasExtensionSlot() {
        return extensionSlot != null;
    }

    /**
     * Gets the Java type representing this class.
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Gets all the slots defined by the class.
     * <p>
     * Note that this includes any slot defined in any ancestor class.
     */
    public Collection<Slot> getSlots() {
        return slots.values();
    }

    /**
     * Gets a slot by its name.
     * 
     * @param name The name of the desired slot. Note that this is the original,
     *             LinkML-defined slot name, which may be different from the name of
     *             the Java field that represents it.
     * @return The corresponding slot, or <code>null</code> if the class has no slot
     *         with that name.
     */
    public Slot getSlot(String name) {
        return slots.get(name);
    }

    /**
     * Gets a slot by its associated URI.
     * <p>
     * The associated URI is the URI specified with the <code>slot_uri</code> slot
     * in the defining LinkML schema (or a URI automatically constructed from the
     * slot name and the schema’s default prefix).
     * 
     * @param uri The URI of the desired slot.
     * @return The corresponding slot, or <code>null</code> if the class either has
     *         no slot with that URI, or at least no slot <em>annotated</em> with
     *         the appropriate annotation (this is outside of the control of this
     *         runtime, and entirely dependent on the code generator).
     */
    public Slot getSlotByURI(String uri) {
        return slotsByURI.get(uri);
    }

    /**
     * Gets the slot that acts as the type designator for the class.
     * 
     * @return The type designator slot, or <code>null</code> if the class has no
     *         such slot.
     */
    public Slot getTypeDesignatorSlot() {
        return designatorSlot;
    }

    /**
     * Gets the slot intended to hold the identifier for objects of that class.
     * <p>
     * This can be either the <em>identifier slot</em> strictly speaking (for
     * globally unique identifiers) or the <em>key slot</em> (for locally unique
     * identifiers), depending on what the class has.
     * 
     * @return The identifier slot, or <code>null</code> if the class has no such
     *         slot.
     */
    public Slot getIdentifierSlot() {
        return identifierSlot;
    }

    /**
     * Gets the slot intended to hold the “primary value” of the object.
     * <p>
     * The “primary” slot of a LinkML class is the slot to which to assign the value
     * of a dict entry when using the “SimpleDict” inlining mode. As per the rules
     * set forth in LinkML’s documentation, the primary slot is either:
     * <ul>
     * <li>the one non-identifier slot, if the class has only one such slot beyond
     * the identifier slot;
     * <li>the one <em>mandatory</em> non-identifier slot, if the class has several
     * non-identifier slots but only one marked as mandatory.
     * </ul>
     * <p>
     * If the class has more than one non-identifier slot without any single one of
     * them being mandatory, or more than one mandatory non-identifier slot, then
     * the class has <em>no</em> primary slot (and is not eligible for inlining in
     * SimpleDict mode).
     * <p>
     * Of note, “identifier slot” here refers to either a proper identifier slot
     * (holding a globally unique identifier) or a key slot (holding a locally
     * unique identifier).
     * 
     * @return The primary value slot, or <code>null</code> if the class has no such
     *         slot.
     */
    public Slot getPrimarySlot() {
        if ( !primarySlotChecked ) {
            Slot mandatorySlot = null;
            Slot nonIdentifierSlot = null;
            int nMandatorySlots = 0;
            int nNonIdentifierSlots = 0;
            for ( Slot slot : slots.values() ) {
                if ( slot == identifierSlot || slot == extensionSlot ) {
                    continue;
                }
                if ( slot.getRequirementLevel() == RequirementLevel.MANDATORY ) {
                    mandatorySlot = slot;
                    nMandatorySlots += 1;
                }
                nonIdentifierSlot = slot;
                nNonIdentifierSlots += 1;
            }
            primarySlot = nNonIdentifierSlots == 1 ? nonIdentifierSlot : nMandatorySlots == 1 ? mandatorySlot : null;
            primarySlotChecked = true;
        }
        return primarySlot;
    }

    /**
     * Gets the slot intended to store non-LinkML-defined attributes.
     * 
     * @return The extension slot, or <code>null</code> if the class has no such
     *         slot.
     */
    public Slot getExtensionSlot() {
        return extensionSlot;
    }

    /**
     * Creates a new instance of the class.
     * <p>
     * This is a convenience method to wrap the exception check that catches
     * reflection errors.
     * <p>
     * Of note, all Java classes representing LinkML classes are expected to have a
     * no-argument constructor – which is the default behaviour of the Java code
     * generator in LinkML-Py. This is a general assumption throughout this
     * LinkML-Java runtime.
     * 
     * @return The newly created object.
     * @throws LinkMLRuntimeException If the object could not be created.
     */
    public Object newInstance() throws LinkMLRuntimeException {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new LinkMLInternalError(String.format(CREATE_ERROR, type.getName()), e);
        }
    }

    /**
     * Gets the <code>ClassInfo</code> object for the LinkML class represented by
     * the given Java type.
     * 
     * @param type The Java type representing the desired LinkML class.
     * @return The corresponding <code>ClassInfo</code> object, or <code>null</code>
     *         if the given type cannot represent a LinkML class (which can happen
     *         if it is a primitive type, a <em>boxed</em> primitive type, a String,
     *         or a enumeration).
     */
    public static ClassInfo get(Class<?> type) {
        // We keep a static cache of all ClassInfos. Informations about a Java class can
        // never change at runtime (unless someone is doing something very dubious with
        // reflection, and even I do not do things _that_ dubious), so there should not
        // be any issue here.
        ClassInfo ci = cache.get(type);
        if ( ci == null && isClass(type) ) {
            ci = new ClassInfo(type);
            cache.put(type, ci);
        }
        return ci;
    }

    /**
     * Helper method to check that the given Java type can represent a LinkML class.
     * 
     * @param type The Java type to check.
     * @return <code>true</code> if the type is possibly a LinkML class, otherwise
     *         <code>false</code>.
     */
    public static boolean isClass(Class<?> type) {
        // FIXME: At some point we will need a more sustainable way of recognising
        // classes that represent scalar types...
        if ( type.getSuperclass() == null || type.isEnum() || type == String.class || type == Boolean.class
                || type == Integer.class || type == Float.class || type == Double.class
                || type == ZonedDateTime.class || type == LocalDate.class || type == LocalTime.class ) {
            return false;
        }
        return true;
    }
}
