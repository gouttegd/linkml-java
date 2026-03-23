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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#DerivedMultiSelfDesignatedClass")
public class DerivedMultiSelfDesignatedClass extends BaseMultiSelfDesignatedClass {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#bar")
    private String bar;

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getBar() {
        return this.bar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("DerivedMultiSelfDesignatedClass(");
        if ( (o = this.getBar()) != null ) {
            sb.append("bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof DerivedMultiSelfDesignatedClass) ) return false;
        final DerivedMultiSelfDesignatedClass other = (DerivedMultiSelfDesignatedClass) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$bar = this.getBar();
        final Object other$bar = other.getBar();
        if ( this$bar == null ? other$bar != null : !this$bar.equals(other$bar)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DerivedMultiSelfDesignatedClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $bar = this.getBar();
        result = result * PRIME + ($bar == null ? 43 : $bar.hashCode());
        return result;
    }
}