package org.incenp.linkml.core.samples.base;

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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfKeyedSelfDesignatedObjects")
public class ContainerOfKeyedSelfDesignatedObjects {

    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#objects")
    private List<KeyedSelfDesignatedClass> objects;

    public void setObjects(List<KeyedSelfDesignatedClass> objects) {
        this.objects = objects;
    }

    public List<KeyedSelfDesignatedClass> getObjects() {
        return this.objects;
    }

    public List<KeyedSelfDesignatedClass> getObjects(boolean set) {
        if ( this.objects == null && set ) {
            this.objects = new ArrayList<>();
        }
        return this.objects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfKeyedSelfDesignatedObjects(");
        if ( (o = this.getObjects()) != null ) {
            sb.append("objects=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfKeyedSelfDesignatedObjects) ) return false;
        final ContainerOfKeyedSelfDesignatedObjects other = (ContainerOfKeyedSelfDesignatedObjects) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$objects = this.getObjects();
        final Object other$objects = other.getObjects();
        if ( this$objects == null ? other$objects != null : !this$objects.equals(other$objects)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfKeyedSelfDesignatedObjects;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $objects = this.getObjects();
        result = result * PRIME + ($objects == null ? 43 : $objects.hashCode());
        return result;
    }
}