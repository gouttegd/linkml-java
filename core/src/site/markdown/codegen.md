Code generation for use with the LinkML-Java runtime
====================================================

In order to be usable with the LinkML-Java runtime, the code that
represents a LinkML model in Java must have been generated according to
certain rules.

LinkML-to-Java translation rules
--------------------------------

### LinkML types
LinkML’s basic types are rendered in Java as follows:

| LinkML type         | Java type               | Notes |
| ------------------- | ----------------------- | ----- |
| xsd:string          | String                  |       |
| xsd:integer         | Integer                 | (1)   |
| xsd:float           | Float                   | (1)   |
| xsd:double          | Double                  | (1)   |
| xsd:boolean         | Boolean                 | (1)   |
| xsd:dateTime        | java.time.ZonedDateTime |       |
| xsd:date            | java.time.LocalDate     |       |
| xsd:time            | java.time.LocalTime     |       |
| xsd:anyURI          | java.net.URI            |       |
| linkml:uriOrCurie   | String                  | (2)   |

(1) Those types may also be represented by their primitive equivalent
(`int`, `float`, `double`, and `boolean`) if and only if the
corresponding slot is **required**. If the slot is not required, then
the boxed types are used instead so that we can represent the fact that
the slot may not a value (by setting the field to `null`).

(2) The LinkML `uriOrCurie` type is represented by a standard Java
String object, rather than by a dedicated class, for convenience. To
support automatic expansion/contraction of CURIEs, the String field
representing a LinkML `uriOrCurie` slot must be annotated with a
`@Converter(CurieConverter.class)` annotation (more on that below).

### LinkML classes
A LinkML class is represented by a Java class that must satisfy the
following constraints:

* The class must have a public no-argument constructor. It may have
  additional constructors as needed but the no-argument constructor must
  be present and publicly accessible.
  
* Each slot is represented by a (typically private) field which must
  have public, predictably named read and write accessors.
  
The general naming rule for accessors is that a slot named _foo_ must
have a `getFoo()` read accessor and a `setFoo()` write accessor.

Two specific rules apply to **required, boolean-typed** slots:

* the read accessor of a boolean-typed slot named _foo_ must be
  `isFoo()`, rather than `getFoo()`;
  
* if the name of the slot already starts with `is` followed by an
  uppercase letter, that `is` prefix is removed before applying the
  previous rule (so for example, a slot named `isFoo` must have a read
  accessor that is also named `isFoo()` and a write accessor that is
  named `setFoo`, as if the slot had been named simply `Foo`).
  
Those rules were chosen because they correspond to the behaviour of the
[Lombok](https://projectlombok.org) annotation processor, which can then
be used to easily generate the accessors.

A multi-valued slot of type _T_ is represented as a _List&lt;T&gt;_ field.
Importantly, this is always the case, even if the slot may be serialised
as a dictionary. The fact that the slot may be _serialised_ as a
dictionary has no bearing on how it is _represented_ in memory.

### LinkML enumerations
“Simple” (non-dynamic) LinML enumerations are rendered in Java as
standard Java `enum` objects.

The one constraint is that the Java enumeration must have a static
`fromString(String)` method that can turn a String value into one of the
possible enumerated value (or into `null` if the given String is not a
permissible value).

Dynamic LinkML enumerations are currently completely unsupported, both
on the side of the LinkML-Py code generator and on the side of the
LinkML-Java runtime. How to represent such enumerations in Java and make
them work as expected is still an open question, but one thing that is
almost certain already is that they will _not_ be represented as
standard Java `enum` objects, because the values of a Java `enum` must
necessarily be fully known at compile-time – this is by design
incompatible with the very idea of a “dynamic enum”.

A possible workaround for dynamic enums for now is to use a tool such as
`vskit expand` to generate the permissible values at compile-time, in
effect turning a dynamic enum into a non-dynamic one – but of course by
doing so you lose all the “dynamic” aspects.

Java annotations
----------------
One principle that underpins the LinkML-Java runtime is that it must not
require runtime access to the LinkML schema – therefore, the runtime
must have a way to access _some_ of the informations from the schema,
solely from the generated code.

This is done via LinkML-specific Java annotations that must be present
in the generated code.

All annotations reside in the `org.incenp.linkml.core.annotations` Java
namespace. They are:

| Annotation     | Purpose |
| -------------- | ------- |
| LinkURI        | Provide the URI associated to a class or a slot |
| Identifier     | Mark the identifier or key slot |
| TypeDesignator | Mark the slot that designates the runtime type of an instance |
| Required       | Mark a slot as being required or recommended |
| Inlined        | Mark a slot as being inlined (as list or as dictionary) |
| SlotName       | Provide the original name of a slot |
| Converter      | Indicate that the class or slot requires a custom converter |

Example
-------
Here is an example of a class ready to be used with the runtime (import
declarations omitted for brevity):

```java
@LinkURI("https://example.org/myschema/classes/MyClass")
public class MyClass {

    // The unique identifier for objects of that class
    @Identifier
    @LinkURI("https://example.org/myschema/slots/id")
    private String id;
    
    // Contains the runtime type of an object (to distinguish between
    // the various subclasses of MyClass)
    @TypeDesignator
    @LinkURI("https://example.org/myschema/slots/type")
    private String type;
    
    // The values of this slot (instances of AnotherClass) are expected
    // to be inlined (rather than referenced) as a list (rather than as
    // dictionary)
    @Inlined(asList = true)
    @LinkURI("https://example.org/myschema/slots/foos")
    public List<AnotherClass> foos;
    
    // Accessors for all the slots
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public List<AnotherClass> getFoos() {
        return foos;
    }
    
    public void setFoos(List<AnotherClass> foos) {
        this.foos = foos;
    }
}
```

Generating the Java code
------------------------
In the future, the LinkML-Java project will most likely provide its own
code generator, that will automatically produce code ready to work with
the runtime. But for the time being, we are dependent on the Java code
generator provided by the LinkML project itself.

LinkML-Java developers are actively working with the LinkML project to
amend the Java code generator so that it produces code meeting all the
requirements of the LinkML-Java runtime. This is still a work in
progress, so for now it is necessary to use custom Jinja2 templates.
