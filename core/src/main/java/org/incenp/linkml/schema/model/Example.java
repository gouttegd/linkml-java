package org.incenp.linkml.schema.model;

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

@LinkURI("https://w3id.org/linkml/Example")
public class Example {

    @LinkURI("http://www.w3.org/2004/02/skos/core#example")
    private String value;

    @SlotName("value_description")
    @LinkURI("https://w3id.org/linkml/value_description")
    private String valueDescription;

    @SlotName("value_object")
    @LinkURI("https://w3id.org/linkml/value_object")
    private Object valueObject;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValueDescription(String valueDescription) {
        this.valueDescription = valueDescription;
    }

    public String getValueDescription() {
        return this.valueDescription;
    }

    public void setValueObject(Object valueObject) {
        this.valueObject = valueObject;
    }

    public Object getValueObject() {
        return this.valueObject;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("Example(");
        if ( (o = this.getValue()) != null ) {
            sb.append("value=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getValueDescription()) != null ) {
            sb.append("value_description=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getValueObject()) != null ) {
            sb.append("value_object=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Example) ) return false;
        final Example other = (Example) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if ( this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        final Object this$valueDescription = this.getValueDescription();
        final Object other$valueDescription = other.getValueDescription();
        if ( this$valueDescription == null ? other$valueDescription != null : !this$valueDescription.equals(other$valueDescription)) return false;
        final Object this$valueObject = this.getValueObject();
        final Object other$valueObject = other.getValueObject();
        if ( this$valueObject == null ? other$valueObject != null : !this$valueObject.equals(other$valueObject)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Example;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        final Object $valueDescription = this.getValueDescription();
        result = result * PRIME + ($valueDescription == null ? 43 : $valueDescription.hashCode());
        final Object $valueObject = this.getValueObject();
        result = result * PRIME + ($valueObject == null ? 43 : $valueObject.hashCode());
        return result;
    }
}