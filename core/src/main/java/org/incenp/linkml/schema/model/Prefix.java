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

@LinkURI("https://w3id.org/linkml/Prefix")
public class Prefix {

    @Identifier(isGlobal = false)
    @SlotName("prefix_prefix")
    @LinkURI("http://www.w3.org/ns/shacl#prefix")
    private String prefixPrefix;

    @SlotName("prefix_reference")
    @Required
    @LinkURI("http://www.w3.org/ns/shacl#namespace")
    private URI prefixReference;

    public void setPrefixPrefix(String prefixPrefix) {
        this.prefixPrefix = prefixPrefix;
    }

    public String getPrefixPrefix() {
        return this.prefixPrefix;
    }

    public void setPrefixReference(URI prefixReference) {
        this.prefixReference = prefixReference;
    }

    public URI getPrefixReference() {
        return this.prefixReference;
    }

    @Override
    public String toString() {
        return "Prefix(prefix_prefix=" + this.getPrefixPrefix() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Prefix) ) return false;
        final Prefix other = (Prefix) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$prefixPrefix = this.getPrefixPrefix();
        final Object other$prefixPrefix = other.getPrefixPrefix();
        if ( this$prefixPrefix == null ? other$prefixPrefix != null : !this$prefixPrefix.equals(other$prefixPrefix)) return false;
        final Object this$prefixReference = this.getPrefixReference();
        final Object other$prefixReference = other.getPrefixReference();
        if ( this$prefixReference == null ? other$prefixReference != null : !this$prefixReference.equals(other$prefixReference)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Prefix;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $prefixPrefix = this.getPrefixPrefix();
        result = result * PRIME + ($prefixPrefix == null ? 43 : $prefixPrefix.hashCode());
        final Object $prefixReference = this.getPrefixReference();
        result = result * PRIME + ($prefixReference == null ? 43 : $prefixReference.hashCode());
        return result;
    }
}