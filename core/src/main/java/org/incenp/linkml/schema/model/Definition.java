package org.incenp.linkml.schema.model;

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

@LinkURI("https://w3id.org/linkml/Definition")
public abstract class Definition extends Element {

    @SlotName("is_a")
    @LinkURI("https://w3id.org/linkml/is_a")
    private Definition isA;

    @SlotName("abstract")
    @LinkURI("https://w3id.org/linkml/abstract")
    private Boolean abstract_;

    @LinkURI("https://w3id.org/linkml/mixin")
    private Boolean mixin;

    @LinkURI("https://w3id.org/linkml/mixins")
    private List<Definition> mixins;

    @SlotName("apply_to")
    @LinkURI("https://w3id.org/linkml/apply_to")
    private List<Definition> applyTo;

    @SlotName("values_from")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/values_from")
    private List<String> valuesFrom;

    @SlotName("string_serialization")
    @LinkURI("https://w3id.org/linkml/string_serialization")
    private String stringSerialization;

    public void setIsA(Definition isA) {
        this.isA = isA;
    }

    public Definition getIsA() {
        return this.isA;
    }

    public void setAbstract(Boolean abstract_) {
        this.abstract_ = abstract_;
    }

    public Boolean getAbstract() {
        return this.abstract_;
    }

    public void setMixin(Boolean mixin) {
        this.mixin = mixin;
    }

    public Boolean getMixin() {
        return this.mixin;
    }

    public void setMixins(List<Definition> mixins) {
        this.mixins = mixins;
    }

    public List<Definition> getMixins() {
        return this.mixins;
    }

    public List<Definition> getMixins(boolean set) {
        if ( this.mixins == null && set ) {
            this.mixins = new ArrayList<>();
        }
        return this.mixins;
    }

    public void setApplyTo(List<Definition> applyTo) {
        this.applyTo = applyTo;
    }

    public List<Definition> getApplyTo() {
        return this.applyTo;
    }

    public List<Definition> getApplyTo(boolean set) {
        if ( this.applyTo == null && set ) {
            this.applyTo = new ArrayList<>();
        }
        return this.applyTo;
    }

    public void setValuesFrom(List<String> valuesFrom) {
        this.valuesFrom = valuesFrom;
    }

    public List<String> getValuesFrom() {
        return this.valuesFrom;
    }

    public List<String> getValuesFrom(boolean set) {
        if ( this.valuesFrom == null && set ) {
            this.valuesFrom = new ArrayList<>();
        }
        return this.valuesFrom;
    }

    public void setStringSerialization(String stringSerialization) {
        this.stringSerialization = stringSerialization;
    }

    public String getStringSerialization() {
        return this.stringSerialization;
    }

    @Override
    public String toString() {
        return "Definition(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Definition) ) return false;
        final Definition other = (Definition) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$isA = this.getIsA();
        final Object other$isA = other.getIsA();
        if ( this$isA == null ? other$isA != null : !this$isA.equals(other$isA)) return false;
        final Object this$abstract_ = this.getAbstract();
        final Object other$abstract_ = other.getAbstract();
        if ( this$abstract_ == null ? other$abstract_ != null : !this$abstract_.equals(other$abstract_)) return false;
        final Object this$mixin = this.getMixin();
        final Object other$mixin = other.getMixin();
        if ( this$mixin == null ? other$mixin != null : !this$mixin.equals(other$mixin)) return false;
        final Object this$mixins = this.getMixins();
        final Object other$mixins = other.getMixins();
        if ( this$mixins == null ? other$mixins != null : !this$mixins.equals(other$mixins)) return false;
        final Object this$applyTo = this.getApplyTo();
        final Object other$applyTo = other.getApplyTo();
        if ( this$applyTo == null ? other$applyTo != null : !this$applyTo.equals(other$applyTo)) return false;
        final Object this$valuesFrom = this.getValuesFrom();
        final Object other$valuesFrom = other.getValuesFrom();
        if ( this$valuesFrom == null ? other$valuesFrom != null : !this$valuesFrom.equals(other$valuesFrom)) return false;
        final Object this$stringSerialization = this.getStringSerialization();
        final Object other$stringSerialization = other.getStringSerialization();
        if ( this$stringSerialization == null ? other$stringSerialization != null : !this$stringSerialization.equals(other$stringSerialization)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Definition;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $isA = this.getIsA();
        result = result * PRIME + ($isA == null ? 43 : $isA.hashCode());
        final Object $abstract_ = this.getAbstract();
        result = result * PRIME + ($abstract_ == null ? 43 : $abstract_.hashCode());
        final Object $mixin = this.getMixin();
        result = result * PRIME + ($mixin == null ? 43 : $mixin.hashCode());
        final Object $mixins = this.getMixins();
        result = result * PRIME + ($mixins == null ? 43 : $mixins.hashCode());
        final Object $applyTo = this.getApplyTo();
        result = result * PRIME + ($applyTo == null ? 43 : $applyTo.hashCode());
        final Object $valuesFrom = this.getValuesFrom();
        result = result * PRIME + ($valuesFrom == null ? 43 : $valuesFrom.hashCode());
        final Object $stringSerialization = this.getStringSerialization();
        result = result * PRIME + ($stringSerialization == null ? 43 : $stringSerialization.hashCode());
        return result;
    }
}