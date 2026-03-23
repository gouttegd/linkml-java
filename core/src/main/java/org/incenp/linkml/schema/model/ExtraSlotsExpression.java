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

@LinkURI("https://w3id.org/linkml/ExtraSlotsExpression")
public class ExtraSlotsExpression {

    @LinkURI("https://w3id.org/linkml/allowed")
    private Boolean allowed;

    @SlotName("range_expression")
    @LinkURI("https://w3id.org/linkml/range_expression")
    private AnonymousSlotExpression rangeExpression;

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }

    public Boolean getAllowed() {
        return this.allowed;
    }

    public void setRangeExpression(AnonymousSlotExpression rangeExpression) {
        this.rangeExpression = rangeExpression;
    }

    public AnonymousSlotExpression getRangeExpression() {
        return this.rangeExpression;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ExtraSlotsExpression(");
        if ( (o = this.getAllowed()) != null ) {
            sb.append("allowed=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRangeExpression()) != null ) {
            sb.append("range_expression=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ExtraSlotsExpression) ) return false;
        final ExtraSlotsExpression other = (ExtraSlotsExpression) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$allowed = this.getAllowed();
        final Object other$allowed = other.getAllowed();
        if ( this$allowed == null ? other$allowed != null : !this$allowed.equals(other$allowed)) return false;
        final Object this$rangeExpression = this.getRangeExpression();
        final Object other$rangeExpression = other.getRangeExpression();
        if ( this$rangeExpression == null ? other$rangeExpression != null : !this$rangeExpression.equals(other$rangeExpression)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExtraSlotsExpression;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $allowed = this.getAllowed();
        result = result * PRIME + ($allowed == null ? 43 : $allowed.hashCode());
        final Object $rangeExpression = this.getRangeExpression();
        result = result * PRIME + ($rangeExpression == null ? 43 : $rangeExpression.hashCode());
        return result;
    }
}