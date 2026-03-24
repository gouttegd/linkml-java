package org.incenp.linkml.schema.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://w3id.org/linkml/alias_predicate_enum")
public enum AliasPredicateEnum {

    @LinkURI("http://www.w3.org/2004/02/skos/core#exactMatch")
    EXACT_SYNONYM("EXACT_SYNONYM"),

    @LinkURI("http://www.w3.org/2004/02/skos/core#relatedMatch")
    RELATED_SYNONYM("RELATED_SYNONYM"),

    @LinkURI("http://www.w3.org/2004/02/skos/core#broaderMatch")
    BROAD_SYNONYM("BROAD_SYNONYM"),

    @LinkURI("http://www.w3.org/2004/02/skos/core#narrowerMatch")
    NARROW_SYNONYM("NARROW_SYNONYM");

    private final static Map<String, AliasPredicateEnum> MAP;

    static {
        Map<String, AliasPredicateEnum> map = new HashMap<String, AliasPredicateEnum>();
        for ( AliasPredicateEnum value : AliasPredicateEnum.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    AliasPredicateEnum(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static AliasPredicateEnum fromString(String v) {
        return MAP.get(v);
    }
}