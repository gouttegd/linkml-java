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

@LinkURI("https://w3id.org/linkml/Setting")
public class Setting {

    @Identifier(isGlobal = false)
    @SlotName("setting_key")
    @LinkURI("https://w3id.org/linkml/setting_key")
    private String settingKey;

    @SlotName("setting_value")
    @Required
    @LinkURI("https://w3id.org/linkml/setting_value")
    private String settingValue;

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingKey() {
        return this.settingKey;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public String getSettingValue() {
        return this.settingValue;
    }

    @Override
    public String toString() {
        return "Setting(setting_key=" + this.getSettingKey() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Setting) ) return false;
        final Setting other = (Setting) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$settingKey = this.getSettingKey();
        final Object other$settingKey = other.getSettingKey();
        if ( this$settingKey == null ? other$settingKey != null : !this$settingKey.equals(other$settingKey)) return false;
        final Object this$settingValue = this.getSettingValue();
        final Object other$settingValue = other.getSettingValue();
        if ( this$settingValue == null ? other$settingValue != null : !this$settingValue.equals(other$settingValue)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Setting;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $settingKey = this.getSettingKey();
        result = result * PRIME + ($settingKey == null ? 43 : $settingKey.hashCode());
        final Object $settingValue = this.getSettingValue();
        result = result * PRIME + ($settingValue == null ? 43 : $settingValue.hashCode());
        return result;
    }
}