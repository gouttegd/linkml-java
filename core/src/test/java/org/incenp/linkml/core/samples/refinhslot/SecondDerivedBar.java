package org.incenp.linkml.core.samples.refinhslot;

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

@LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/SecondDerivedBar")
public class SecondDerivedBar extends FirstDerivedBar {

    @LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/width")
    private Integer width;

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getWidth() {
        return this.width;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("SecondDerivedBar(");
        if ( (o = this.getWidth()) != null ) {
            sb.append("width=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLength()) != null ) {
            sb.append("length=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getName()) != null ) {
            sb.append("name=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof SecondDerivedBar) ) return false;
        final SecondDerivedBar other = (SecondDerivedBar) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$width = this.getWidth();
        final Object other$width = other.getWidth();
        if ( this$width == null ? other$width != null : !this$width.equals(other$width)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SecondDerivedBar;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $width = this.getWidth();
        result = result * PRIME + ($width == null ? 43 : $width.hashCode());
        return result;
    }
}