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

@LinkURI("https://w3id.org/linkml/MatchQuery")
public class MatchQuery {

    @SlotName("identifier_pattern")
    @LinkURI("https://w3id.org/linkml/identifier_pattern")
    private String identifierPattern;

    @SlotName("source_ontology")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/source_ontology")
    private String sourceOntology;

    public void setIdentifierPattern(String identifierPattern) {
        this.identifierPattern = identifierPattern;
    }

    public String getIdentifierPattern() {
        return this.identifierPattern;
    }

    public void setSourceOntology(String sourceOntology) {
        this.sourceOntology = sourceOntology;
    }

    public String getSourceOntology() {
        return this.sourceOntology;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("MatchQuery(");
        if ( (o = this.getIdentifierPattern()) != null ) {
            sb.append("identifier_pattern=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSourceOntology()) != null ) {
            sb.append("source_ontology=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof MatchQuery) ) return false;
        final MatchQuery other = (MatchQuery) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$identifierPattern = this.getIdentifierPattern();
        final Object other$identifierPattern = other.getIdentifierPattern();
        if ( this$identifierPattern == null ? other$identifierPattern != null : !this$identifierPattern.equals(other$identifierPattern)) return false;
        final Object this$sourceOntology = this.getSourceOntology();
        final Object other$sourceOntology = other.getSourceOntology();
        if ( this$sourceOntology == null ? other$sourceOntology != null : !this$sourceOntology.equals(other$sourceOntology)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MatchQuery;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $identifierPattern = this.getIdentifierPattern();
        result = result * PRIME + ($identifierPattern == null ? 43 : $identifierPattern.hashCode());
        final Object $sourceOntology = this.getSourceOntology();
        result = result * PRIME + ($sourceOntology == null ? 43 : $sourceOntology.hashCode());
        return result;
    }
}