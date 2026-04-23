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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#SecondDerivedSelfDesignatedClass")
public class SecondDerivedSelfDesignatedClass extends BaseSelfDesignatedClass {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#baz")
    private String baz;

    public void setBaz(String baz) {
        this.baz = baz;
    }

    public String getBaz() {
        return this.baz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("SecondDerivedSelfDesignatedClass(");
        if ( (o = this.getBaz()) != null ) {
            sb.append("baz=");
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
        if ( !(o instanceof SecondDerivedSelfDesignatedClass) ) return false;
        final SecondDerivedSelfDesignatedClass other = (SecondDerivedSelfDesignatedClass) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$baz = this.getBaz();
        final Object other$baz = other.getBaz();
        if ( this$baz == null ? other$baz != null : !this$baz.equals(other$baz)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SecondDerivedSelfDesignatedClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $baz = this.getBaz();
        result = result * PRIME + ($baz == null ? 43 : $baz.hashCode());
        return result;
    }
}