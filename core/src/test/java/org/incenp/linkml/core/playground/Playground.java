package org.incenp.linkml.core.playground;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Playground {

    private ConverterContext ctx = new ConverterContext();

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

    @Test
    void testParsing() throws IOException, LinkMLRuntimeException {
        Foo f = parse("refined-inherited-slots.yaml", Foo.class);
        Assertions.assertInstanceOf(Bar.class, f.getBar());
        Assertions.assertEquals("the bar", f.getBar().getName());
        Assertions.assertInstanceOf(Bar.class, f.getBars().get(0));
        Assertions.assertEquals("the first bar", f.getBars().get(0).getName());

        FirstDerivedFoo fdf = parse("refined-inherited-slots.yaml", FirstDerivedFoo.class);
        Assertions.assertInstanceOf(FirstDerivedBar.class, fdf.getBar());
        Assertions.assertEquals("the bar", fdf.getBar().getName());
        Assertions.assertEquals(1, fdf.getBar().getLength());
        Assertions.assertInstanceOf(FirstDerivedBar.class, fdf.getBars().get(0));
        Assertions.assertEquals("the first bar", fdf.getBars().get(0).getName());
        Assertions.assertEquals(2, fdf.getBars().get(0).getLength());

        SecondDerivedFoo sdf = parse("refined-inherited-slots.yaml", SecondDerivedFoo.class);
        Assertions.assertInstanceOf(FirstDerivedBar.class, sdf.getBar());
        Assertions.assertEquals("the bar", sdf.getBar().getName());
        Assertions.assertEquals(1, sdf.getBar().getLength());
        Assertions.assertInstanceOf(FirstDerivedBar.class, sdf.getBars().get(0));
        Assertions.assertEquals("the first bar", sdf.getBars().get(0).getName());
        Assertions.assertEquals(2, sdf.getBars().get(0).getLength());

        ThirdDerivedFoo tdf = parse("refined-inherited-slots.yaml", ThirdDerivedFoo.class);
        Assertions.assertInstanceOf(SecondDerivedBar.class, tdf.getBar());
        Assertions.assertEquals("the bar", tdf.getBar().getName());
        Assertions.assertEquals(1, tdf.getBar().getLength());
        Assertions.assertInstanceOf(SecondDerivedBar.class, tdf.getBars().get(0));
        Assertions.assertEquals("the first bar", tdf.getBars().get(0).getName());
        Assertions.assertEquals(2, tdf.getBars().get(0).getLength());
    }

    private <T> T parse(String file, Class<T> target) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        FileInputStream stream = new FileInputStream(new File("src/test/resources/core/samples/", file));
        Object raw = mapper.readValue(stream, Map.class);

        try {
            Object cooked = ctx.getConverter(target).convert(raw, ctx);
            Assertions.assertTrue(target.isInstance(cooked));
            return target.cast(cooked);
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }
        return null;
    }
}
