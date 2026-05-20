package org.incenp.linkml.core.playground;

import java.util.List;

/**
 * An example of a class that refines the range of its slots, that it inherited
 * from a class that already refined them.
 * 
 * Importantly, this class has no derived class, so we know its slots cannot be
 * further refined by another class.
 */
public class ThirdDerivedFoo extends SecondDerivedFoo {

    /*
     * Overridden read accessor for the `bar` slot.
     * 
     * We override it to ensure that it returns the more specialised subtype.
     */
    @Override
    public SecondDerivedBar getBar() {
        return (SecondDerivedBar) super.getBar();
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
        if ( !(value instanceof SecondDerivedBar) ) {
            throw new IllegalArgumentException("Invalid bar value");
        }
        super.setBar(value);
    }

    /*
     * Second overridden write accessor for the `bar` slot.
     * 
     * Since `FirstDerivedFoo` defined this accessor, we must override it as well,
     * otherwise it would allow client code to assign a FirstDerivedBar to the slot.
     */
    @Override
    public void setBar(FirstDerivedBar value) {
        if ( !(value instanceof SecondDerivedBar) ) {
            throw new IllegalArgumentException("Invalid bar value");
        }
        super.setBar(value);
    }

    /*
     * Overloaded write accessor for the `bar` slot.
     * 
     * This accessor is not strictly necessary, but it makes it clearer that in this
     * class, the value of the `bar` slot should be a `SecondDerivedBar`. It also
     * allows to bypass the dynamic check in the normal accessor above, if the
     * compiler already knows that the assigned value is a FirstDerivedBar.
     */
    public void setBar(SecondDerivedBar value) {
        super.setBar(value);
    }

    /*
     * Overridden “standard” read accessor.
     * 
     * We override it to ensure it returns the more specialised subtype.
     * 
     * Here, since we know the slot cannot be further refined (no subclass), we can
     * dispense with a wildcard generic.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<SecondDerivedBar> getBars() {
        return (List<SecondDerivedBar>) super.getBars();
    }

    /*
     * Overriden read accessor with optional creation of the list.
     * 
     * We must override this accessor to ensure that the created list (if the list
     * needs to be created) is using the more specialised type.
     */
    @Override
    public List<SecondDerivedBar> getBars(boolean create) {
        // We can delegate the logic to the parent
        return super.getBars(SecondDerivedBar.class, create);
    }

    /*
     * Overidden parameterised read accessor.
     * 
     * In this class, we don’t need this accessor to get a modifiable list (we can
     * use `getBars()` directly), but we must still override the accessor we inherit
     * from the parent, otherwise this would allow client code to get a
     * `List<FirstDerivedBar>`-typed value.
     */
    @Override
    public <T extends Bar> List<T> getBars(Class<T> t) {
        if ( !SecondDerivedBar.class.isAssignableFrom(t) ) {
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
        if ( !SecondDerivedBar.class.isAssignableFrom(t) ) {
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
            if ( !(b instanceof SecondDerivedBar) ) {
                throw new IllegalArgumentException("Invalid bar value");
            }
        }
        // FIXME: the parent method will in turn perform a (no longer needed) runtime
        // check...
        super.setBars(value);
    }
}
