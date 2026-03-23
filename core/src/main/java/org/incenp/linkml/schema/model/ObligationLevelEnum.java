package org.incenp.linkml.schema.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://w3id.org/linkml/obligation_level_enum")
public enum ObligationLevelEnum {

    REQUIRED("REQUIRED"),

    RECOMMENDED("RECOMMENDED"),

    OPTIONAL("OPTIONAL"),

    EXAMPLE("EXAMPLE"),

    DISCOURAGED("DISCOURAGED");

    private final static Map<String, ObligationLevelEnum> MAP;

    static {
        Map<String, ObligationLevelEnum> map = new HashMap<String, ObligationLevelEnum>();
        for ( ObligationLevelEnum value : ObligationLevelEnum.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    ObligationLevelEnum(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static ObligationLevelEnum fromString(String v) {
        return MAP.get(v);
    }
}