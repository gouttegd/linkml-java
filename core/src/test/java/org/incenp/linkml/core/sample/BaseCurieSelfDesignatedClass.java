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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#BaseCurieSelfDesignatedClass")
public class BaseCurieSelfDesignatedClass {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private String foo;

    @TypeDesignator
    @Converter(CurieConverter.class)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#type")
    private String type;

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("BaseCurieSelfDesignatedClass(");
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
        if ( !(o instanceof BaseCurieSelfDesignatedClass) ) return false;
        final BaseCurieSelfDesignatedClass other = (BaseCurieSelfDesignatedClass) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseCurieSelfDesignatedClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        return result;
    }
}