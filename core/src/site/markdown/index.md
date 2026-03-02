LinkML-Java core runtime library
================================

This is the LinkML-Java (core) runtime library. It aims to provide a set
of Java classes to facilitate working with LinkML-defined instance data
– that is, data that is conforming to a model defined as a LinkML
schema.

The library supposes that the LinkML schema you want to work with is
**known at compile-time**. Working with schemas that are only discovered
at runtime is (currently) out of scope for this library.

Furthermore, the library supposes that Java classes representing the
model you want to work with have already been generated, typically with
LinkML-Py’s Java code generator (`linkml generate java`). Java code
generation directly from within the LinkML-Java runtime is (again,
currently) out of scope. See the page about [code generation](codegen.html)
for more details.

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
In the initial release, the LinkML-Java runtime supports reading and
writing LinkML instance from and to YAML or JSON files.

Assuming you have a schema that defines a _Foo_ class, you can for
example load an instance of that class from a YAML file as follows:

```java
import org.incenp.linkml.core.YAMLLoader;
import org.incenp.linkml.core LinkMLRuntimeException;

YAMLLoader loader = new YAMLLoader();
try {
    Foo foo = loader.loadObject(new File("foo.yaml"), Foo.class);
} catch ( IOException e ) {
    // Non-LinkML error, including I/O error or YAML error
} catch ( LinkMLRuntimeException e ) {
    // LinkML-specific error (content of foo.yaml does not match
    // the definition of the Foo class)
}
```

> Why not use an existing JSON/YAML library like Jackson, instead of a
> LinkML-specific runtime?
>
> ```java
> ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
> Foo foo = mapper.readValue(new File("foo.yaml"), Foo.class);
> ```

This is certainly an option – and the LinkML-Java runtime is in fact
currently built on top of the Jackson libraries –, but it would only
work for some simple schemas that don’t make use of LinkML-specific
features that the Jackson libraries know nothing about.

In particular, the LinkML-Java runtime brings support for

* [type designator slots](https://linkml.io/linkml/schemas/type-designators.html),
  by which the runtime type of an object is indicated by the value of a
  particular slot of its defining class;
  
* automatic expansion (upon reading) and contraction (upon writing) of
  values typed as `linkml:uriOrCurie`;
  
* dereferencing global objects;

* the various forms of [inlining](https://linkml.io/linkml/schemas/inlining.html).

