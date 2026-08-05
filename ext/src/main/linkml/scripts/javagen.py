#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import JavaGenerator


def cleanup_dir(directory: Path) -> None:
    if directory.exists():
        for file in directory.iterdir():
            file.unlink()
        directory.rmdir()


@click.option("--output-directory",
              type=click.Path(dir_okay=True, file_okay=False, path_type=Path),
              default=Path("ext/src/main/java"))
@click.option("--schema-directory",
              type=click.Path(dir_okay=True, file_okay=False, exists=True, path_type=Path),
              default=Path("ext/src/main/linkml/schemas"))
@click.option("--linkml-directory",
              type=click.Path(dir_okay=True, file_okay=False, exists=True, path_type=Path),
              default=Path("ext/src/main/resources/schemas/linkml"))
@click.command()
def cli(output_directory: Path, schema_directory: Path, linkml_directory: Path) -> None:

    cleaned_up_dirs = {}

    for schema in schema_directory.glob("**/*.yaml"):
        package_dir = schema.relative_to(schema_directory).parent
        output_dir = output_directory / package_dir
        if output_dir not in cleaned_up_dirs:
            cleanup_dir(output_dir)
            cleaned_up_dirs[output_dir] = 1

        package_name = package_dir.as_posix().replace("/", ".")
        gen = JavaGenerator(schema,
                            importmap={"linkml:": linkml_directory.absolute().as_posix()},
                            true_enums=True,
                            use_aliases=True,
                            package=package_name)
        gen.serialize(output_dir, template_variant="org.incenp.linkml")


if __name__ == "__main__":
    cli()
