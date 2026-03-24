LinkML-Java extended library
============================

This is the LinkML-Java extended library, a library that builds on top
of the [core library](../linkml-core/index.html) to provide additional
features.

Currently, the extended library has two main purposes:

* providing a higher level interface (compared to the core library) for
  loading and dumping LinkML instance data;
* loading and manipulating LinkML schemas.


Library identifiers and namespace
---------------------------------
To use the library in a Maven or Gradle project, use the following
identifiers:

* groupId: `org.incenp`
* artifactId: `linkml-ext`

The extended library automatically brings the `linkml-core` library as a
dependency.


Loading and dumping LinkML instance data
----------------------------------------
The extended library provides a high level interface to load and dump
LinkML instance data. Compared to the interface provided by the core
library, the high level interface

* dispenses you from manipulating the `ConverterContext` directly (in
  particular, you do not have to worry about “finalising assignments”);
* dispenses you from having to parse a YAML or JSON file into a generic
  `Map` structure;
* depends specifically on the Jackson library for YAML or JSON parsing.

You may still prefer to use the core library directly if (1) you need
finer control of the `ConverterContext`, (2) your data has already been
parsed into a `Map` structure, or (3) you do not want to depend on the
Jackson libraries (maybe because your project already depends on another
parser library).

Assuming you have a schema that defines a _Foo_ class, you can for
example load an instance of that class from a YAML file as follows:

```java
import org.incenp.linkml.ext.ObjectLoader;
import org.incenp.linkml.core LinkMLRuntimeException;

ObjectLoader loader = new ObjectLoader();
try {
    Foo foo = loader.loadObject(new File("foo.yaml"), Foo.class);
} catch ( IOException e ) {
    // Non-LinkML error, including I/O error or YAML error
} catch ( LinkMLRuntimeException e ) {
    // LinkML-specific error (content of foo.yaml does not match
    // the definition of the Foo class)
}
```


Loading a LinkML schema
-----------------------
Even though a LinkML schema is itself a piece of instance data (it’s an
instance of the `SchemaDefinition` class in LinkML’s own meta-schema),
trying to load one using the `ObjectLoader` as above would only work
correctly for a completely “standalone” schema – a schema that does not
use any `imports` declaration. Import declarations are a concept that is
specific to LinkML’s meta-schema, so they cannot be handled by a generic
loader.

In practice, most if not all LinkML schemas are expected to use
`imports`, if only to load the
[linkml:types](https:/w3id.org/linkml/types.yaml) sub-schema that
defines all of LinkML’s basic types.

Use the `SchemaDocument` class to load a schema along with all its
imported schemas:

```java
import org.incenp.linkml.schema import SchemaDocument;

try {
    SchemaDocument doc = new SchemaDocument(new File("schema.yaml"));
} catch ( IOException e ) {
    // Non-LinkML error, including I/O error or YAML error
} catch ( InvalidSchemaException e ) {
    // LinkML-specific error (schema.yaml is not a valid LinkML schema)
}
```

The `SchemaDocument` object then provides methods to query the contents
of the schema, such as `getAllClasses()`, `getAllSlots()`, etc. Those
methods work on the imports closure, meaning they will return all the
elements regardless of whether they are declared in the root schema or
in any of the imported schema.
