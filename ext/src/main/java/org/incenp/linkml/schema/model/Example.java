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

    @LinkURI("https://w3id.org/linkml/value_description")
    private String description;

    @LinkURI("https://w3id.org/linkml/value_object")
    private Object object;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return this.object;
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
        if ( (o = this.getDescription()) != null ) {
            sb.append("description=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getObject()) != null ) {
            sb.append("object=");
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
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if ( this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$object = this.getObject();
        final Object other$object = other.getObject();
        if ( this$object == null ? other$object != null : !this$object.equals(other$object)) return false;
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
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $object = this.getObject();
        result = result * PRIME + ($object == null ? 43 : $object.hashCode());
        return result;
    }
}