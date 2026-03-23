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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ClassWithCustomConverter")
public class ClassWithCustomConverter {

    @Converter(CurieConverter.class)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#uri")
    private String uri;

    @Converter(CurieConverter.class)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#uris")
    private List<String> uris;

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public List<String> getUris() {
        return this.uris;
    }

    public List<String> getUris(boolean set) {
        if ( this.uris == null && set ) {
            this.uris = new ArrayList<>();
        }
        return this.uris;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ClassWithCustomConverter(");
        if ( (o = this.getUri()) != null ) {
            sb.append("uri=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getUris()) != null ) {
            sb.append("uris=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ClassWithCustomConverter) ) return false;
        final ClassWithCustomConverter other = (ClassWithCustomConverter) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$uri = this.getUri();
        final Object other$uri = other.getUri();
        if ( this$uri == null ? other$uri != null : !this$uri.equals(other$uri)) return false;
        final Object this$uris = this.getUris();
        final Object other$uris = other.getUris();
        if ( this$uris == null ? other$uris != null : !this$uris.equals(other$uris)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ClassWithCustomConverter;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $uri = this.getUri();
        result = result * PRIME + ($uri == null ? 43 : $uri.hashCode());
        final Object $uris = this.getUris();
        result = result * PRIME + ($uris == null ? 43 : $uris.hashCode());
        return result;
    }
}