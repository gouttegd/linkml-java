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

import org.incenp.linkml.model.annotations.Identifier;
import org.incenp.linkml.model.annotations.LinkURI;
import org.incenp.linkml.model.annotations.Requirement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * A named element in the LinkML model.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public abstract class Element {
    @Identifier
    @LinkURI("http://www.w3.org/2000/01/rdf-schema#label")
    private String name;

    @Requirement(RequirementLevel.RECOMMENDED)
    @LinkURI("http://www.w3.org/2004/02/skos/core#definition")
    private String description;

    @LinkURI("http://purl.org/dc/terms/title")
    private String title;
}
