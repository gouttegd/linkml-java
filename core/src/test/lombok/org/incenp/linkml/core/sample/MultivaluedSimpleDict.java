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

package org.incenp.linkml.core.sample;

import java.util.List;

import org.incenp.linkml.core.annotations.Identifier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * An example of a class that is eligible for inlining as a “SimpleDict” (only
 * one additional slot beyond the identifier slot), where the primary slot is
 * multivalued.
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public class MultivaluedSimpleDict {
    @Identifier
    private String key;
    private List<String> values;
}
