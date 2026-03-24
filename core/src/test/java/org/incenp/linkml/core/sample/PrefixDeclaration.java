package org.incenp.linkml.core.sample;

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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#PrefixDeclaration")
public class PrefixDeclaration {

    @Identifier(isGlobal = false)
    @LinkURI("http://www.w3.org/ns/shacl#prefix")
    private String name;

    @LinkURI("http://www.w3.org/ns/shacl#namespace")
    private URI prefix;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setPrefix(URI prefix) {
        this.prefix = prefix;
    }

    public URI getPrefix() {
        return this.prefix;
    }

    @Override
    public String toString() {
        return "PrefixDeclaration(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PrefixDeclaration) ) return false;
        final PrefixDeclaration other = (PrefixDeclaration) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if ( this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$prefix = this.getPrefix();
        final Object other$prefix = other.getPrefix();
        if ( this$prefix == null ? other$prefix != null : !this$prefix.equals(other$prefix)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PrefixDeclaration;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $prefix = this.getPrefix();
        result = result * PRIME + ($prefix == null ? 43 : $prefix.hashCode());
        return result;
    }
}