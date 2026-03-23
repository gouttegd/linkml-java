package org.incenp.linkml.core.sample;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://incenp.org/dvlpt/linkml-java/tests/samples#SampleEnum")
public enum SampleEnum {

    FOO("foo"),

    BAR("bar"),

    BAZ("baz");

    private final static Map<String, SampleEnum> MAP;

    static {
        Map<String, SampleEnum> map = new HashMap<String, SampleEnum>();
        for ( SampleEnum value : SampleEnum.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    SampleEnum(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static SampleEnum fromString(String v) {
        return MAP.get(v);
    }
}