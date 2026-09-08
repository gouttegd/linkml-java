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

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfBinaryData")
public class ContainerOfBinaryData {

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#checksum")
    private byte[] checksum;

    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#checksums")
    private List<byte[]> checksums;

    public void setChecksum(byte[] checksum) {
        this.checksum = checksum;
    }

    public byte[] getChecksum() {
        return this.checksum;
    }

    public void setChecksums(List<byte[]> checksums) {
        this.checksums = checksums;
    }

    public List<byte[]> getChecksums() {
        return this.checksums;
    }

    public List<byte[]> getChecksums(boolean set) {
        if ( this.checksums == null && set ) {
            this.checksums = new ArrayList<>();
        }
        return this.checksums;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ContainerOfBinaryData(");
        if ( (o = this.getChecksum()) != null ) {
            sb.append("checksum=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getChecksums()) != null ) {
            sb.append("checksums=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ContainerOfBinaryData) ) return false;
        final ContainerOfBinaryData other = (ContainerOfBinaryData) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$checksum = this.getChecksum();
        final Object other$checksum = other.getChecksum();
        if ( this$checksum == null ? other$checksum != null : !this$checksum.equals(other$checksum) ) return false;
        final Object this$checksums = this.getChecksums();
        final Object other$checksums = other.getChecksums();
        if ( this$checksums == null ? other$checksums != null : !this$checksums.equals(other$checksums) ) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ContainerOfBinaryData;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $checksum = this.getChecksum();
        result = result * PRIME + ($checksum == null ? 43 : $checksum.hashCode());
        final Object $checksums = this.getChecksums();
        result = result * PRIME + ($checksums == null ? 43 : $checksums.hashCode());
        return result;
    }
}