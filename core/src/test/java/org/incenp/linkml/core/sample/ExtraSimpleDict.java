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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ExtraSimpleDict")
public class ExtraSimpleDict {

    @Identifier
    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#key")
    private String key;

    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#value")
    private String value;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#extra")
    private String extra;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getExtra() {
        return this.extra;
    }

    @Override
    public String toString() {
        return "ExtraSimpleDict(key=" + this.getKey() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ExtraSimpleDict) ) return false;
        final ExtraSimpleDict other = (ExtraSimpleDict) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        if ( this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if ( this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        final Object this$extra = this.getExtra();
        final Object other$extra = other.getExtra();
        if ( this$extra == null ? other$extra != null : !this$extra.equals(other$extra)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExtraSimpleDict;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $key = this.getKey();
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        final Object $extra = this.getExtra();
        result = result * PRIME + ($extra == null ? 43 : $extra.hashCode());
        return result;
    }
}