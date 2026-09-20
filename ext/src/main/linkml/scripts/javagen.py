#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import cli


linkml_directory = Path("ext/src/main/resources/schemas/linkml")
ctx = click.Context(cli)
ctx.invoke(cli,
           yamlfile=Path("ext/src/main/linkml/schemas"),
           output_directory=Path("ext/src/main/java"),
           true_enums=True,
           use_aliases=True,
           template_variant="org.incenp.linkml",
           importmap={"linkml:": linkml_directory.absolute().as_posix()})
