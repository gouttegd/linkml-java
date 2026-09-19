#!/usr/bin/env python3

import click
from pathlib import Path
from linkml.generators.javagen import JavaGenerator


def cleanup_dir(directory: Path) -> None:
    if directory.exists():
        for file in directory.iterdir():
            file.unlink()
        directory.rmdir()


class CustomGenerator(JavaGenerator):

    custom_type_map_read: bool = False
    custom_type_map = {}

    def lookup_custom_map_type(self, t):
        if not self.custom_type_map_read:
            mapfile = self.template_dir / "_types.map"
            if mapfile.exists():
                with mapfile.open("r") as f:
                    for line in f:
                        line = line.strip()
                        if line.startswith("#"):
                            continue
                        items = line.split()
                        if len(items) == 2:
                            self.custom_type_map[items[0]] = items[1]
            self.custom_type_map_read = True
        return self.custom_type_map.get(t, None)

    def map_type(self, t, required=False):
        uri = self.get_type_uri(t.name)
        if uri is not None:
            custom_type = self.lookup_custom_map_type(uri)
            if custom_type is not None:
                return custom_type
        return super().map_type(t, required)

    def get_type_uri(self, name, custom_only=False):
        if name in self.schemaview.all_types():
            uri = self.schemaview.get_uri(name, expand=True, native=True)
            if not uri.startswith("https://w3id.org/linkml/"):
                uri = self.schemaview.get_uri(name, expand=True, native=False)
            if not custom_only or self.lookup_custom_map_type(uri) is not None:
                return uri
        return None


@click.option("--output-directory",
              type=click.Path(dir_okay=True, file_okay=False, path_type=Path),
              default=Path("core/src/test/java"))
@click.option("--schema-directory",
              type=click.Path(dir_okay=True, file_okay=False, exists=True, path_type=Path),
              default=Path("core/src/test/linkml/schemas"))
@click.command()
def cli(output_directory: Path, schema_directory: Path) -> None:

    cleaned_up_dirs = {}

    for schema in schema_directory.glob("**/*.yaml"):
        package_dir = schema.relative_to(schema_directory).parent
        output_dir = output_directory / package_dir
        if output_dir not in cleaned_up_dirs:
            cleanup_dir(output_dir)
            cleaned_up_dirs[output_dir] = 1

        package_name = package_dir.as_posix().replace("/", ".")
        gen = CustomGenerator(schema,
                              true_enums=True,
                              use_aliases=True,
                              package=package_name,
                              template_dir=Path("core/src/test/linkml/templates"))
        gen.serialize(output_dir, template_variant="org.incenp.linkml")


if __name__ == "__main__":
    cli()
