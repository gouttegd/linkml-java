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

@LinkURI("https://w3id.org/linkml/TypeDefinition")
public class TypeDefinition extends Element {

    @LinkURI("https://w3id.org/linkml/typeof")
    private TypeDefinition typeof;

    @LinkURI("https://w3id.org/linkml/base")
    private String base;

    @SlotName("type_uri")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/type_uri")
    private String typeUri;

    @LinkURI("https://w3id.org/linkml/repr")
    private String repr;

    @SlotName("union_of")
    @LinkURI("https://w3id.org/linkml/union_of")
    private List<TypeDefinition> unionOf;

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

    @SlotName("equals_string")
    @LinkURI("https://w3id.org/linkml/equals_string")
    private String equalsString;

    @SlotName("equals_string_in")
    @LinkURI("https://w3id.org/linkml/equals_string_in")
    private List<String> equalsStringIn;

    @SlotName("equals_number")
    @LinkURI("https://w3id.org/linkml/equals_number")
    private Integer equalsNumber;

    @SlotName("minimum_value")
    @LinkURI("https://w3id.org/linkml/minimum_value")
    private Object minimumValue;

    @SlotName("maximum_value")
    @LinkURI("https://w3id.org/linkml/maximum_value")
    private Object maximumValue;

    @SlotName("none_of")
    @LinkURI("https://w3id.org/linkml/none_of")
    private List<AnonymousTypeExpression> noneOf;

    @SlotName("exactly_one_of")
    @LinkURI("https://w3id.org/linkml/exactly_one_of")
    private List<AnonymousTypeExpression> exactlyOneOf;

    @SlotName("any_of")
    @LinkURI("https://w3id.org/linkml/any_of")
    private List<AnonymousTypeExpression> anyOf;

    @SlotName("all_of")
    @LinkURI("https://w3id.org/linkml/all_of")
    private List<AnonymousTypeExpression> allOf;

    public void setTypeof(TypeDefinition typeof) {
        this.typeof = typeof;
    }

    public TypeDefinition getTypeof() {
        return this.typeof;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return this.base;
    }

    public void setTypeUri(String typeUri) {
        this.typeUri = typeUri;
    }

    public String getTypeUri() {
        return this.typeUri;
    }

    public void setRepr(String repr) {
        this.repr = repr;
    }

    public String getRepr() {
        return this.repr;
    }

    public void setUnionOf(List<TypeDefinition> unionOf) {
        this.unionOf = unionOf;
    }

    public List<TypeDefinition> getUnionOf() {
        return this.unionOf;
    }

    public List<TypeDefinition> getUnionOf(boolean set) {
        if ( this.unionOf == null && set ) {
            this.unionOf = new ArrayList<>();
        }
        return this.unionOf;
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

    public void setNoneOf(List<AnonymousTypeExpression> noneOf) {
        this.noneOf = noneOf;
    }

    public List<AnonymousTypeExpression> getNoneOf() {
        return this.noneOf;
    }

    public List<AnonymousTypeExpression> getNoneOf(boolean set) {
        if ( this.noneOf == null && set ) {
            this.noneOf = new ArrayList<>();
        }
        return this.noneOf;
    }

    public void setExactlyOneOf(List<AnonymousTypeExpression> exactlyOneOf) {
        this.exactlyOneOf = exactlyOneOf;
    }

    public List<AnonymousTypeExpression> getExactlyOneOf() {
        return this.exactlyOneOf;
    }

    public List<AnonymousTypeExpression> getExactlyOneOf(boolean set) {
        if ( this.exactlyOneOf == null && set ) {
            this.exactlyOneOf = new ArrayList<>();
        }
        return this.exactlyOneOf;
    }

    public void setAnyOf(List<AnonymousTypeExpression> anyOf) {
        this.anyOf = anyOf;
    }

    public List<AnonymousTypeExpression> getAnyOf() {
        return this.anyOf;
    }

    public List<AnonymousTypeExpression> getAnyOf(boolean set) {
        if ( this.anyOf == null && set ) {
            this.anyOf = new ArrayList<>();
        }
        return this.anyOf;
    }

    public void setAllOf(List<AnonymousTypeExpression> allOf) {
        this.allOf = allOf;
    }

    public List<AnonymousTypeExpression> getAllOf() {
        return this.allOf;
    }

    public List<AnonymousTypeExpression> getAllOf(boolean set) {
        if ( this.allOf == null && set ) {
            this.allOf = new ArrayList<>();
        }
        return this.allOf;
    }

    @Override
    public String toString() {
        return "TypeDefinition(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof TypeDefinition) ) return false;
        final TypeDefinition other = (TypeDefinition) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$typeof = this.getTypeof();
        final Object other$typeof = other.getTypeof();
        if ( this$typeof == null ? other$typeof != null : !this$typeof.equals(other$typeof)) return false;
        final Object this$base = this.getBase();
        final Object other$base = other.getBase();
        if ( this$base == null ? other$base != null : !this$base.equals(other$base)) return false;
        final Object this$typeUri = this.getTypeUri();
        final Object other$typeUri = other.getTypeUri();
        if ( this$typeUri == null ? other$typeUri != null : !this$typeUri.equals(other$typeUri)) return false;
        final Object this$repr = this.getRepr();
        final Object other$repr = other.getRepr();
        if ( this$repr == null ? other$repr != null : !this$repr.equals(other$repr)) return false;
        final Object this$unionOf = this.getUnionOf();
        final Object other$unionOf = other.getUnionOf();
        if ( this$unionOf == null ? other$unionOf != null : !this$unionOf.equals(other$unionOf)) return false;
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
        final Object this$equalsString = this.getEqualsString();
        final Object other$equalsString = other.getEqualsString();
        if ( this$equalsString == null ? other$equalsString != null : !this$equalsString.equals(other$equalsString)) return false;
        final Object this$equalsStringIn = this.getEqualsStringIn();
        final Object other$equalsStringIn = other.getEqualsStringIn();
        if ( this$equalsStringIn == null ? other$equalsStringIn != null : !this$equalsStringIn.equals(other$equalsStringIn)) return false;
        final Object this$equalsNumber = this.getEqualsNumber();
        final Object other$equalsNumber = other.getEqualsNumber();
        if ( this$equalsNumber == null ? other$equalsNumber != null : !this$equalsNumber.equals(other$equalsNumber)) return false;
        final Object this$minimumValue = this.getMinimumValue();
        final Object other$minimumValue = other.getMinimumValue();
        if ( this$minimumValue == null ? other$minimumValue != null : !this$minimumValue.equals(other$minimumValue)) return false;
        final Object this$maximumValue = this.getMaximumValue();
        final Object other$maximumValue = other.getMaximumValue();
        if ( this$maximumValue == null ? other$maximumValue != null : !this$maximumValue.equals(other$maximumValue)) return false;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TypeDefinition;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $typeof = this.getTypeof();
        result = result * PRIME + ($typeof == null ? 43 : $typeof.hashCode());
        final Object $base = this.getBase();
        result = result * PRIME + ($base == null ? 43 : $base.hashCode());
        final Object $typeUri = this.getTypeUri();
        result = result * PRIME + ($typeUri == null ? 43 : $typeUri.hashCode());
        final Object $repr = this.getRepr();
        result = result * PRIME + ($repr == null ? 43 : $repr.hashCode());
        final Object $unionOf = this.getUnionOf();
        result = result * PRIME + ($unionOf == null ? 43 : $unionOf.hashCode());
        final Object $pattern = this.getPattern();
        result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
        final Object $structuredPattern = this.getStructuredPattern();
        result = result * PRIME + ($structuredPattern == null ? 43 : $structuredPattern.hashCode());
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        final Object $implicitPrefix = this.getImplicitPrefix();
        result = result * PRIME + ($implicitPrefix == null ? 43 : $implicitPrefix.hashCode());
        final Object $equalsString = this.getEqualsString();
        result = result * PRIME + ($equalsString == null ? 43 : $equalsString.hashCode());
        final Object $equalsStringIn = this.getEqualsStringIn();
        result = result * PRIME + ($equalsStringIn == null ? 43 : $equalsStringIn.hashCode());
        final Object $equalsNumber = this.getEqualsNumber();
        result = result * PRIME + ($equalsNumber == null ? 43 : $equalsNumber.hashCode());
        final Object $minimumValue = this.getMinimumValue();
        result = result * PRIME + ($minimumValue == null ? 43 : $minimumValue.hashCode());
        final Object $maximumValue = this.getMaximumValue();
        result = result * PRIME + ($maximumValue == null ? 43 : $maximumValue.hashCode());
        final Object $noneOf = this.getNoneOf();
        result = result * PRIME + ($noneOf == null ? 43 : $noneOf.hashCode());
        final Object $exactlyOneOf = this.getExactlyOneOf();
        result = result * PRIME + ($exactlyOneOf == null ? 43 : $exactlyOneOf.hashCode());
        final Object $anyOf = this.getAnyOf();
        result = result * PRIME + ($anyOf == null ? 43 : $anyOf.hashCode());
        final Object $allOf = this.getAllOf();
        result = result * PRIME + ($allOf == null ? 43 : $allOf.hashCode());
        return result;
    }
}