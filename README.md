LinkML-Java – LinkML runtime library for Java
=============================================

LinkML-Java is an implementation of [LinkML](https://linkml.io/), the
Linked Data Modeling Language, for the Java language.

Implementation status
---------------------
The project is in a (very) early phase of development and cannot
reallistically be used for anything (yet!).

For now, it merely provides the Java classes that would allow to
represent and manipulate a “basic” LinkML model in memory, without any
facilities to read/write a model from/to a LinkML YAML file and without
any facilities to exploit the model in a meaningful way (e.g. validating
instance data or generating code). Such facilities will come soon (for
some value of “soon”).

Note that “basic”, in the above sentence, is _not_ related to the
[BasicSubset](https://linkml.io/linkml-model/latest/docs/BasicSubset/),
because LinkML currently does not provide readily usable tools to
“extract” a subset from a larger schema. Instead, what is implemented
here is my own interpretation of what a “basic” form of LinkML should
be; the plan is to make a functional Java runtime that can be
practically used to work with simple (“basic”) schemas, and then to
progressively expand the subset to cover more and more of the LinkML
specification. Though reallistically, given that LinkML is very much a
“moving target”, this project will likely never cover it in its
entirety.

Homepage and repository
-----------------------
The project is currently located at
<https://github.com/gouttegd/linkml-java>, which both hosts the
repository and acts as a temporary home page.

Copying
-------
LinkML-Java is distributed under the terms of the GNU General Public
License, version 3 or higher. The full license is included in the
[COPYING file](COPYING) of the source distribution.
