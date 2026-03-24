package org.incenp.linkml.schema.model;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.Converter;
import org.incenp.linkml.core.annotations.ExtensionHolder;
import org.incenp.linkml.core.annotations.Identifier;
import org.incenp.linkml.core.annotations.Inlined;
import org.incenp.linkml.core.annotations.LinkURI;
import org.incenp.linkml.core.annotations.Required;
import org.incenp.linkml.core.annotations.SlotName;
import org.incenp.linkml.core.annotations.TypeDesignator;
import org.incenp.linkml.core.CurieConverter;

@LinkURI("https://w3id.org/linkml/AnonymousClassExpression")
public class AnonymousClassExpression extends AnonymousExpression {

    @SlotName("is_a")
    @LinkURI("https://w3id.org/linkml/is_a")
    private Definition isA;

    @SlotName("any_of")
    @LinkURI("https://w3id.org/linkml/any_of")
    private List<AnonymousClassExpression> anyOf;

    @SlotName("exactly_one_of")
    @LinkURI("https://w3id.org/linkml/exactly_one_of")
    private List<AnonymousClassExpression> exactlyOneOf;

    @SlotName("none_of")
    @LinkURI("https://w3id.org/linkml/none_of")
    private List<AnonymousClassExpression> noneOf;

    @SlotName("all_of")
    @LinkURI("https://w3id.org/linkml/all_of")
    private List<AnonymousClassExpression> allOf;

    @SlotName("slot_conditions")
    @Inlined
    @LinkURI("https://w3id.org/linkml/slot_conditions")
    private List<SlotDefinition> slotConditions;

    public void setIsA(Definition isA) {
        this.isA = isA;
    }

    public Definition getIsA() {
        return this.isA;
    }

    public void setAnyOf(List<AnonymousClassExpression> anyOf) {
        this.anyOf = anyOf;
    }

    public List<AnonymousClassExpression> getAnyOf() {
        return this.anyOf;
    }

    public List<AnonymousClassExpression> getAnyOf(boolean set) {
        if ( this.anyOf == null && set ) {
            this.anyOf = new ArrayList<>();
        }
        return this.anyOf;
    }

    public void setExactlyOneOf(List<AnonymousClassExpression> exactlyOneOf) {
        this.exactlyOneOf = exactlyOneOf;
    }

    public List<AnonymousClassExpression> getExactlyOneOf() {
        return this.exactlyOneOf;
    }

    public List<AnonymousClassExpression> getExactlyOneOf(boolean set) {
        if ( this.exactlyOneOf == null && set ) {
            this.exactlyOneOf = new ArrayList<>();
        }
        return this.exactlyOneOf;
    }

    public void setNoneOf(List<AnonymousClassExpression> noneOf) {
        this.noneOf = noneOf;
    }

    public List<AnonymousClassExpression> getNoneOf() {
        return this.noneOf;
    }

    public List<AnonymousClassExpression> getNoneOf(boolean set) {
        if ( this.noneOf == null && set ) {
            this.noneOf = new ArrayList<>();
        }
        return this.noneOf;
    }

    public void setAllOf(List<AnonymousClassExpression> allOf) {
        this.allOf = allOf;
    }

    public List<AnonymousClassExpression> getAllOf() {
        return this.allOf;
    }

    public List<AnonymousClassExpression> getAllOf(boolean set) {
        if ( this.allOf == null && set ) {
            this.allOf = new ArrayList<>();
        }
        return this.allOf;
    }

    public void setSlotConditions(List<SlotDefinition> slotConditions) {
        this.slotConditions = slotConditions;
    }

    public List<SlotDefinition> getSlotConditions() {
        return this.slotConditions;
    }

    public List<SlotDefinition> getSlotConditions(boolean set) {
        if ( this.slotConditions == null && set ) {
            this.slotConditions = new ArrayList<>();
        }
        return this.slotConditions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("AnonymousClassExpression(");
        if ( (o = this.getIsA()) != null ) {
            sb.append("is_a=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAnyOf()) != null ) {
            sb.append("any_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExactlyOneOf()) != null ) {
            sb.append("exactly_one_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getNoneOf()) != null ) {
            sb.append("none_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAllOf()) != null ) {
            sb.append("all_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSlotConditions()) != null ) {
            sb.append("slot_conditions=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExtensions()) != null ) {
            sb.append("extensions=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAnnotations()) != null ) {
            sb.append("annotations=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDescription()) != null ) {
            sb.append("description=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAltDescriptions()) != null ) {
            sb.append("alt_descriptions=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTitle()) != null ) {
            sb.append("title=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDeprecated()) != null ) {
            sb.append("deprecated=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTodos()) != null ) {
            sb.append("todos=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getNotes()) != null ) {
            sb.append("notes=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getComments()) != null ) {
            sb.append("comments=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExamples()) != null ) {
            sb.append("examples=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInSubset()) != null ) {
            sb.append("in_subset=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getFromSchema()) != null ) {
            sb.append("from_schema=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getImportedFrom()) != null ) {
            sb.append("imported_from=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSource()) != null ) {
            sb.append("source=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInLanguage()) != null ) {
            sb.append("in_language=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSeeAlso()) != null ) {
            sb.append("see_also=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDeprecatedElementHasExactReplacement()) != null ) {
            sb.append("deprecated element has exact replacement=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDeprecatedElementHasPossibleReplacement()) != null ) {
            sb.append("deprecated element has possible replacement=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAliases()) != null ) {
            sb.append("aliases=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getStructuredAliases()) != null ) {
            sb.append("structured_aliases=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMappings()) != null ) {
            sb.append("mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExactMappings()) != null ) {
            sb.append("exact mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCloseMappings()) != null ) {
            sb.append("close mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRelatedMappings()) != null ) {
            sb.append("related mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getNarrowMappings()) != null ) {
            sb.append("narrow mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBroadMappings()) != null ) {
            sb.append("broad mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCreatedBy()) != null ) {
            sb.append("created_by=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getContributors()) != null ) {
            sb.append("contributors=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCreatedOn()) != null ) {
            sb.append("created_on=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLastUpdatedOn()) != null ) {
            sb.append("last_updated_on=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getModifiedBy()) != null ) {
            sb.append("modified_by=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getStatus()) != null ) {
            sb.append("status=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRank()) != null ) {
            sb.append("rank=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCategories()) != null ) {
            sb.append("categories=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getKeywords()) != null ) {
            sb.append("keywords=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof AnonymousClassExpression) ) return false;
        final AnonymousClassExpression other = (AnonymousClassExpression) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$isA = this.getIsA();
        final Object other$isA = other.getIsA();
        if ( this$isA == null ? other$isA != null : !this$isA.equals(other$isA)) return false;
        final Object this$anyOf = this.getAnyOf();
        final Object other$anyOf = other.getAnyOf();
        if ( this$anyOf == null ? other$anyOf != null : !this$anyOf.equals(other$anyOf)) return false;
        final Object this$exactlyOneOf = this.getExactlyOneOf();
        final Object other$exactlyOneOf = other.getExactlyOneOf();
        if ( this$exactlyOneOf == null ? other$exactlyOneOf != null : !this$exactlyOneOf.equals(other$exactlyOneOf)) return false;
        final Object this$noneOf = this.getNoneOf();
        final Object other$noneOf = other.getNoneOf();
        if ( this$noneOf == null ? other$noneOf != null : !this$noneOf.equals(other$noneOf)) return false;
        final Object this$allOf = this.getAllOf();
        final Object other$allOf = other.getAllOf();
        if ( this$allOf == null ? other$allOf != null : !this$allOf.equals(other$allOf)) return false;
        final Object this$slotConditions = this.getSlotConditions();
        final Object other$slotConditions = other.getSlotConditions();
        if ( this$slotConditions == null ? other$slotConditions != null : !this$slotConditions.equals(other$slotConditions)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AnonymousClassExpression;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $isA = this.getIsA();
        result = result * PRIME + ($isA == null ? 43 : $isA.hashCode());
        final Object $anyOf = this.getAnyOf();
        result = result * PRIME + ($anyOf == null ? 43 : $anyOf.hashCode());
        final Object $exactlyOneOf = this.getExactlyOneOf();
        result = result * PRIME + ($exactlyOneOf == null ? 43 : $exactlyOneOf.hashCode());
        final Object $noneOf = this.getNoneOf();
        result = result * PRIME + ($noneOf == null ? 43 : $noneOf.hashCode());
        final Object $allOf = this.getAllOf();
        result = result * PRIME + ($allOf == null ? 43 : $allOf.hashCode());
        final Object $slotConditions = this.getSlotConditions();
        result = result * PRIME + ($slotConditions == null ? 43 : $slotConditions.hashCode());
        return result;
    }
}