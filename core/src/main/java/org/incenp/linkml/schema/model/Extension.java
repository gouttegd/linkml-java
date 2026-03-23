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
    @SlotName("extension_tag")
    @Required
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/extension_tag")
    private String extensionTag;

    @SlotName("extension_value")
    @Required
    @LinkURI("https://w3id.org/linkml/extension_value")
    private Object extensionValue;

    @Inlined
    @LinkURI("https://w3id.org/linkml/extensions")
    private List<Extension> extensions;

    public void setExtensionTag(String extensionTag) {
        this.extensionTag = extensionTag;
    }

    public String getExtensionTag() {
        return this.extensionTag;
    }

    public void setExtensionValue(Object extensionValue) {
        this.extensionValue = extensionValue;
    }

    public Object getExtensionValue() {
        return this.extensionValue;
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
        return "Extension(extension_tag=" + this.getExtensionTag() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Extension) ) return false;
        final Extension other = (Extension) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$extensionTag = this.getExtensionTag();
        final Object other$extensionTag = other.getExtensionTag();
        if ( this$extensionTag == null ? other$extensionTag != null : !this$extensionTag.equals(other$extensionTag)) return false;
        final Object this$extensionValue = this.getExtensionValue();
        final Object other$extensionValue = other.getExtensionValue();
        if ( this$extensionValue == null ? other$extensionValue != null : !this$extensionValue.equals(other$extensionValue)) return false;
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
        final Object $extensionTag = this.getExtensionTag();
        result = result * PRIME + ($extensionTag == null ? 43 : $extensionTag.hashCode());
        final Object $extensionValue = this.getExtensionValue();
        result = result * PRIME + ($extensionValue == null ? 43 : $extensionValue.hashCode());
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        return result;
    }
}