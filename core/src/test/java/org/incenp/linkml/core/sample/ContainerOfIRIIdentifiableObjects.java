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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfIRIIdentifiableObjects")
public class ContainerOfIRIIdentifiableObjects {

    @SlotName("single_reference")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single_reference")
    private IRISimpleIdentifiableClass singleReference;

    @SlotName("multiple_references")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multiple_references")
    private List<IRISimpleIdentifiableClass> multipleReferences;

    @SlotName("single_inlined")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#single_inlined")
    private IRISimpleIdentifiableClass singleInlined;

    @SlotName("multiple_inlined_as_list")
    @Inlined(asList = true)
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multiple_inlined_as_list")
    private List<IRISimpleIdentifiableClass> multipleInlinedAsList;

    @SlotName("multiple_inlined_as_dict")
    @Inlined
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#multiple_inlined_as_dict")
    private List<IRISimpleIdentifiableClass> multipleInlinedAsDict;

    public void setSingleReference(IRISimpleIdentifiableClass singleReference) {
        this.singleReference = singleReference;
    }

    public IRISimpleIdentifiableClass getSingleReference() {
        return this.singleReference;
    }

    public void setMultipleReferences(List<IRISimpleIdentifiableClass> multipleReferences) {
        this.multipleReferences = multipleReferences;
    }

    public List<IRISimpleIdentifiableClass> getMultipleReferences() {
        return this.multipleReferences;
    }

    public List<IRISimpleIdentifiableClass> getMultipleReferences(boolean set) {
        if ( this.multipleReferences == null && set ) {
            this.multipleReferences = new ArrayList<>();
        }
        return this.multipleReferences;
    }

    public void setSingleInlined(IRISimpleIdentifiableClass singleInlined) {
        this.singleInlined = singleInlined;
    }

    public IRISimpleIdentifiableClass getSingleInlined() {
        return this.singleInlined;
    }

    public void setMultipleInlinedAsList(List<IRISimpleIdentifiableClass> multipleInlinedAsList) {
        this.multipleInlinedAsList = multipleInlinedAsList;
    }

    public List<IRISimpleIdentifiableClass> getMultipleInlinedAsList() {
        return this.multipleInlinedAsList;
    }

    public List<IRISimpleIdentifiableClass> getMultipleInlinedAsList(boolean set) {
        if ( this.multipleInlinedAsList == null && set ) {
            this.multipleInlinedAsList = new ArrayList<>();
        }
        return this.multipleInlinedAsList;
    }

    public void setMultipleInlinedAsDict(List<IRISimpleIdentifiableClass> multipleInlinedAsDict) {
        this.multipleInlinedAsDict = multipleInlinedAsDict;
    }

    public List<IRISimpleIdentifiableClass> getMultipleInlinedAsDict() {
        return this.multipleInlinedAsDict;
    }

    public List<IRISimpleIdentifiableClass> getMultipleInlinedAsDict(boolean set) {
        if ( this.multipleInlinedAsDict == null && set ) {
            this.multipleInlinedAsDict = new ArrayList<>();
        }
        return this.multipleInlinedAsDict;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfIRIIdentifiableObjects(");
        if ( (o = this.getSingleReference()) != null ) {
            sb.append("single_reference=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultipleReferences()) != null ) {
            sb.append("multiple_references=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSingleInlined()) != null ) {
            sb.append("single_inlined=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultipleInlinedAsList()) != null ) {
            sb.append("multiple_inlined_as_list=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getMultipleInlinedAsDict()) != null ) {
            sb.append("multiple_inlined_as_dict=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfIRIIdentifiableObjects) ) return false;
        final ContainerOfIRIIdentifiableObjects other = (ContainerOfIRIIdentifiableObjects) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$singleReference = this.getSingleReference();
        final Object other$singleReference = other.getSingleReference();
        if ( this$singleReference == null ? other$singleReference != null : !this$singleReference.equals(other$singleReference)) return false;
        final Object this$multipleReferences = this.getMultipleReferences();
        final Object other$multipleReferences = other.getMultipleReferences();
        if ( this$multipleReferences == null ? other$multipleReferences != null : !this$multipleReferences.equals(other$multipleReferences)) return false;
        final Object this$singleInlined = this.getSingleInlined();
        final Object other$singleInlined = other.getSingleInlined();
        if ( this$singleInlined == null ? other$singleInlined != null : !this$singleInlined.equals(other$singleInlined)) return false;
        final Object this$multipleInlinedAsList = this.getMultipleInlinedAsList();
        final Object other$multipleInlinedAsList = other.getMultipleInlinedAsList();
        if ( this$multipleInlinedAsList == null ? other$multipleInlinedAsList != null : !this$multipleInlinedAsList.equals(other$multipleInlinedAsList)) return false;
        final Object this$multipleInlinedAsDict = this.getMultipleInlinedAsDict();
        final Object other$multipleInlinedAsDict = other.getMultipleInlinedAsDict();
        if ( this$multipleInlinedAsDict == null ? other$multipleInlinedAsDict != null : !this$multipleInlinedAsDict.equals(other$multipleInlinedAsDict)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfIRIIdentifiableObjects;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $singleReference = this.getSingleReference();
        result = result * PRIME + ($singleReference == null ? 43 : $singleReference.hashCode());
        final Object $multipleReferences = this.getMultipleReferences();
        result = result * PRIME + ($multipleReferences == null ? 43 : $multipleReferences.hashCode());
        final Object $singleInlined = this.getSingleInlined();
        result = result * PRIME + ($singleInlined == null ? 43 : $singleInlined.hashCode());
        final Object $multipleInlinedAsList = this.getMultipleInlinedAsList();
        result = result * PRIME + ($multipleInlinedAsList == null ? 43 : $multipleInlinedAsList.hashCode());
        final Object $multipleInlinedAsDict = this.getMultipleInlinedAsDict();
        result = result * PRIME + ($multipleInlinedAsDict == null ? 43 : $multipleInlinedAsDict.hashCode());
        return result;
    }
}