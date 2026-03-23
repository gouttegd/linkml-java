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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfInlinedObjects")
public class ContainerOfInlinedObjects {

    @SlotName("single_inlined")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single_inlined")
    private SimpleIdentifiableClass singleInlined;

    @SlotName("inlined_as_list")
    @Inlined(asList = true)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#inlined_as_list")
    private List<SimpleIdentifiableClass> inlinedAsList;

    @SlotName("inlined_as_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#inlined_as_dict")
    private List<SimpleIdentifiableClass> inlinedAsDict;

    @SlotName("local_single_inlined")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#local_single_inlined")
    private SimpleKeyableClass localSingleInlined;

    @SlotName("local_inlined_as_list")
    @Inlined(asList = true)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#local_inlined_as_list")
    private List<SimpleKeyableClass> localInlinedAsList;

    @SlotName("local_inlined_as_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#local_inlined_as_dict")
    private List<SimpleKeyableClass> localInlinedAsDict;

    public void setSingleInlined(SimpleIdentifiableClass singleInlined) {
        this.singleInlined = singleInlined;
    }

    public SimpleIdentifiableClass getSingleInlined() {
        return this.singleInlined;
    }

    public void setInlinedAsList(List<SimpleIdentifiableClass> inlinedAsList) {
        this.inlinedAsList = inlinedAsList;
    }

    public List<SimpleIdentifiableClass> getInlinedAsList() {
        return this.inlinedAsList;
    }

    public List<SimpleIdentifiableClass> getInlinedAsList(boolean set) {
        if ( this.inlinedAsList == null && set ) {
            this.inlinedAsList = new ArrayList<>();
        }
        return this.inlinedAsList;
    }

    public void setInlinedAsDict(List<SimpleIdentifiableClass> inlinedAsDict) {
        this.inlinedAsDict = inlinedAsDict;
    }

    public List<SimpleIdentifiableClass> getInlinedAsDict() {
        return this.inlinedAsDict;
    }

    public List<SimpleIdentifiableClass> getInlinedAsDict(boolean set) {
        if ( this.inlinedAsDict == null && set ) {
            this.inlinedAsDict = new ArrayList<>();
        }
        return this.inlinedAsDict;
    }

    public void setLocalSingleInlined(SimpleKeyableClass localSingleInlined) {
        this.localSingleInlined = localSingleInlined;
    }

    public SimpleKeyableClass getLocalSingleInlined() {
        return this.localSingleInlined;
    }

    public void setLocalInlinedAsList(List<SimpleKeyableClass> localInlinedAsList) {
        this.localInlinedAsList = localInlinedAsList;
    }

    public List<SimpleKeyableClass> getLocalInlinedAsList() {
        return this.localInlinedAsList;
    }

    public List<SimpleKeyableClass> getLocalInlinedAsList(boolean set) {
        if ( this.localInlinedAsList == null && set ) {
            this.localInlinedAsList = new ArrayList<>();
        }
        return this.localInlinedAsList;
    }

    public void setLocalInlinedAsDict(List<SimpleKeyableClass> localInlinedAsDict) {
        this.localInlinedAsDict = localInlinedAsDict;
    }

    public List<SimpleKeyableClass> getLocalInlinedAsDict() {
        return this.localInlinedAsDict;
    }

    public List<SimpleKeyableClass> getLocalInlinedAsDict(boolean set) {
        if ( this.localInlinedAsDict == null && set ) {
            this.localInlinedAsDict = new ArrayList<>();
        }
        return this.localInlinedAsDict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfInlinedObjects(");
        if ( (o = this.getSingleInlined()) != null ) {
            sb.append("single_inlined=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInlinedAsList()) != null ) {
            sb.append("inlined_as_list=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getInlinedAsDict()) != null ) {
            sb.append("inlined_as_dict=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLocalSingleInlined()) != null ) {
            sb.append("local_single_inlined=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLocalInlinedAsList()) != null ) {
            sb.append("local_inlined_as_list=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLocalInlinedAsDict()) != null ) {
            sb.append("local_inlined_as_dict=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfInlinedObjects) ) return false;
        final ContainerOfInlinedObjects other = (ContainerOfInlinedObjects) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$singleInlined = this.getSingleInlined();
        final Object other$singleInlined = other.getSingleInlined();
        if ( this$singleInlined == null ? other$singleInlined != null : !this$singleInlined.equals(other$singleInlined)) return false;
        final Object this$inlinedAsList = this.getInlinedAsList();
        final Object other$inlinedAsList = other.getInlinedAsList();
        if ( this$inlinedAsList == null ? other$inlinedAsList != null : !this$inlinedAsList.equals(other$inlinedAsList)) return false;
        final Object this$inlinedAsDict = this.getInlinedAsDict();
        final Object other$inlinedAsDict = other.getInlinedAsDict();
        if ( this$inlinedAsDict == null ? other$inlinedAsDict != null : !this$inlinedAsDict.equals(other$inlinedAsDict)) return false;
        final Object this$localSingleInlined = this.getLocalSingleInlined();
        final Object other$localSingleInlined = other.getLocalSingleInlined();
        if ( this$localSingleInlined == null ? other$localSingleInlined != null : !this$localSingleInlined.equals(other$localSingleInlined)) return false;
        final Object this$localInlinedAsList = this.getLocalInlinedAsList();
        final Object other$localInlinedAsList = other.getLocalInlinedAsList();
        if ( this$localInlinedAsList == null ? other$localInlinedAsList != null : !this$localInlinedAsList.equals(other$localInlinedAsList)) return false;
        final Object this$localInlinedAsDict = this.getLocalInlinedAsDict();
        final Object other$localInlinedAsDict = other.getLocalInlinedAsDict();
        if ( this$localInlinedAsDict == null ? other$localInlinedAsDict != null : !this$localInlinedAsDict.equals(other$localInlinedAsDict)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfInlinedObjects;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $singleInlined = this.getSingleInlined();
        result = result * PRIME + ($singleInlined == null ? 43 : $singleInlined.hashCode());
        final Object $inlinedAsList = this.getInlinedAsList();
        result = result * PRIME + ($inlinedAsList == null ? 43 : $inlinedAsList.hashCode());
        final Object $inlinedAsDict = this.getInlinedAsDict();
        result = result * PRIME + ($inlinedAsDict == null ? 43 : $inlinedAsDict.hashCode());
        final Object $localSingleInlined = this.getLocalSingleInlined();
        result = result * PRIME + ($localSingleInlined == null ? 43 : $localSingleInlined.hashCode());
        final Object $localInlinedAsList = this.getLocalInlinedAsList();
        result = result * PRIME + ($localInlinedAsList == null ? 43 : $localInlinedAsList.hashCode());
        final Object $localInlinedAsDict = this.getLocalInlinedAsDict();
        result = result * PRIME + ($localInlinedAsDict == null ? 43 : $localInlinedAsDict.hashCode());
        return result;
    }
}