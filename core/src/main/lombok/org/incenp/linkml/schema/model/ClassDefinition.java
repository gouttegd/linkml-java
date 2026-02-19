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

import java.util.List;

import org.incenp.linkml.core.CurieConverter;
import org.incenp.linkml.core.InliningMode;
import org.incenp.linkml.core.annotations.Converter;
import org.incenp.linkml.core.annotations.Inlining;
import org.incenp.linkml.schema.ClassDefinitionConverter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents the definition of a class. A LinkML class should be directly
 * equivalent to a Java class (or a record).
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Converter(ClassDefinitionConverter.class)
public class ClassDefinition extends Definition {
    private List<SlotDefinition> slots;

    @JsonProperty("slot_usage")
    @Inlining(InliningMode.DICT)
    private List<SlotDefinition> slotUsage;

    @Inlining(InliningMode.DICT)
    private List<SlotDefinition> attributes;

    @JsonProperty("class_uri")
    @Converter(CurieConverter.class)
    private String URI;

    @Override
    public void setParent(Definition parent) {
        if ( !(parent instanceof ClassDefinition) ) {
            throw new IllegalArgumentException("Invalid type, ClassDefinition expected");
        }
        super.setParent(parent);
    }
}
