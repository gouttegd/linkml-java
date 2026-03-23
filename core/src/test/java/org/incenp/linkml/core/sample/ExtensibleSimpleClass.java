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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ExtensibleSimpleClass")
public class ExtensibleSimpleClass extends SimpleClass {

    @ExtensionHolder
    private Map<String, Object> extraSlots;

    public void setExtraSlots(Map<String,Object> extraSlots) {
        this.extraSlots = extraSlots;
    }

    public Map<String,Object> getExtraSlots() {
        return this.extraSlots;
    }

    public Map<String,Object> getExtraSlots(boolean set) {
        if ( this.extraSlots == null && set ) {
            this.extraSlots = new HashMap<>();
        }
        return this.extraSlots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ExtensibleSimpleClass(");
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTheBar()) != null ) {
            sb.append("the_bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBaz()) != null ) {
            sb.append("baz=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getFoos()) != null ) {
            sb.append("foos=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDatetime()) != null ) {
            sb.append("datetime=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDate()) != null ) {
            sb.append("date=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTime()) != null ) {
            sb.append("time=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ExtensibleSimpleClass) ) return false;
        final ExtensibleSimpleClass other = (ExtensibleSimpleClass) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExtensibleSimpleClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        return result;
    }
}