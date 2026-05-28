package org.incenp.linkml.core.sample.refinhslot;

/**
 * An example of a class that inherits from a class that refines its slots, but
 * that does no do any refinement itself.
 */
public class SecondDerivedFoo extends FirstDerivedFoo {

    /*
     * In this class, the `bar` and `bars` slot are of the same type as in the
     * parental `FirstDerivedFoo` class, so no overriding of accessors is necessary.
     */
}
