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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfSelfDesignatedObjects")
public class ContainerOfSelfDesignatedObjects {

    @SlotName("single_top")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single_top")
    private BaseSelfDesignatedClass singleTop;

    @SlotName("single_second_level")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single_second_level")
    private SecondDerivedSelfDesignatedClass singleSecondLevel;

    @SlotName("multiple_top")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multiple_top")
    private List<BaseSelfDesignatedClass> multipleTop;

    public void setSingleTop(BaseSelfDesignatedClass singleTop) {
        this.singleTop = singleTop;
    }

    public BaseSelfDesignatedClass getSingleTop() {
        return this.singleTop;
    }

    public void setSingleSecondLevel(SecondDerivedSelfDesignatedClass singleSecondLevel) {
        this.singleSecondLevel = singleSecondLevel;
    }

    public SecondDerivedSelfDesignatedClass getSingleSecondLevel() {
        return this.singleSecondLevel;
    }

    public void setMultipleTop(List<BaseSelfDesignatedClass> multipleTop) {
        this.multipleTop = multipleTop;
    }

    public List<BaseSelfDesignatedClass> getMultipleTop() {
        return this.multipleTop;
    }

    public List<BaseSelfDesignatedClass> getMultipleTop(boolean set) {
        if ( this.multipleTop == null && set ) {
            this.multipleTop = new ArrayList<>();
        }
        return this.multipleTop;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfSelfDesignatedObjects(");
        if ( (o = this.getSingleTop()) != null ) {
            sb.append("single_top=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSingleSecondLevel()) != null ) {
            sb.append("single_second_level=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultipleTop()) != null ) {
            sb.append("multiple_top=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfSelfDesignatedObjects) ) return false;
        final ContainerOfSelfDesignatedObjects other = (ContainerOfSelfDesignatedObjects) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$singleTop = this.getSingleTop();
        final Object other$singleTop = other.getSingleTop();
        if ( this$singleTop == null ? other$singleTop != null : !this$singleTop.equals(other$singleTop)) return false;
        final Object this$singleSecondLevel = this.getSingleSecondLevel();
        final Object other$singleSecondLevel = other.getSingleSecondLevel();
        if ( this$singleSecondLevel == null ? other$singleSecondLevel != null : !this$singleSecondLevel.equals(other$singleSecondLevel)) return false;
        final Object this$multipleTop = this.getMultipleTop();
        final Object other$multipleTop = other.getMultipleTop();
        if ( this$multipleTop == null ? other$multipleTop != null : !this$multipleTop.equals(other$multipleTop)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfSelfDesignatedObjects;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $singleTop = this.getSingleTop();
        result = result * PRIME + ($singleTop == null ? 43 : $singleTop.hashCode());
        final Object $singleSecondLevel = this.getSingleSecondLevel();
        result = result * PRIME + ($singleSecondLevel == null ? 43 : $singleSecondLevel.hashCode());
        final Object $multipleTop = this.getMultipleTop();
        result = result * PRIME + ($multipleTop == null ? 43 : $multipleTop.hashCode());
        return result;
    }
}