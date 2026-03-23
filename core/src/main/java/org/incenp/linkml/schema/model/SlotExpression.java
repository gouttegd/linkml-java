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

@LinkURI("https://w3id.org/linkml/SlotExpression")
public class SlotExpression extends Expression {

    @LinkURI("https://w3id.org/linkml/range")
    private Element range;

    @SlotName("range_expression")
    @LinkURI("https://w3id.org/linkml/range_expression")
    private AnonymousClassExpression rangeExpression;

    @SlotName("enum_range")
    @LinkURI("https://w3id.org/linkml/enum_range")
    private EnumExpression enumRange;

    @Inlined
    @LinkURI("https://w3id.org/linkml/bindings")
    private List<EnumBinding> bindings;

    @LinkURI("https://w3id.org/linkml/required")
    private Boolean required;

    @LinkURI("https://w3id.org/linkml/recommended")
    private Boolean recommended;

    @LinkURI("https://w3id.org/linkml/multivalued")
    private Boolean multivalued;

    @LinkURI("https://w3id.org/linkml/inlined")
    private Boolean inlined;

    @SlotName("inlined_as_list")
    @LinkURI("https://w3id.org/linkml/inlined_as_list")
    private Boolean inlinedAsList;

    @SlotName("minimum_value")
    @LinkURI("https://w3id.org/linkml/minimum_value")
    private Object minimumValue;

    @SlotName("maximum_value")
    @LinkURI("https://w3id.org/linkml/maximum_value")
    private Object maximumValue;

    @LinkURI("https://w3id.org/linkml/pattern")
    private String pattern;

    @SlotName("structured_pattern")
    @LinkURI("https://w3id.org/linkml/structured_pattern")
    private PatternExpression structuredPattern;

    @LinkURI("http://qudt.org/schema/qudt/unit")
    private UnitOfMeasure unit;

    @SlotName("implicit_prefix")
    @LinkURI("https://w3id.org/linkml/implicit_prefix")
    private String implicitPrefix;

    @SlotName("value_presence")
    @LinkURI("https://w3id.org/linkml/value_presence")
    private PresenceEnum valuePresence;

    @SlotName("equals_string")
    @LinkURI("https://w3id.org/linkml/equals_string")
    private String equalsString;

    @SlotName("equals_string_in")
    @LinkURI("https://w3id.org/linkml/equals_string_in")
    private List<String> equalsStringIn;

    @SlotName("equals_number")
    @LinkURI("https://w3id.org/linkml/equals_number")
    private Integer equalsNumber;

    @SlotName("equals_expression")
    @LinkURI("https://w3id.org/linkml/equals_expression")
    private String equalsExpression;

    @SlotName("exact_cardinality")
    @LinkURI("https://w3id.org/linkml/exact_cardinality")
    private Integer exactCardinality;

    @SlotName("minimum_cardinality")
    @LinkURI("https://w3id.org/linkml/minimum_cardinality")
    private Integer minimumCardinality;

    @SlotName("maximum_cardinality")
    @LinkURI("https://w3id.org/linkml/maximum_cardinality")
    private Integer maximumCardinality;

    @SlotName("has_member")
    @LinkURI("https://w3id.org/linkml/has_member")
    private AnonymousSlotExpression hasMember;

    @SlotName("all_members")
    @LinkURI("https://w3id.org/linkml/all_members")
    private AnonymousSlotExpression allMembers;

    @SlotName("none_of")
    @LinkURI("https://w3id.org/linkml/none_of")
    private List<AnonymousSlotExpression> noneOf;

    @SlotName("exactly_one_of")
    @LinkURI("https://w3id.org/linkml/exactly_one_of")
    private List<AnonymousSlotExpression> exactlyOneOf;

    @SlotName("any_of")
    @LinkURI("https://w3id.org/linkml/any_of")
    private List<AnonymousSlotExpression> anyOf;

    @SlotName("all_of")
    @LinkURI("https://w3id.org/linkml/all_of")
    private List<AnonymousSlotExpression> allOf;

    @LinkURI("https://w3id.org/linkml/array")
    private ArrayExpression array;

    public void setRange(Element range) {
        this.range = range;
    }

    public Element getRange() {
        return this.range;
    }

    public void setRangeExpression(AnonymousClassExpression rangeExpression) {
        this.rangeExpression = rangeExpression;
    }

    public AnonymousClassExpression getRangeExpression() {
        return this.rangeExpression;
    }

    public void setEnumRange(EnumExpression enumRange) {
        this.enumRange = enumRange;
    }

    public EnumExpression getEnumRange() {
        return this.enumRange;
    }

    public void setBindings(List<EnumBinding> bindings) {
        this.bindings = bindings;
    }

    public List<EnumBinding> getBindings() {
        return this.bindings;
    }

    public List<EnumBinding> getBindings(boolean set) {
        if ( this.bindings == null && set ) {
            this.bindings = new ArrayList<>();
        }
        return this.bindings;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public Boolean getRecommended() {
        return this.recommended;
    }

    public void setMultivalued(Boolean multivalued) {
        this.multivalued = multivalued;
    }

    public Boolean getMultivalued() {
        return this.multivalued;
    }

    public void setInlined(Boolean inlined) {
        this.inlined = inlined;
    }

    public Boolean getInlined() {
        return this.inlined;
    }

    public void setInlinedAsList(Boolean inlinedAsList) {
        this.inlinedAsList = inlinedAsList;
    }

    public Boolean getInlinedAsList() {
        return this.inlinedAsList;
    }

    public void setMinimumValue(Object minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Object getMinimumValue() {
        return this.minimumValue;
    }

    public void setMaximumValue(Object maximumValue) {
        this.maximumValue = maximumValue;
    }

    public Object getMaximumValue() {
        return this.maximumValue;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setStructuredPattern(PatternExpression structuredPattern) {
        this.structuredPattern = structuredPattern;
    }

    public PatternExpression getStructuredPattern() {
        return this.structuredPattern;
    }

    public void setUnit(UnitOfMeasure unit) {
        this.unit = unit;
    }

    public UnitOfMeasure getUnit() {
        return this.unit;
    }

    public void setImplicitPrefix(String implicitPrefix) {
        this.implicitPrefix = implicitPrefix;
    }

    public String getImplicitPrefix() {
        return this.implicitPrefix;
    }

    public void setValuePresence(PresenceEnum valuePresence) {
        this.valuePresence = valuePresence;
    }

    public PresenceEnum getValuePresence() {
        return this.valuePresence;
    }

    public void setEqualsString(String equalsString) {
        this.equalsString = equalsString;
    }

    public String getEqualsString() {
        return this.equalsString;
    }

    public void setEqualsStringIn(List<String> equalsStringIn) {
        this.equalsStringIn = equalsStringIn;
    }

    public List<String> getEqualsStringIn() {
        return this.equalsStringIn;
    }

    public List<String> getEqualsStringIn(boolean set) {
        if ( this.equalsStringIn == null && set ) {
            this.equalsStringIn = new ArrayList<>();
        }
        return this.equalsStringIn;
    }

    public void setEqualsNumber(Integer equalsNumber) {
        this.equalsNumber = equalsNumber;
    }

    public Integer getEqualsNumber() {
        return this.equalsNumber;
    }

    public void setEqualsExpression(String equalsExpression) {
        this.equalsExpression = equalsExpression;
    }

    public String getEqualsExpression() {
        return this.equalsExpression;
    }

    public void setExactCardinality(Integer exactCardinality) {
        this.exactCardinality = exactCardinality;
    }

    public Integer getExactCardinality() {
        return this.exactCardinality;
    }

    public void setMinimumCardinality(Integer minimumCardinality) {
        this.minimumCardinality = minimumCardinality;
    }

    public Integer getMinimumCardinality() {
        return this.minimumCardinality;
    }

    public void setMaximumCardinality(Integer maximumCardinality) {
        this.maximumCardinality = maximumCardinality;
    }

    public Integer getMaximumCardinality() {
        return this.maximumCardinality;
    }

    public void setHasMember(AnonymousSlotExpression hasMember) {
        this.hasMember = hasMember;
    }

    public AnonymousSlotExpression getHasMember() {
        return this.hasMember;
    }

    public void setAllMembers(AnonymousSlotExpression allMembers) {
        this.allMembers = allMembers;
    }

    public AnonymousSlotExpression getAllMembers() {
        return this.allMembers;
    }

    public void setNoneOf(List<AnonymousSlotExpression> noneOf) {
        this.noneOf = noneOf;
    }

    public List<AnonymousSlotExpression> getNoneOf() {
        return this.noneOf;
    }

    public List<AnonymousSlotExpression> getNoneOf(boolean set) {
        if ( this.noneOf == null && set ) {
            this.noneOf = new ArrayList<>();
        }
        return this.noneOf;
    }

    public void setExactlyOneOf(List<AnonymousSlotExpression> exactlyOneOf) {
        this.exactlyOneOf = exactlyOneOf;
    }

    public List<AnonymousSlotExpression> getExactlyOneOf() {
        return this.exactlyOneOf;
    }

    public List<AnonymousSlotExpression> getExactlyOneOf(boolean set) {
        if ( this.exactlyOneOf == null && set ) {
            this.exactlyOneOf = new ArrayList<>();
        }
        return this.exactlyOneOf;
    }

    public void setAnyOf(List<AnonymousSlotExpression> anyOf) {
        this.anyOf = anyOf;
    }

    public List<AnonymousSlotExpression> getAnyOf() {
        return this.anyOf;
    }

    public List<AnonymousSlotExpression> getAnyOf(boolean set) {
        if ( this.anyOf == null && set ) {
            this.anyOf = new ArrayList<>();
        }
        return this.anyOf;
    }

    public void setAllOf(List<AnonymousSlotExpression> allOf) {
        this.allOf = allOf;
    }

    public List<AnonymousSlotExpression> getAllOf() {
        return this.allOf;
    }

    public List<AnonymousSlotExpression> getAllOf(boolean set) {
        if ( this.allOf == null && set ) {
            this.allOf = new ArrayList<>();
        }
        return this.allOf;
    }

    public void setArray(ArrayExpression array) {
        this.array = array;
    }

    public ArrayExpression getArray() {
        return this.array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("SlotExpression(");
        if ( (o = this.getRange()) != null ) {
            sb.append("range=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRangeExpression()) != null ) {
            sb.append("range_expression=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getEnumRange()) != null ) {
            sb.append("enum_range=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBindings()) != null ) {
            sb.append("bindings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRequired()) != null ) {
            sb.append("required=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRecommended()) != null ) {
            sb.append("recommended=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultivalued()) != null ) {
            sb.append("multivalued=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInlined()) != null ) {
            sb.append("inlined=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInlinedAsList()) != null ) {
            sb.append("inlined_as_list=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMinimumValue()) != null ) {
            sb.append("minimum_value=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMaximumValue()) != null ) {
            sb.append("maximum_value=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getPattern()) != null ) {
            sb.append("pattern=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getStructuredPattern()) != null ) {
            sb.append("structured_pattern=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getUnit()) != null ) {
            sb.append("unit=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getImplicitPrefix()) != null ) {
            sb.append("implicit_prefix=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getValuePresence()) != null ) {
            sb.append("value_presence=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getEqualsString()) != null ) {
            sb.append("equals_string=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getEqualsStringIn()) != null ) {
            sb.append("equals_string_in=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getEqualsNumber()) != null ) {
            sb.append("equals_number=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getEqualsExpression()) != null ) {
            sb.append("equals_expression=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExactCardinality()) != null ) {
            sb.append("exact_cardinality=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMinimumCardinality()) != null ) {
            sb.append("minimum_cardinality=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMaximumCardinality()) != null ) {
            sb.append("maximum_cardinality=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getHasMember()) != null ) {
            sb.append("has_member=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAllMembers()) != null ) {
            sb.append("all_members=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getNoneOf()) != null ) {
            sb.append("none_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExactlyOneOf()) != null ) {
            sb.append("exactly_one_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAnyOf()) != null ) {
            sb.append("any_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAllOf()) != null ) {
            sb.append("all_of=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getArray()) != null ) {
            sb.append("array=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof SlotExpression) ) return false;
        final SlotExpression other = (SlotExpression) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$range = this.getRange();
        final Object other$range = other.getRange();
        if ( this$range == null ? other$range != null : !this$range.equals(other$range)) return false;
        final Object this$rangeExpression = this.getRangeExpression();
        final Object other$rangeExpression = other.getRangeExpression();
        if ( this$rangeExpression == null ? other$rangeExpression != null : !this$rangeExpression.equals(other$rangeExpression)) return false;
        final Object this$enumRange = this.getEnumRange();
        final Object other$enumRange = other.getEnumRange();
        if ( this$enumRange == null ? other$enumRange != null : !this$enumRange.equals(other$enumRange)) return false;
        final Object this$bindings = this.getBindings();
        final Object other$bindings = other.getBindings();
        if ( this$bindings == null ? other$bindings != null : !this$bindings.equals(other$bindings)) return false;
        final Object this$required = this.getRequired();
        final Object other$required = other.getRequired();
        if ( this$required == null ? other$required != null : !this$required.equals(other$required)) return false;
        final Object this$recommended = this.getRecommended();
        final Object other$recommended = other.getRecommended();
        if ( this$recommended == null ? other$recommended != null : !this$recommended.equals(other$recommended)) return false;
        final Object this$multivalued = this.getMultivalued();
        final Object other$multivalued = other.getMultivalued();
        if ( this$multivalued == null ? other$multivalued != null : !this$multivalued.equals(other$multivalued)) return false;
        final Object this$inlined = this.getInlined();
        final Object other$inlined = other.getInlined();
        if ( this$inlined == null ? other$inlined != null : !this$inlined.equals(other$inlined)) return false;
        final Object this$inlinedAsList = this.getInlinedAsList();
        final Object other$inlinedAsList = other.getInlinedAsList();
        if ( this$inlinedAsList == null ? other$inlinedAsList != null : !this$inlinedAsList.equals(other$inlinedAsList)) return false;
        final Object this$minimumValue = this.getMinimumValue();
        final Object other$minimumValue = other.getMinimumValue();
        if ( this$minimumValue == null ? other$minimumValue != null : !this$minimumValue.equals(other$minimumValue)) return false;
        final Object this$maximumValue = this.getMaximumValue();
        final Object other$maximumValue = other.getMaximumValue();
        if ( this$maximumValue == null ? other$maximumValue != null : !this$maximumValue.equals(other$maximumValue)) return false;
        final Object this$pattern = this.getPattern();
        final Object other$pattern = other.getPattern();
        if ( this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) return false;
        final Object this$structuredPattern = this.getStructuredPattern();
        final Object other$structuredPattern = other.getStructuredPattern();
        if ( this$structuredPattern == null ? other$structuredPattern != null : !this$structuredPattern.equals(other$structuredPattern)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if ( this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) return false;
        final Object this$implicitPrefix = this.getImplicitPrefix();
        final Object other$implicitPrefix = other.getImplicitPrefix();
        if ( this$implicitPrefix == null ? other$implicitPrefix != null : !this$implicitPrefix.equals(other$implicitPrefix)) return false;
        final Object this$valuePresence = this.getValuePresence();
        final Object other$valuePresence = other.getValuePresence();
        if ( this$valuePresence == null ? other$valuePresence != null : !this$valuePresence.equals(other$valuePresence)) return false;
        final Object this$equalsString = this.getEqualsString();
        final Object other$equalsString = other.getEqualsString();
        if ( this$equalsString == null ? other$equalsString != null : !this$equalsString.equals(other$equalsString)) return false;
        final Object this$equalsStringIn = this.getEqualsStringIn();
        final Object other$equalsStringIn = other.getEqualsStringIn();
        if ( this$equalsStringIn == null ? other$equalsStringIn != null : !this$equalsStringIn.equals(other$equalsStringIn)) return false;
        final Object this$equalsNumber = this.getEqualsNumber();
        final Object other$equalsNumber = other.getEqualsNumber();
        if ( this$equalsNumber == null ? other$equalsNumber != null : !this$equalsNumber.equals(other$equalsNumber)) return false;
        final Object this$equalsExpression = this.getEqualsExpression();
        final Object other$equalsExpression = other.getEqualsExpression();
        if ( this$equalsExpression == null ? other$equalsExpression != null : !this$equalsExpression.equals(other$equalsExpression)) return false;
        final Object this$exactCardinality = this.getExactCardinality();
        final Object other$exactCardinality = other.getExactCardinality();
        if ( this$exactCardinality == null ? other$exactCardinality != null : !this$exactCardinality.equals(other$exactCardinality)) return false;
        final Object this$minimumCardinality = this.getMinimumCardinality();
        final Object other$minimumCardinality = other.getMinimumCardinality();
        if ( this$minimumCardinality == null ? other$minimumCardinality != null : !this$minimumCardinality.equals(other$minimumCardinality)) return false;
        final Object this$maximumCardinality = this.getMaximumCardinality();
        final Object other$maximumCardinality = other.getMaximumCardinality();
        if ( this$maximumCardinality == null ? other$maximumCardinality != null : !this$maximumCardinality.equals(other$maximumCardinality)) return false;
        final Object this$hasMember = this.getHasMember();
        final Object other$hasMember = other.getHasMember();
        if ( this$hasMember == null ? other$hasMember != null : !this$hasMember.equals(other$hasMember)) return false;
        final Object this$allMembers = this.getAllMembers();
        final Object other$allMembers = other.getAllMembers();
        if ( this$allMembers == null ? other$allMembers != null : !this$allMembers.equals(other$allMembers)) return false;
        final Object this$noneOf = this.getNoneOf();
        final Object other$noneOf = other.getNoneOf();
        if ( this$noneOf == null ? other$noneOf != null : !this$noneOf.equals(other$noneOf)) return false;
        final Object this$exactlyOneOf = this.getExactlyOneOf();
        final Object other$exactlyOneOf = other.getExactlyOneOf();
        if ( this$exactlyOneOf == null ? other$exactlyOneOf != null : !this$exactlyOneOf.equals(other$exactlyOneOf)) return false;
        final Object this$anyOf = this.getAnyOf();
        final Object other$anyOf = other.getAnyOf();
        if ( this$anyOf == null ? other$anyOf != null : !this$anyOf.equals(other$anyOf)) return false;
        final Object this$allOf = this.getAllOf();
        final Object other$allOf = other.getAllOf();
        if ( this$allOf == null ? other$allOf != null : !this$allOf.equals(other$allOf)) return false;
        final Object this$array = this.getArray();
        final Object other$array = other.getArray();
        if ( this$array == null ? other$array != null : !this$array.equals(other$array)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SlotExpression;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $range = this.getRange();
        result = result * PRIME + ($range == null ? 43 : $range.hashCode());
        final Object $rangeExpression = this.getRangeExpression();
        result = result * PRIME + ($rangeExpression == null ? 43 : $rangeExpression.hashCode());
        final Object $enumRange = this.getEnumRange();
        result = result * PRIME + ($enumRange == null ? 43 : $enumRange.hashCode());
        final Object $bindings = this.getBindings();
        result = result * PRIME + ($bindings == null ? 43 : $bindings.hashCode());
        final Object $required = this.getRequired();
        result = result * PRIME + ($required == null ? 43 : $required.hashCode());
        final Object $recommended = this.getRecommended();
        result = result * PRIME + ($recommended == null ? 43 : $recommended.hashCode());
        final Object $multivalued = this.getMultivalued();
        result = result * PRIME + ($multivalued == null ? 43 : $multivalued.hashCode());
        final Object $inlined = this.getInlined();
        result = result * PRIME + ($inlined == null ? 43 : $inlined.hashCode());
        final Object $inlinedAsList = this.getInlinedAsList();
        result = result * PRIME + ($inlinedAsList == null ? 43 : $inlinedAsList.hashCode());
        final Object $minimumValue = this.getMinimumValue();
        result = result * PRIME + ($minimumValue == null ? 43 : $minimumValue.hashCode());
        final Object $maximumValue = this.getMaximumValue();
        result = result * PRIME + ($maximumValue == null ? 43 : $maximumValue.hashCode());
        final Object $pattern = this.getPattern();
        result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
        final Object $structuredPattern = this.getStructuredPattern();
        result = result * PRIME + ($structuredPattern == null ? 43 : $structuredPattern.hashCode());
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        final Object $implicitPrefix = this.getImplicitPrefix();
        result = result * PRIME + ($implicitPrefix == null ? 43 : $implicitPrefix.hashCode());
        final Object $valuePresence = this.getValuePresence();
        result = result * PRIME + ($valuePresence == null ? 43 : $valuePresence.hashCode());
        final Object $equalsString = this.getEqualsString();
        result = result * PRIME + ($equalsString == null ? 43 : $equalsString.hashCode());
        final Object $equalsStringIn = this.getEqualsStringIn();
        result = result * PRIME + ($equalsStringIn == null ? 43 : $equalsStringIn.hashCode());
        final Object $equalsNumber = this.getEqualsNumber();
        result = result * PRIME + ($equalsNumber == null ? 43 : $equalsNumber.hashCode());
        final Object $equalsExpression = this.getEqualsExpression();
        result = result * PRIME + ($equalsExpression == null ? 43 : $equalsExpression.hashCode());
        final Object $exactCardinality = this.getExactCardinality();
        result = result * PRIME + ($exactCardinality == null ? 43 : $exactCardinality.hashCode());
        final Object $minimumCardinality = this.getMinimumCardinality();
        result = result * PRIME + ($minimumCardinality == null ? 43 : $minimumCardinality.hashCode());
        final Object $maximumCardinality = this.getMaximumCardinality();
        result = result * PRIME + ($maximumCardinality == null ? 43 : $maximumCardinality.hashCode());
        final Object $hasMember = this.getHasMember();
        result = result * PRIME + ($hasMember == null ? 43 : $hasMember.hashCode());
        final Object $allMembers = this.getAllMembers();
        result = result * PRIME + ($allMembers == null ? 43 : $allMembers.hashCode());
        final Object $noneOf = this.getNoneOf();
        result = result * PRIME + ($noneOf == null ? 43 : $noneOf.hashCode());
        final Object $exactlyOneOf = this.getExactlyOneOf();
        result = result * PRIME + ($exactlyOneOf == null ? 43 : $exactlyOneOf.hashCode());
        final Object $anyOf = this.getAnyOf();
        result = result * PRIME + ($anyOf == null ? 43 : $anyOf.hashCode());
        final Object $allOf = this.getAllOf();
        result = result * PRIME + ($allOf == null ? 43 : $allOf.hashCode());
        final Object $array = this.getArray();
        result = result * PRIME + ($array == null ? 43 : $array.hashCode());
        return result;
    }
}