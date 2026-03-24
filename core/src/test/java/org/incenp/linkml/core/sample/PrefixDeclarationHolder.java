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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#PrefixDeclarationHolder")
public class PrefixDeclarationHolder {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#prefixes")
    private List<PrefixDeclaration> prefixes;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private String foo;

    public void setPrefixes(List<PrefixDeclaration> prefixes) {
        this.prefixes = prefixes;
    }

    public List<PrefixDeclaration> getPrefixes() {
        return this.prefixes;
    }

    public List<PrefixDeclaration> getPrefixes(boolean set) {
        if ( this.prefixes == null && set ) {
            this.prefixes = new ArrayList<>();
        }
        return this.prefixes;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("PrefixDeclarationHolder(");
        if ( (o = this.getPrefixes()) != null ) {
            sb.append("prefixes=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PrefixDeclarationHolder) ) return false;
        final PrefixDeclarationHolder other = (PrefixDeclarationHolder) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$prefixes = this.getPrefixes();
        final Object other$prefixes = other.getPrefixes();
        if ( this$prefixes == null ? other$prefixes != null : !this$prefixes.equals(other$prefixes)) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PrefixDeclarationHolder;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $prefixes = this.getPrefixes();
        result = result * PRIME + ($prefixes == null ? 43 : $prefixes.hashCode());
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        return result;
    }
}