package org.incenp.linkml.schema.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://w3id.org/linkml/presence_enum")
public enum PresenceEnum {

    UNCOMMITTED("UNCOMMITTED"),

    PRESENT("PRESENT"),

    ABSENT("ABSENT");

    private final static Map<String, PresenceEnum> MAP;

    static {
        Map<String, PresenceEnum> map = new HashMap<String, PresenceEnum>();
        for ( PresenceEnum value : PresenceEnum.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PresenceEnum(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PresenceEnum fromString(String v) {
        return MAP.get(v);
    }
}