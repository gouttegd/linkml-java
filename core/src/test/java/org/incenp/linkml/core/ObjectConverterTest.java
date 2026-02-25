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
 *   (3)The name of the author may not be used to endorse or promote
 *   products derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.incenp.linkml.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.incenp.linkml.core.sample.BaseSelfDesignatedClass;
import org.incenp.linkml.core.sample.ClassWithCustomConverter;
import org.incenp.linkml.core.sample.ContainerOfBooleanValues;
import org.incenp.linkml.core.sample.ContainerOfIRIIdentifiableObjects;
import org.incenp.linkml.core.sample.ContainerOfInlinedObjects;
import org.incenp.linkml.core.sample.ContainerOfIntegerValues;
import org.incenp.linkml.core.sample.ContainerOfReferences;
import org.incenp.linkml.core.sample.ContainerOfSimpleDicts;
import org.incenp.linkml.core.sample.ContainerOfSimpleObjects;
import org.incenp.linkml.core.sample.DerivedSelfDesignatedClass;
import org.incenp.linkml.core.sample.ExtensibleSimpleClass;
import org.incenp.linkml.core.sample.ExtraSimpleDict;
import org.incenp.linkml.core.sample.IRISimpleIdentifiableClass;
import org.incenp.linkml.core.sample.MultivaluedSimpleDict;
import org.incenp.linkml.core.sample.SampleEnum;
import org.incenp.linkml.core.sample.SimpleClass;
import org.incenp.linkml.core.sample.SimpleDict;
import org.incenp.linkml.core.sample.SimpleIdentifiableClass;
import org.incenp.linkml.core.sample.SimpleKeyableClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class ObjectConverterTest {

    private ConverterContext ctx = new ConverterContext();

    @Test
    void testConvertingSimpleClass() throws IOException {
        SimpleClass sc = parse("simple-class.yaml", SimpleClass.class);

        Assertions.assertEquals("a string", sc.getFoo());
        Assertions.assertEquals("https://example.org/a/URI", sc.getBar().toString());
        Assertions.assertTrue(sc.getBaz());
        Assertions.assertEquals("a string in a list", sc.getFoos().get(0));
        Assertions.assertEquals(SampleEnum.FOO, sc.getType());

        roundtrip(sc);
    }

    @Test
    void testConvertingExtensibleSimpleClass() throws IOException {
        ExtensibleSimpleClass esc = parse("simple-class.yaml", ExtensibleSimpleClass.class);

        Assertions.assertEquals("a string", esc.getFoo());
        Assertions.assertNotNull(esc.getExtensions());

        Object unknown1 = esc.getExtensions().get("unknown1");
        Assertions.assertNotNull(unknown1);
        Assertions.assertEquals("unknown string", unknown1);

        Object unknown2 = esc.getExtensions().get("unknown2");
        Assertions.assertNotNull(unknown2);
        Assertions.assertInstanceOf(Map.class, unknown2);

        @SuppressWarnings("unchecked")
        Map<String, Object> unknown2AsMap = Map.class.cast(unknown2);
        Assertions.assertEquals("unknown dict", unknown2AsMap.get("name"));

        roundtrip(esc);
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

        roundtrip(sic);
    }

    @Test
    void testConvertingContainerOfSimpleObjects() throws IOException {
        ContainerOfSimpleObjects obj = parse("container-of-simple-objects.yaml", ContainerOfSimpleObjects.class);

        Assertions.assertEquals("a string", obj.getSingle().getFoo());
        Assertions.assertEquals("https://example.org/a/URI", obj.getSingle().getBar().toString());
        Assertions.assertFalse(obj.getSingle().getBaz());

        Assertions.assertEquals(1, obj.getMultiple().size());
        SimpleClass sc = obj.getMultiple().get(0);
        Assertions.assertEquals("another string", sc.getFoo());
        Assertions.assertEquals("https://example.org/another/URI", sc.getBar().toString());
        Assertions.assertTrue(sc.getBaz());

        roundtrip(obj);
    }

    @Test
    void testConvertingContainerOfInlinedObjects() throws IOException, LinkMLRuntimeException {
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

        Assertions.assertEquals("a local string", obj.getLocalSingleInlined().getFoo());
        Assertions.assertEquals("skc1", obj.getLocalSingleInlined().getId());

        Assertions.assertEquals(1, obj.getLocalInlinedAsList().size());
        SimpleKeyableClass skc = obj.getLocalInlinedAsList().get(0);
        Assertions.assertEquals("another local string", skc.getFoo());
        Assertions.assertEquals("skc2", skc.getId());

        Assertions.assertEquals(1, obj.getLocalInlinedAsDict().size());
        skc = obj.getLocalInlinedAsDict().get(0);
        Assertions.assertEquals("yet another local string", skc.getFoo());
        Assertions.assertEquals("skc3", skc.getId());

        // Additionally check that no global objects have been created for the locally
        // identifiable objects
        Assertions.assertNull(ctx.getObject(SimpleKeyableClass.class, "skc1", false));
        Assertions.assertNull(ctx.getObject(SimpleKeyableClass.class, "skc2", false));
        Assertions.assertNull(ctx.getObject(SimpleKeyableClass.class, "skc3", false));

        roundtrip(obj);
    }

    @Test
    void testNormalisingLists() throws IOException {
        ContainerOfSimpleDicts cos = parse("container-of-normalisable-inlined-objects.yaml",
                ContainerOfSimpleDicts.class);

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

        roundtrip(cor);
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

        roundtrip(cor);
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

        roundtrip(cos);
    }

    @Test
    void testCompactDictInlining() throws LinkMLRuntimeException {
        ContainerOfInlinedObjects coi = new ContainerOfInlinedObjects();
        coi.setInlinedAsDict(new ArrayList<>());
        SimpleIdentifiableClass sic1 = new SimpleIdentifiableClass();
        sic1.setFoo("a string");
        sic1.setId("sic1");
        coi.getInlinedAsDict().add(sic1);

        ObjectConverter conv = (ObjectConverter) ctx.getConverter(ContainerOfInlinedObjects.class);
        Map<String, Object> raw = conv.serialise(coi, false, ctx);
        @SuppressWarnings("unchecked")
        Map<String, Object> inlinedAsDict = (Map<String, Object>) raw.get("inlinedAsDict");
        Assertions.assertFalse(inlinedAsDict.containsKey("id"));
        Assertions.assertEquals(coi, conv.convert(raw, ctx));
    }

    @Test
    void testBooleanValues() throws IOException {
        ContainerOfBooleanValues cobv = parse("container-of-boolean-values.yaml", ContainerOfBooleanValues.class);

        Assertions.assertTrue(cobv.getFoo());
        Assertions.assertFalse(cobv.getBar());
        Assertions.assertNull(cobv.getBaz());
        Assertions.assertTrue(cobv.isPrimitiveFoo());
        Assertions.assertFalse(cobv.isPrimitiveBar());
        Assertions.assertFalse(cobv.isPrimitiveBaz());

        roundtrip(cobv);
    }

    @Test
    void testIntegerValues() throws IOException {
        ContainerOfIntegerValues coiv = parse("container-of-integer-values.yaml", ContainerOfIntegerValues.class);

        Assertions.assertTrue(coiv.getFoo() == 123);
        Assertions.assertNotNull(coiv.getBar());
        Assertions.assertTrue(coiv.getBar() == 456);
        Assertions.assertTrue(coiv.getBaz().get(0) == 321);
        Assertions.assertTrue(coiv.getBaz().get(1) == 654);

        roundtrip(coiv);
    }

    @Test
    void testCustomConverters() throws IOException, LinkMLRuntimeException {
        ctx.addPrefix("FBbi", "http://purl.obolibrary.org/obo/FBbi_");
        ClassWithCustomConverter cwcc = parse("custom-converters.yaml", ClassWithCustomConverter.class);

        Assertions.assertEquals("http://purl.obolibrary.org/obo/FBbi_00000123", cwcc.getUri());
        Assertions.assertEquals("http://purl.obolibrary.org/obo/FBbi_00000456", cwcc.getUris().get(0));
        Assertions.assertEquals("http://purl.obolibrary.org/obo/FBbi_00000789", cwcc.getUris().get(1));

        roundtrip(cwcc);

        // Check that IRIs are compacted back
        ObjectConverter conv = (ObjectConverter) ctx.getConverter(ClassWithCustomConverter.class);
        Map<String, Object> raw = conv.serialise(cwcc, false, ctx);
        Assertions.assertEquals("FBbi:00000123", raw.get("uri"));
    }

    @Test
    void testTypeDesignator() throws IOException, LinkMLRuntimeException {
        String text = "foo: A string\nbar: Another string\ntype: DerivedSelfDesignatedClass\n";
        BaseSelfDesignatedClass bsdc = parseString(text, BaseSelfDesignatedClass.class);

        Assertions.assertInstanceOf(DerivedSelfDesignatedClass.class, bsdc);
        DerivedSelfDesignatedClass dsdc = (DerivedSelfDesignatedClass) bsdc;
        Assertions.assertEquals("A string", dsdc.getFoo());
        Assertions.assertEquals("Another string", dsdc.getBar());
        Assertions.assertEquals("DerivedSelfDesignatedClass", dsdc.getType());

        roundtrip(bsdc);

        // Try serialising again, but this time with the type designator slot unset; the
        // type designator should still appear in the serialised object
        bsdc.setType(null);
        ObjectConverter conv = (ObjectConverter) ctx.getConverter(bsdc.getClass());
        Map<String, Object> raw = conv.serialise(bsdc, true, ctx);
        Assertions.assertTrue(raw.containsKey("type"));
        Assertions.assertEquals("DerivedSelfDesignatedClass", raw.get("type"));
    }

    @Test
    void testReferenceToIRIIdentifiers() throws IOException, LinkMLRuntimeException {
        ctx.addPrefix("PFX", "https://example.org/");
        String text = "id: PFX:0001\nfoo: A string\n";
        IRISimpleIdentifiableClass isic = parseString(text, IRISimpleIdentifiableClass.class);

        Assertions.assertEquals("A string", isic.getFoo());
        Assertions.assertEquals("https://example.org/0001", isic.getId());

        text = "singleReference: PFX:0001";
        ContainerOfIRIIdentifiableObjects coii = parseString(text, ContainerOfIRIIdentifiableObjects.class);
        Assertions.assertTrue(isic == coii.getSingleReference());

        // Test that the identifiers are shortened back upon serialisation
        ObjectConverter conv = (ObjectConverter) ctx.getConverter(ContainerOfIRIIdentifiableObjects.class);
        Map<String, Object> raw = conv.serialise(coii, false, ctx);
        Assertions.assertEquals("PFX:0001", raw.get("singleReference"));
        conv = (ObjectConverter) ctx.getConverter(IRISimpleIdentifiableClass.class);
        raw = conv.serialise(isic, true, ctx);
        Assertions.assertEquals("PFX:0001", raw.get("id"));

        // Now test with forward references
        ctx = new ConverterContext();
        ctx.addPrefix("PFX", "https://example.org/");
        coii = parseString(text, ContainerOfIRIIdentifiableObjects.class);
        isic = parseString("id: PFX:0001\nfoo: A string", IRISimpleIdentifiableClass.class);
        Assertions.assertNull(coii.getSingleReference());
        ctx.finalizeAssignments();
        Assertions.assertEquals("https://example.org/0001", coii.getSingleReference().getId());
        Assertions.assertTrue(isic == coii.getSingleReference());

        // Now with multi-valued slots
        ctx = new ConverterContext();
        ctx.addPrefix("PFX", "https://example.org/");
        isic = parseString("id: PFX:0003\nfoo: A string", IRISimpleIdentifiableClass.class);
        coii = parseString("multipleReferences:\n  - PFX:0003\n", ContainerOfIRIIdentifiableObjects.class);
        Assertions.assertEquals("https://example.org/0003", isic.getId());
        Assertions.assertTrue(isic == coii.getMultipleReferences().get(0));

        // Multi-valued slots with forward references
        ctx = new ConverterContext();
        ctx.addPrefix("PFX", "https://example.org");
        coii = parseString("multipleReferences:\n  - PFX:0004\n", ContainerOfIRIIdentifiableObjects.class);
        isic = parseString("id: PFX:0004\nfoo: A string", IRISimpleIdentifiableClass.class);
        Assertions.assertTrue(coii.getMultipleReferences().isEmpty());
        ctx.finalizeAssignments();
        Assertions.assertTrue(isic == coii.getMultipleReferences().get(0));
    }

    @Test
    void testInliningWithIRIIdentifiers() throws IOException {
        ctx.addPrefix("PFX", "https://example.org/");
        ContainerOfIRIIdentifiableObjects coii = parseString("multipleInlinedAsList:\n  - id: PFX:0001\n",
                ContainerOfIRIIdentifiableObjects.class);
        Assertions.assertEquals("https://example.org/0001", coii.getMultipleInlinedAsList().get(0).getId());

        coii = parseString("multipleInlinedAsDict:\n  PFX:0002:\n    foo: A string\n",
                ContainerOfIRIIdentifiableObjects.class);
        Assertions.assertEquals("https://example.org/0002", coii.getMultipleInlinedAsDict().get(0).getId());
        roundtrip(coii);
    }

    @Test
    void testSimpleDictEligibility() {
        ObjectConverter conv = new ObjectConverter(SimpleDict.class);
        Assertions.assertTrue(conv.isEligibleForSimpleDict(false));
        Assertions.assertTrue(conv.isEligibleForSimpleDict(true));

        conv = new ObjectConverter(ExtraSimpleDict.class);
        Assertions.assertTrue(conv.isEligibleForSimpleDict(false));
        Assertions.assertFalse(conv.isEligibleForSimpleDict(true));
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

    private <T> T parseString(String text, Class<T> target) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Object raw = mapper.readValue(text, Map.class);

        try {
            Object cooked = ctx.getConverter(target).convert(raw, ctx);
            Assertions.assertTrue(target.isInstance(cooked));
            return target.cast(cooked);
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("Unexpected exception", e);
        }
        return null;
    }

    private <T> void roundtrip(T obj) {
        try {
            IConverter conv = ctx.getConverter(obj.getClass());
            Object raw = conv.serialise(obj, ctx);
            Object recooked = conv.convert(raw, ctx);
            Assertions.assertEquals(obj, recooked);
        } catch ( LinkMLRuntimeException e ) {
            Assertions.fail("unexpected exception", e);
        }
    }
}
