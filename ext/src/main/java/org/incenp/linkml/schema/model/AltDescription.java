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

@LinkURI("https://w3id.org/linkml/AltDescription")
public class AltDescription {

    @Identifier(isGlobal = false)
    @SlotName("alt_description_source")
    @LinkURI("https://w3id.org/linkml/alt_description_source")
    private String altDescriptionSource;

    @SlotName("alt_description_text")
    @Required
    @LinkURI("https://w3id.org/linkml/alt_description_text")
    private String altDescriptionText;

    public void setAltDescriptionSource(String altDescriptionSource) {
        this.altDescriptionSource = altDescriptionSource;
    }

    public String getAltDescriptionSource() {
        return this.altDescriptionSource;
    }

    public void setAltDescriptionText(String altDescriptionText) {
        this.altDescriptionText = altDescriptionText;
    }

    public String getAltDescriptionText() {
        return this.altDescriptionText;
    }

    @Override
    public String toString() {
        return "AltDescription(alt_description_source=" + this.getAltDescriptionSource() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof AltDescription) ) return false;
        final AltDescription other = (AltDescription) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$altDescriptionSource = this.getAltDescriptionSource();
        final Object other$altDescriptionSource = other.getAltDescriptionSource();
        if ( this$altDescriptionSource == null ? other$altDescriptionSource != null : !this$altDescriptionSource.equals(other$altDescriptionSource)) return false;
        final Object this$altDescriptionText = this.getAltDescriptionText();
        final Object other$altDescriptionText = other.getAltDescriptionText();
        if ( this$altDescriptionText == null ? other$altDescriptionText != null : !this$altDescriptionText.equals(other$altDescriptionText)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AltDescription;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $altDescriptionSource = this.getAltDescriptionSource();
        result = result * PRIME + ($altDescriptionSource == null ? 43 : $altDescriptionSource.hashCode());
        final Object $altDescriptionText = this.getAltDescriptionText();
        result = result * PRIME + ($altDescriptionText == null ? 43 : $altDescriptionText.hashCode());
        return result;
    }
}