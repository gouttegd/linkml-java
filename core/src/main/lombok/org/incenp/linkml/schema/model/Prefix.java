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

package org.incenp.linkml.schema.model;

import org.incenp.linkml.core.RequirementLevel;
import org.incenp.linkml.core.annotations.Identifier;
import org.incenp.linkml.core.annotations.LinkURI;
import org.incenp.linkml.core.annotations.Requirement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a tuple associating a prefix name to a IRI prefix.
 * 
 * Disambiguation: We follow the nomenclature used in the OWLAPI, where “prefix”
 * (or “IRI prefix”) refers to the IRI a “prefix name” expands to. For example,
 * <code>dcterms</code> could be a <em>prefix name</em> that expands to the
 * <em>prefix</em> <code>http://purl.org/dc/terms/</code>. Common usage in both
 * the OBO Foundry and in LinkML is to call <code>dcterms</code> the “prefix”
 * rather than the “prefix name”, but we avoid that usage here as it is
 * needlessly misleading.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public class Prefix {
    @JsonProperty("prefix_prefix")
    @Identifier
    @LinkURI("https://www.w3.org/ns/shacl#prefix")
    private String prefixName;

    @JsonProperty("prefix_reference")
    @Requirement(RequirementLevel.MANDATORY)
    @LinkURI("http://www.w3.org/ns/shacl#namespace")
    private String iriPrefix;
}
