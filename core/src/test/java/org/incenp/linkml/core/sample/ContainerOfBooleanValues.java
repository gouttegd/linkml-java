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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfBooleanValues")
public class ContainerOfBooleanValues {

    @SlotName("primitive_foo")
    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#primitive_foo")
    private boolean primitiveFoo;

    @SlotName("primitive_bar")
    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#primitive_bar")
    private boolean primitiveBar;

    @SlotName("primitive_baz")
    @Required
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#primitive_baz")
    private boolean primitiveBaz;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private Boolean foo;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#bar")
    private Boolean bar;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#baz")
    private Boolean baz;

    public void setPrimitiveFoo(boolean primitiveFoo) {
        this.primitiveFoo = primitiveFoo;
    }

    public boolean isPrimitiveFoo() {
        return this.primitiveFoo;
    }

    public void setPrimitiveBar(boolean primitiveBar) {
        this.primitiveBar = primitiveBar;
    }

    public boolean isPrimitiveBar() {
        return this.primitiveBar;
    }

    public void setPrimitiveBaz(boolean primitiveBaz) {
        this.primitiveBaz = primitiveBaz;
    }

    public boolean isPrimitiveBaz() {
        return this.primitiveBaz;
    }

    public void setFoo(Boolean foo) {
        this.foo = foo;
    }

    public Boolean getFoo() {
        return this.foo;
    }

    public void setBar(Boolean bar) {
        this.bar = bar;
    }

    public Boolean getBar() {
        return this.bar;
    }

    public void setBaz(Boolean baz) {
        this.baz = baz;
    }

    public Boolean getBaz() {
        return this.baz;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfBooleanValues(");
        if ( (o = this.isPrimitiveFoo()) != null ) {
            sb.append("primitive_foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.isPrimitiveBar()) != null ) {
            sb.append("primitive_bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.isPrimitiveBaz()) != null ) {
            sb.append("primitive_baz=");
            sb.append(o);
            sb.append(",");
        }
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
        if ( !(o instanceof ContainerOfBooleanValues) ) return false;
        final ContainerOfBooleanValues other = (ContainerOfBooleanValues) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$primitiveFoo = this.isPrimitiveFoo();
        final Object other$primitiveFoo = other.isPrimitiveFoo();
        if ( this$primitiveFoo != other$primitiveFoo ) return false;
        final Object this$primitiveBar = this.isPrimitiveBar();
        final Object other$primitiveBar = other.isPrimitiveBar();
        if ( this$primitiveBar != other$primitiveBar ) return false;
        final Object this$primitiveBaz = this.isPrimitiveBaz();
        final Object other$primitiveBaz = other.isPrimitiveBaz();
        if ( this$primitiveBaz != other$primitiveBaz ) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        final Object this$bar = this.getBar();
        final Object other$bar = other.getBar();
        if ( this$bar == null ? other$bar != null : !this$bar.equals(other$bar)) return false;
        final Object this$baz = this.getBaz();
        final Object other$baz = other.getBaz();
        if ( this$baz == null ? other$baz != null : !this$baz.equals(other$baz)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfBooleanValues;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isPrimitiveFoo() ? 79 : 97);
        result = result * PRIME + (this.isPrimitiveBar() ? 79 : 97);
        result = result * PRIME + (this.isPrimitiveBaz() ? 79 : 97);
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        final Object $bar = this.getBar();
        result = result * PRIME + ($bar == null ? 43 : $bar.hashCode());
        final Object $baz = this.getBaz();
        result = result * PRIME + ($baz == null ? 43 : $baz.hashCode());
        return result;
    }
}