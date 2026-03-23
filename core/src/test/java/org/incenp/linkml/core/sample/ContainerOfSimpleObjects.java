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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfSimpleObjects")
public class ContainerOfSimpleObjects {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single")
    private SimpleClass single;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multiple")
    private List<SimpleClass> multiple;

    public void setSingle(SimpleClass single) {
        this.single = single;
    }

    public SimpleClass getSingle() {
        return this.single;
    }

    public void setMultiple(List<SimpleClass> multiple) {
        this.multiple = multiple;
    }

    public List<SimpleClass> getMultiple() {
        return this.multiple;
    }

    public List<SimpleClass> getMultiple(boolean set) {
        if ( this.multiple == null && set ) {
            this.multiple = new ArrayList<>();
        }
        return this.multiple;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfSimpleObjects(");
        if ( (o = this.getSingle()) != null ) {
            sb.append("single=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultiple()) != null ) {
            sb.append("multiple=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfSimpleObjects) ) return false;
        final ContainerOfSimpleObjects other = (ContainerOfSimpleObjects) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$single = this.getSingle();
        final Object other$single = other.getSingle();
        if ( this$single == null ? other$single != null : !this$single.equals(other$single)) return false;
        final Object this$multiple = this.getMultiple();
        final Object other$multiple = other.getMultiple();
        if ( this$multiple == null ? other$multiple != null : !this$multiple.equals(other$multiple)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfSimpleObjects;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $single = this.getSingle();
        result = result * PRIME + ($single == null ? 43 : $single.hashCode());
        final Object $multiple = this.getMultiple();
        result = result * PRIME + ($multiple == null ? 43 : $multiple.hashCode());
        return result;
    }
}