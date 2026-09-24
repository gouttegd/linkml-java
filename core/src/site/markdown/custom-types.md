Using custom types with the LinkML-Java runtime
===============================================

Any LinkML schema can define its own types, in addition to the built-in
types defined in the `linkml:types` schema.

Custom types can be used with the LinkML-Java runtime, but may require
additional setup, both when generating the LinkML-derived code and when
using the code with the runtime.

Example
-------
Let us suppose a schema that defines the following custom type:

```yaml
prefixes:
  ex: https://example.org/

types:
  foo:
    typeof: string
    type_uri: ex:foo
```

(We omit `base` and `repr` here, since they are only meaningful for
Python.)

By default, this type will be treated as a string, which means that any
slot/attribute with a range set to this type will be rendered in Java
code as a `String`-typed field. This will be enough to make the
generated code work, but will not allow to implement any behaviour
specific to the custom type (e.g. checking that the value obeys some
type-specific constraints).

There are two ways of overriding this default behaviour:

* using a custom [`IConverter`](../apidocs/org/incenp/linkml/core/IConverter.html);
* using a custom `IConverter` _and_ a dedicated class representing the
  type.

Using a custom converter only
-----------------------------
Using only a custom `IConverter` means that the custom type will still
be represented in Java as a `String`, but the conversion of raw data
into the `String` object (and, conversely, the conversion of the
`String` object into raw data, when deserializing) will be performed by
the custom `IConverter` implementation rather than by the `IConverter`
that is normally used for actual strings. That custom `IConverter` can
then perform any operation necessary to support the custom type.

To use that approach, create a `IConverter` implementation. It can be
a completely independent implementation, but since the custom type is
string-based it might be appropriate to instead derive the custom
converter from the existing [`StringConverter`](../apidocs/org/incenp/linkml/core/converters/StringConverter.html).

The custom converter must implement the `getURI` method, which must
return the URI of the custom type the converter is intended for:

```java
public class FooConverter extends StringConverter {

    @Override
    public String getURI() {
        return "https://example.org/foo";
    }
}
```

It should then override the other methods of the `IConverter` interface
as needed to correctly handle the custom type.

Then, the custom converter must be registered to the
[`ConverterContext`](../apidocs/org/incenp/linkml/core/ConverterContext.html)
object that is going to be used to (de)serialize any data where the
custom type is used.

Lastly, during code generation, any slot whose range is set to the
custom type must yield a Java field annotated with the `@TypeURI`
annotation indicating the URI of the type, as in:

```java
public class MySampleClass {

    @TypeURI("https://example.org/foo")
    private String myFooTypedSlot;
}
```

Using the Java generator included with LinkML-Py (and starting from
LinkML-Py 1.12.0), this can be done by adding the following line to the
`_types-org.incenp.linkml.map` file in the Javagen template directory:

```
https://example.org/foo String
```

This line simultaneously (1) tells the generator to render the
`https://example.org/foo` type as `String`, and (2) tells it to produce
the aforementioned `@TypeURI` annotation.

Using a custom converter and a custom class
-------------------------------------------
It may happen that a custom type should be represented in Java using its
own dedicated class, rather than a more generic class such as `String`.
For example, this may allow to implement any required custom behaviour
for the `equals()` and `hashCode()` methods.

In such a case, the general principle is the same as in the previous
section, except that:

* the custom `IConverter` must be adapted to deserialize raw data into
  an instance of the custom class representing the type, and serialize
  an instance of the custom class into raw data;
* the Java code generator must know to render the custom type using the
  dedicated custom class.

For the second step, and again using the Java generator included with
LinkML-Py, the line to add to the `_types-org.incenp.linkml.map` file
would be:

```
https://example.org/foo org.example.Foo
```

if we assume that `org.example.Foo` is the fully qualified name of the
custom class representing the type.
