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

@LinkURI("http://qudt.org/schema/qudt/Unit")
public class UnitOfMeasure {

    @LinkURI("http://qudt.org/schema/qudt/symbol")
    private String symbol;

    @LinkURI("http://qudt.org/schema/qudt/abbreviation")
    private String abbreviation;

    @SlotName("descriptive_name")
    @LinkURI("rdfs:label")
    private String descriptiveName;

    @SlotName("exact mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#exactMatch")
    private List<String> exactMappings;

    @SlotName("ucum_code")
    @Required(isRecommended = true)
    @LinkURI("http://qudt.org/schema/qudt/ucumCode")
    private String ucumCode;

    @LinkURI("https://w3id.org/linkml/derivation")
    private String derivation;

    @SlotName("has_quantity_kind")
    @Converter(CurieConverter.class)
    @LinkURI("http://qudt.org/schema/qudt/hasQuantityKind")
    private String hasQuantityKind;

    @LinkURI("http://qudt.org/schema/qudt/iec61360Code")
    private String iec61360code;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setDescriptiveName(String descriptiveName) {
        this.descriptiveName = descriptiveName;
    }

    public String getDescriptiveName() {
        return this.descriptiveName;
    }

    public void setExactMappings(List<String> exactMappings) {
        this.exactMappings = exactMappings;
    }

    public List<String> getExactMappings() {
        return this.exactMappings;
    }

    public List<String> getExactMappings(boolean set) {
        if ( this.exactMappings == null && set ) {
            this.exactMappings = new ArrayList<>();
        }
        return this.exactMappings;
    }

    public void setUcumCode(String ucumCode) {
        this.ucumCode = ucumCode;
    }

    public String getUcumCode() {
        return this.ucumCode;
    }

    public void setDerivation(String derivation) {
        this.derivation = derivation;
    }

    public String getDerivation() {
        return this.derivation;
    }

    public void setHasQuantityKind(String hasQuantityKind) {
        this.hasQuantityKind = hasQuantityKind;
    }

    public String getHasQuantityKind() {
        return this.hasQuantityKind;
    }

    public void setIec61360code(String iec61360code) {
        this.iec61360code = iec61360code;
    }

    public String getIec61360code() {
        return this.iec61360code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("UnitOfMeasure(");
        if ( (o = this.getSymbol()) != null ) {
            sb.append("symbol=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getAbbreviation()) != null ) {
            sb.append("abbreviation=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDescriptiveName()) != null ) {
            sb.append("descriptive_name=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExactMappings()) != null ) {
            sb.append("exact mappings=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getUcumCode()) != null ) {
            sb.append("ucum_code=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDerivation()) != null ) {
            sb.append("derivation=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getHasQuantityKind()) != null ) {
            sb.append("has_quantity_kind=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getIec61360code()) != null ) {
            sb.append("iec61360code=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof UnitOfMeasure) ) return false;
        final UnitOfMeasure other = (UnitOfMeasure) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$symbol = this.getSymbol();
        final Object other$symbol = other.getSymbol();
        if ( this$symbol == null ? other$symbol != null : !this$symbol.equals(other$symbol)) return false;
        final Object this$abbreviation = this.getAbbreviation();
        final Object other$abbreviation = other.getAbbreviation();
        if ( this$abbreviation == null ? other$abbreviation != null : !this$abbreviation.equals(other$abbreviation)) return false;
        final Object this$descriptiveName = this.getDescriptiveName();
        final Object other$descriptiveName = other.getDescriptiveName();
        if ( this$descriptiveName == null ? other$descriptiveName != null : !this$descriptiveName.equals(other$descriptiveName)) return false;
        final Object this$exactMappings = this.getExactMappings();
        final Object other$exactMappings = other.getExactMappings();
        if ( this$exactMappings == null ? other$exactMappings != null : !this$exactMappings.equals(other$exactMappings)) return false;
        final Object this$ucumCode = this.getUcumCode();
        final Object other$ucumCode = other.getUcumCode();
        if ( this$ucumCode == null ? other$ucumCode != null : !this$ucumCode.equals(other$ucumCode)) return false;
        final Object this$derivation = this.getDerivation();
        final Object other$derivation = other.getDerivation();
        if ( this$derivation == null ? other$derivation != null : !this$derivation.equals(other$derivation)) return false;
        final Object this$hasQuantityKind = this.getHasQuantityKind();
        final Object other$hasQuantityKind = other.getHasQuantityKind();
        if ( this$hasQuantityKind == null ? other$hasQuantityKind != null : !this$hasQuantityKind.equals(other$hasQuantityKind)) return false;
        final Object this$iec61360code = this.getIec61360code();
        final Object other$iec61360code = other.getIec61360code();
        if ( this$iec61360code == null ? other$iec61360code != null : !this$iec61360code.equals(other$iec61360code)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UnitOfMeasure;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $symbol = this.getSymbol();
        result = result * PRIME + ($symbol == null ? 43 : $symbol.hashCode());
        final Object $abbreviation = this.getAbbreviation();
        result = result * PRIME + ($abbreviation == null ? 43 : $abbreviation.hashCode());
        final Object $descriptiveName = this.getDescriptiveName();
        result = result * PRIME + ($descriptiveName == null ? 43 : $descriptiveName.hashCode());
        final Object $exactMappings = this.getExactMappings();
        result = result * PRIME + ($exactMappings == null ? 43 : $exactMappings.hashCode());
        final Object $ucumCode = this.getUcumCode();
        result = result * PRIME + ($ucumCode == null ? 43 : $ucumCode.hashCode());
        final Object $derivation = this.getDerivation();
        result = result * PRIME + ($derivation == null ? 43 : $derivation.hashCode());
        final Object $hasQuantityKind = this.getHasQuantityKind();
        result = result * PRIME + ($hasQuantityKind == null ? 43 : $hasQuantityKind.hashCode());
        final Object $iec61360code = this.getIec61360code();
        result = result * PRIME + ($iec61360code == null ? 43 : $iec61360code.hashCode());
        return result;
    }
}