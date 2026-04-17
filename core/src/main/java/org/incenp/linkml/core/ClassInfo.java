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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.LinkURI;

/**
 * Holds informations about a LinkML class and its corresponding Java class.
 * <p>
 * This class is intended merely as a container of various informations about a
 * given LinkML class, as obtained at runtime from the Java class that
 * represents it.
 * <p>
 * Of note, to obtain most of the informations collected here, the LinkML-Java
 * runtime is dependent on the Java class having been properly annotated during
 * code generation.
 * <p>
 * Use the {@link #get(Class)} static method to obtain an instance of that
 * class. The instance object can then be used to inquire about most aspects of
 * the LinkML class it represents, such as the list of its slots, whether it has
 * an identifier slot, a type designator slot, etc.
 * <p>
 * For the methods that concern a slot, it does not matter whether the slot is
 * defined in the very LinkML class that this object represents, or in any of
 * its parent classes. For example, given the following schema:
 * 
 * <pre>
 * slots:
 *   id:
 *     designates_type: true
 * 
 * classes:
 *   Foo:
 *     slots:
 *       - id
 *   Bar:
 *     is_a: Foo
 * </pre>
 * <p>
 * a <code>ClassInfo</code> object for the <code>Bar</code> class would indicate
 * (through the {@link #hasTypeDesignator()} method) that the class has a type
 * designator – because indeed it does have one, even if it is defined in its
 * parent class <code>Foo</code>.
 */
public class ClassInfo {

    private static final String CREATE_ERROR = "Cannot create global object of type '%s'";

    private static Map<Class<?>, ClassInfo> cache = new HashMap<>();
    private static Map<String, ClassInfo> cacheByURI = new HashMap<>();

    private Class<?> type;
    private Map<String, Slot> slots = new HashMap<>();
    private Map<String, Slot> slotsByURI = new HashMap<>();
    private List<ClassInfo> parents = new ArrayList<>();
    private Slot identifierSlot;
    private Slot designatorSlot;
    private Slot extensionSlot;
    private Slot primarySlot;
    private boolean identifierIsLocal;
    private boolean primarySlotChecked;
    private String uri;

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

        LinkURI uriAnnot = klass.getAnnotation(LinkURI.class);
        if ( uriAnnot != null ) {
            uri = uriAnnot.value();
        }

        Class<?> parent = klass;
        do {
            parent = parent.getSuperclass();
            if ( parent != null ) {
                ClassInfo parentInfo = ClassInfo.get(parent);
                if ( parentInfo != null ) {
                    parents.add(parentInfo);
                } else {
                    parent = null;
                }
            }
        } while ( parent != null );
    }

    /**
     * Gets the LinkML URI for the class.
     * <p>
     * The URI of a LinkML class is used for two purposes:
     * <ul>
     * <li>to represent the class in RDF serialisations;
     * <li>to resolve URI-typed type designator values.
     * </ul>
     * 
     * @return The class’s URI, or <code>null</code> if we don't know its URI (the
     *         only way this could happen is if the class had not been properly
     *         annotated during code generation).
     */
    public String getURI() {
        return uri;
    }

    /**
     * Gets the name of the class. This may not necessarily be the <em>original</em>
     * name as defined in the LinkML schema – the name may have been transformed
     * into a “PascalCase” form (e.g. <code>mapping set</code> would be transformed
     * into <code>MappingSet</code>).
     * 
     * @return The class name.
     */
    public String getName() {
        return type.getSimpleName();
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
     * Indicates whether the class has any kind of identifier slot. An “identifier
     * slot” in the context of this method can be either a proper LinkML
     * <em>identifier</em> slot (a slot marked with <code>identifier: true</code>,
     * intended to hold a globally unique identifier) or a <em>key</em> slot (a slot
     * marked with <code>key: true</code>, intended to hold a locally unique
     * identifier).
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
     * Indicates whether the class has a <em>type designator slot</em>. Such a slot
     * is intended to hold a value that unambiguously specify the runtime type of an
     * object.
     */
    public boolean hasTypeDesignator() {
        return designatorSlot != null;
    }

    /**
     * Indicates whether the class has an additional, non-LinkML-defined slot
     * intended to store non-LinkML-defined attributes.
     * <p>
     * The “extension slot” is how the LinkML-Java runtime supports LinkML’s
     * <code>extra_slots.allowed</code> feature, by which a LinkML class is allowed
     * to contain “extra slots” that do not correspond to any slot or attribute
     * defined in the LinkML schema.
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
     * Gets all the slots carried by the class.
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
     *         the appropriate annotation.
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
     * Gets all the superclasses of this class.
     * 
     * @return The list of superclasses. The list is ordered from the closest parent
     *         (the immediate superclass) to the farthest one. It only includes
     *         classes that represent LinkML classes (i.e., it does not include
     *         {@link Object}). The list may be empty if the class has no
     *         superclass.
     */
    public List<ClassInfo> getParents() {
        return parents;
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
            return type.getConstructor(new Class<?>[] {}).newInstance();
        } catch ( InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | NoSuchMethodException | SecurityException e ) {
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
        if ( ci == null && ObjectType.get(type) == ObjectType.CLASS ) {
            ci = new ClassInfo(type);
            cache.put(type, ci);
            if ( ci.getURI() != null ) {
                cacheByURI.put(ci.getURI(), ci);
            }
        }
        return ci;
    }

    /**
     * Gets the <code>ClassInfo</code> object for the LinkML class identifier by the
     * given URI.
     * <p>
     * Importantly, the runtime currently has no way of pro-actively searching for a
     * class by its URI, so this method can only work if the <code>ClassInfo</code>
     * object for the desired class has already been queried before (at least once)
     * using {@link #get(Class)}.
     * 
     * @param uri The LinkML URI of the class to lookup.
     * @return The corresponding <code>ClassInfo</code> object, or <code>null</code>
     *         if the URI does not correspond to a known LinkML class.
     */
    public static ClassInfo get(String uri) {
        return cacheByURI.get(uri);
    }
}
