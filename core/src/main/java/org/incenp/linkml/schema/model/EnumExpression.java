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

@LinkURI("https://w3id.org/linkml/EnumExpression")
public class EnumExpression extends Expression {

    @SlotName("code_set")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/code_set")
    private String codeSet;

    @SlotName("code_set_tag")
    @LinkURI("https://w3id.org/linkml/code_set_tag")
    private String codeSetTag;

    @SlotName("code_set_version")
    @LinkURI("https://w3id.org/linkml/code_set_version")
    private String codeSetVersion;

    @SlotName("pv_formula")
    @LinkURI("https://w3id.org/linkml/pv_formula")
    private PvFormulaOptions pvFormula;

    @SlotName("permissible_values")
    @Inlined
    @LinkURI("https://w3id.org/linkml/permissible_values")
    private List<PermissibleValue> permissibleValues;

    @LinkURI("https://w3id.org/linkml/include")
    private List<AnonymousEnumExpression> include;

    @LinkURI("https://w3id.org/linkml/minus")
    private List<AnonymousEnumExpression> minus;

    @LinkURI("https://w3id.org/linkml/inherits")
    private List<EnumDefinition> inherits;

    @SlotName("reachable_from")
    @LinkURI("https://w3id.org/linkml/reachable_from")
    private ReachabilityQuery reachableFrom;

    @LinkURI("https://w3id.org/linkml/matches")
    private MatchQuery matches;

    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/concepts")
    private List<String> concepts;

    public void setCodeSet(String codeSet) {
        this.codeSet = codeSet;
    }

    public String getCodeSet() {
        return this.codeSet;
    }

    public void setCodeSetTag(String codeSetTag) {
        this.codeSetTag = codeSetTag;
    }

    public String getCodeSetTag() {
        return this.codeSetTag;
    }

    public void setCodeSetVersion(String codeSetVersion) {
        this.codeSetVersion = codeSetVersion;
    }

    public String getCodeSetVersion() {
        return this.codeSetVersion;
    }

    public void setPvFormula(PvFormulaOptions pvFormula) {
        this.pvFormula = pvFormula;
    }

    public PvFormulaOptions getPvFormula() {
        return this.pvFormula;
    }

    public void setPermissibleValues(List<PermissibleValue> permissibleValues) {
        this.permissibleValues = permissibleValues;
    }

    public List<PermissibleValue> getPermissibleValues() {
        return this.permissibleValues;
    }

    public List<PermissibleValue> getPermissibleValues(boolean set) {
        if ( this.permissibleValues == null && set ) {
            this.permissibleValues = new ArrayList<>();
        }
        return this.permissibleValues;
    }

    public void setInclude(List<AnonymousEnumExpression> include) {
        this.include = include;
    }

    public List<AnonymousEnumExpression> getInclude() {
        return this.include;
    }

    public List<AnonymousEnumExpression> getInclude(boolean set) {
        if ( this.include == null && set ) {
            this.include = new ArrayList<>();
        }
        return this.include;
    }

    public void setMinus(List<AnonymousEnumExpression> minus) {
        this.minus = minus;
    }

    public List<AnonymousEnumExpression> getMinus() {
        return this.minus;
    }

    public List<AnonymousEnumExpression> getMinus(boolean set) {
        if ( this.minus == null && set ) {
            this.minus = new ArrayList<>();
        }
        return this.minus;
    }

    public void setInherits(List<EnumDefinition> inherits) {
        this.inherits = inherits;
    }

    public List<EnumDefinition> getInherits() {
        return this.inherits;
    }

    public List<EnumDefinition> getInherits(boolean set) {
        if ( this.inherits == null && set ) {
            this.inherits = new ArrayList<>();
        }
        return this.inherits;
    }

    public void setReachableFrom(ReachabilityQuery reachableFrom) {
        this.reachableFrom = reachableFrom;
    }

    public ReachabilityQuery getReachableFrom() {
        return this.reachableFrom;
    }

    public void setMatches(MatchQuery matches) {
        this.matches = matches;
    }

    public MatchQuery getMatches() {
        return this.matches;
    }

    public void setConcepts(List<String> concepts) {
        this.concepts = concepts;
    }

    public List<String> getConcepts() {
        return this.concepts;
    }

    public List<String> getConcepts(boolean set) {
        if ( this.concepts == null && set ) {
            this.concepts = new ArrayList<>();
        }
        return this.concepts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("EnumExpression(");
        if ( (o = this.getCodeSet()) != null ) {
            sb.append("code_set=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCodeSetTag()) != null ) {
            sb.append("code_set_tag=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCodeSetVersion()) != null ) {
            sb.append("code_set_version=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getPvFormula()) != null ) {
            sb.append("pv_formula=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getPermissibleValues()) != null ) {
            sb.append("permissible_values=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInclude()) != null ) {
            sb.append("include=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMinus()) != null ) {
            sb.append("minus=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInherits()) != null ) {
            sb.append("inherits=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getReachableFrom()) != null ) {
            sb.append("reachable_from=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMatches()) != null ) {
            sb.append("matches=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getConcepts()) != null ) {
            sb.append("concepts=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof EnumExpression) ) return false;
        final EnumExpression other = (EnumExpression) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$codeSet = this.getCodeSet();
        final Object other$codeSet = other.getCodeSet();
        if ( this$codeSet == null ? other$codeSet != null : !this$codeSet.equals(other$codeSet)) return false;
        final Object this$codeSetTag = this.getCodeSetTag();
        final Object other$codeSetTag = other.getCodeSetTag();
        if ( this$codeSetTag == null ? other$codeSetTag != null : !this$codeSetTag.equals(other$codeSetTag)) return false;
        final Object this$codeSetVersion = this.getCodeSetVersion();
        final Object other$codeSetVersion = other.getCodeSetVersion();
        if ( this$codeSetVersion == null ? other$codeSetVersion != null : !this$codeSetVersion.equals(other$codeSetVersion)) return false;
        final Object this$pvFormula = this.getPvFormula();
        final Object other$pvFormula = other.getPvFormula();
        if ( this$pvFormula == null ? other$pvFormula != null : !this$pvFormula.equals(other$pvFormula)) return false;
        final Object this$permissibleValues = this.getPermissibleValues();
        final Object other$permissibleValues = other.getPermissibleValues();
        if ( this$permissibleValues == null ? other$permissibleValues != null : !this$permissibleValues.equals(other$permissibleValues)) return false;
        final Object this$include = this.getInclude();
        final Object other$include = other.getInclude();
        if ( this$include == null ? other$include != null : !this$include.equals(other$include)) return false;
        final Object this$minus = this.getMinus();
        final Object other$minus = other.getMinus();
        if ( this$minus == null ? other$minus != null : !this$minus.equals(other$minus)) return false;
        final Object this$inherits = this.getInherits();
        final Object other$inherits = other.getInherits();
        if ( this$inherits == null ? other$inherits != null : !this$inherits.equals(other$inherits)) return false;
        final Object this$reachableFrom = this.getReachableFrom();
        final Object other$reachableFrom = other.getReachableFrom();
        if ( this$reachableFrom == null ? other$reachableFrom != null : !this$reachableFrom.equals(other$reachableFrom)) return false;
        final Object this$matches = this.getMatches();
        final Object other$matches = other.getMatches();
        if ( this$matches == null ? other$matches != null : !this$matches.equals(other$matches)) return false;
        final Object this$concepts = this.getConcepts();
        final Object other$concepts = other.getConcepts();
        if ( this$concepts == null ? other$concepts != null : !this$concepts.equals(other$concepts)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EnumExpression;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $codeSet = this.getCodeSet();
        result = result * PRIME + ($codeSet == null ? 43 : $codeSet.hashCode());
        final Object $codeSetTag = this.getCodeSetTag();
        result = result * PRIME + ($codeSetTag == null ? 43 : $codeSetTag.hashCode());
        final Object $codeSetVersion = this.getCodeSetVersion();
        result = result * PRIME + ($codeSetVersion == null ? 43 : $codeSetVersion.hashCode());
        final Object $pvFormula = this.getPvFormula();
        result = result * PRIME + ($pvFormula == null ? 43 : $pvFormula.hashCode());
        final Object $permissibleValues = this.getPermissibleValues();
        result = result * PRIME + ($permissibleValues == null ? 43 : $permissibleValues.hashCode());
        final Object $include = this.getInclude();
        result = result * PRIME + ($include == null ? 43 : $include.hashCode());
        final Object $minus = this.getMinus();
        result = result * PRIME + ($minus == null ? 43 : $minus.hashCode());
        final Object $inherits = this.getInherits();
        result = result * PRIME + ($inherits == null ? 43 : $inherits.hashCode());
        final Object $reachableFrom = this.getReachableFrom();
        result = result * PRIME + ($reachableFrom == null ? 43 : $reachableFrom.hashCode());
        final Object $matches = this.getMatches();
        result = result * PRIME + ($matches == null ? 43 : $matches.hashCode());
        final Object $concepts = this.getConcepts();
        result = result * PRIME + ($concepts == null ? 43 : $concepts.hashCode());
        return result;
    }
}