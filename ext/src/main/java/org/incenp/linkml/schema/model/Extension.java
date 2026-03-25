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

@LinkURI("https://w3id.org/linkml/Extension")
public class Extension {

    @Identifier(isGlobal = false)
    @Required
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/extension_tag")
    private String tag;

    @Required
    @LinkURI("https://w3id.org/linkml/extension_value")
    private Object value;

    @Inlined
    @LinkURI("https://w3id.org/linkml/extensions")
    private List<Extension> extensions;

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

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
        return "Extension(tag=" + this.getTag() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Extension) ) return false;
        final Extension other = (Extension) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$tag = this.getTag();
        final Object other$tag = other.getTag();
        if ( this$tag == null ? other$tag != null : !this$tag.equals(other$tag)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if ( this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        final Object this$extensions = this.getExtensions();
        final Object other$extensions = other.getExtensions();
        if ( this$extensions == null ? other$extensions != null : !this$extensions.equals(other$extensions)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Extension;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tag = this.getTag();
        result = result * PRIME + ($tag == null ? 43 : $tag.hashCode());
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        return result;
    }
}