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

@LinkURI("https://w3id.org/linkml/ClassDefinition")
public class ClassDefinition extends Definition {

    @LinkURI("https://w3id.org/linkml/slots")
    private List<SlotDefinition> slots;

    @SlotName("slot_usage")
    @Inlined
    @LinkURI("https://w3id.org/linkml/slot_usage")
    private List<SlotDefinition> slotUsage;

    @Inlined
    @LinkURI("https://w3id.org/linkml/attributes")
    private List<SlotDefinition> attributes;

    @SlotName("class_uri")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/class_uri")
    private String classUri;

    @SlotName("subclass_of")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/subclass_of")
    private String subclassOf;

    @SlotName("union_of")
    @LinkURI("https://w3id.org/linkml/union_of")
    private List<ClassDefinition> unionOf;

    @SlotName("defining_slots")
    @LinkURI("https://w3id.org/linkml/defining_slots")
    private List<SlotDefinition> definingSlots;

    @SlotName("tree_root")
    @LinkURI("https://w3id.org/linkml/tree_root")
    private Boolean treeRoot;

    @SlotName("unique_keys")
    @Inlined
    @LinkURI("https://w3id.org/linkml/unique_keys")
    private List<UniqueKey> uniqueKeys;

    @Inlined
    @LinkURI("http://www.w3.org/ns/shacl#rule")
    private List<ClassRule> rules;

    @SlotName("classification_rules")
    @Inlined
    @LinkURI("https://w3id.org/linkml/classification_rules")
    private List<AnonymousClassExpression> classificationRules;

    @SlotName("slot_names_unique")
    @LinkURI("https://w3id.org/linkml/slot_names_unique")
    private Boolean slotNamesUnique;

    @SlotName("represents_relationship")
    @LinkURI("https://w3id.org/linkml/represents_relationship")
    private Boolean representsRelationship;

    @SlotName("disjoint_with")
    @LinkURI("https://w3id.org/linkml/disjoint_with")
    private List<ClassDefinition> disjointWith;

    @SlotName("children_are_mutually_disjoint")
    @LinkURI("https://w3id.org/linkml/children_are_mutually_disjoint")
    private Boolean childrenAreMutuallyDisjoint;

    @SlotName("extra_slots")
    @LinkURI("https://w3id.org/linkml/extra_slots")
    private ExtraSlotsExpression extraSlots;

    @LinkURI("http://www.w3.org/2004/02/skos/core#prefLabel")
    private String alias;

    @SlotName("any_of")
    @LinkURI("https://w3id.org/linkml/any_of")
    private List<AnonymousClassExpression> anyOf;

    @SlotName("exactly_one_of")
    @LinkURI("https://w3id.org/linkml/exactly_one_of")
    private List<AnonymousClassExpression> exactlyOneOf;

    @SlotName("none_of")
    @LinkURI("https://w3id.org/linkml/none_of")
    private List<AnonymousClassExpression> noneOf;

    @SlotName("all_of")
    @LinkURI("https://w3id.org/linkml/all_of")
    private List<AnonymousClassExpression> allOf;

    @SlotName("slot_conditions")
    @Inlined
    @LinkURI("https://w3id.org/linkml/slot_conditions")
    private List<SlotDefinition> slotConditions;

    public void setSlots(List<SlotDefinition> slots) {
        this.slots = slots;
    }

    public List<SlotDefinition> getSlots() {
        return this.slots;
    }

    public List<SlotDefinition> getSlots(boolean set) {
        if ( this.slots == null && set ) {
            this.slots = new ArrayList<>();
        }
        return this.slots;
    }

    public void setSlotUsage(List<SlotDefinition> slotUsage) {
        this.slotUsage = slotUsage;
    }

    public List<SlotDefinition> getSlotUsage() {
        return this.slotUsage;
    }

    public List<SlotDefinition> getSlotUsage(boolean set) {
        if ( this.slotUsage == null && set ) {
            this.slotUsage = new ArrayList<>();
        }
        return this.slotUsage;
    }

    public void setAttributes(List<SlotDefinition> attributes) {
        this.attributes = attributes;
    }

    public List<SlotDefinition> getAttributes() {
        return this.attributes;
    }

    public List<SlotDefinition> getAttributes(boolean set) {
        if ( this.attributes == null && set ) {
            this.attributes = new ArrayList<>();
        }
        return this.attributes;
    }

    public void setClassUri(String classUri) {
        this.classUri = classUri;
    }

    public String getClassUri() {
        return this.classUri;
    }

    public void setSubclassOf(String subclassOf) {
        this.subclassOf = subclassOf;
    }

    public String getSubclassOf() {
        return this.subclassOf;
    }

    public void setUnionOf(List<ClassDefinition> unionOf) {
        this.unionOf = unionOf;
    }

    public List<ClassDefinition> getUnionOf() {
        return this.unionOf;
    }

    public List<ClassDefinition> getUnionOf(boolean set) {
        if ( this.unionOf == null && set ) {
            this.unionOf = new ArrayList<>();
        }
        return this.unionOf;
    }

    public void setDefiningSlots(List<SlotDefinition> definingSlots) {
        this.definingSlots = definingSlots;
    }

    public List<SlotDefinition> getDefiningSlots() {
        return this.definingSlots;
    }

    public List<SlotDefinition> getDefiningSlots(boolean set) {
        if ( this.definingSlots == null && set ) {
            this.definingSlots = new ArrayList<>();
        }
        return this.definingSlots;
    }

    public void setTreeRoot(Boolean treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Boolean getTreeRoot() {
        return this.treeRoot;
    }

    public void setUniqueKeys(List<UniqueKey> uniqueKeys) {
        this.uniqueKeys = uniqueKeys;
    }

    public List<UniqueKey> getUniqueKeys() {
        return this.uniqueKeys;
    }

    public List<UniqueKey> getUniqueKeys(boolean set) {
        if ( this.uniqueKeys == null && set ) {
            this.uniqueKeys = new ArrayList<>();
        }
        return this.uniqueKeys;
    }

    public void setRules(List<ClassRule> rules) {
        this.rules = rules;
    }

    public List<ClassRule> getRules() {
        return this.rules;
    }

    public List<ClassRule> getRules(boolean set) {
        if ( this.rules == null && set ) {
            this.rules = new ArrayList<>();
        }
        return this.rules;
    }

    public void setClassificationRules(List<AnonymousClassExpression> classificationRules) {
        this.classificationRules = classificationRules;
    }

    public List<AnonymousClassExpression> getClassificationRules() {
        return this.classificationRules;
    }

    public List<AnonymousClassExpression> getClassificationRules(boolean set) {
        if ( this.classificationRules == null && set ) {
            this.classificationRules = new ArrayList<>();
        }
        return this.classificationRules;
    }

    public void setSlotNamesUnique(Boolean slotNamesUnique) {
        this.slotNamesUnique = slotNamesUnique;
    }

    public Boolean getSlotNamesUnique() {
        return this.slotNamesUnique;
    }

    public void setRepresentsRelationship(Boolean representsRelationship) {
        this.representsRelationship = representsRelationship;
    }

    public Boolean getRepresentsRelationship() {
        return this.representsRelationship;
    }

    public void setDisjointWith(List<ClassDefinition> disjointWith) {
        this.disjointWith = disjointWith;
    }

    public List<ClassDefinition> getDisjointWith() {
        return this.disjointWith;
    }

    public List<ClassDefinition> getDisjointWith(boolean set) {
        if ( this.disjointWith == null && set ) {
            this.disjointWith = new ArrayList<>();
        }
        return this.disjointWith;
    }

    public void setChildrenAreMutuallyDisjoint(Boolean childrenAreMutuallyDisjoint) {
        this.childrenAreMutuallyDisjoint = childrenAreMutuallyDisjoint;
    }

    public Boolean getChildrenAreMutuallyDisjoint() {
        return this.childrenAreMutuallyDisjoint;
    }

    public void setExtraSlots(ExtraSlotsExpression extraSlots) {
        this.extraSlots = extraSlots;
    }

    public ExtraSlotsExpression getExtraSlots() {
        return this.extraSlots;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAnyOf(List<AnonymousClassExpression> anyOf) {
        this.anyOf = anyOf;
    }

    public List<AnonymousClassExpression> getAnyOf() {
        return this.anyOf;
    }

    public List<AnonymousClassExpression> getAnyOf(boolean set) {
        if ( this.anyOf == null && set ) {
            this.anyOf = new ArrayList<>();
        }
        return this.anyOf;
    }

    public void setExactlyOneOf(List<AnonymousClassExpression> exactlyOneOf) {
        this.exactlyOneOf = exactlyOneOf;
    }

    public List<AnonymousClassExpression> getExactlyOneOf() {
        return this.exactlyOneOf;
    }

    public List<AnonymousClassExpression> getExactlyOneOf(boolean set) {
        if ( this.exactlyOneOf == null && set ) {
            this.exactlyOneOf = new ArrayList<>();
        }
        return this.exactlyOneOf;
    }

    public void setNoneOf(List<AnonymousClassExpression> noneOf) {
        this.noneOf = noneOf;
    }

    public List<AnonymousClassExpression> getNoneOf() {
        return this.noneOf;
    }

    public List<AnonymousClassExpression> getNoneOf(boolean set) {
        if ( this.noneOf == null && set ) {
            this.noneOf = new ArrayList<>();
        }
        return this.noneOf;
    }

    public void setAllOf(List<AnonymousClassExpression> allOf) {
        this.allOf = allOf;
    }

    public List<AnonymousClassExpression> getAllOf() {
        return this.allOf;
    }

    public List<AnonymousClassExpression> getAllOf(boolean set) {
        if ( this.allOf == null && set ) {
            this.allOf = new ArrayList<>();
        }
        return this.allOf;
    }

    public void setSlotConditions(List<SlotDefinition> slotConditions) {
        this.slotConditions = slotConditions;
    }

    public List<SlotDefinition> getSlotConditions() {
        return this.slotConditions;
    }

    public List<SlotDefinition> getSlotConditions(boolean set) {
        if ( this.slotConditions == null && set ) {
            this.slotConditions = new ArrayList<>();
        }
        return this.slotConditions;
    }

    @Override
    public String toString() {
        return "ClassDefinition(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof ClassDefinition) ) return false;
        final ClassDefinition other = (ClassDefinition) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$slots = this.getSlots();
        final Object other$slots = other.getSlots();
        if ( this$slots == null ? other$slots != null : !this$slots.equals(other$slots)) return false;
        final Object this$slotUsage = this.getSlotUsage();
        final Object other$slotUsage = other.getSlotUsage();
        if ( this$slotUsage == null ? other$slotUsage != null : !this$slotUsage.equals(other$slotUsage)) return false;
        final Object this$attributes = this.getAttributes();
        final Object other$attributes = other.getAttributes();
        if ( this$attributes == null ? other$attributes != null : !this$attributes.equals(other$attributes)) return false;
        final Object this$classUri = this.getClassUri();
        final Object other$classUri = other.getClassUri();
        if ( this$classUri == null ? other$classUri != null : !this$classUri.equals(other$classUri)) return false;
        final Object this$subclassOf = this.getSubclassOf();
        final Object other$subclassOf = other.getSubclassOf();
        if ( this$subclassOf == null ? other$subclassOf != null : !this$subclassOf.equals(other$subclassOf)) return false;
        final Object this$unionOf = this.getUnionOf();
        final Object other$unionOf = other.getUnionOf();
        if ( this$unionOf == null ? other$unionOf != null : !this$unionOf.equals(other$unionOf)) return false;
        final Object this$definingSlots = this.getDefiningSlots();
        final Object other$definingSlots = other.getDefiningSlots();
        if ( this$definingSlots == null ? other$definingSlots != null : !this$definingSlots.equals(other$definingSlots)) return false;
        final Object this$treeRoot = this.getTreeRoot();
        final Object other$treeRoot = other.getTreeRoot();
        if ( this$treeRoot == null ? other$treeRoot != null : !this$treeRoot.equals(other$treeRoot)) return false;
        final Object this$uniqueKeys = this.getUniqueKeys();
        final Object other$uniqueKeys = other.getUniqueKeys();
        if ( this$uniqueKeys == null ? other$uniqueKeys != null : !this$uniqueKeys.equals(other$uniqueKeys)) return false;
        final Object this$rules = this.getRules();
        final Object other$rules = other.getRules();
        if ( this$rules == null ? other$rules != null : !this$rules.equals(other$rules)) return false;
        final Object this$classificationRules = this.getClassificationRules();
        final Object other$classificationRules = other.getClassificationRules();
        if ( this$classificationRules == null ? other$classificationRules != null : !this$classificationRules.equals(other$classificationRules)) return false;
        final Object this$slotNamesUnique = this.getSlotNamesUnique();
        final Object other$slotNamesUnique = other.getSlotNamesUnique();
        if ( this$slotNamesUnique == null ? other$slotNamesUnique != null : !this$slotNamesUnique.equals(other$slotNamesUnique)) return false;
        final Object this$representsRelationship = this.getRepresentsRelationship();
        final Object other$representsRelationship = other.getRepresentsRelationship();
        if ( this$representsRelationship == null ? other$representsRelationship != null : !this$representsRelationship.equals(other$representsRelationship)) return false;
        final Object this$disjointWith = this.getDisjointWith();
        final Object other$disjointWith = other.getDisjointWith();
        if ( this$disjointWith == null ? other$disjointWith != null : !this$disjointWith.equals(other$disjointWith)) return false;
        final Object this$childrenAreMutuallyDisjoint = this.getChildrenAreMutuallyDisjoint();
        final Object other$childrenAreMutuallyDisjoint = other.getChildrenAreMutuallyDisjoint();
        if ( this$childrenAreMutuallyDisjoint == null ? other$childrenAreMutuallyDisjoint != null : !this$childrenAreMutuallyDisjoint.equals(other$childrenAreMutuallyDisjoint)) return false;
        final Object this$extraSlots = this.getExtraSlots();
        final Object other$extraSlots = other.getExtraSlots();
        if ( this$extraSlots == null ? other$extraSlots != null : !this$extraSlots.equals(other$extraSlots)) return false;
        final Object this$alias = this.getAlias();
        final Object other$alias = other.getAlias();
        if ( this$alias == null ? other$alias != null : !this$alias.equals(other$alias)) return false;
        final Object this$anyOf = this.getAnyOf();
        final Object other$anyOf = other.getAnyOf();
        if ( this$anyOf == null ? other$anyOf != null : !this$anyOf.equals(other$anyOf)) return false;
        final Object this$exactlyOneOf = this.getExactlyOneOf();
        final Object other$exactlyOneOf = other.getExactlyOneOf();
        if ( this$exactlyOneOf == null ? other$exactlyOneOf != null : !this$exactlyOneOf.equals(other$exactlyOneOf)) return false;
        final Object this$noneOf = this.getNoneOf();
        final Object other$noneOf = other.getNoneOf();
        if ( this$noneOf == null ? other$noneOf != null : !this$noneOf.equals(other$noneOf)) return false;
        final Object this$allOf = this.getAllOf();
        final Object other$allOf = other.getAllOf();
        if ( this$allOf == null ? other$allOf != null : !this$allOf.equals(other$allOf)) return false;
        final Object this$slotConditions = this.getSlotConditions();
        final Object other$slotConditions = other.getSlotConditions();
        if ( this$slotConditions == null ? other$slotConditions != null : !this$slotConditions.equals(other$slotConditions)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ClassDefinition;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $slots = this.getSlots();
        result = result * PRIME + ($slots == null ? 43 : $slots.hashCode());
        final Object $slotUsage = this.getSlotUsage();
        result = result * PRIME + ($slotUsage == null ? 43 : $slotUsage.hashCode());
        final Object $attributes = this.getAttributes();
        result = result * PRIME + ($attributes == null ? 43 : $attributes.hashCode());
        final Object $classUri = this.getClassUri();
        result = result * PRIME + ($classUri == null ? 43 : $classUri.hashCode());
        final Object $subclassOf = this.getSubclassOf();
        result = result * PRIME + ($subclassOf == null ? 43 : $subclassOf.hashCode());
        final Object $unionOf = this.getUnionOf();
        result = result * PRIME + ($unionOf == null ? 43 : $unionOf.hashCode());
        final Object $definingSlots = this.getDefiningSlots();
        result = result * PRIME + ($definingSlots == null ? 43 : $definingSlots.hashCode());
        final Object $treeRoot = this.getTreeRoot();
        result = result * PRIME + ($treeRoot == null ? 43 : $treeRoot.hashCode());
        final Object $uniqueKeys = this.getUniqueKeys();
        result = result * PRIME + ($uniqueKeys == null ? 43 : $uniqueKeys.hashCode());
        final Object $rules = this.getRules();
        result = result * PRIME + ($rules == null ? 43 : $rules.hashCode());
        final Object $classificationRules = this.getClassificationRules();
        result = result * PRIME + ($classificationRules == null ? 43 : $classificationRules.hashCode());
        final Object $slotNamesUnique = this.getSlotNamesUnique();
        result = result * PRIME + ($slotNamesUnique == null ? 43 : $slotNamesUnique.hashCode());
        final Object $representsRelationship = this.getRepresentsRelationship();
        result = result * PRIME + ($representsRelationship == null ? 43 : $representsRelationship.hashCode());
        final Object $disjointWith = this.getDisjointWith();
        result = result * PRIME + ($disjointWith == null ? 43 : $disjointWith.hashCode());
        final Object $childrenAreMutuallyDisjoint = this.getChildrenAreMutuallyDisjoint();
        result = result * PRIME + ($childrenAreMutuallyDisjoint == null ? 43 : $childrenAreMutuallyDisjoint.hashCode());
        final Object $extraSlots = this.getExtraSlots();
        result = result * PRIME + ($extraSlots == null ? 43 : $extraSlots.hashCode());
        final Object $alias = this.getAlias();
        result = result * PRIME + ($alias == null ? 43 : $alias.hashCode());
        final Object $anyOf = this.getAnyOf();
        result = result * PRIME + ($anyOf == null ? 43 : $anyOf.hashCode());
        final Object $exactlyOneOf = this.getExactlyOneOf();
        result = result * PRIME + ($exactlyOneOf == null ? 43 : $exactlyOneOf.hashCode());
        final Object $noneOf = this.getNoneOf();
        result = result * PRIME + ($noneOf == null ? 43 : $noneOf.hashCode());
        final Object $allOf = this.getAllOf();
        result = result * PRIME + ($allOf == null ? 43 : $allOf.hashCode());
        final Object $slotConditions = this.getSlotConditions();
        result = result * PRIME + ($slotConditions == null ? 43 : $slotConditions.hashCode());
        return result;
    }
}