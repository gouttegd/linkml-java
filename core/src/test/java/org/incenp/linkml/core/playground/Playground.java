package org.incenp.linkml.core.playground;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.WildcardType;

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
    void testObtainingParameterBound() {
        Class<?> klass = FirstDerivedFoo.class;
        Method m = null;
        try {
            m = klass.getDeclaredMethod("getBars", (Class<?>[]) null);
        } catch ( NoSuchMethodException | SecurityException e ) {
            Assertions.fail("No getBars method");
        }

        ParameterizedType t = (ParameterizedType) m.getGenericReturnType();
        WildcardType t2 = (WildcardType) t.getActualTypeArguments()[0];
        Assertions.assertEquals(FirstDerivedBar.class, t2.getUpperBounds()[0]);
    }
}
