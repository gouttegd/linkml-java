#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import cli


ctx = click.Context(cli)
ctx.invoke(cli,
           yamlfile=Path("core/src/test/linkml/schemas"),
           output_directory=Path("core/src/test/java"),
           true_enums=True,
           use_aliases=True,
           template_variant="org.incenp.linkml")
