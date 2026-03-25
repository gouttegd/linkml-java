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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfAny")
public class ContainerOfAny {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private String foo;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#misc")
    private Object misc;

    @SlotName("several_misc")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#several_misc")
    private List<Object> severalMisc;

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setMisc(Object misc) {
        this.misc = misc;
    }

    public Object getMisc() {
        return this.misc;
    }

    public void setSeveralMisc(List<Object> severalMisc) {
        this.severalMisc = severalMisc;
    }

    public List<Object> getSeveralMisc() {
        return this.severalMisc;
    }

    public List<Object> getSeveralMisc(boolean set) {
        if ( this.severalMisc == null && set ) {
            this.severalMisc = new ArrayList<>();
        }
        return this.severalMisc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfAny(");
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMisc()) != null ) {
            sb.append("misc=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSeveralMisc()) != null ) {
            sb.append("several_misc=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfAny) ) return false;
        final ContainerOfAny other = (ContainerOfAny) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        final Object this$misc = this.getMisc();
        final Object other$misc = other.getMisc();
        if ( this$misc == null ? other$misc != null : !this$misc.equals(other$misc)) return false;
        final Object this$severalMisc = this.getSeveralMisc();
        final Object other$severalMisc = other.getSeveralMisc();
        if ( this$severalMisc == null ? other$severalMisc != null : !this$severalMisc.equals(other$severalMisc)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfAny;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        final Object $misc = this.getMisc();
        result = result * PRIME + ($misc == null ? 43 : $misc.hashCode());
        final Object $severalMisc = this.getSeveralMisc();
        result = result * PRIME + ($severalMisc == null ? 43 : $severalMisc.hashCode());
        return result;
    }
}