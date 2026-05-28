package org.incenp.linkml.core.sample.refinhslot;

/**
 * First derived class from Bar.
 * <p>
 * This class is used, instead of its parent <code>Bar</code> in
 * {@link FirstDerivedFoo}.
 */
public class FirstDerivedBar extends Bar {

    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int value) {
        length = value;
    }
}
