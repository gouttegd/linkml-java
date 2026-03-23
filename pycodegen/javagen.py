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


@click.option("--linkml-model-repo",
              default="linkml-model", show_default=True,
              type=click.Path(path_type=Path, dir_okay=True, file_okay=False))
@click.command()
def cli(linkml_model_repo):

    # Generating test code
    output_dir = ROOT / "core/src/test/java/org/incenp/linkml/core/sample"
    cleanup_dir(output_dir)
    gen = JavaGenerator(ROOT / "core/src/test/linkml/samples.yaml",
                        true_enums=True,
                        package="org.incenp.linkml.core.sample",
                        template_dir=HERE / "templates")
    gen.serialize(output_dir, template_variant="org.incenp.linkml")

    # Generate code for LinkML meta model
    if not linkml_model_repo.exists():
        print("Skipping LinkML meta model generation")
        return

    output_dir = ROOT / "core/src/main/java/org/incenp/linkml/schema/model"
    cleanup_dir(output_dir)
    for schema in ["annotations", "extensions", "mappings", "meta", "types", "units"]:
        model_dir = linkml_model_repo / "linkml_model/model/schema"
        gen = JavaGenerator(model_dir / (schema + ".yaml"),
                            true_enums=True,
                            package="org.incenp.linkml.schema.model",
                            template_dir=HERE / "templates")
        gen.serialize(output_dir, template_variant="org.incenp.linkml")

if __name__ == "__main__":
    cli()
