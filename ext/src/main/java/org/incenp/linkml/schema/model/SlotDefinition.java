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

@LinkURI("https://w3id.org/linkml/SlotDefinition")
public class SlotDefinition extends Definition {

    @SlotName("singular_name")
    @LinkURI("https://w3id.org/linkml/singular_name")
    private String singularName;

    @LinkURI("https://w3id.org/linkml/domain")
    private ClassDefinition domain;

    @SlotName("slot_uri")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/slot_uri")
    private String slotUri;

    @LinkURI("https://w3id.org/linkml/inherited")
    private Boolean inherited;

    @LinkURI("https://w3id.org/linkml/readonly")
    private String readonly;

    @LinkURI("https://w3id.org/linkml/ifabsent")
    private String ifabsent;

    @SlotName("list_elements_unique")
    @LinkURI("https://w3id.org/linkml/list_elements_unique")
    private Boolean listElementsUnique;

    @SlotName("list_elements_ordered")
    @LinkURI("https://w3id.org/linkml/list_elements_ordered")
    private Boolean listElementsOrdered;

    @LinkURI("https://w3id.org/linkml/shared")
    private Boolean shared;

    @LinkURI("https://w3id.org/linkml/key")
    private Boolean key;

    @LinkURI("https://w3id.org/linkml/identifier")
    private Boolean identifier;

    @SlotName("designates_type")
    @LinkURI("https://w3id.org/linkml/designates_type")
    private Boolean designatesType;

    @LinkURI("http://www.w3.org/2004/02/skos/core#prefLabel")
    private String alias;

    @LinkURI("https://w3id.org/linkml/owner")
    private Definition owner;

    @SlotName("domain_of")
    @LinkURI("https://w3id.org/linkml/domain_of")
    private List<ClassDefinition> domainOf;

    @SlotName("subproperty_of")
    @LinkURI("http://www.w3.org/2000/01/rdf-schema#subPropertyOf")
    private SlotDefinition subpropertyOf;

    @LinkURI("https://w3id.org/linkml/symmetric")
    private Boolean symmetric;

    @LinkURI("https://w3id.org/linkml/reflexive")
    private Boolean reflexive;

    @SlotName("locally_reflexive")
    @LinkURI("https://w3id.org/linkml/locally_reflexive")
    private Boolean locallyReflexive;

    @LinkURI("https://w3id.org/linkml/irreflexive")
    private Boolean irreflexive;

    @LinkURI("https://w3id.org/linkml/asymmetric")
    private Boolean asymmetric;

    @LinkURI("https://w3id.org/linkml/transitive")
    private Boolean transitive;

    @LinkURI("http://www.w3.org/2002/07/owl#inverseOf")
    private SlotDefinition inverse;

    @SlotName("is_class_field")
    @LinkURI("https://w3id.org/linkml/is_class_field")
    private Boolean isClassField;

    @SlotName("transitive_form_of")
    @LinkURI("https://w3id.org/linkml/transitive_form_of")
    private SlotDefinition transitiveFormOf;

    @SlotName("reflexive_transitive_form_of")
    @LinkURI("https://w3id.org/linkml/reflexive_transitive_form_of")
    private SlotDefinition reflexiveTransitiveFormOf;

    @LinkURI("https://w3id.org/linkml/role")
    private String role;

    @SlotName("is_usage_slot")
    @LinkURI("https://w3id.org/linkml/is_usage_slot")
    private Boolean isUsageSlot;

    @SlotName("usage_slot_name")
    @LinkURI("https://w3id.org/linkml/usage_slot_name")
    private String usageSlotName;

    @SlotName("relational_role")
    @LinkURI("https://w3id.org/linkml/relational_role")
    private RelationalRoleEnum relationalRole;

    @SlotName("slot_group")
    @LinkURI("http://www.w3.org/ns/shacl#group")
    private SlotDefinition slotGroup;

    @SlotName("is_grouping_slot")
    @LinkURI("https://w3id.org/linkml/is_grouping_slot")
    private Boolean isGroupingSlot;

    @SlotName("path_rule")
    @LinkURI("https://w3id.org/linkml/path_rule")
    private PathExpression pathRule;

    @SlotName("disjoint_with")
    @LinkURI("https://w3id.org/linkml/disjoint_with")
    private List<SlotDefinition> disjointWith;

    @SlotName("children_are_mutually_disjoint")
    @LinkURI("https://w3id.org/linkml/children_are_mutually_disjoint")
    private Boolean childrenAreMutuallyDisjoint;

    @SlotName("union_of")
    @LinkURI("https://w3id.org/linkml/union_of")
    private List<SlotDefinition> unionOf;

    @SlotName("type_mappings")
    @LinkURI("https://w3id.org/linkml/type_mappings")
    private List<TypeMapping> typeMappings;

    @LinkURI("https://w3id.org/linkml/range")
    private Element range;

    @SlotName("range_expression")
    @LinkURI("https://w3id.org/linkml/range_expression")
    private AnonymousClassExpression rangeExpression;

    @SlotName("enum_range")
    @LinkURI("https://w3id.org/linkml/enum_range")
    private EnumExpression enumRange;

    @Inlined
    @LinkURI("https://w3id.org/linkml/bindings")
    private List<EnumBinding> bindings;

    @LinkURI("https://w3id.org/linkml/required")
    private Boolean required;

    @LinkURI("https://w3id.org/linkml/recommended")
    private Boolean recommended;

    @LinkURI("https://w3id.org/linkml/multivalued")
    private Boolean multivalued;

    @LinkURI("https://w3id.org/linkml/inlined")
    private Boolean inlined;

    @SlotName("inlined_as_list")
    @LinkURI("https://w3id.org/linkml/inlined_as_list")
    private Boolean inlinedAsList;

    @SlotName("minimum_value")
    @LinkURI("https://w3id.org/linkml/minimum_value")
    private Object minimumValue;

    @SlotName("maximum_value")
    @LinkURI("https://w3id.org/linkml/maximum_value")
    private Object maximumValue;

    @LinkURI("https://w3id.org/linkml/pattern")
    private String pattern;

    @SlotName("structured_pattern")
    @LinkURI("https://w3id.org/linkml/structured_pattern")
    private PatternExpression structuredPattern;

    @LinkURI("http://qudt.org/schema/qudt/unit")
    private UnitOfMeasure unit;

    @SlotName("implicit_prefix")
    @LinkURI("https://w3id.org/linkml/implicit_prefix")
    private String implicitPrefix;

    @SlotName("value_presence")
    @LinkURI("https://w3id.org/linkml/value_presence")
    private PresenceEnum valuePresence;

    @SlotName("equals_string")
    @LinkURI("https://w3id.org/linkml/equals_string")
    private String equalsString;

    @SlotName("equals_string_in")
    @LinkURI("https://w3id.org/linkml/equals_string_in")
    private List<String> equalsStringIn;

    @SlotName("equals_number")
    @LinkURI("https://w3id.org/linkml/equals_number")
    private Integer equalsNumber;

    @SlotName("equals_expression")
    @LinkURI("https://w3id.org/linkml/equals_expression")
    private String equalsExpression;

    @SlotName("exact_cardinality")
    @LinkURI("https://w3id.org/linkml/exact_cardinality")
    private Integer exactCardinality;

    @SlotName("minimum_cardinality")
    @LinkURI("https://w3id.org/linkml/minimum_cardinality")
    private Integer minimumCardinality;

    @SlotName("maximum_cardinality")
    @LinkURI("https://w3id.org/linkml/maximum_cardinality")
    private Integer maximumCardinality;

    @SlotName("has_member")
    @LinkURI("https://w3id.org/linkml/has_member")
    private AnonymousSlotExpression hasMember;

    @SlotName("all_members")
    @LinkURI("https://w3id.org/linkml/all_members")
    private AnonymousSlotExpression allMembers;

    @SlotName("none_of")
    @LinkURI("https://w3id.org/linkml/none_of")
    private List<AnonymousSlotExpression> noneOf;

    @SlotName("exactly_one_of")
    @LinkURI("https://w3id.org/linkml/exactly_one_of")
    private List<AnonymousSlotExpression> exactlyOneOf;

    @SlotName("any_of")
    @LinkURI("https://w3id.org/linkml/any_of")
    private List<AnonymousSlotExpression> anyOf;

    @SlotName("all_of")
    @LinkURI("https://w3id.org/linkml/all_of")
    private List<AnonymousSlotExpression> allOf;

    @LinkURI("https://w3id.org/linkml/array")
    private ArrayExpression array;

    public void setSingularName(String singularName) {
        this.singularName = singularName;
    }

    public String getSingularName() {
        return this.singularName;
    }

    public void setDomain(ClassDefinition domain) {
        this.domain = domain;
    }

    public ClassDefinition getDomain() {
        return this.domain;
    }

    public void setSlotUri(String slotUri) {
        this.slotUri = slotUri;
    }

    public String getSlotUri() {
        return this.slotUri;
    }

    public void setInherited(Boolean inherited) {
        this.inherited = inherited;
    }

    public Boolean getInherited() {
        return this.inherited;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getReadonly() {
        return this.readonly;
    }

    public void setIfabsent(String ifabsent) {
        this.ifabsent = ifabsent;
    }

    public String getIfabsent() {
        return this.ifabsent;
    }

    public void setListElementsUnique(Boolean listElementsUnique) {
        this.listElementsUnique = listElementsUnique;
    }

    public Boolean getListElementsUnique() {
        return this.listElementsUnique;
    }

    public void setListElementsOrdered(Boolean listElementsOrdered) {
        this.listElementsOrdered = listElementsOrdered;
    }

    public Boolean getListElementsOrdered() {
        return this.listElementsOrdered;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getShared() {
        return this.shared;
    }

    public void setKey(Boolean key) {
        this.key = key;
    }

    public Boolean getKey() {
        return this.key;
    }

    public void setIdentifier(Boolean identifier) {
        this.identifier = identifier;
    }

    public Boolean getIdentifier() {
        return this.identifier;
    }

    public void setDesignatesType(Boolean designatesType) {
        this.designatesType = designatesType;
    }

    public Boolean getDesignatesType() {
        return this.designatesType;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setOwner(Definition owner) {
        this.owner = owner;
    }

    public Definition getOwner() {
        return this.owner;
    }

    public void setDomainOf(List<ClassDefinition> domainOf) {
        this.domainOf = domainOf;
    }

    public List<ClassDefinition> getDomainOf() {
        return this.domainOf;
    }

    public List<ClassDefinition> getDomainOf(boolean set) {
        if ( this.domainOf == null && set ) {
            this.domainOf = new ArrayList<>();
        }
        return this.domainOf;
    }

    public void setSubpropertyOf(SlotDefinition subpropertyOf) {
        this.subpropertyOf = subpropertyOf;
    }

    public SlotDefinition getSubpropertyOf() {
        return this.subpropertyOf;
    }

    public void setSymmetric(Boolean symmetric) {
        this.symmetric = symmetric;
    }

    public Boolean getSymmetric() {
        return this.symmetric;
    }

    public void setReflexive(Boolean reflexive) {
        this.reflexive = reflexive;
    }

    public Boolean getReflexive() {
        return this.reflexive;
    }

    public void setLocallyReflexive(Boolean locallyReflexive) {
        this.locallyReflexive = locallyReflexive;
    }

    public Boolean getLocallyReflexive() {
        return this.locallyReflexive;
    }

    public void setIrreflexive(Boolean irreflexive) {
        this.irreflexive = irreflexive;
    }

    public Boolean getIrreflexive() {
        return this.irreflexive;
    }

    public void setAsymmetric(Boolean asymmetric) {
        this.asymmetric = asymmetric;
    }

    public Boolean getAsymmetric() {
        return this.asymmetric;
    }

    public void setTransitive(Boolean transitive) {
        this.transitive = transitive;
    }

    public Boolean getTransitive() {
        return this.transitive;
    }

    public void setInverse(SlotDefinition inverse) {
        this.inverse = inverse;
    }

    public SlotDefinition getInverse() {
        return this.inverse;
    }

    public void setIsClassField(Boolean isClassField) {
        this.isClassField = isClassField;
    }

    public Boolean getIsClassField() {
        return this.isClassField;
    }

    public void setTransitiveFormOf(SlotDefinition transitiveFormOf) {
        this.transitiveFormOf = transitiveFormOf;
    }

    public SlotDefinition getTransitiveFormOf() {
        return this.transitiveFormOf;
    }

    public void setReflexiveTransitiveFormOf(SlotDefinition reflexiveTransitiveFormOf) {
        this.reflexiveTransitiveFormOf = reflexiveTransitiveFormOf;
    }

    public SlotDefinition getReflexiveTransitiveFormOf() {
        return this.reflexiveTransitiveFormOf;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public void setIsUsageSlot(Boolean isUsageSlot) {
        this.isUsageSlot = isUsageSlot;
    }

    public Boolean getIsUsageSlot() {
        return this.isUsageSlot;
    }

    public void setUsageSlotName(String usageSlotName) {
        this.usageSlotName = usageSlotName;
    }

    public String getUsageSlotName() {
        return this.usageSlotName;
    }

    public void setRelationalRole(RelationalRoleEnum relationalRole) {
        this.relationalRole = relationalRole;
    }

    public RelationalRoleEnum getRelationalRole() {
        return this.relationalRole;
    }

    public void setSlotGroup(SlotDefinition slotGroup) {
        this.slotGroup = slotGroup;
    }

    public SlotDefinition getSlotGroup() {
        return this.slotGroup;
    }

    public void setIsGroupingSlot(Boolean isGroupingSlot) {
        this.isGroupingSlot = isGroupingSlot;
    }

    public Boolean getIsGroupingSlot() {
        return this.isGroupingSlot;
    }

    public void setPathRule(PathExpression pathRule) {
        this.pathRule = pathRule;
    }

    public PathExpression getPathRule() {
        return this.pathRule;
    }

    public void setDisjointWith(List<SlotDefinition> disjointWith) {
        this.disjointWith = disjointWith;
    }

    public List<SlotDefinition> getDisjointWith() {
        return this.disjointWith;
    }

    public List<SlotDefinition> getDisjointWith(boolean set) {
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

    public void setUnionOf(List<SlotDefinition> unionOf) {
        this.unionOf = unionOf;
    }

    public List<SlotDefinition> getUnionOf() {
        return this.unionOf;
    }

    public List<SlotDefinition> getUnionOf(boolean set) {
        if ( this.unionOf == null && set ) {
            this.unionOf = new ArrayList<>();
        }
        return this.unionOf;
    }

    public void setTypeMappings(List<TypeMapping> typeMappings) {
        this.typeMappings = typeMappings;
    }

    public List<TypeMapping> getTypeMappings() {
        return this.typeMappings;
    }

    public List<TypeMapping> getTypeMappings(boolean set) {
        if ( this.typeMappings == null && set ) {
            this.typeMappings = new ArrayList<>();
        }
        return this.typeMappings;
    }

    public void setRange(Element range) {
        this.range = range;
    }

    public Element getRange() {
        return this.range;
    }

    public void setRangeExpression(AnonymousClassExpression rangeExpression) {
        this.rangeExpression = rangeExpression;
    }

    public AnonymousClassExpression getRangeExpression() {
        return this.rangeExpression;
    }

    public void setEnumRange(EnumExpression enumRange) {
        this.enumRange = enumRange;
    }

    public EnumExpression getEnumRange() {
        return this.enumRange;
    }

    public void setBindings(List<EnumBinding> bindings) {
        this.bindings = bindings;
    }

    public List<EnumBinding> getBindings() {
        return this.bindings;
    }

    public List<EnumBinding> getBindings(boolean set) {
        if ( this.bindings == null && set ) {
            this.bindings = new ArrayList<>();
        }
        return this.bindings;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRecommended(Boolean recommended) {
        this.recommended = recommended;
    }

    public Boolean getRecommended() {
        return this.recommended;
    }

    public void setMultivalued(Boolean multivalued) {
        this.multivalued = multivalued;
    }

    public Boolean getMultivalued() {
        return this.multivalued;
    }

    public void setInlined(Boolean inlined) {
        this.inlined = inlined;
    }

    public Boolean getInlined() {
        return this.inlined;
    }

    public void setInlinedAsList(Boolean inlinedAsList) {
        this.inlinedAsList = inlinedAsList;
    }

    public Boolean getInlinedAsList() {
        return this.inlinedAsList;
    }

    public void setMinimumValue(Object minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Object getMinimumValue() {
        return this.minimumValue;
    }

    public void setMaximumValue(Object maximumValue) {
        this.maximumValue = maximumValue;
    }

    public Object getMaximumValue() {
        return this.maximumValue;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setStructuredPattern(PatternExpression structuredPattern) {
        this.structuredPattern = structuredPattern;
    }

    public PatternExpression getStructuredPattern() {
        return this.structuredPattern;
    }

    public void setUnit(UnitOfMeasure unit) {
        this.unit = unit;
    }

    public UnitOfMeasure getUnit() {
        return this.unit;
    }

    public void setImplicitPrefix(String implicitPrefix) {
        this.implicitPrefix = implicitPrefix;
    }

    public String getImplicitPrefix() {
        return this.implicitPrefix;
    }

    public void setValuePresence(PresenceEnum valuePresence) {
        this.valuePresence = valuePresence;
    }

    public PresenceEnum getValuePresence() {
        return this.valuePresence;
    }

    public void setEqualsString(String equalsString) {
        this.equalsString = equalsString;
    }

    public String getEqualsString() {
        return this.equalsString;
    }

    public void setEqualsStringIn(List<String> equalsStringIn) {
        this.equalsStringIn = equalsStringIn;
    }

    public List<String> getEqualsStringIn() {
        return this.equalsStringIn;
    }

    public List<String> getEqualsStringIn(boolean set) {
        if ( this.equalsStringIn == null && set ) {
            this.equalsStringIn = new ArrayList<>();
        }
        return this.equalsStringIn;
    }

    public void setEqualsNumber(Integer equalsNumber) {
        this.equalsNumber = equalsNumber;
    }

    public Integer getEqualsNumber() {
        return this.equalsNumber;
    }

    public void setEqualsExpression(String equalsExpression) {
        this.equalsExpression = equalsExpression;
    }

    public String getEqualsExpression() {
        return this.equalsExpression;
    }

    public void setExactCardinality(Integer exactCardinality) {
        this.exactCardinality = exactCardinality;
    }

    public Integer getExactCardinality() {
        return this.exactCardinality;
    }

    public void setMinimumCardinality(Integer minimumCardinality) {
        this.minimumCardinality = minimumCardinality;
    }

    public Integer getMinimumCardinality() {
        return this.minimumCardinality;
    }

    public void setMaximumCardinality(Integer maximumCardinality) {
        this.maximumCardinality = maximumCardinality;
    }

    public Integer getMaximumCardinality() {
        return this.maximumCardinality;
    }

    public void setHasMember(AnonymousSlotExpression hasMember) {
        this.hasMember = hasMember;
    }

    public AnonymousSlotExpression getHasMember() {
        return this.hasMember;
    }

    public void setAllMembers(AnonymousSlotExpression allMembers) {
        this.allMembers = allMembers;
    }

    public AnonymousSlotExpression getAllMembers() {
        return this.allMembers;
    }

    public void setNoneOf(List<AnonymousSlotExpression> noneOf) {
        this.noneOf = noneOf;
    }

    public List<AnonymousSlotExpression> getNoneOf() {
        return this.noneOf;
    }

    public List<AnonymousSlotExpression> getNoneOf(boolean set) {
        if ( this.noneOf == null && set ) {
            this.noneOf = new ArrayList<>();
        }
        return this.noneOf;
    }

    public void setExactlyOneOf(List<AnonymousSlotExpression> exactlyOneOf) {
        this.exactlyOneOf = exactlyOneOf;
    }

    public List<AnonymousSlotExpression> getExactlyOneOf() {
        return this.exactlyOneOf;
    }

    public List<AnonymousSlotExpression> getExactlyOneOf(boolean set) {
        if ( this.exactlyOneOf == null && set ) {
            this.exactlyOneOf = new ArrayList<>();
        }
        return this.exactlyOneOf;
    }

    public void setAnyOf(List<AnonymousSlotExpression> anyOf) {
        this.anyOf = anyOf;
    }

    public List<AnonymousSlotExpression> getAnyOf() {
        return this.anyOf;
    }

    public List<AnonymousSlotExpression> getAnyOf(boolean set) {
        if ( this.anyOf == null && set ) {
            this.anyOf = new ArrayList<>();
        }
        return this.anyOf;
    }

    public void setAllOf(List<AnonymousSlotExpression> allOf) {
        this.allOf = allOf;
    }

    public List<AnonymousSlotExpression> getAllOf() {
        return this.allOf;
    }

    public List<AnonymousSlotExpression> getAllOf(boolean set) {
        if ( this.allOf == null && set ) {
            this.allOf = new ArrayList<>();
        }
        return this.allOf;
    }

    public void setArray(ArrayExpression array) {
        this.array = array;
    }

    public ArrayExpression getArray() {
        return this.array;
    }

    @Override
    public String toString() {
        return "SlotDefinition(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof SlotDefinition) ) return false;
        final SlotDefinition other = (SlotDefinition) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$singularName = this.getSingularName();
        final Object other$singularName = other.getSingularName();
        if ( this$singularName == null ? other$singularName != null : !this$singularName.equals(other$singularName)) return false;
        final Object this$domain = this.getDomain();
        final Object other$domain = other.getDomain();
        if ( this$domain == null ? other$domain != null : !this$domain.equals(other$domain)) return false;
        final Object this$slotUri = this.getSlotUri();
        final Object other$slotUri = other.getSlotUri();
        if ( this$slotUri == null ? other$slotUri != null : !this$slotUri.equals(other$slotUri)) return false;
        final Object this$inherited = this.getInherited();
        final Object other$inherited = other.getInherited();
        if ( this$inherited == null ? other$inherited != null : !this$inherited.equals(other$inherited)) return false;
        final Object this$readonly = this.getReadonly();
        final Object other$readonly = other.getReadonly();
        if ( this$readonly == null ? other$readonly != null : !this$readonly.equals(other$readonly)) return false;
        final Object this$ifabsent = this.getIfabsent();
        final Object other$ifabsent = other.getIfabsent();
        if ( this$ifabsent == null ? other$ifabsent != null : !this$ifabsent.equals(other$ifabsent)) return false;
        final Object this$listElementsUnique = this.getListElementsUnique();
        final Object other$listElementsUnique = other.getListElementsUnique();
        if ( this$listElementsUnique == null ? other$listElementsUnique != null : !this$listElementsUnique.equals(other$listElementsUnique)) return false;
        final Object this$listElementsOrdered = this.getListElementsOrdered();
        final Object other$listElementsOrdered = other.getListElementsOrdered();
        if ( this$listElementsOrdered == null ? other$listElementsOrdered != null : !this$listElementsOrdered.equals(other$listElementsOrdered)) return false;
        final Object this$shared = this.getShared();
        final Object other$shared = other.getShared();
        if ( this$shared == null ? other$shared != null : !this$shared.equals(other$shared)) return false;
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        if ( this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
        final Object this$identifier = this.getIdentifier();
        final Object other$identifier = other.getIdentifier();
        if ( this$identifier == null ? other$identifier != null : !this$identifier.equals(other$identifier)) return false;
        final Object this$designatesType = this.getDesignatesType();
        final Object other$designatesType = other.getDesignatesType();
        if ( this$designatesType == null ? other$designatesType != null : !this$designatesType.equals(other$designatesType)) return false;
        final Object this$alias = this.getAlias();
        final Object other$alias = other.getAlias();
        if ( this$alias == null ? other$alias != null : !this$alias.equals(other$alias)) return false;
        final Object this$owner = this.getOwner();
        final Object other$owner = other.getOwner();
        if ( this$owner == null ? other$owner != null : !this$owner.equals(other$owner)) return false;
        final Object this$domainOf = this.getDomainOf();
        final Object other$domainOf = other.getDomainOf();
        if ( this$domainOf == null ? other$domainOf != null : !this$domainOf.equals(other$domainOf)) return false;
        final Object this$subpropertyOf = this.getSubpropertyOf();
        final Object other$subpropertyOf = other.getSubpropertyOf();
        if ( this$subpropertyOf == null ? other$subpropertyOf != null : !this$subpropertyOf.equals(other$subpropertyOf)) return false;
        final Object this$symmetric = this.getSymmetric();
        final Object other$symmetric = other.getSymmetric();
        if ( this$symmetric == null ? other$symmetric != null : !this$symmetric.equals(other$symmetric)) return false;
        final Object this$reflexive = this.getReflexive();
        final Object other$reflexive = other.getReflexive();
        if ( this$reflexive == null ? other$reflexive != null : !this$reflexive.equals(other$reflexive)) return false;
        final Object this$locallyReflexive = this.getLocallyReflexive();
        final Object other$locallyReflexive = other.getLocallyReflexive();
        if ( this$locallyReflexive == null ? other$locallyReflexive != null : !this$locallyReflexive.equals(other$locallyReflexive)) return false;
        final Object this$irreflexive = this.getIrreflexive();
        final Object other$irreflexive = other.getIrreflexive();
        if ( this$irreflexive == null ? other$irreflexive != null : !this$irreflexive.equals(other$irreflexive)) return false;
        final Object this$asymmetric = this.getAsymmetric();
        final Object other$asymmetric = other.getAsymmetric();
        if ( this$asymmetric == null ? other$asymmetric != null : !this$asymmetric.equals(other$asymmetric)) return false;
        final Object this$transitive = this.getTransitive();
        final Object other$transitive = other.getTransitive();
        if ( this$transitive == null ? other$transitive != null : !this$transitive.equals(other$transitive)) return false;
        final Object this$inverse = this.getInverse();
        final Object other$inverse = other.getInverse();
        if ( this$inverse == null ? other$inverse != null : !this$inverse.equals(other$inverse)) return false;
        final Object this$isClassField = this.getIsClassField();
        final Object other$isClassField = other.getIsClassField();
        if ( this$isClassField == null ? other$isClassField != null : !this$isClassField.equals(other$isClassField)) return false;
        final Object this$transitiveFormOf = this.getTransitiveFormOf();
        final Object other$transitiveFormOf = other.getTransitiveFormOf();
        if ( this$transitiveFormOf == null ? other$transitiveFormOf != null : !this$transitiveFormOf.equals(other$transitiveFormOf)) return false;
        final Object this$reflexiveTransitiveFormOf = this.getReflexiveTransitiveFormOf();
        final Object other$reflexiveTransitiveFormOf = other.getReflexiveTransitiveFormOf();
        if ( this$reflexiveTransitiveFormOf == null ? other$reflexiveTransitiveFormOf != null : !this$reflexiveTransitiveFormOf.equals(other$reflexiveTransitiveFormOf)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if ( this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$isUsageSlot = this.getIsUsageSlot();
        final Object other$isUsageSlot = other.getIsUsageSlot();
        if ( this$isUsageSlot == null ? other$isUsageSlot != null : !this$isUsageSlot.equals(other$isUsageSlot)) return false;
        final Object this$usageSlotName = this.getUsageSlotName();
        final Object other$usageSlotName = other.getUsageSlotName();
        if ( this$usageSlotName == null ? other$usageSlotName != null : !this$usageSlotName.equals(other$usageSlotName)) return false;
        final Object this$relationalRole = this.getRelationalRole();
        final Object other$relationalRole = other.getRelationalRole();
        if ( this$relationalRole == null ? other$relationalRole != null : !this$relationalRole.equals(other$relationalRole)) return false;
        final Object this$slotGroup = this.getSlotGroup();
        final Object other$slotGroup = other.getSlotGroup();
        if ( this$slotGroup == null ? other$slotGroup != null : !this$slotGroup.equals(other$slotGroup)) return false;
        final Object this$isGroupingSlot = this.getIsGroupingSlot();
        final Object other$isGroupingSlot = other.getIsGroupingSlot();
        if ( this$isGroupingSlot == null ? other$isGroupingSlot != null : !this$isGroupingSlot.equals(other$isGroupingSlot)) return false;
        final Object this$pathRule = this.getPathRule();
        final Object other$pathRule = other.getPathRule();
        if ( this$pathRule == null ? other$pathRule != null : !this$pathRule.equals(other$pathRule)) return false;
        final Object this$disjointWith = this.getDisjointWith();
        final Object other$disjointWith = other.getDisjointWith();
        if ( this$disjointWith == null ? other$disjointWith != null : !this$disjointWith.equals(other$disjointWith)) return false;
        final Object this$childrenAreMutuallyDisjoint = this.getChildrenAreMutuallyDisjoint();
        final Object other$childrenAreMutuallyDisjoint = other.getChildrenAreMutuallyDisjoint();
        if ( this$childrenAreMutuallyDisjoint == null ? other$childrenAreMutuallyDisjoint != null : !this$childrenAreMutuallyDisjoint.equals(other$childrenAreMutuallyDisjoint)) return false;
        final Object this$unionOf = this.getUnionOf();
        final Object other$unionOf = other.getUnionOf();
        if ( this$unionOf == null ? other$unionOf != null : !this$unionOf.equals(other$unionOf)) return false;
        final Object this$typeMappings = this.getTypeMappings();
        final Object other$typeMappings = other.getTypeMappings();
        if ( this$typeMappings == null ? other$typeMappings != null : !this$typeMappings.equals(other$typeMappings)) return false;
        final Object this$range = this.getRange();
        final Object other$range = other.getRange();
        if ( this$range == null ? other$range != null : !this$range.equals(other$range)) return false;
        final Object this$rangeExpression = this.getRangeExpression();
        final Object other$rangeExpression = other.getRangeExpression();
        if ( this$rangeExpression == null ? other$rangeExpression != null : !this$rangeExpression.equals(other$rangeExpression)) return false;
        final Object this$enumRange = this.getEnumRange();
        final Object other$enumRange = other.getEnumRange();
        if ( this$enumRange == null ? other$enumRange != null : !this$enumRange.equals(other$enumRange)) return false;
        final Object this$bindings = this.getBindings();
        final Object other$bindings = other.getBindings();
        if ( this$bindings == null ? other$bindings != null : !this$bindings.equals(other$bindings)) return false;
        final Object this$required = this.getRequired();
        final Object other$required = other.getRequired();
        if ( this$required == null ? other$required != null : !this$required.equals(other$required)) return false;
        final Object this$recommended = this.getRecommended();
        final Object other$recommended = other.getRecommended();
        if ( this$recommended == null ? other$recommended != null : !this$recommended.equals(other$recommended)) return false;
        final Object this$multivalued = this.getMultivalued();
        final Object other$multivalued = other.getMultivalued();
        if ( this$multivalued == null ? other$multivalued != null : !this$multivalued.equals(other$multivalued)) return false;
        final Object this$inlined = this.getInlined();
        final Object other$inlined = other.getInlined();
        if ( this$inlined == null ? other$inlined != null : !this$inlined.equals(other$inlined)) return false;
        final Object this$inlinedAsList = this.getInlinedAsList();
        final Object other$inlinedAsList = other.getInlinedAsList();
        if ( this$inlinedAsList == null ? other$inlinedAsList != null : !this$inlinedAsList.equals(other$inlinedAsList)) return false;
        final Object this$minimumValue = this.getMinimumValue();
        final Object other$minimumValue = other.getMinimumValue();
        if ( this$minimumValue == null ? other$minimumValue != null : !this$minimumValue.equals(other$minimumValue)) return false;
        final Object this$maximumValue = this.getMaximumValue();
        final Object other$maximumValue = other.getMaximumValue();
        if ( this$maximumValue == null ? other$maximumValue != null : !this$maximumValue.equals(other$maximumValue)) return false;
        final Object this$pattern = this.getPattern();
        final Object other$pattern = other.getPattern();
        if ( this$pattern == null ? other$pattern != null : !this$pattern.equals(other$pattern)) return false;
        final Object this$structuredPattern = this.getStructuredPattern();
        final Object other$structuredPattern = other.getStructuredPattern();
        if ( this$structuredPattern == null ? other$structuredPattern != null : !this$structuredPattern.equals(other$structuredPattern)) return false;
        final Object this$unit = this.getUnit();
        final Object other$unit = other.getUnit();
        if ( this$unit == null ? other$unit != null : !this$unit.equals(other$unit)) return false;
        final Object this$implicitPrefix = this.getImplicitPrefix();
        final Object other$implicitPrefix = other.getImplicitPrefix();
        if ( this$implicitPrefix == null ? other$implicitPrefix != null : !this$implicitPrefix.equals(other$implicitPrefix)) return false;
        final Object this$valuePresence = this.getValuePresence();
        final Object other$valuePresence = other.getValuePresence();
        if ( this$valuePresence == null ? other$valuePresence != null : !this$valuePresence.equals(other$valuePresence)) return false;
        final Object this$equalsString = this.getEqualsString();
        final Object other$equalsString = other.getEqualsString();
        if ( this$equalsString == null ? other$equalsString != null : !this$equalsString.equals(other$equalsString)) return false;
        final Object this$equalsStringIn = this.getEqualsStringIn();
        final Object other$equalsStringIn = other.getEqualsStringIn();
        if ( this$equalsStringIn == null ? other$equalsStringIn != null : !this$equalsStringIn.equals(other$equalsStringIn)) return false;
        final Object this$equalsNumber = this.getEqualsNumber();
        final Object other$equalsNumber = other.getEqualsNumber();
        if ( this$equalsNumber == null ? other$equalsNumber != null : !this$equalsNumber.equals(other$equalsNumber)) return false;
        final Object this$equalsExpression = this.getEqualsExpression();
        final Object other$equalsExpression = other.getEqualsExpression();
        if ( this$equalsExpression == null ? other$equalsExpression != null : !this$equalsExpression.equals(other$equalsExpression)) return false;
        final Object this$exactCardinality = this.getExactCardinality();
        final Object other$exactCardinality = other.getExactCardinality();
        if ( this$exactCardinality == null ? other$exactCardinality != null : !this$exactCardinality.equals(other$exactCardinality)) return false;
        final Object this$minimumCardinality = this.getMinimumCardinality();
        final Object other$minimumCardinality = other.getMinimumCardinality();
        if ( this$minimumCardinality == null ? other$minimumCardinality != null : !this$minimumCardinality.equals(other$minimumCardinality)) return false;
        final Object this$maximumCardinality = this.getMaximumCardinality();
        final Object other$maximumCardinality = other.getMaximumCardinality();
        if ( this$maximumCardinality == null ? other$maximumCardinality != null : !this$maximumCardinality.equals(other$maximumCardinality)) return false;
        final Object this$hasMember = this.getHasMember();
        final Object other$hasMember = other.getHasMember();
        if ( this$hasMember == null ? other$hasMember != null : !this$hasMember.equals(other$hasMember)) return false;
        final Object this$allMembers = this.getAllMembers();
        final Object other$allMembers = other.getAllMembers();
        if ( this$allMembers == null ? other$allMembers != null : !this$allMembers.equals(other$allMembers)) return false;
        final Object this$noneOf = this.getNoneOf();
        final Object other$noneOf = other.getNoneOf();
        if ( this$noneOf == null ? other$noneOf != null : !this$noneOf.equals(other$noneOf)) return false;
        final Object this$exactlyOneOf = this.getExactlyOneOf();
        final Object other$exactlyOneOf = other.getExactlyOneOf();
        if ( this$exactlyOneOf == null ? other$exactlyOneOf != null : !this$exactlyOneOf.equals(other$exactlyOneOf)) return false;
        final Object this$anyOf = this.getAnyOf();
        final Object other$anyOf = other.getAnyOf();
        if ( this$anyOf == null ? other$anyOf != null : !this$anyOf.equals(other$anyOf)) return false;
        final Object this$allOf = this.getAllOf();
        final Object other$allOf = other.getAllOf();
        if ( this$allOf == null ? other$allOf != null : !this$allOf.equals(other$allOf)) return false;
        final Object this$array = this.getArray();
        final Object other$array = other.getArray();
        if ( this$array == null ? other$array != null : !this$array.equals(other$array)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SlotDefinition;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $singularName = this.getSingularName();
        result = result * PRIME + ($singularName == null ? 43 : $singularName.hashCode());
        final Object $domain = this.getDomain();
        result = result * PRIME + ($domain == null ? 43 : $domain.hashCode());
        final Object $slotUri = this.getSlotUri();
        result = result * PRIME + ($slotUri == null ? 43 : $slotUri.hashCode());
        final Object $inherited = this.getInherited();
        result = result * PRIME + ($inherited == null ? 43 : $inherited.hashCode());
        final Object $readonly = this.getReadonly();
        result = result * PRIME + ($readonly == null ? 43 : $readonly.hashCode());
        final Object $ifabsent = this.getIfabsent();
        result = result * PRIME + ($ifabsent == null ? 43 : $ifabsent.hashCode());
        final Object $listElementsUnique = this.getListElementsUnique();
        result = result * PRIME + ($listElementsUnique == null ? 43 : $listElementsUnique.hashCode());
        final Object $listElementsOrdered = this.getListElementsOrdered();
        result = result * PRIME + ($listElementsOrdered == null ? 43 : $listElementsOrdered.hashCode());
        final Object $shared = this.getShared();
        result = result * PRIME + ($shared == null ? 43 : $shared.hashCode());
        final Object $key = this.getKey();
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        final Object $identifier = this.getIdentifier();
        result = result * PRIME + ($identifier == null ? 43 : $identifier.hashCode());
        final Object $designatesType = this.getDesignatesType();
        result = result * PRIME + ($designatesType == null ? 43 : $designatesType.hashCode());
        final Object $alias = this.getAlias();
        result = result * PRIME + ($alias == null ? 43 : $alias.hashCode());
        final Object $owner = this.getOwner();
        result = result * PRIME + ($owner == null ? 43 : $owner.hashCode());
        final Object $domainOf = this.getDomainOf();
        result = result * PRIME + ($domainOf == null ? 43 : $domainOf.hashCode());
        final Object $subpropertyOf = this.getSubpropertyOf();
        result = result * PRIME + ($subpropertyOf == null ? 43 : $subpropertyOf.hashCode());
        final Object $symmetric = this.getSymmetric();
        result = result * PRIME + ($symmetric == null ? 43 : $symmetric.hashCode());
        final Object $reflexive = this.getReflexive();
        result = result * PRIME + ($reflexive == null ? 43 : $reflexive.hashCode());
        final Object $locallyReflexive = this.getLocallyReflexive();
        result = result * PRIME + ($locallyReflexive == null ? 43 : $locallyReflexive.hashCode());
        final Object $irreflexive = this.getIrreflexive();
        result = result * PRIME + ($irreflexive == null ? 43 : $irreflexive.hashCode());
        final Object $asymmetric = this.getAsymmetric();
        result = result * PRIME + ($asymmetric == null ? 43 : $asymmetric.hashCode());
        final Object $transitive = this.getTransitive();
        result = result * PRIME + ($transitive == null ? 43 : $transitive.hashCode());
        final Object $inverse = this.getInverse();
        result = result * PRIME + ($inverse == null ? 43 : $inverse.hashCode());
        final Object $isClassField = this.getIsClassField();
        result = result * PRIME + ($isClassField == null ? 43 : $isClassField.hashCode());
        final Object $transitiveFormOf = this.getTransitiveFormOf();
        result = result * PRIME + ($transitiveFormOf == null ? 43 : $transitiveFormOf.hashCode());
        final Object $reflexiveTransitiveFormOf = this.getReflexiveTransitiveFormOf();
        result = result * PRIME + ($reflexiveTransitiveFormOf == null ? 43 : $reflexiveTransitiveFormOf.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $isUsageSlot = this.getIsUsageSlot();
        result = result * PRIME + ($isUsageSlot == null ? 43 : $isUsageSlot.hashCode());
        final Object $usageSlotName = this.getUsageSlotName();
        result = result * PRIME + ($usageSlotName == null ? 43 : $usageSlotName.hashCode());
        final Object $relationalRole = this.getRelationalRole();
        result = result * PRIME + ($relationalRole == null ? 43 : $relationalRole.hashCode());
        final Object $slotGroup = this.getSlotGroup();
        result = result * PRIME + ($slotGroup == null ? 43 : $slotGroup.hashCode());
        final Object $isGroupingSlot = this.getIsGroupingSlot();
        result = result * PRIME + ($isGroupingSlot == null ? 43 : $isGroupingSlot.hashCode());
        final Object $pathRule = this.getPathRule();
        result = result * PRIME + ($pathRule == null ? 43 : $pathRule.hashCode());
        final Object $disjointWith = this.getDisjointWith();
        result = result * PRIME + ($disjointWith == null ? 43 : $disjointWith.hashCode());
        final Object $childrenAreMutuallyDisjoint = this.getChildrenAreMutuallyDisjoint();
        result = result * PRIME + ($childrenAreMutuallyDisjoint == null ? 43 : $childrenAreMutuallyDisjoint.hashCode());
        final Object $unionOf = this.getUnionOf();
        result = result * PRIME + ($unionOf == null ? 43 : $unionOf.hashCode());
        final Object $typeMappings = this.getTypeMappings();
        result = result * PRIME + ($typeMappings == null ? 43 : $typeMappings.hashCode());
        final Object $range = this.getRange();
        result = result * PRIME + ($range == null ? 43 : $range.hashCode());
        final Object $rangeExpression = this.getRangeExpression();
        result = result * PRIME + ($rangeExpression == null ? 43 : $rangeExpression.hashCode());
        final Object $enumRange = this.getEnumRange();
        result = result * PRIME + ($enumRange == null ? 43 : $enumRange.hashCode());
        final Object $bindings = this.getBindings();
        result = result * PRIME + ($bindings == null ? 43 : $bindings.hashCode());
        final Object $required = this.getRequired();
        result = result * PRIME + ($required == null ? 43 : $required.hashCode());
        final Object $recommended = this.getRecommended();
        result = result * PRIME + ($recommended == null ? 43 : $recommended.hashCode());
        final Object $multivalued = this.getMultivalued();
        result = result * PRIME + ($multivalued == null ? 43 : $multivalued.hashCode());
        final Object $inlined = this.getInlined();
        result = result * PRIME + ($inlined == null ? 43 : $inlined.hashCode());
        final Object $inlinedAsList = this.getInlinedAsList();
        result = result * PRIME + ($inlinedAsList == null ? 43 : $inlinedAsList.hashCode());
        final Object $minimumValue = this.getMinimumValue();
        result = result * PRIME + ($minimumValue == null ? 43 : $minimumValue.hashCode());
        final Object $maximumValue = this.getMaximumValue();
        result = result * PRIME + ($maximumValue == null ? 43 : $maximumValue.hashCode());
        final Object $pattern = this.getPattern();
        result = result * PRIME + ($pattern == null ? 43 : $pattern.hashCode());
        final Object $structuredPattern = this.getStructuredPattern();
        result = result * PRIME + ($structuredPattern == null ? 43 : $structuredPattern.hashCode());
        final Object $unit = this.getUnit();
        result = result * PRIME + ($unit == null ? 43 : $unit.hashCode());
        final Object $implicitPrefix = this.getImplicitPrefix();
        result = result * PRIME + ($implicitPrefix == null ? 43 : $implicitPrefix.hashCode());
        final Object $valuePresence = this.getValuePresence();
        result = result * PRIME + ($valuePresence == null ? 43 : $valuePresence.hashCode());
        final Object $equalsString = this.getEqualsString();
        result = result * PRIME + ($equalsString == null ? 43 : $equalsString.hashCode());
        final Object $equalsStringIn = this.getEqualsStringIn();
        result = result * PRIME + ($equalsStringIn == null ? 43 : $equalsStringIn.hashCode());
        final Object $equalsNumber = this.getEqualsNumber();
        result = result * PRIME + ($equalsNumber == null ? 43 : $equalsNumber.hashCode());
        final Object $equalsExpression = this.getEqualsExpression();
        result = result * PRIME + ($equalsExpression == null ? 43 : $equalsExpression.hashCode());
        final Object $exactCardinality = this.getExactCardinality();
        result = result * PRIME + ($exactCardinality == null ? 43 : $exactCardinality.hashCode());
        final Object $minimumCardinality = this.getMinimumCardinality();
        result = result * PRIME + ($minimumCardinality == null ? 43 : $minimumCardinality.hashCode());
        final Object $maximumCardinality = this.getMaximumCardinality();
        result = result * PRIME + ($maximumCardinality == null ? 43 : $maximumCardinality.hashCode());
        final Object $hasMember = this.getHasMember();
        result = result * PRIME + ($hasMember == null ? 43 : $hasMember.hashCode());
        final Object $allMembers = this.getAllMembers();
        result = result * PRIME + ($allMembers == null ? 43 : $allMembers.hashCode());
        final Object $noneOf = this.getNoneOf();
        result = result * PRIME + ($noneOf == null ? 43 : $noneOf.hashCode());
        final Object $exactlyOneOf = this.getExactlyOneOf();
        result = result * PRIME + ($exactlyOneOf == null ? 43 : $exactlyOneOf.hashCode());
        final Object $anyOf = this.getAnyOf();
        result = result * PRIME + ($anyOf == null ? 43 : $anyOf.hashCode());
        final Object $allOf = this.getAllOf();
        result = result * PRIME + ($allOf == null ? 43 : $allOf.hashCode());
        final Object $array = this.getArray();
        result = result * PRIME + ($array == null ? 43 : $array.hashCode());
        return result;
    }
}