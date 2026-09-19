package org.incenp.linkml.core.samples.base;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.*;
import org.incenp.linkml.core.types.*;

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#ContainerOfBinaryData")
public class ContainerOfBinaryData {

    @TypeURI("http://www.w3.org/2001/XMLSchema#hexBinary")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#checksum")
    private BinaryBlob checksum;

    @TypeURI("http://www.w3.org/2001/XMLSchema#hexBinary")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#checksums")
    private List<BinaryBlob> checksums;

    @TypeURI("http://www.w3.org/2001/XMLSchema#base64Binary")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#signature")
    private BinaryBlob signature;

    @TypeURI("http://www.w3.org/2001/XMLSchema#base64Binary")
    @LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#signatures")
    private List<BinaryBlob> signatures;

    public void setChecksum(BinaryBlob checksum) {
        this.checksum = checksum;
    }

    public BinaryBlob getChecksum() {
        return this.checksum;
    }

    public void setChecksums(List<BinaryBlob> checksums) {
        this.checksums = checksums;
    }

    public List<BinaryBlob> getChecksums() {
        return this.checksums;
    }

    public List<BinaryBlob> getChecksums(boolean set) {
        if ( this.checksums == null && set ) {
            this.checksums = new ArrayList<>();
        }
        return this.checksums;
    }

    public void setSignature(BinaryBlob signature) {
        this.signature = signature;
    }

    public BinaryBlob getSignature() {
        return this.signature;
    }

    public void setSignatures(List<BinaryBlob> signatures) {
        this.signatures = signatures;
    }

    public List<BinaryBlob> getSignatures() {
        return this.signatures;
    }

    public List<BinaryBlob> getSignatures(boolean set) {
        if ( this.signatures == null && set ) {
            this.signatures = new ArrayList<>();
        }
        return this.signatures;
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
        if ( (o = this.getSignature()) != null ) {
            sb.append("signature=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSignatures()) != null ) {
            sb.append("signatures=");
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
        final Object this$signature = this.getSignature();
        final Object other$signature = other.getSignature();
        if ( this$signature == null ? other$signature != null : !this$signature.equals(other$signature) ) return false;
        final Object this$signatures = this.getSignatures();
        final Object other$signatures = other.getSignatures();
        if ( this$signatures == null ? other$signatures != null : !this$signatures.equals(other$signatures) ) return false;
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
        final Object $signature = this.getSignature();
        result = result * PRIME + ($signature == null ? 43 : $signature.hashCode());
        final Object $signatures = this.getSignatures();
        result = result * PRIME + ($signatures == null ? 43 : $signatures.hashCode());
        return result;
    }
}