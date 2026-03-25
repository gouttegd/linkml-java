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

@LinkURI("https://w3id.org/linkml/Annotation")
public class Annotation extends Extension {

    @Inlined
    @LinkURI("https://w3id.org/linkml/annotations")
    private List<Annotation> annotations;

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public List<Annotation> getAnnotations(boolean set) {
        if ( this.annotations == null && set ) {
            this.annotations = new ArrayList<>();
        }
        return this.annotations;
    }

    @Override
    public String toString() {
        return "Annotation(tag=" + this.getTag() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Annotation) ) return false;
        final Annotation other = (Annotation) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$annotations = this.getAnnotations();
        final Object other$annotations = other.getAnnotations();
        if ( this$annotations == null ? other$annotations != null : !this$annotations.equals(other$annotations)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Annotation;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $annotations = this.getAnnotations();
        result = result * PRIME + ($annotations == null ? 43 : $annotations.hashCode());
        return result;
    }
}