package me.wonwoo.setting;

import org.springframework.lang.Nullable;

public class LocalIndexSettingsService implements IndexSettingsService {

    private final String settingUrl;
    private final String mappingUrl;

    public LocalIndexSettingsService(String settingUrl, String mappingUrl) {
        this.settingUrl = settingUrl;
        this.mappingUrl = mappingUrl;
    }

    @Override
    @Nullable
    public String getSetting() {
        return classPathSetting(settingUrl);
    }

    @Override
    @Nullable
    public String getMapping() {
        return classPathSetting(mappingUrl);
    }
}
