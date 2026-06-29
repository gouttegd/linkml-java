package org.incenp.linkml.core.samples.base;

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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#DerivedIdentifiedSelfDesignatedClass")
public class DerivedIdentifiedSelfDesignatedClass extends IdentifiedSelfDesignatedClass {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#length")
    private Integer length;

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLength() {
        return this.length;
    }

    @Override
    public String toString() {
        return "DerivedIdentifiedSelfDesignatedClass(type=" + this.getType() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof DerivedIdentifiedSelfDesignatedClass) ) return false;
        final DerivedIdentifiedSelfDesignatedClass other = (DerivedIdentifiedSelfDesignatedClass) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$length = this.getLength();
        final Object other$length = other.getLength();
        if ( this$length == null ? other$length != null : !this$length.equals(other$length)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DerivedIdentifiedSelfDesignatedClass;
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