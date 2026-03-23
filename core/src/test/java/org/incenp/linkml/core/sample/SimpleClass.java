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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#SimpleClass")
public class SimpleClass {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foo")
    private String foo;

    @SlotName("the_bar")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#the_bar")
    private URI theBar;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#baz")
    private Boolean baz;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#foos")
    private List<String> foos;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#type")
    private SampleEnum type;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#datetime")
    private ZonedDateTime datetime;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#date")
    private LocalDate date;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#time")
    private LocalTime time;

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setTheBar(URI theBar) {
        this.theBar = theBar;
    }

    public URI getTheBar() {
        return this.theBar;
    }

    public void setBaz(Boolean baz) {
        this.baz = baz;
    }

    public Boolean getBaz() {
        return this.baz;
    }

    public void setFoos(List<String> foos) {
        this.foos = foos;
    }

    public List<String> getFoos() {
        return this.foos;
    }

    public List<String> getFoos(boolean set) {
        if ( this.foos == null && set ) {
            this.foos = new ArrayList<>();
        }
        return this.foos;
    }

    public void setType(SampleEnum type) {
        this.type = type;
    }

    public SampleEnum getType() {
        return this.type;
    }

    public void setDatetime(ZonedDateTime datetime) {
        this.datetime = datetime;
    }

    public ZonedDateTime getDatetime() {
        return this.datetime;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("SimpleClass(");
        if ( (o = this.getFoo()) != null ) {
            sb.append("foo=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTheBar()) != null ) {
            sb.append("the_bar=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getBaz()) != null ) {
            sb.append("baz=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getFoos()) != null ) {
            sb.append("foos=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDatetime()) != null ) {
            sb.append("datetime=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getDate()) != null ) {
            sb.append("date=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTime()) != null ) {
            sb.append("time=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof SimpleClass) ) return false;
        final SimpleClass other = (SimpleClass) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        final Object this$theBar = this.getTheBar();
        final Object other$theBar = other.getTheBar();
        if ( this$theBar == null ? other$theBar != null : !this$theBar.equals(other$theBar)) return false;
        final Object this$baz = this.getBaz();
        final Object other$baz = other.getBaz();
        if ( this$baz == null ? other$baz != null : !this$baz.equals(other$baz)) return false;
        final Object this$foos = this.getFoos();
        final Object other$foos = other.getFoos();
        if ( this$foos == null ? other$foos != null : !this$foos.equals(other$foos)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$datetime = this.getDatetime();
        final Object other$datetime = other.getDatetime();
        if ( this$datetime == null ? other$datetime != null : !this$datetime.equals(other$datetime)) return false;
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if ( this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$time = this.getTime();
        final Object other$time = other.getTime();
        if ( this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SimpleClass;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        final Object $theBar = this.getTheBar();
        result = result * PRIME + ($theBar == null ? 43 : $theBar.hashCode());
        final Object $baz = this.getBaz();
        result = result * PRIME + ($baz == null ? 43 : $baz.hashCode());
        final Object $foos = this.getFoos();
        result = result * PRIME + ($foos == null ? 43 : $foos.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $datetime = this.getDatetime();
        result = result * PRIME + ($datetime == null ? 43 : $datetime.hashCode());
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        return result;
    }
}