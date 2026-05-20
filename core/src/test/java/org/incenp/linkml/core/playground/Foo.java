package org.incenp.linkml.core.playground;

import java.util.ArrayList;
import java.util.List;

/**
 * An example of a class whose slots are “refined” in derived classes.
 */
public class Foo {

    private Bar bar;

    // We use a wildcard generic to allow derived classes to “refine” the parameter
    // type
    private List<? extends Bar> bars;

    /*
     * Accessors for the `bar` slot.
     * 
     * Nothing out of the ordinary here.
     */
    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar value) {
        bar = value;
    }

    /*
     * Accessors for the multi-valued `bars` slot.
     */

    /*
     * “Standard” read accessor. Its return type is parameterized with a wildcard
     * generic to allow derived classes to refine the parameter.
     * 
     * Modifying the returned list is only possible by explicitly casting it to a
     * non-wildcard form, as in:
     * 
     * ((List<Bar>) foo.getBars()).add(new Bar());
     * 
     * Without the cast, the following would be a compile-time error:
     * 
     * foo.getBars().add(new Bar());
     */
    public List<? extends Bar> getBars() {
        return bars;
    }

    /*
     * LinkML-Java “Standard” read accessor with optional creation of the list.
     * 
     * This is a convenience accessor, intended to allow client code to dispense
     * with a null-ness check.
     * 
     * As for the argument-less read accessor, the return type is a wildcard, so
     * modifying the returned list requires an explicit cast.
     */
    public List<? extends Bar> getBars(boolean create) {
        if ( bars == null && create ) {
            bars = new ArrayList<Bar>();
        }
        return bars;
    }

    /*
     * Parameterised read accessor.
     * 
     * This is another convenience accessor. This one is intended to allow client
     * code to dispense with an explicit cast to modify the list:
     * 
     * foo.getBars(Bar.class).add(new Bar());
     */
    @SuppressWarnings("unchecked")
    public <T extends Bar> List<T> getBars(Class<T> t) {
        return (List<T>) bars;
    }

    /*
     * Parameterised read accessor with optional creation of the list.
     * 
     * This is another convenience accessor, combining the effects of the two
     * accessors above.
     */
    @SuppressWarnings("unchecked")
    public <T extends Bar> List<T> getBars(Class<T> t, boolean create) {
        if ( bars == null && create ) {
            bars = new ArrayList<T>();
        }
        return (List<T>) bars;
    }

    /*
     * “Standard” write accessor.
     */
    public void setBars(List<? extends Bar> value) {
        bars = value;
    }
}
