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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents the definition of a “slot”. A slot in LinkML parlance is the
 * equivalent of a “field” in Java.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SlotDefinition extends Definition {
    @JsonProperty("identifier")
    private boolean isIdentifier;

    @JsonProperty("designates_type")
    private boolean isTypeDesignator;

    private Element range;

    private boolean required;

    private boolean recommended;

    private boolean multivalued;

    private boolean inlined;

    @JsonProperty("inlined_as_list")
    private boolean inlinedAsList;

    @Override
    public void setParent(Definition parent) {
        if ( !(parent instanceof SlotDefinition) ) {
            throw new IllegalArgumentException("Invalid type, SlotDefinition expected");
        }
        super.setParent(parent);
    }
}
