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

@LinkURI("https://w3id.org/linkml/LocalName")
public class LocalName {

    @Identifier(isGlobal = false)
    @SlotName("local_name_source")
    @LinkURI("https://w3id.org/linkml/local_name_source")
    private String localNameSource;

    @SlotName("local_name_value")
    @Required
    @LinkURI("http://www.w3.org/2004/02/skos/core#altLabel")
    private String localNameValue;

    public void setLocalNameSource(String localNameSource) {
        this.localNameSource = localNameSource;
    }

    public String getLocalNameSource() {
        return this.localNameSource;
    }

    public void setLocalNameValue(String localNameValue) {
        this.localNameValue = localNameValue;
    }

    public String getLocalNameValue() {
        return this.localNameValue;
    }

    @Override
    public String toString() {
        return "LocalName(local_name_source=" + this.getLocalNameSource() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof LocalName) ) return false;
        final LocalName other = (LocalName) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$localNameSource = this.getLocalNameSource();
        final Object other$localNameSource = other.getLocalNameSource();
        if ( this$localNameSource == null ? other$localNameSource != null : !this$localNameSource.equals(other$localNameSource)) return false;
        final Object this$localNameValue = this.getLocalNameValue();
        final Object other$localNameValue = other.getLocalNameValue();
        if ( this$localNameValue == null ? other$localNameValue != null : !this$localNameValue.equals(other$localNameValue)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LocalName;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $localNameSource = this.getLocalNameSource();
        result = result * PRIME + ($localNameSource == null ? 43 : $localNameSource.hashCode());
        final Object $localNameValue = this.getLocalNameValue();
        result = result * PRIME + ($localNameValue == null ? 43 : $localNameValue.hashCode());
        return result;
    }
}