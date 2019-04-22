package me.wonwoo.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("index")
public class MappingProperties {

    private String settingUrl;
    private String mappingUrl;

    public String getSettingUrl() {
        return settingUrl;
    }

    public void setSettingUrl(String settingUrl) {
        this.settingUrl = settingUrl;
    }

    public String getMappingUrl() {
        return mappingUrl;
    }

    public void setMappingUrl(String mappingUrl) {
        this.mappingUrl = mappingUrl;
    }
}
