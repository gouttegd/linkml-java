#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import JavaGenerator

HERE = Path(__file__).parent.resolve()
ROOT = HERE.parent.resolve()


def cleanup_dir(directory):
    if directory.exists():
        for file in directory.iterdir():
            file.unlink()
        directory.rmdir()


@click.command()
def cli():

    # Generating test code
    output_dir = ROOT / "core/src/test/java/org/incenp/linkml/core/sample"
    cleanup_dir(output_dir)
    gen = JavaGenerator(ROOT / "core/src/test/linkml/samples.yaml",
                        true_enums=True,
                        use_aliases=True,
                        package="org.incenp.linkml.core.sample")
    gen.serialize(output_dir, template_variant="org.incenp.linkml")

    # Generate code for LinkML meta model
    output_dir = ROOT / "ext/src/main/java/org/incenp/linkml/schema/model"
    cleanup_dir(output_dir)
    for schema in ["annotations", "extensions", "mappings", "meta", "types", "units"]:
        model_dir = ROOT / "ext/src/main/resources/schemas/linkml"
        gen = JavaGenerator(model_dir / (schema + ".yaml"),
                            true_enums=True,
                            use_aliases=True,
                            package="org.incenp.linkml.schema.model")
        gen.serialize(output_dir, template_variant="org.incenp.linkml")

if __name__ == "__main__":
    cli()
