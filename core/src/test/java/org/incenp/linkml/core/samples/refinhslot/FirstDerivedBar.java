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

@LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/FirstDerivedBar")
public class FirstDerivedBar extends Bar {

    @LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/length")
    private Integer length;

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("FirstDerivedBar(");
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
        if ( !(o instanceof FirstDerivedBar) ) return false;
        final FirstDerivedBar other = (FirstDerivedBar) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$length = this.getLength();
        final Object other$length = other.getLength();
        if ( this$length == null ? other$length != null : !this$length.equals(other$length)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FirstDerivedBar;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $length = this.getLength();
        result = result * PRIME + ($length == null ? 43 : $length.hashCode());
        return result;
    }
}