package org.incenp.linkml.core.playground;

import java.util.List;

/**
 * An example of a class that refines the range of its slots to make them accept
 * only a more specialised subclass.
 */
public class FirstDerivedFoo extends Foo {

    /*
     * Overridden read accessor for the `bar` slot.
     * 
     * We override it to ensure that it returns the more specialised subtype.
     */
    @Override
    public FirstDerivedBar getBar() {
        // This cast is perfectly safe because the write accessor below guarantees that
        // only a FirstDerivedBar object can be assigned to the slot.
        return (FirstDerivedBar) super.getBar();
    }

    /*
     * Overridden write accessor for the `bar` slot.
     * 
     * We override it to add a runtime check to enforce the more specialised type
     * constraint. We cannot prevent client code from trying to assign an object of
     * the wrong type, but if that happens we can at least immediately throw an
     * exception.
     */
    @Override
    public void setBar(Bar value) {
        if ( !(value instanceof FirstDerivedBar) ) {
            throw new IllegalArgumentException("Invalid bar value");
        }
        super.setBar(value);
    }

    /*
     * Overloaded write accessor for the `bar` slot.
     * 
     * This accessor is not strictly necessary, but it makes it clearer that in this
     * class, the value of the `bar` slot should be a `FirstDerivedBar`. It also
     * allows to bypass the dynamic check in the normal accessor above, if the
     * compiler already knows that the assigned value is a FirstDerivedBar.
     */
    public void setBar(FirstDerivedBar value) {
        super.setBar(value);
    }

    /*
     * Overridden “Standard” read accessor.
     * 
     * We override it to ensure it returns the more specialised subtype.
     * 
     * Because the slot could be (and instead is, in this example) refined further
     * in subclasses, we must still return a generic wildcard, so this accessor has
     * the same limitation as the one it overrides in the `Foo` class: modifying the
     * returned list requires an explicit cast into a non-wildcard form.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<? extends FirstDerivedBar> getBars() {
        // This cast should be safe IFF nobody explicitly modify the value returned by
        // this accessor after casting it into a `List<Bar>`.
        return (List<FirstDerivedBar>) super.getBars();
    }

    /*
     * Overridden read accessor with optional creation of the list.
     * 
     * We must override this accessor to ensure that the created list (if the list
     * needs to be created) is using the more specialised type.
     */
    @Override
    public List<? extends FirstDerivedBar> getBars(boolean create) {
        // We can delegate the logic to the parent
        return super.getBars(FirstDerivedBar.class, create);
    }

    /*
     * Overridden parameterised read accessor.
     * 
     * We must override this accessor to add a runtime check that the given type
     * parameter is compatible with the more specialised type.
     */
    @Override
    public <T extends Bar> List<T> getBars(Class<T> t) {
        if ( !FirstDerivedBar.class.isAssignableFrom(t) ) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        return super.getBars(t);
    }

    /*
     * Overridden parameterised read accessor with optional creation of the list.
     * 
     * Same as above: we must override this accessor to add a runtime check on the
     * type parameter.
     */
    @Override
    public <T extends Bar> List<T> getBars(Class<T> t, boolean create) {
        if ( !FirstDerivedBar.class.isAssignableFrom(t) ) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        return super.getBars(t, create);
    }

    /*
     * Overridden “standard” write accessor.
     * 
     * We must override this accessor to include a runtime check. The check must be
     * performed on all list items.
     */
    @Override
    public void setBars(List<? extends Bar> value) {
        for ( Bar b : value ) {
            if ( !(b instanceof FirstDerivedBar) ) {
                throw new IllegalArgumentException("Invalid bars value");
            }
        }
        super.setBars(value);
    }

}
