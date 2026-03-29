Getting started
===============

This page is intended as a quick tutorial into using the LinkML-Java
core runtime library.

Let us assume that you have a LinkML schema named `foo.yaml`, defining
several classes such as `Foo`, `Bar`, and `Baz`.

Generating the Java code
------------------------
The first step is to generate the Java code for the various classes that
make up the schema, according to the runtime’s
[code generation rules](codegen.html).

This can be done with LinkML-Py with the following command:

```sh
$ linkml generate java --true-enums \
                       --template-variant org.incenp.linkml \
                       --package org.example.foo.model \
                       --output-directory src/main/java/org/example/foo/model \
                       foo.yaml
```

where `org.example.foo.model` is the name of the Java package where you
wish all the Java classes and enums to reside. It is recommended, though
not at all mandatory, to put the generated Java code in a distinct
package, separated from the rest of the code from your application. In
this example, the main (manually written) code for the application would
be in the `org.example.foo` package, with the generated code in the
`org.example.foo.model` subpackage.

Deserialising instance data from JSON or YAML
---------------------------------------------
To deserialise an instance of the `Foo` class from a JSON or YAML file,
you must first parse the file into a generic `Map<String,Object>` object
using any JSON or YAML parser of your choice.

Then, you must create a
[ConverterContext](../apidocs/org/incenp/linkml/core/ConverterContext.html)
object. This is the main interface to the entire LinkML-Java core
runtime library. From it, you can then obtain a
[IConverter](../apidocs/org/incenp/linkml/core/IConverter.html) object
for the `Foo` class, which you can then use to convert the generic
`Map<String,Object>` object into an instance of the `Foo` class.

The entire procedure would look like this:

```java
import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.foo.model.Foo;

// Parsing the file into a generic map
ObjectMapper mapper = new ObjectMapper();
Map<String,Object> rawMap = null;
try {
    rawMap = mapper.readValue(new File("foo.json"), Map.class);
} catch ( IOException e ) {
    // I/O error or JSON parsing error
    ...
}

// Converting the generic map into a Foo instance
ConverterContext ctx = new ConverterContext();
Foo foo = null;
try {
    foo = ctx.getConverter(Foo.class).convert(rawMap, ctx);
    ctx.finalizeAssignments();
} catch ( LinkMLRuntimeException e 
    // LinkML-specific errors (invalid instance data)
    ...
}
```

The call to `finalizeAssignments()` allows to ensure that all LinkML
references (when a slot is expected to contain the _name_ of a global
object that is inlined elsewhere, rather than the object itself) are
resolved into Java references. This is _not_ done automatically by the
converter, so as to give client code the opportunity to postpone the
resolution step after having converted several objects.

For example, if the data you want to deserialise is spread over several
JSON files (say, one file containing an instance of `Foo` and one file
containing an instance of `Bar`), you would first parse and convert both
files as above, and then call `finalizeAssignments()`:

```java
Map<String,Object> rawFoo = mapper.readValue(new File("foo.json"), Map.class);
Map<String,Object> rawBar = mapper.readValue(new File("bar.json"), Map.class);

Foo foo = ctx.getConverter(Foo.class).convert(rawFoo, ctx);
Bar bar = ctx.getConverter(Bar.class).convert(rawBar, ctx);
ctx.finalizeAssignments();
```

### Deserialising a list of instances
If the JSON or YAML file contains a _list_ of `Foo` objects, rather than
a single object, then the principle remains generally the same as for a
single object, but you should parse the file into a generic
`List<Object>` object, and then convert all the individual objects:

```java
// Parsing the file into a generic list
List<Object> rawList = mapper.readValue(new File("foos.json"), List.class);

// Converting all objects
IConverter converter = ctx.getConverter(Foo.class);
List<Foo> foos = new ArrayList<>();
for ( Object rawItem : rawList ) {
    foos.add(converter.convert(rawItem, ctx));
}
ctx.finalizeAssignments();
```

Serialising instance data into JSON or YAML
-------------------------------------------
Serialisation of instance data follows the same principle as
deserialisation, only in reverse. That is, you must first convert
the instance object into a generic `Object`, which
you can then give to the JSON or YAML serialisation library:

```java
Foo foo = new Foo();
// Setting the attributes of foo...

// Converting foo to a generic Object
Object raw = ctx.getConverter(Foo.class).serialise(foo, ctx);

// Writing the raw object to file
mapper.writeValue(new File("foo.json"), raw);
```
