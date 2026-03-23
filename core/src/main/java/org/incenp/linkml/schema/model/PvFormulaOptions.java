package org.incenp.linkml.schema.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://w3id.org/linkml/pv_formula_options")
public enum PvFormulaOptions {

    CODE("CODE"),

    CURIE("CURIE"),

    URI("URI"),

    FHIR_CODING("FHIR_CODING"),

    LABEL("LABEL");

    private final static Map<String, PvFormulaOptions> MAP;

    static {
        Map<String, PvFormulaOptions> map = new HashMap<String, PvFormulaOptions>();
        for ( PvFormulaOptions value : PvFormulaOptions.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PvFormulaOptions(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PvFormulaOptions fromString(String v) {
        return MAP.get(v);
    }
}