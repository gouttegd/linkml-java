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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ExtendedIdentifiableClass")
public class ExtendedIdentifiableClass extends SimpleIdentifiableClass {

    @SlotName("another_foo")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#another_foo")
    private String anotherFoo;

    public void setAnotherFoo(String anotherFoo) {
        this.anotherFoo = anotherFoo;
    }

    public String getAnotherFoo() {
        return this.anotherFoo;
    }

    @Override
    public String toString() {
        return "ExtendedIdentifiableClass(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ExtendedIdentifiableClass) ) return false;
        final ExtendedIdentifiableClass other = (ExtendedIdentifiableClass) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$anotherFoo = this.getAnotherFoo();
        final Object other$anotherFoo = other.getAnotherFoo();
        if ( this$anotherFoo == null ? other$anotherFoo != null : !this$anotherFoo.equals(other$anotherFoo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExtendedIdentifiableClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $anotherFoo = this.getAnotherFoo();
        result = result * PRIME + ($anotherFoo == null ? 43 : $anotherFoo.hashCode());
        return result;
    }
}