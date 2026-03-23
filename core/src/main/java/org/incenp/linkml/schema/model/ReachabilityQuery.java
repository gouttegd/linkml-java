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

@LinkURI("https://w3id.org/linkml/ReachabilityQuery")
public class ReachabilityQuery {

    @SlotName("source_ontology")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/source_ontology")
    private String sourceOntology;

    @SlotName("source_nodes")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/source_nodes")
    private List<String> sourceNodes;

    @SlotName("relationship_types")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/relationship_types")
    private List<String> relationshipTypes;

    @SlotName("is_direct")
    @LinkURI("https://w3id.org/linkml/is_direct")
    private Boolean isDirect;

    @SlotName("include_self")
    @LinkURI("https://w3id.org/linkml/include_self")
    private Boolean includeSelf;

    @SlotName("traverse_up")
    @LinkURI("https://w3id.org/linkml/traverse_up")
    private Boolean traverseUp;

    public void setSourceOntology(String sourceOntology) {
        this.sourceOntology = sourceOntology;
    }

    public String getSourceOntology() {
        return this.sourceOntology;
    }

    public void setSourceNodes(List<String> sourceNodes) {
        this.sourceNodes = sourceNodes;
    }

    public List<String> getSourceNodes() {
        return this.sourceNodes;
    }

    public List<String> getSourceNodes(boolean set) {
        if ( this.sourceNodes == null && set ) {
            this.sourceNodes = new ArrayList<>();
        }
        return this.sourceNodes;
    }

    public void setRelationshipTypes(List<String> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }

    public List<String> getRelationshipTypes() {
        return this.relationshipTypes;
    }

    public List<String> getRelationshipTypes(boolean set) {
        if ( this.relationshipTypes == null && set ) {
            this.relationshipTypes = new ArrayList<>();
        }
        return this.relationshipTypes;
    }

    public void setIsDirect(Boolean isDirect) {
        this.isDirect = isDirect;
    }

    public Boolean getIsDirect() {
        return this.isDirect;
    }

    public void setIncludeSelf(Boolean includeSelf) {
        this.includeSelf = includeSelf;
    }

    public Boolean getIncludeSelf() {
        return this.includeSelf;
    }

    public void setTraverseUp(Boolean traverseUp) {
        this.traverseUp = traverseUp;
    }

    public Boolean getTraverseUp() {
        return this.traverseUp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("ReachabilityQuery(");
        if ( (o = this.getSourceOntology()) != null ) {
            sb.append("source_ontology=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSourceNodes()) != null ) {
            sb.append("source_nodes=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRelationshipTypes()) != null ) {
            sb.append("relationship_types=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getIsDirect()) != null ) {
            sb.append("is_direct=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getIncludeSelf()) != null ) {
            sb.append("include_self=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTraverseUp()) != null ) {
            sb.append("traverse_up=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ReachabilityQuery) ) return false;
        final ReachabilityQuery other = (ReachabilityQuery) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$sourceOntology = this.getSourceOntology();
        final Object other$sourceOntology = other.getSourceOntology();
        if ( this$sourceOntology == null ? other$sourceOntology != null : !this$sourceOntology.equals(other$sourceOntology)) return false;
        final Object this$sourceNodes = this.getSourceNodes();
        final Object other$sourceNodes = other.getSourceNodes();
        if ( this$sourceNodes == null ? other$sourceNodes != null : !this$sourceNodes.equals(other$sourceNodes)) return false;
        final Object this$relationshipTypes = this.getRelationshipTypes();
        final Object other$relationshipTypes = other.getRelationshipTypes();
        if ( this$relationshipTypes == null ? other$relationshipTypes != null : !this$relationshipTypes.equals(other$relationshipTypes)) return false;
        final Object this$isDirect = this.getIsDirect();
        final Object other$isDirect = other.getIsDirect();
        if ( this$isDirect == null ? other$isDirect != null : !this$isDirect.equals(other$isDirect)) return false;
        final Object this$includeSelf = this.getIncludeSelf();
        final Object other$includeSelf = other.getIncludeSelf();
        if ( this$includeSelf == null ? other$includeSelf != null : !this$includeSelf.equals(other$includeSelf)) return false;
        final Object this$traverseUp = this.getTraverseUp();
        final Object other$traverseUp = other.getTraverseUp();
        if ( this$traverseUp == null ? other$traverseUp != null : !this$traverseUp.equals(other$traverseUp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReachabilityQuery;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sourceOntology = this.getSourceOntology();
        result = result * PRIME + ($sourceOntology == null ? 43 : $sourceOntology.hashCode());
        final Object $sourceNodes = this.getSourceNodes();
        result = result * PRIME + ($sourceNodes == null ? 43 : $sourceNodes.hashCode());
        final Object $relationshipTypes = this.getRelationshipTypes();
        result = result * PRIME + ($relationshipTypes == null ? 43 : $relationshipTypes.hashCode());
        final Object $isDirect = this.getIsDirect();
        result = result * PRIME + ($isDirect == null ? 43 : $isDirect.hashCode());
        final Object $includeSelf = this.getIncludeSelf();
        result = result * PRIME + ($includeSelf == null ? 43 : $includeSelf.hashCode());
        final Object $traverseUp = this.getTraverseUp();
        result = result * PRIME + ($traverseUp == null ? 43 : $traverseUp.hashCode());
        return result;
    }
}