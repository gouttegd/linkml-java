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

@LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/Foo")
public class Foo {

    @LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/bar")
    private Bar bar;

    @LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/bars")
    private List<? extends Bar> bars;

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Bar getBar() {
        return this.bar;
    }

    public void setBars(List<? extends Bar> bars) {
        this.bars = bars;
    }

    public List<? extends Bar> getBars() {
        return this.bars;
    }

    public List<? extends Bar> getBars(boolean set) {
        if ( this.bars == null && set ) {
            this.bars = new ArrayList<Bar>();
        }
        return this.bars;
    }

    @SuppressWarnings("unchecked")
    public <T extends Bar> List<T> getBars(Class<T> t) {
        return (List<T>) this.bars;
    }

    @SuppressWarnings("unchecked")
    public <T extends Bar> List<T> getBars(Class<T> t, boolean create) {
        if ( this.bars == null && create ) {
            this.bars = new ArrayList<T>();
        }
        return (List<T>) this.bars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("Foo(");
        if ( (o = this.getBar()) != null ) {
            sb.append("bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBars()) != null ) {
            sb.append("bars=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Foo) ) return false;
        final Foo other = (Foo) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$bar = this.getBar();
        final Object other$bar = other.getBar();
        if ( this$bar == null ? other$bar != null : !this$bar.equals(other$bar)) return false;
        final Object this$bars = this.getBars();
        final Object other$bars = other.getBars();
        if ( this$bars == null ? other$bars != null : !this$bars.equals(other$bars)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Foo;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $bar = this.getBar();
        result = result * PRIME + ($bar == null ? 43 : $bar.hashCode());
        final Object $bars = this.getBars();
        result = result * PRIME + ($bars == null ? 43 : $bars.hashCode());
        return result;
    }
}