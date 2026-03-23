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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfIntegerValues")
public class ContainerOfIntegerValues {

    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private int foo;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#bar")
    private Integer bar;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#baz")
    private List<Integer> baz;

    public void setFoo(int foo) {
        this.foo = foo;
    }

    public int getFoo() {
        return this.foo;
    }

    public void setBar(Integer bar) {
        this.bar = bar;
    }

    public Integer getBar() {
        return this.bar;
    }

    public void setBaz(List<Integer> baz) {
        this.baz = baz;
    }

    public List<Integer> getBaz() {
        return this.baz;
    }

    public List<Integer> getBaz(boolean set) {
        if ( this.baz == null && set ) {
            this.baz = new ArrayList<>();
        }
        return this.baz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfIntegerValues(");
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBar()) != null ) {
            sb.append("bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBaz()) != null ) {
            sb.append("baz=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfIntegerValues) ) return false;
        final ContainerOfIntegerValues other = (ContainerOfIntegerValues) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo != other$foo ) return false;
        final Object this$bar = this.getBar();
        final Object other$bar = other.getBar();
        if ( this$bar == null ? other$bar != null : !this$bar.equals(other$bar)) return false;
        final Object this$baz = this.getBaz();
        final Object other$baz = other.getBaz();
        if ( this$baz == null ? other$baz != null : !this$baz.equals(other$baz)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfIntegerValues;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getFoo();
        final Object $bar = this.getBar();
        result = result * PRIME + ($bar == null ? 43 : $bar.hashCode());
        final Object $baz = this.getBaz();
        result = result * PRIME + ($baz == null ? 43 : $baz.hashCode());
        return result;
    }
}