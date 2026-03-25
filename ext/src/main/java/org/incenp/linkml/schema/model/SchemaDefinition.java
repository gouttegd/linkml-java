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

@LinkURI("https://w3id.org/linkml/SchemaDefinition")
public class SchemaDefinition extends Element {

    @Required
    @LinkURI("https://w3id.org/linkml/id")
    private URI id;

    @LinkURI("http://purl.org/pav/version")
    private String version;

    @Converter(CurieConverter.class)
    @LinkURI("https://w3id.org/linkml/imports")
    private List<String> imports;

    @LinkURI("http://purl.org/dc/terms/license")
    private String license;

    @Inlined
    @LinkURI("http://www.w3.org/ns/shacl#declare")
    private List<Prefix> prefixes;

    @SlotName("emit_prefixes")
    @LinkURI("https://w3id.org/linkml/emit_prefixes")
    private List<String> emitPrefixes;

    @SlotName("default_curi_maps")
    @LinkURI("https://w3id.org/linkml/default_curi_maps")
    private List<String> defaultCuriMaps;

    @SlotName("default_prefix")
    @LinkURI("https://w3id.org/linkml/default_prefix")
    private String defaultPrefix;

    @SlotName("default_range")
    @LinkURI("https://w3id.org/linkml/default_range")
    private TypeDefinition defaultRange;

    @Inlined
    @LinkURI("https://w3id.org/linkml/subsets")
    private List<SubsetDefinition> subsets;

    @Inlined
    @LinkURI("https://w3id.org/linkml/types")
    private List<TypeDefinition> types;

    @Inlined
    @LinkURI("https://w3id.org/linkml/enums")
    private List<EnumDefinition> enums;

    @Inlined
    @LinkURI("https://w3id.org/linkml/slot_definitions")
    private List<SlotDefinition> slots;

    @Inlined
    @LinkURI("https://w3id.org/linkml/classes")
    private List<ClassDefinition> classes;

    @SlotName("metamodel_version")
    @LinkURI("https://w3id.org/linkml/metamodel_version")
    private String metamodelVersion;

    @SlotName("source_file")
    @LinkURI("https://w3id.org/linkml/source_file")
    private String sourceFile;

    @SlotName("source_file_date")
    @LinkURI("https://w3id.org/linkml/source_file_date")
    private ZonedDateTime sourceFileDate;

    @SlotName("source_file_size")
    @LinkURI("https://w3id.org/linkml/source_file_size")
    private Integer sourceFileSize;

    @SlotName("generation_date")
    @LinkURI("https://w3id.org/linkml/generation_date")
    private ZonedDateTime generationDate;

    @SlotName("slot_names_unique")
    @LinkURI("https://w3id.org/linkml/slot_names_unique")
    private Boolean slotNamesUnique;

    @Inlined
    @LinkURI("https://w3id.org/linkml/settings")
    private List<Setting> settings;

    @Inlined
    @LinkURI("https://w3id.org/linkml/bindings")
    private List<EnumBinding> bindings;

    public void setId(URI id) {
        this.id = id;
    }

    public URI getId() {
        return this.id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public List<String> getImports() {
        return this.imports;
    }

    public List<String> getImports(boolean set) {
        if ( this.imports == null && set ) {
            this.imports = new ArrayList<>();
        }
        return this.imports;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicense() {
        return this.license;
    }

    public void setPrefixes(List<Prefix> prefixes) {
        this.prefixes = prefixes;
    }

    public List<Prefix> getPrefixes() {
        return this.prefixes;
    }

    public List<Prefix> getPrefixes(boolean set) {
        if ( this.prefixes == null && set ) {
            this.prefixes = new ArrayList<>();
        }
        return this.prefixes;
    }

    public void setEmitPrefixes(List<String> emitPrefixes) {
        this.emitPrefixes = emitPrefixes;
    }

    public List<String> getEmitPrefixes() {
        return this.emitPrefixes;
    }

    public List<String> getEmitPrefixes(boolean set) {
        if ( this.emitPrefixes == null && set ) {
            this.emitPrefixes = new ArrayList<>();
        }
        return this.emitPrefixes;
    }

    public void setDefaultCuriMaps(List<String> defaultCuriMaps) {
        this.defaultCuriMaps = defaultCuriMaps;
    }

    public List<String> getDefaultCuriMaps() {
        return this.defaultCuriMaps;
    }

    public List<String> getDefaultCuriMaps(boolean set) {
        if ( this.defaultCuriMaps == null && set ) {
            this.defaultCuriMaps = new ArrayList<>();
        }
        return this.defaultCuriMaps;
    }

    public void setDefaultPrefix(String defaultPrefix) {
        this.defaultPrefix = defaultPrefix;
    }

    public String getDefaultPrefix() {
        return this.defaultPrefix;
    }

    public void setDefaultRange(TypeDefinition defaultRange) {
        this.defaultRange = defaultRange;
    }

    public TypeDefinition getDefaultRange() {
        return this.defaultRange;
    }

    public void setSubsets(List<SubsetDefinition> subsets) {
        this.subsets = subsets;
    }

    public List<SubsetDefinition> getSubsets() {
        return this.subsets;
    }

    public List<SubsetDefinition> getSubsets(boolean set) {
        if ( this.subsets == null && set ) {
            this.subsets = new ArrayList<>();
        }
        return this.subsets;
    }

    public void setTypes(List<TypeDefinition> types) {
        this.types = types;
    }

    public List<TypeDefinition> getTypes() {
        return this.types;
    }

    public List<TypeDefinition> getTypes(boolean set) {
        if ( this.types == null && set ) {
            this.types = new ArrayList<>();
        }
        return this.types;
    }

    public void setEnums(List<EnumDefinition> enums) {
        this.enums = enums;
    }

    public List<EnumDefinition> getEnums() {
        return this.enums;
    }

    public List<EnumDefinition> getEnums(boolean set) {
        if ( this.enums == null && set ) {
            this.enums = new ArrayList<>();
        }
        return this.enums;
    }

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

    public void setClasses(List<ClassDefinition> classes) {
        this.classes = classes;
    }

    public List<ClassDefinition> getClasses() {
        return this.classes;
    }

    public List<ClassDefinition> getClasses(boolean set) {
        if ( this.classes == null && set ) {
            this.classes = new ArrayList<>();
        }
        return this.classes;
    }

    public void setMetamodelVersion(String metamodelVersion) {
        this.metamodelVersion = metamodelVersion;
    }

    public String getMetamodelVersion() {
        return this.metamodelVersion;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getSourceFile() {
        return this.sourceFile;
    }

    public void setSourceFileDate(ZonedDateTime sourceFileDate) {
        this.sourceFileDate = sourceFileDate;
    }

    public ZonedDateTime getSourceFileDate() {
        return this.sourceFileDate;
    }

    public void setSourceFileSize(Integer sourceFileSize) {
        this.sourceFileSize = sourceFileSize;
    }

    public Integer getSourceFileSize() {
        return this.sourceFileSize;
    }

    public void setGenerationDate(ZonedDateTime generationDate) {
        this.generationDate = generationDate;
    }

    public ZonedDateTime getGenerationDate() {
        return this.generationDate;
    }

    public void setSlotNamesUnique(Boolean slotNamesUnique) {
        this.slotNamesUnique = slotNamesUnique;
    }

    public Boolean getSlotNamesUnique() {
        return this.slotNamesUnique;
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public List<Setting> getSettings(boolean set) {
        if ( this.settings == null && set ) {
            this.settings = new ArrayList<>();
        }
        return this.settings;
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

    @Override
    public String toString() {
        return "SchemaDefinition(name=" + this.getName() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof SchemaDefinition) ) return false;
        final SchemaDefinition other = (SchemaDefinition) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if ( this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if ( this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        final Object this$imports = this.getImports();
        final Object other$imports = other.getImports();
        if ( this$imports == null ? other$imports != null : !this$imports.equals(other$imports)) return false;
        final Object this$license = this.getLicense();
        final Object other$license = other.getLicense();
        if ( this$license == null ? other$license != null : !this$license.equals(other$license)) return false;
        final Object this$prefixes = this.getPrefixes();
        final Object other$prefixes = other.getPrefixes();
        if ( this$prefixes == null ? other$prefixes != null : !this$prefixes.equals(other$prefixes)) return false;
        final Object this$emitPrefixes = this.getEmitPrefixes();
        final Object other$emitPrefixes = other.getEmitPrefixes();
        if ( this$emitPrefixes == null ? other$emitPrefixes != null : !this$emitPrefixes.equals(other$emitPrefixes)) return false;
        final Object this$defaultCuriMaps = this.getDefaultCuriMaps();
        final Object other$defaultCuriMaps = other.getDefaultCuriMaps();
        if ( this$defaultCuriMaps == null ? other$defaultCuriMaps != null : !this$defaultCuriMaps.equals(other$defaultCuriMaps)) return false;
        final Object this$defaultPrefix = this.getDefaultPrefix();
        final Object other$defaultPrefix = other.getDefaultPrefix();
        if ( this$defaultPrefix == null ? other$defaultPrefix != null : !this$defaultPrefix.equals(other$defaultPrefix)) return false;
        final Object this$defaultRange = this.getDefaultRange();
        final Object other$defaultRange = other.getDefaultRange();
        if ( this$defaultRange == null ? other$defaultRange != null : !this$defaultRange.equals(other$defaultRange)) return false;
        final Object this$subsets = this.getSubsets();
        final Object other$subsets = other.getSubsets();
        if ( this$subsets == null ? other$subsets != null : !this$subsets.equals(other$subsets)) return false;
        final Object this$types = this.getTypes();
        final Object other$types = other.getTypes();
        if ( this$types == null ? other$types != null : !this$types.equals(other$types)) return false;
        final Object this$enums = this.getEnums();
        final Object other$enums = other.getEnums();
        if ( this$enums == null ? other$enums != null : !this$enums.equals(other$enums)) return false;
        final Object this$slots = this.getSlots();
        final Object other$slots = other.getSlots();
        if ( this$slots == null ? other$slots != null : !this$slots.equals(other$slots)) return false;
        final Object this$classes = this.getClasses();
        final Object other$classes = other.getClasses();
        if ( this$classes == null ? other$classes != null : !this$classes.equals(other$classes)) return false;
        final Object this$metamodelVersion = this.getMetamodelVersion();
        final Object other$metamodelVersion = other.getMetamodelVersion();
        if ( this$metamodelVersion == null ? other$metamodelVersion != null : !this$metamodelVersion.equals(other$metamodelVersion)) return false;
        final Object this$sourceFile = this.getSourceFile();
        final Object other$sourceFile = other.getSourceFile();
        if ( this$sourceFile == null ? other$sourceFile != null : !this$sourceFile.equals(other$sourceFile)) return false;
        final Object this$sourceFileDate = this.getSourceFileDate();
        final Object other$sourceFileDate = other.getSourceFileDate();
        if ( this$sourceFileDate == null ? other$sourceFileDate != null : !this$sourceFileDate.equals(other$sourceFileDate)) return false;
        final Object this$sourceFileSize = this.getSourceFileSize();
        final Object other$sourceFileSize = other.getSourceFileSize();
        if ( this$sourceFileSize == null ? other$sourceFileSize != null : !this$sourceFileSize.equals(other$sourceFileSize)) return false;
        final Object this$generationDate = this.getGenerationDate();
        final Object other$generationDate = other.getGenerationDate();
        if ( this$generationDate == null ? other$generationDate != null : !this$generationDate.equals(other$generationDate)) return false;
        final Object this$slotNamesUnique = this.getSlotNamesUnique();
        final Object other$slotNamesUnique = other.getSlotNamesUnique();
        if ( this$slotNamesUnique == null ? other$slotNamesUnique != null : !this$slotNamesUnique.equals(other$slotNamesUnique)) return false;
        final Object this$settings = this.getSettings();
        final Object other$settings = other.getSettings();
        if ( this$settings == null ? other$settings != null : !this$settings.equals(other$settings)) return false;
        final Object this$bindings = this.getBindings();
        final Object other$bindings = other.getBindings();
        if ( this$bindings == null ? other$bindings != null : !this$bindings.equals(other$bindings)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SchemaDefinition;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $imports = this.getImports();
        result = result * PRIME + ($imports == null ? 43 : $imports.hashCode());
        final Object $license = this.getLicense();
        result = result * PRIME + ($license == null ? 43 : $license.hashCode());
        final Object $prefixes = this.getPrefixes();
        result = result * PRIME + ($prefixes == null ? 43 : $prefixes.hashCode());
        final Object $emitPrefixes = this.getEmitPrefixes();
        result = result * PRIME + ($emitPrefixes == null ? 43 : $emitPrefixes.hashCode());
        final Object $defaultCuriMaps = this.getDefaultCuriMaps();
        result = result * PRIME + ($defaultCuriMaps == null ? 43 : $defaultCuriMaps.hashCode());
        final Object $defaultPrefix = this.getDefaultPrefix();
        result = result * PRIME + ($defaultPrefix == null ? 43 : $defaultPrefix.hashCode());
        final Object $defaultRange = this.getDefaultRange();
        result = result * PRIME + ($defaultRange == null ? 43 : $defaultRange.hashCode());
        final Object $subsets = this.getSubsets();
        result = result * PRIME + ($subsets == null ? 43 : $subsets.hashCode());
        final Object $types = this.getTypes();
        result = result * PRIME + ($types == null ? 43 : $types.hashCode());
        final Object $enums = this.getEnums();
        result = result * PRIME + ($enums == null ? 43 : $enums.hashCode());
        final Object $slots = this.getSlots();
        result = result * PRIME + ($slots == null ? 43 : $slots.hashCode());
        final Object $classes = this.getClasses();
        result = result * PRIME + ($classes == null ? 43 : $classes.hashCode());
        final Object $metamodelVersion = this.getMetamodelVersion();
        result = result * PRIME + ($metamodelVersion == null ? 43 : $metamodelVersion.hashCode());
        final Object $sourceFile = this.getSourceFile();
        result = result * PRIME + ($sourceFile == null ? 43 : $sourceFile.hashCode());
        final Object $sourceFileDate = this.getSourceFileDate();
        result = result * PRIME + ($sourceFileDate == null ? 43 : $sourceFileDate.hashCode());
        final Object $sourceFileSize = this.getSourceFileSize();
        result = result * PRIME + ($sourceFileSize == null ? 43 : $sourceFileSize.hashCode());
        final Object $generationDate = this.getGenerationDate();
        result = result * PRIME + ($generationDate == null ? 43 : $generationDate.hashCode());
        final Object $slotNamesUnique = this.getSlotNamesUnique();
        result = result * PRIME + ($slotNamesUnique == null ? 43 : $slotNamesUnique.hashCode());
        final Object $settings = this.getSettings();
        result = result * PRIME + ($settings == null ? 43 : $settings.hashCode());
        final Object $bindings = this.getBindings();
        result = result * PRIME + ($bindings == null ? 43 : $bindings.hashCode());
        return result;
    }
}