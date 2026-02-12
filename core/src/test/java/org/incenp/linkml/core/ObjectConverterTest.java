/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.incenp.linkml.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.incenp.linkml.core.sample.ContainerOfInlinedObjects;
import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.ContainerOfSimpleDicts;
import org.incenp.linkml.core.sample.ContainerOfSimpleObjects;
import org.incenp.linkml.core.sample.ExtraSimpleDict;
import org.incenp.linkml.core.sample.MultivaluedSimpleDict;
import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.core.sample.SimpleDict;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ObjectConverterTest {

    private ConverterContext ctx;

    ObjectConverterTest() {
        ctx = new ConverterContext();
        ctx.addConverter(new StringConverter());
        ctx.addConverter(new URIConverter());
        ctx.addConverter(new BooleanConverter());
        ctx.addConverter(SimpleClass.class);
        ctx.addConverter(SimpleIdentifiableClass.class);
        ctx.addConverter(SimpleDict.class);
        ctx.addConverter(ExtraSimpleDict.class);
        ctx.addConverter(MultivaluedSimpleDict.class);
        ctx.addConverter(ContainerOfSimpleObjects.class);
        ctx.addConverter(ContainerOfReferences.class);
        ctx.addConverter(ContainerOfInlinedObjects.class);
        ctx.addConverter(ContainerOfSimpleDicts.class);
    }

    @Test
    void testConvertingSimpleClass() throws IOException {
        SimpleClass sc = parse("simple-class.yaml", SimpleClass.class);

        Assertions.assertEquals("a string", sc.getFoo());
        Assertions.assertEquals("https://example.org/a/URI", sc.getBar().toString());
        Assertions.assertTrue(sc.isBaz());
        Assertions.assertEquals("a string in a list", sc.getFoos().get(0));
    }

    @Test
    void testConvertingIdentifiableClass() throws IOException {
        SimpleIdentifiableClass sic = parse("simple-identifiable-class.yaml", SimpleIdentifiableClass.class);

        Assertions.assertEquals("sic1", sic.getId());
        Assertions.assertEquals("a string", sic.getFoo());

        try {
            SimpleIdentifiableClass cached = ctx.getObject(SimpleIdentifiableClass.class, "sic1", false);
            Assertions.assertTrue(sic == cached);
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }
    }

    @Test
    void testConvertingContainerOfSimpleObjects() throws IOException {
        ContainerOfSimpleObjects obj = parse("container-of-simple-objects.yaml", ContainerOfSimpleObjects.class);

        Assertions.assertEquals("a string", obj.getSingle().getFoo());
        Assertions.assertEquals("https://example.org/a/URI", obj.getSingle().getBar().toString());
        Assertions.assertFalse(obj.getSingle().isBaz());

        Assertions.assertEquals(1, obj.getMultiple().size());
        SimpleClass sc = obj.getMultiple().get(0);
        Assertions.assertEquals("another string", sc.getFoo());
        Assertions.assertEquals("https://example.org/another/URI", sc.getBar().toString());
        Assertions.assertTrue(sc.isBaz());
    }

    @Test
    void testConvertingContainerOfInlinedObjects() throws IOException {
        ContainerOfInlinedObjects obj = parse("container-of-inlined-objects.yaml", ContainerOfInlinedObjects.class);

        Assertions.assertEquals("a string", obj.getSingleInlined().getFoo());
        Assertions.assertEquals("sic1", obj.getSingleInlined().getId());

        Assertions.assertEquals(1, obj.getInlinedAsList().size());
        SimpleIdentifiableClass sic = obj.getInlinedAsList().get(0);
        Assertions.assertEquals("another string", sic.getFoo());
        Assertions.assertEquals("sic2", sic.getId());

        Assertions.assertEquals(1, obj.getInlinedAsDict().size());
        sic = obj.getInlinedAsDict().get(0);
        Assertions.assertEquals("yet another string", sic.getFoo());
        Assertions.assertEquals("sic3", sic.getId());
    }

    @Test
    void testConvertingContainerOfReferences() throws IOException {
        // First read the inlined objects
        ContainerOfInlinedObjects coi = parse("container-of-inlined-objects.yaml", ContainerOfInlinedObjects.class);

        // Then read the container of references
        ContainerOfReferences cor = parse("container-of-references.yaml", ContainerOfReferences.class);

        // All references should have been automatically resolved
        Assertions.assertTrue(cor.getSingle() == coi.getSingleInlined());
        Assertions.assertEquals("a string", cor.getSingle().getFoo());
        Assertions.assertTrue(cor.getMultiple().get(0) == coi.getInlinedAsList().get(0));
        Assertions.assertEquals("another string", cor.getMultiple().get(0).getFoo());
    }

    @Test
    void testConvertingContainerOfForwardReferences() throws IOException {
        // As above, but this time we read the references first
        ContainerOfReferences cor = parse("container-of-references.yaml", ContainerOfReferences.class);
        ContainerOfInlinedObjects coi = parse("container-of-inlined-objects.yaml", ContainerOfInlinedObjects.class);

        // Without finalization, references should still be dangling
        Assertions.assertNull(cor.getSingle());
        Assertions.assertTrue(cor.getMultiple().isEmpty());

        // Now resolve the forward references
        try {
            ctx.finalizeAssignments();
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }

        Assertions.assertTrue(cor.getSingle() == coi.getSingleInlined());
        Assertions.assertEquals("a string", cor.getSingle().getFoo());
        Assertions.assertTrue(cor.getMultiple().get(0) == coi.getInlinedAsList().get(0));
        Assertions.assertEquals("another string", cor.getMultiple().get(0).getFoo());
    }

    @Test
    void testConvertingContainerOfSimpleDicts() throws IOException {
        ContainerOfSimpleDicts cos = parse("container-of-simple-dicts.yaml", ContainerOfSimpleDicts.class);

        SimpleDict sd = cos.getSimpleDict().get(0);
        Assertions.assertNotNull(sd);
        Assertions.assertEquals("key1", sd.getKey());
        Assertions.assertEquals("value1", sd.getValue());

        ExtraSimpleDict esd = cos.getExtraSimpleDict().get(0);
        Assertions.assertNotNull(esd);
        Assertions.assertEquals("key2", esd.getKey());
        Assertions.assertEquals("value2", esd.getValue());
        Assertions.assertNull(esd.getExtra());

        Assertions.assertNotNull(cos.getMultivaluedSimpleDict());
        Assertions.assertEquals(1, cos.getMultivaluedSimpleDict().size());
        MultivaluedSimpleDict msd = cos.getMultivaluedSimpleDict().get(0);
        Assertions.assertEquals("key3", msd.getKey());
        Assertions.assertEquals("value3", msd.getValues().get(0));
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
