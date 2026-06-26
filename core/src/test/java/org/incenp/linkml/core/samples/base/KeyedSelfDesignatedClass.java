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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#KeyedSelfDesignatedClass")
public class KeyedSelfDesignatedClass {

    @Identifier(isGlobal = false)
    @TypeDesignator
    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#type")
    private String type;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#frobnicator")
    private String frobnicator;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setFrobnicator(String frobnicator) {
        this.frobnicator = frobnicator;
    }

    public String getFrobnicator() {
        return this.frobnicator;
    }

    @Override
    public String toString() {
        return "KeyedSelfDesignatedClass(type=" + this.getType() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof KeyedSelfDesignatedClass) ) return false;
        final KeyedSelfDesignatedClass other = (KeyedSelfDesignatedClass) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$frobnicator = this.getFrobnicator();
        final Object other$frobnicator = other.getFrobnicator();
        if ( this$frobnicator == null ? other$frobnicator != null : !this$frobnicator.equals(other$frobnicator)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof KeyedSelfDesignatedClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $frobnicator = this.getFrobnicator();
        result = result * PRIME + ($frobnicator == null ? 43 : $frobnicator.hashCode());
        return result;
    }
}