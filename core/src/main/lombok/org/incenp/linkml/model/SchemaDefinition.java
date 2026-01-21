/*
 * LinkML-Java - LinkML library for Java
 * Copyright © 2026 Damien Goutte-Gattat
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.incenp.linkml.model;

import java.net.URI;
import java.util.List;

import org.incenp.linkml.model.annotations.Inlining;
import org.incenp.linkml.model.annotations.LinkURI;
import org.incenp.linkml.model.annotations.Requirement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a single LinkML schema. This can be the top-level schema or an
 * imported schema.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SchemaDefinition extends Element {
    @Requirement(RequirementLevel.MANDATORY)
    private URI id;

    @LinkURI("http://purl.org/pav/version")
    private String version;

    @LinkURI("http://purl.org/dc/terms/license")
    private String license;

    @LinkURI("http://www.w3.org/ns/shacl#declare")
    private List<Prefix> prefixes;

    @JsonProperty("default_prefix")
    private String defaultPrefix;

    @JsonProperty("default_range")
    private TypeDefinition defaultRange;

    private List<String> imports;

    @Inlining(InliningMode.DICT)
    private List<TypeDefinition> types;

    @Inlining(InliningMode.DICT)
    private List<EnumDefinition> enums;

    @JsonProperty("slot_definitions")
    @Inlining(InliningMode.DICT)
    private List<SlotDefinition> slotDefinitions;

    @Inlining(InliningMode.DICT)
    private List<ClassDefinition> classes;
}
