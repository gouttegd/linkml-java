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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfSimpleDicts")
public class ContainerOfSimpleDicts {

    @SlotName("simple_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#simple_dict")
    private List<SimpleDict> simpleDict;

    @SlotName("extra_simple_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#extra_simple_dict")
    private List<ExtraSimpleDict> extraSimpleDict;

    @SlotName("multivalued_simple_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multivalued_simple_dict")
    private List<MultivaluedSimpleDict> multivaluedSimpleDict;

    public void setSimpleDict(List<SimpleDict> simpleDict) {
        this.simpleDict = simpleDict;
    }

    public List<SimpleDict> getSimpleDict() {
        return this.simpleDict;
    }

    public List<SimpleDict> getSimpleDict(boolean set) {
        if ( this.simpleDict == null && set ) {
            this.simpleDict = new ArrayList<>();
        }
        return this.simpleDict;
    }

    public void setExtraSimpleDict(List<ExtraSimpleDict> extraSimpleDict) {
        this.extraSimpleDict = extraSimpleDict;
    }

    public List<ExtraSimpleDict> getExtraSimpleDict() {
        return this.extraSimpleDict;
    }

    public List<ExtraSimpleDict> getExtraSimpleDict(boolean set) {
        if ( this.extraSimpleDict == null && set ) {
            this.extraSimpleDict = new ArrayList<>();
        }
        return this.extraSimpleDict;
    }

    public void setMultivaluedSimpleDict(List<MultivaluedSimpleDict> multivaluedSimpleDict) {
        this.multivaluedSimpleDict = multivaluedSimpleDict;
    }

    public List<MultivaluedSimpleDict> getMultivaluedSimpleDict() {
        return this.multivaluedSimpleDict;
    }

    public List<MultivaluedSimpleDict> getMultivaluedSimpleDict(boolean set) {
        if ( this.multivaluedSimpleDict == null && set ) {
            this.multivaluedSimpleDict = new ArrayList<>();
        }
        return this.multivaluedSimpleDict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfSimpleDicts(");
        if ( (o = this.getSimpleDict()) != null ) {
            sb.append("simple_dict=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExtraSimpleDict()) != null ) {
            sb.append("extra_simple_dict=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultivaluedSimpleDict()) != null ) {
            sb.append("multivalued_simple_dict=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfSimpleDicts) ) return false;
        final ContainerOfSimpleDicts other = (ContainerOfSimpleDicts) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$simpleDict = this.getSimpleDict();
        final Object other$simpleDict = other.getSimpleDict();
        if ( this$simpleDict == null ? other$simpleDict != null : !this$simpleDict.equals(other$simpleDict)) return false;
        final Object this$extraSimpleDict = this.getExtraSimpleDict();
        final Object other$extraSimpleDict = other.getExtraSimpleDict();
        if ( this$extraSimpleDict == null ? other$extraSimpleDict != null : !this$extraSimpleDict.equals(other$extraSimpleDict)) return false;
        final Object this$multivaluedSimpleDict = this.getMultivaluedSimpleDict();
        final Object other$multivaluedSimpleDict = other.getMultivaluedSimpleDict();
        if ( this$multivaluedSimpleDict == null ? other$multivaluedSimpleDict != null : !this$multivaluedSimpleDict.equals(other$multivaluedSimpleDict)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfSimpleDicts;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $simpleDict = this.getSimpleDict();
        result = result * PRIME + ($simpleDict == null ? 43 : $simpleDict.hashCode());
        final Object $extraSimpleDict = this.getExtraSimpleDict();
        result = result * PRIME + ($extraSimpleDict == null ? 43 : $extraSimpleDict.hashCode());
        final Object $multivaluedSimpleDict = this.getMultivaluedSimpleDict();
        result = result * PRIME + ($multivaluedSimpleDict == null ? 43 : $multivaluedSimpleDict.hashCode());
        return result;
    }
}