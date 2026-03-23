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

@LinkURI("https://w3id.org/linkml/Extensible")
public class Extensible {

    @Inlined
    @LinkURI("https://w3id.org/linkml/extensions")
    private List<Extension> extensions;

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public List<Extension> getExtensions() {
        return this.extensions;
    }

    public List<Extension> getExtensions(boolean set) {
        if ( this.extensions == null && set ) {
            this.extensions = new ArrayList<>();
        }
        return this.extensions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("Extensible(");
        if ( (o = this.getExtensions()) != null ) {
            sb.append("extensions=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Extensible) ) return false;
        final Extensible other = (Extensible) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$extensions = this.getExtensions();
        final Object other$extensions = other.getExtensions();
        if ( this$extensions == null ? other$extensions != null : !this$extensions.equals(other$extensions)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Extensible;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        return result;
    }
}