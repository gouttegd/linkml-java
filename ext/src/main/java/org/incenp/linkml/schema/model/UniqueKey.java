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

@LinkURI("https://w3id.org/linkml/UniqueKey")
public class UniqueKey {

    @Identifier(isGlobal = false)
    @SlotName("unique_key_name")
    @Required
    @LinkURI("https://w3id.org/linkml/unique_key_name")
    private String uniqueKeyName;

    @SlotName("unique_key_slots")
    @Required
    @LinkURI("https://w3id.org/linkml/unique_key_slots")
    private List<SlotDefinition> uniqueKeySlots;

    @SlotName("consider_nulls_inequal")
    @LinkURI("https://w3id.org/linkml/consider_nulls_inequal")
    private Boolean considerNullsInequal;

    @Inlined
    @LinkURI("https://w3id.org/linkml/extensions")
    private List<Extension> extensions;

    @Inlined
    @LinkURI("https://w3id.org/linkml/annotations")
    private List<Annotation> annotations;

    @Required(isRecommended = true)
    @LinkURI("http://www.w3.org/2004/02/skos/core#definition")
    private String description;

    @SlotName("alt_descriptions")
    @Inlined
    @LinkURI("https://w3id.org/linkml/alt_descriptions")
    private List<AltDescription> altDescriptions;

    @LinkURI("http://purl.org/dc/terms/title")
    private String title;

    @LinkURI("https://w3id.org/linkml/deprecated")
    private String deprecated;

    @LinkURI("https://w3id.org/linkml/todos")
    private List<String> todos;

    @LinkURI("http://www.w3.org/2004/02/skos/core#editorialNote")
    private List<String> notes;

    @LinkURI("http://www.w3.org/2004/02/skos/core#note")
    private List<String> comments;

    @Inlined
    @LinkURI("https://w3id.org/linkml/examples")
    private List<Example> examples;

    @SlotName("in_subset")
    @LinkURI("http://www.geneontology.org/formats/oboInOwl#inSubset")
    private List<SubsetDefinition> inSubset;

    @SlotName("from_schema")
    @LinkURI("http://www.w3.org/2004/02/skos/core#inScheme")
    private URI fromSchema;

    @SlotName("imported_from")
    @LinkURI("https://w3id.org/linkml/imported_from")
    private String importedFrom;

    @Converter(CurieConverter.class)
    @LinkURI("http://purl.org/dc/terms/source")
    private String source;

    @SlotName("in_language")
    @LinkURI("http://schema.org/inLanguage")
    private String inLanguage;

    @SlotName("see_also")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2000/01/rdf-schema#seeAlso")
    private List<String> seeAlso;

    @SlotName("deprecated_element_has_exact_replacement")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/deprecated_element_has_exact_replacement")
    private String deprecatedElementHasExactReplacement;

    @SlotName("deprecated_element_has_possible_replacement")
    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/deprecated_element_has_possible_replacement")
    private String deprecatedElementHasPossibleReplacement;

    @LinkURI("http://www.w3.org/2004/02/skos/core#altLabel")
    private List<String> aliases;

    @SlotName("structured_aliases")
    @Inlined
    @LinkURI("http://www.w3.org/2008/05/skos-xl#altLabel")
    private List<StructuredAlias> structuredAliases;

    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#mappingRelation")
    private List<String> mappings;

    @SlotName("exact_mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#exactMatch")
    private List<String> exactMappings;

    @SlotName("close_mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#closeMatch")
    private List<String> closeMappings;

    @SlotName("related_mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#relatedMatch")
    private List<String> relatedMappings;

    @SlotName("narrow_mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#narrowMatch")
    private List<String> narrowMappings;

    @SlotName("broad_mappings")
    @Converter(CurieConverter.class)
    @LinkURI("http://www.w3.org/2004/02/skos/core#broadMatch")
    private List<String> broadMappings;

    @SlotName("created_by")
    @Converter(CurieConverter.class)
    @LinkURI("http://purl.org/pav/createdBy")
    private String createdBy;

    @Converter(CurieConverter.class)
    @LinkURI("http://purl.org/dc/terms/contributor")
    private List<String> contributors;

    @SlotName("created_on")
    @LinkURI("http://purl.org/pav/createdOn")
    private ZonedDateTime createdOn;

    @SlotName("last_updated_on")
    @LinkURI("http://purl.org/pav/lastUpdatedOn")
    private ZonedDateTime lastUpdatedOn;

    @SlotName("modified_by")
    @Converter(CurieConverter.class)
    @LinkURI("http://open-services.net/ns/core#modifiedBy")
    private String modifiedBy;

    @Converter(CurieConverter.class)
    @LinkURI("http://purl.org/ontology/bibo/status")
    private String status;

    @LinkURI("http://www.w3.org/ns/shacl#order")
    private Integer rank;

    @Converter(CurieConverter.class)
    @LinkURI("http://purl.org/dc/terms/subject")
    private List<String> categories;

    @LinkURI("http://schema.org/keywords")
    private List<String> keywords;

    public void setUniqueKeyName(String uniqueKeyName) {
        this.uniqueKeyName = uniqueKeyName;
    }

    public String getUniqueKeyName() {
        return this.uniqueKeyName;
    }

    public void setUniqueKeySlots(List<SlotDefinition> uniqueKeySlots) {
        this.uniqueKeySlots = uniqueKeySlots;
    }

    public List<SlotDefinition> getUniqueKeySlots() {
        return this.uniqueKeySlots;
    }

    public List<SlotDefinition> getUniqueKeySlots(boolean set) {
        if ( this.uniqueKeySlots == null && set ) {
            this.uniqueKeySlots = new ArrayList<>();
        }
        return this.uniqueKeySlots;
    }

    public void setConsiderNullsInequal(Boolean considerNullsInequal) {
        this.considerNullsInequal = considerNullsInequal;
    }

    public Boolean getConsiderNullsInequal() {
        return this.considerNullsInequal;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public List<Extension> getExtensions() {
        return this.extensions;
    }

    public List<Extension> getExtensions(boolean set) {
        if ( this.extensions == null && set ) {
            this.extensions = new ArrayList<>();
        }
        return this.extensions;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public List<Annotation> getAnnotations(boolean set) {
        if ( this.annotations == null && set ) {
            this.annotations = new ArrayList<>();
        }
        return this.annotations;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setAltDescriptions(List<AltDescription> altDescriptions) {
        this.altDescriptions = altDescriptions;
    }

    public List<AltDescription> getAltDescriptions() {
        return this.altDescriptions;
    }

    public List<AltDescription> getAltDescriptions(boolean set) {
        if ( this.altDescriptions == null && set ) {
            this.altDescriptions = new ArrayList<>();
        }
        return this.altDescriptions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDeprecated(String deprecated) {
        this.deprecated = deprecated;
    }

    public String getDeprecated() {
        return this.deprecated;
    }

    public void setTodos(List<String> todos) {
        this.todos = todos;
    }

    public List<String> getTodos() {
        return this.todos;
    }

    public List<String> getTodos(boolean set) {
        if ( this.todos == null && set ) {
            this.todos = new ArrayList<>();
        }
        return this.todos;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getNotes() {
        return this.notes;
    }

    public List<String> getNotes(boolean set) {
        if ( this.notes == null && set ) {
            this.notes = new ArrayList<>();
        }
        return this.notes;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public List<String> getComments(boolean set) {
        if ( this.comments == null && set ) {
            this.comments = new ArrayList<>();
        }
        return this.comments;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public List<Example> getExamples() {
        return this.examples;
    }

    public List<Example> getExamples(boolean set) {
        if ( this.examples == null && set ) {
            this.examples = new ArrayList<>();
        }
        return this.examples;
    }

    public void setInSubset(List<SubsetDefinition> inSubset) {
        this.inSubset = inSubset;
    }

    public List<SubsetDefinition> getInSubset() {
        return this.inSubset;
    }

    public List<SubsetDefinition> getInSubset(boolean set) {
        if ( this.inSubset == null && set ) {
            this.inSubset = new ArrayList<>();
        }
        return this.inSubset;
    }

    public void setFromSchema(URI fromSchema) {
        this.fromSchema = fromSchema;
    }

    public URI getFromSchema() {
        return this.fromSchema;
    }

    public void setImportedFrom(String importedFrom) {
        this.importedFrom = importedFrom;
    }

    public String getImportedFrom() {
        return this.importedFrom;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    public void setInLanguage(String inLanguage) {
        this.inLanguage = inLanguage;
    }

    public String getInLanguage() {
        return this.inLanguage;
    }

    public void setSeeAlso(List<String> seeAlso) {
        this.seeAlso = seeAlso;
    }

    public List<String> getSeeAlso() {
        return this.seeAlso;
    }

    public List<String> getSeeAlso(boolean set) {
        if ( this.seeAlso == null && set ) {
            this.seeAlso = new ArrayList<>();
        }
        return this.seeAlso;
    }

    public void setDeprecatedElementHasExactReplacement(String deprecatedElementHasExactReplacement) {
        this.deprecatedElementHasExactReplacement = deprecatedElementHasExactReplacement;
    }

    public String getDeprecatedElementHasExactReplacement() {
        return this.deprecatedElementHasExactReplacement;
    }

    public void setDeprecatedElementHasPossibleReplacement(String deprecatedElementHasPossibleReplacement) {
        this.deprecatedElementHasPossibleReplacement = deprecatedElementHasPossibleReplacement;
    }

    public String getDeprecatedElementHasPossibleReplacement() {
        return this.deprecatedElementHasPossibleReplacement;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public List<String> getAliases(boolean set) {
        if ( this.aliases == null && set ) {
            this.aliases = new ArrayList<>();
        }
        return this.aliases;
    }

    public void setStructuredAliases(List<StructuredAlias> structuredAliases) {
        this.structuredAliases = structuredAliases;
    }

    public List<StructuredAlias> getStructuredAliases() {
        return this.structuredAliases;
    }

    public List<StructuredAlias> getStructuredAliases(boolean set) {
        if ( this.structuredAliases == null && set ) {
            this.structuredAliases = new ArrayList<>();
        }
        return this.structuredAliases;
    }

    public void setMappings(List<String> mappings) {
        this.mappings = mappings;
    }

    public List<String> getMappings() {
        return this.mappings;
    }

    public List<String> getMappings(boolean set) {
        if ( this.mappings == null && set ) {
            this.mappings = new ArrayList<>();
        }
        return this.mappings;
    }

    public void setExactMappings(List<String> exactMappings) {
        this.exactMappings = exactMappings;
    }

    public List<String> getExactMappings() {
        return this.exactMappings;
    }

    public List<String> getExactMappings(boolean set) {
        if ( this.exactMappings == null && set ) {
            this.exactMappings = new ArrayList<>();
        }
        return this.exactMappings;
    }

    public void setCloseMappings(List<String> closeMappings) {
        this.closeMappings = closeMappings;
    }

    public List<String> getCloseMappings() {
        return this.closeMappings;
    }

    public List<String> getCloseMappings(boolean set) {
        if ( this.closeMappings == null && set ) {
            this.closeMappings = new ArrayList<>();
        }
        return this.closeMappings;
    }

    public void setRelatedMappings(List<String> relatedMappings) {
        this.relatedMappings = relatedMappings;
    }

    public List<String> getRelatedMappings() {
        return this.relatedMappings;
    }

    public List<String> getRelatedMappings(boolean set) {
        if ( this.relatedMappings == null && set ) {
            this.relatedMappings = new ArrayList<>();
        }
        return this.relatedMappings;
    }

    public void setNarrowMappings(List<String> narrowMappings) {
        this.narrowMappings = narrowMappings;
    }

    public List<String> getNarrowMappings() {
        return this.narrowMappings;
    }

    public List<String> getNarrowMappings(boolean set) {
        if ( this.narrowMappings == null && set ) {
            this.narrowMappings = new ArrayList<>();
        }
        return this.narrowMappings;
    }

    public void setBroadMappings(List<String> broadMappings) {
        this.broadMappings = broadMappings;
    }

    public List<String> getBroadMappings() {
        return this.broadMappings;
    }

    public List<String> getBroadMappings(boolean set) {
        if ( this.broadMappings == null && set ) {
            this.broadMappings = new ArrayList<>();
        }
        return this.broadMappings;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setContributors(List<String> contributors) {
        this.contributors = contributors;
    }

    public List<String> getContributors() {
        return this.contributors;
    }

    public List<String> getContributors(boolean set) {
        if ( this.contributors == null && set ) {
            this.contributors = new ArrayList<>();
        }
        return this.contributors;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getCreatedOn() {
        return this.createdOn;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return this.lastUpdatedOn;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return this.categories;
    }

    public List<String> getCategories(boolean set) {
        if ( this.categories == null && set ) {
            this.categories = new ArrayList<>();
        }
        return this.categories;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public List<String> getKeywords(boolean set) {
        if ( this.keywords == null && set ) {
            this.keywords = new ArrayList<>();
        }
        return this.keywords;
    }

    @Override
    public String toString() {
        return "UniqueKey(unique_key_name=" + this.getUniqueKeyName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof UniqueKey) ) return false;
        final UniqueKey other = (UniqueKey) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$uniqueKeyName = this.getUniqueKeyName();
        final Object other$uniqueKeyName = other.getUniqueKeyName();
        if ( this$uniqueKeyName == null ? other$uniqueKeyName != null : !this$uniqueKeyName.equals(other$uniqueKeyName)) return false;
        final Object this$uniqueKeySlots = this.getUniqueKeySlots();
        final Object other$uniqueKeySlots = other.getUniqueKeySlots();
        if ( this$uniqueKeySlots == null ? other$uniqueKeySlots != null : !this$uniqueKeySlots.equals(other$uniqueKeySlots)) return false;
        final Object this$considerNullsInequal = this.getConsiderNullsInequal();
        final Object other$considerNullsInequal = other.getConsiderNullsInequal();
        if ( this$considerNullsInequal == null ? other$considerNullsInequal != null : !this$considerNullsInequal.equals(other$considerNullsInequal)) return false;
        final Object this$extensions = this.getExtensions();
        final Object other$extensions = other.getExtensions();
        if ( this$extensions == null ? other$extensions != null : !this$extensions.equals(other$extensions)) return false;
        final Object this$annotations = this.getAnnotations();
        final Object other$annotations = other.getAnnotations();
        if ( this$annotations == null ? other$annotations != null : !this$annotations.equals(other$annotations)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if ( this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$altDescriptions = this.getAltDescriptions();
        final Object other$altDescriptions = other.getAltDescriptions();
        if ( this$altDescriptions == null ? other$altDescriptions != null : !this$altDescriptions.equals(other$altDescriptions)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if ( this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$deprecated = this.getDeprecated();
        final Object other$deprecated = other.getDeprecated();
        if ( this$deprecated == null ? other$deprecated != null : !this$deprecated.equals(other$deprecated)) return false;
        final Object this$todos = this.getTodos();
        final Object other$todos = other.getTodos();
        if ( this$todos == null ? other$todos != null : !this$todos.equals(other$todos)) return false;
        final Object this$notes = this.getNotes();
        final Object other$notes = other.getNotes();
        if ( this$notes == null ? other$notes != null : !this$notes.equals(other$notes)) return false;
        final Object this$comments = this.getComments();
        final Object other$comments = other.getComments();
        if ( this$comments == null ? other$comments != null : !this$comments.equals(other$comments)) return false;
        final Object this$examples = this.getExamples();
        final Object other$examples = other.getExamples();
        if ( this$examples == null ? other$examples != null : !this$examples.equals(other$examples)) return false;
        final Object this$inSubset = this.getInSubset();
        final Object other$inSubset = other.getInSubset();
        if ( this$inSubset == null ? other$inSubset != null : !this$inSubset.equals(other$inSubset)) return false;
        final Object this$fromSchema = this.getFromSchema();
        final Object other$fromSchema = other.getFromSchema();
        if ( this$fromSchema == null ? other$fromSchema != null : !this$fromSchema.equals(other$fromSchema)) return false;
        final Object this$importedFrom = this.getImportedFrom();
        final Object other$importedFrom = other.getImportedFrom();
        if ( this$importedFrom == null ? other$importedFrom != null : !this$importedFrom.equals(other$importedFrom)) return false;
        final Object this$source = this.getSource();
        final Object other$source = other.getSource();
        if ( this$source == null ? other$source != null : !this$source.equals(other$source)) return false;
        final Object this$inLanguage = this.getInLanguage();
        final Object other$inLanguage = other.getInLanguage();
        if ( this$inLanguage == null ? other$inLanguage != null : !this$inLanguage.equals(other$inLanguage)) return false;
        final Object this$seeAlso = this.getSeeAlso();
        final Object other$seeAlso = other.getSeeAlso();
        if ( this$seeAlso == null ? other$seeAlso != null : !this$seeAlso.equals(other$seeAlso)) return false;
        final Object this$deprecatedElementHasExactReplacement = this.getDeprecatedElementHasExactReplacement();
        final Object other$deprecatedElementHasExactReplacement = other.getDeprecatedElementHasExactReplacement();
        if ( this$deprecatedElementHasExactReplacement == null ? other$deprecatedElementHasExactReplacement != null : !this$deprecatedElementHasExactReplacement.equals(other$deprecatedElementHasExactReplacement)) return false;
        final Object this$deprecatedElementHasPossibleReplacement = this.getDeprecatedElementHasPossibleReplacement();
        final Object other$deprecatedElementHasPossibleReplacement = other.getDeprecatedElementHasPossibleReplacement();
        if ( this$deprecatedElementHasPossibleReplacement == null ? other$deprecatedElementHasPossibleReplacement != null : !this$deprecatedElementHasPossibleReplacement.equals(other$deprecatedElementHasPossibleReplacement)) return false;
        final Object this$aliases = this.getAliases();
        final Object other$aliases = other.getAliases();
        if ( this$aliases == null ? other$aliases != null : !this$aliases.equals(other$aliases)) return false;
        final Object this$structuredAliases = this.getStructuredAliases();
        final Object other$structuredAliases = other.getStructuredAliases();
        if ( this$structuredAliases == null ? other$structuredAliases != null : !this$structuredAliases.equals(other$structuredAliases)) return false;
        final Object this$mappings = this.getMappings();
        final Object other$mappings = other.getMappings();
        if ( this$mappings == null ? other$mappings != null : !this$mappings.equals(other$mappings)) return false;
        final Object this$exactMappings = this.getExactMappings();
        final Object other$exactMappings = other.getExactMappings();
        if ( this$exactMappings == null ? other$exactMappings != null : !this$exactMappings.equals(other$exactMappings)) return false;
        final Object this$closeMappings = this.getCloseMappings();
        final Object other$closeMappings = other.getCloseMappings();
        if ( this$closeMappings == null ? other$closeMappings != null : !this$closeMappings.equals(other$closeMappings)) return false;
        final Object this$relatedMappings = this.getRelatedMappings();
        final Object other$relatedMappings = other.getRelatedMappings();
        if ( this$relatedMappings == null ? other$relatedMappings != null : !this$relatedMappings.equals(other$relatedMappings)) return false;
        final Object this$narrowMappings = this.getNarrowMappings();
        final Object other$narrowMappings = other.getNarrowMappings();
        if ( this$narrowMappings == null ? other$narrowMappings != null : !this$narrowMappings.equals(other$narrowMappings)) return false;
        final Object this$broadMappings = this.getBroadMappings();
        final Object other$broadMappings = other.getBroadMappings();
        if ( this$broadMappings == null ? other$broadMappings != null : !this$broadMappings.equals(other$broadMappings)) return false;
        final Object this$createdBy = this.getCreatedBy();
        final Object other$createdBy = other.getCreatedBy();
        if ( this$createdBy == null ? other$createdBy != null : !this$createdBy.equals(other$createdBy)) return false;
        final Object this$contributors = this.getContributors();
        final Object other$contributors = other.getContributors();
        if ( this$contributors == null ? other$contributors != null : !this$contributors.equals(other$contributors)) return false;
        final Object this$createdOn = this.getCreatedOn();
        final Object other$createdOn = other.getCreatedOn();
        if ( this$createdOn == null ? other$createdOn != null : !this$createdOn.equals(other$createdOn)) return false;
        final Object this$lastUpdatedOn = this.getLastUpdatedOn();
        final Object other$lastUpdatedOn = other.getLastUpdatedOn();
        if ( this$lastUpdatedOn == null ? other$lastUpdatedOn != null : !this$lastUpdatedOn.equals(other$lastUpdatedOn)) return false;
        final Object this$modifiedBy = this.getModifiedBy();
        final Object other$modifiedBy = other.getModifiedBy();
        if ( this$modifiedBy == null ? other$modifiedBy != null : !this$modifiedBy.equals(other$modifiedBy)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if ( this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$rank = this.getRank();
        final Object other$rank = other.getRank();
        if ( this$rank == null ? other$rank != null : !this$rank.equals(other$rank)) return false;
        final Object this$categories = this.getCategories();
        final Object other$categories = other.getCategories();
        if ( this$categories == null ? other$categories != null : !this$categories.equals(other$categories)) return false;
        final Object this$keywords = this.getKeywords();
        final Object other$keywords = other.getKeywords();
        if ( this$keywords == null ? other$keywords != null : !this$keywords.equals(other$keywords)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UniqueKey;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $uniqueKeyName = this.getUniqueKeyName();
        result = result * PRIME + ($uniqueKeyName == null ? 43 : $uniqueKeyName.hashCode());
        final Object $uniqueKeySlots = this.getUniqueKeySlots();
        result = result * PRIME + ($uniqueKeySlots == null ? 43 : $uniqueKeySlots.hashCode());
        final Object $considerNullsInequal = this.getConsiderNullsInequal();
        result = result * PRIME + ($considerNullsInequal == null ? 43 : $considerNullsInequal.hashCode());
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        final Object $annotations = this.getAnnotations();
        result = result * PRIME + ($annotations == null ? 43 : $annotations.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $altDescriptions = this.getAltDescriptions();
        result = result * PRIME + ($altDescriptions == null ? 43 : $altDescriptions.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $deprecated = this.getDeprecated();
        result = result * PRIME + ($deprecated == null ? 43 : $deprecated.hashCode());
        final Object $todos = this.getTodos();
        result = result * PRIME + ($todos == null ? 43 : $todos.hashCode());
        final Object $notes = this.getNotes();
        result = result * PRIME + ($notes == null ? 43 : $notes.hashCode());
        final Object $comments = this.getComments();
        result = result * PRIME + ($comments == null ? 43 : $comments.hashCode());
        final Object $examples = this.getExamples();
        result = result * PRIME + ($examples == null ? 43 : $examples.hashCode());
        final Object $inSubset = this.getInSubset();
        result = result * PRIME + ($inSubset == null ? 43 : $inSubset.hashCode());
        final Object $fromSchema = this.getFromSchema();
        result = result * PRIME + ($fromSchema == null ? 43 : $fromSchema.hashCode());
        final Object $importedFrom = this.getImportedFrom();
        result = result * PRIME + ($importedFrom == null ? 43 : $importedFrom.hashCode());
        final Object $source = this.getSource();
        result = result * PRIME + ($source == null ? 43 : $source.hashCode());
        final Object $inLanguage = this.getInLanguage();
        result = result * PRIME + ($inLanguage == null ? 43 : $inLanguage.hashCode());
        final Object $seeAlso = this.getSeeAlso();
        result = result * PRIME + ($seeAlso == null ? 43 : $seeAlso.hashCode());
        final Object $deprecatedElementHasExactReplacement = this.getDeprecatedElementHasExactReplacement();
        result = result * PRIME + ($deprecatedElementHasExactReplacement == null ? 43 : $deprecatedElementHasExactReplacement.hashCode());
        final Object $deprecatedElementHasPossibleReplacement = this.getDeprecatedElementHasPossibleReplacement();
        result = result * PRIME + ($deprecatedElementHasPossibleReplacement == null ? 43 : $deprecatedElementHasPossibleReplacement.hashCode());
        final Object $aliases = this.getAliases();
        result = result * PRIME + ($aliases == null ? 43 : $aliases.hashCode());
        final Object $structuredAliases = this.getStructuredAliases();
        result = result * PRIME + ($structuredAliases == null ? 43 : $structuredAliases.hashCode());
        final Object $mappings = this.getMappings();
        result = result * PRIME + ($mappings == null ? 43 : $mappings.hashCode());
        final Object $exactMappings = this.getExactMappings();
        result = result * PRIME + ($exactMappings == null ? 43 : $exactMappings.hashCode());
        final Object $closeMappings = this.getCloseMappings();
        result = result * PRIME + ($closeMappings == null ? 43 : $closeMappings.hashCode());
        final Object $relatedMappings = this.getRelatedMappings();
        result = result * PRIME + ($relatedMappings == null ? 43 : $relatedMappings.hashCode());
        final Object $narrowMappings = this.getNarrowMappings();
        result = result * PRIME + ($narrowMappings == null ? 43 : $narrowMappings.hashCode());
        final Object $broadMappings = this.getBroadMappings();
        result = result * PRIME + ($broadMappings == null ? 43 : $broadMappings.hashCode());
        final Object $createdBy = this.getCreatedBy();
        result = result * PRIME + ($createdBy == null ? 43 : $createdBy.hashCode());
        final Object $contributors = this.getContributors();
        result = result * PRIME + ($contributors == null ? 43 : $contributors.hashCode());
        final Object $createdOn = this.getCreatedOn();
        result = result * PRIME + ($createdOn == null ? 43 : $createdOn.hashCode());
        final Object $lastUpdatedOn = this.getLastUpdatedOn();
        result = result * PRIME + ($lastUpdatedOn == null ? 43 : $lastUpdatedOn.hashCode());
        final Object $modifiedBy = this.getModifiedBy();
        result = result * PRIME + ($modifiedBy == null ? 43 : $modifiedBy.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $rank = this.getRank();
        result = result * PRIME + ($rank == null ? 43 : $rank.hashCode());
        final Object $categories = this.getCategories();
        result = result * PRIME + ($categories == null ? 43 : $categories.hashCode());
        final Object $keywords = this.getKeywords();
        result = result * PRIME + ($keywords == null ? 43 : $keywords.hashCode());
        return result;
    }
}