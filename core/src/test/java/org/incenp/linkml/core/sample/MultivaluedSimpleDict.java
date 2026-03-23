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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#MultivaluedSimpleDict")
public class MultivaluedSimpleDict {

    @Identifier
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#key")
    private String key;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#values")
    private List<String> values;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getValues() {
        return this.values;
    }

    public List<String> getValues(boolean set) {
        if ( this.values == null && set ) {
            this.values = new ArrayList<>();
        }
        return this.values;
    }

    @Override
    public String toString() {
        return "MultivaluedSimpleDict(key=" + this.getKey() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof MultivaluedSimpleDict) ) return false;
        final MultivaluedSimpleDict other = (MultivaluedSimpleDict) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        if ( this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
        final Object this$values = this.getValues();
        final Object other$values = other.getValues();
        if ( this$values == null ? other$values != null : !this$values.equals(other$values)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MultivaluedSimpleDict;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $key = this.getKey();
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        final Object $values = this.getValues();
        result = result * PRIME + ($values == null ? 43 : $values.hashCode());
        return result;
    }
}