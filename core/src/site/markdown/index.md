LinkML-Java core runtime library
================================

This is the LinkML-Java (core) runtime library. It aims to provide a set
of Java classes to facilitate working with LinkML-defined instance data
– that is, data that is conforming to a model defined as a LinkML
schema.

The library supposes that the LinkML schema you want to work with is
**known at compile-time**. Working with schemas that are only discovered
at runtime is out of scope for this library.

Furthermore, the library supposes that Java classes representing the
model you want to work with have already been generated, typically with
LinkML-Py’s Java code generator (`linkml generate java`). Java code
generation directly from within the LinkML-Java runtime is out of scope
(at least for the “core” library–at some point, it may in scope for the
[“extended” library](../linkml-ext/index.html)). See the page about
[code generation](codegen.html) for more details.

Library identifiers and namespace
---------------------------------
To use the library in a Maven or Gradle project, use the following
identifiers:

* groupId: `org.incenp`
* artifactId: `linkml-core`

All classes are under the `org.incenp.linkml.core` namespace. The
`org.incenp.linkml.core.annotations` namespace contains the annotations
that are needed in the generated code, as explained in the
[corresponding section](codegen.html#Java_annotations) in the page about
code generation.


Supported features
------------------
The main aim of the core runtime library, at least for now, is to
provide facilities for loading and dumping LinkML instance data from and
to YAML or JSON files.

Assuming you have a schema that defines a _Foo_ class, you can for
example load an instance of that class from a YAML file as follows:

```java
import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core LinkMLRuntimeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

ConverterContext ctx = new ConverterContext();
ObjectMaper mapper = new ObjectMapper(new YAMLFactory());
try {
    Map<String, Object> raw = mapper.readValue(new File("foo.yaml"), Map.class);
    Foo foo = ctx.getConverter(Foo.class).convert(raw, ctx);
    ctx.finalizeAssignments();
} catch ( IOException e ) {
    // Non-LinkML error, including I/O error or YAML error
} catch ( LinkMLRuntimeException e ) {
    // LinkML-specific error (content of foo.yaml does not match
    // the definition of the Foo class)
}
```

As seen in that example, the general principle is that the YAML file is
first parsed into a generic `Map` object (using any YAML parser
available, such as the one from the Jackson libraries in that example),
which is then passed to the LinkML-Java runtime to be “converted” into
an instance of the actual LinkML-defined class.

This conversion step is necessary for all but the most simple schemas,
because it takes care of handling LinkML-specific features such as:

* [type designator slots](https://linkml.io/linkml/schemas/type-designators.html),
  by which the runtime type of an object is indicated by the value of a
  particular slot of its defining class;
  
* the automatic expansion (upon reading) and contraction (upon writing)
  of values typed as `linkml:uriOrCurie`;
  
* dereferencing global objects;

* the various forms of [inlining](https://linkml.io/linkml/schemas/inlining.html);

that a generic, non-LinkML-aware deserialisation library (such as
Jackson) cannot know about.
