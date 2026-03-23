#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import JavaGenerator

HERE = Path(__file__).parent.resolve()


@click.option("--linkml-model-directory",
              default="linkml-model", show_default=True,
              type=click.Path(exists=True, path_type=Path,
                              dir_okay=True, file_okay=False))
@click.option("--output-directory", default="output", show_default=True)
@click.command()
def cli(linkml_model_directory, output_directory):
    for schema in ["annotations", "extensions", "mappings", "meta", "types", "units"]:
        gen = JavaGenerator(linkml_model_directory / (schema + ".yaml"),
                            true_enums=True,
                            package="org.incenp.linkml.schema.model",
                            template_dir=HERE / "templates")
        gen.serialize(output_directory, template_variant="org.incenp.linkml")

if __name__ == "__main__":
    cli()
