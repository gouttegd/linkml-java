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
              type=click.Path(dir_okay=True, file_okay=False),
              default=Path("core/src/test/java"))
@click.option("--linkml-directory",
              type=click.Path(dir_okay=True, file_okay=False, exists=True),
              default=Path("core/src/test/linkml/schemas"))
@click.command()
def cli(output_directory: Path, linkml_directory: Path) -> None:

    cleaned_up_dirs = {}

    for schema in linkml_directory.glob("**/*.yaml"):
        package_dir = schema.relative_to(linkml_directory).parent
        output_dir = output_directory / package_dir
        if output_dir not in cleaned_up_dirs:
            cleanup_dir(output_dir)
            cleaned_up_dirs[output_dir] = 1

        package_name = package_dir.as_posix().replace("/", ".")
        gen = JavaGenerator(schema,
                            true_enums=True,
                            use_aliases=True,
                            package=package_name)
        gen.serialize(output_dir, template_variant="org.incenp.linkml")


if __name__ == "__main__":
    cli()
