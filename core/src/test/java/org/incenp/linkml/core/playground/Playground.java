package org.incenp.linkml.core.playground;

import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Playground {

    @Test
    void testFoo() {
        Foo f = new Foo();
        f.getBars(Bar.class, true).add(new Bar());
        // Can add a derived Bar
        f.getBars(Bar.class).add(new FirstDerivedBar());
    }

    @Test
    void testFirstDerivedFoo() {
        FirstDerivedFoo fdf = new FirstDerivedFoo();
        try {
            fdf.getBars(Bar.class, true).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }

        fdf.getBars(FirstDerivedBar.class, true).add(new FirstDerivedBar());
        // Can access bars items as FirstDerivedBar objects in read mode
        fdf.getBars().get(0).setLength(9);

        Foo f = fdf;
        try {
            // Even when accessed from a Foo object, cannot assign a Bar
            f.getBars(Bar.class).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }
    }

    @Test
    void testSecondDerivedFoo() {
        SecondDerivedFoo sdf = new SecondDerivedFoo();
        try {
            sdf.getBars(Bar.class, true).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }

        sdf.getBars(FirstDerivedBar.class, true).add(new FirstDerivedBar());
        // Can access bars items as FirstDerivedBar objects in read mode
        sdf.getBars().get(0).setLength(9);

        FirstDerivedFoo fdf = sdf;
        try {
            // Even when accessed from a FirstDerivedFoo object, cannot assign a Bar
            fdf.getBars(Bar.class).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }

        Foo f = sdf;
        try {
            // Even when accessed from a Foo object, cannot assign a Bar
            f.getBars(Bar.class).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }
    }

    @Test
    void testThirdDerivedFoo() {
        ThirdDerivedFoo tdf = new ThirdDerivedFoo();
        try {
            tdf.getBars(FirstDerivedBar.class, true).add(new FirstDerivedBar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }

        tdf.getBars(SecondDerivedBar.class, true).add(new SecondDerivedBar());
        // Can access bars items as FirstDerivedBar objects in read mode
        tdf.getBars().get(0).setLength(9);

        Foo f = tdf;
        try {
            // Even when accessed from a Foo object, cannot assign a Bar
            f.getBars(Bar.class).add(new Bar());
            Assertions.fail("Wrong assignment not caught");
        } catch ( IllegalArgumentException iae ) {
            Assertions.assertEquals("Invalid type parameter", iae.getMessage());
        }
    }

    @Test
    void testGetRefinedSingleValuedType() throws LinkMLRuntimeException {
        Slot barSlot = Slot.getSlot(Foo.class, "bar");
        Assertions.assertEquals(Bar.class, barSlot.getInnerType());
        Assertions.assertNull(barSlot.getRefiningClass());

        barSlot = Slot.getSlot(FirstDerivedFoo.class, "bar");
        Assertions.assertEquals(FirstDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(FirstDerivedFoo.class, barSlot.getRefiningClass());

        barSlot = Slot.getSlot(SecondDerivedFoo.class, "bar");
        Assertions.assertEquals(FirstDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(FirstDerivedFoo.class, barSlot.getRefiningClass());

        barSlot = Slot.getSlot(ThirdDerivedFoo.class, "bar");
        Assertions.assertEquals(SecondDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(ThirdDerivedFoo.class, barSlot.getRefiningClass());
    }

    @Test
    void testGetRefinedMultiValuedType() throws LinkMLRuntimeException {
        Slot barSlot = Slot.getSlot(Foo.class, "bars");
        Assertions.assertEquals(Bar.class, barSlot.getInnerType());
        Assertions.assertNull(barSlot.getRefiningClass());

        barSlot = Slot.getSlot(FirstDerivedFoo.class, "bars");
        Assertions.assertEquals(FirstDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(FirstDerivedFoo.class, barSlot.getRefiningClass());

        barSlot = Slot.getSlot(SecondDerivedFoo.class, "bars");
        Assertions.assertEquals(FirstDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(FirstDerivedFoo.class, barSlot.getRefiningClass());

        barSlot = Slot.getSlot(ThirdDerivedFoo.class, "bars");
        Assertions.assertEquals(SecondDerivedBar.class, barSlot.getInnerType());
        Assertions.assertEquals(ThirdDerivedFoo.class, barSlot.getRefiningClass());
    }
}
