package org.incenp.linkml.schema.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://w3id.org/linkml/relational_role_enum")
public enum RelationalRoleEnum {

    @LinkURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#subject")
    SUBJECT("SUBJECT"),

    @LinkURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#object")
    OBJECT("OBJECT"),

    @LinkURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate")
    PREDICATE("PREDICATE"),

    NODE("NODE"),

    OTHER_ROLE("OTHER_ROLE");

    private final static Map<String, RelationalRoleEnum> MAP;

    static {
        Map<String, RelationalRoleEnum> map = new HashMap<String, RelationalRoleEnum>();
        for ( RelationalRoleEnum value : RelationalRoleEnum.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    RelationalRoleEnum(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static RelationalRoleEnum fromString(String v) {
        return MAP.get(v);
    }
}