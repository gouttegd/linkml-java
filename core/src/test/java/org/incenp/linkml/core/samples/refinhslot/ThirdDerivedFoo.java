package org.incenp.linkml.core.samples.refinhslot;

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

@LinkURI("https://w3id.org/linkml/tests/refined_derived_slots/ThirdDerivedFoo")
public class ThirdDerivedFoo extends SecondDerivedFoo {

    @Override
    public SecondDerivedBar getBar() {
        return (SecondDerivedBar) super.getBar();
    }

    public void setBar(SecondDerivedBar value) {
        super.setBar(value);
    }

    @Override
    public void setBar(FirstDerivedBar value) {
        if ( !(value instanceof SecondDerivedBar) ) {
            throw new IllegalArgumentException("Invalid bar value");
        }
        super.setBar(value);
    }

    @Override
    public void setBar(Bar value) {
        if ( !(value instanceof SecondDerivedBar) ) {
            throw new IllegalArgumentException("Invalid bar value");
        }
        super.setBar(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SecondDerivedBar> getBars() {
        return (List<SecondDerivedBar>) super.getBars();
    }

    @Override
    public List<SecondDerivedBar> getBars(boolean create) {
        return super.getBars(SecondDerivedBar.class, create);
    }

    @Override
    public <T extends Bar> List<T> getBars(Class<T> t) {
        if ( !SecondDerivedBar.class.isAssignableFrom(t) ) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        return super.getBars(t);
    }

    @Override
    public <T extends Bar> List<T> getBars(Class<T> t, boolean create) {
        if ( !SecondDerivedBar.class.isAssignableFrom(t) ) {
            throw new IllegalArgumentException("Invalid type parameter");
        }
        return super.getBars(t, create);
    }

    @Override
    public void setBars(List<? extends Bar> value) {
        if ( value != null ) {
            for ( Bar item : value ) {
                if ( !(item instanceof SecondDerivedBar) ) {
                    throw new IllegalArgumentException("Invalid bars value");
                }
            }
        }
        super.setBars(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ThirdDerivedFoo(");
        if ( (o = this.getBar()) != null ) {
            sb.append("bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBars()) != null ) {
            sb.append("bars=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ThirdDerivedFoo) ) return false;
        final ThirdDerivedFoo other = (ThirdDerivedFoo) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ThirdDerivedFoo;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        return result;
    }
}