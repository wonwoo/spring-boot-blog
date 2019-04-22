package me.wonwoo.setting;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

public class RemoteIndexSettingsService implements IndexSettingsService {

    private final RestTemplate restTemplate;
    private final String settingUrl;
    private final String mappingUrl;

    public RemoteIndexSettingsService(RestTemplateBuilder builder, String settingUrl, String mappingUrl) {
        this.restTemplate = builder.build();
        this.settingUrl = settingUrl;
        this.mappingUrl = mappingUrl;
    }

    @Override
    @Nullable
    public String getSetting() {
        try {
            return restTemplate.getForObject(settingUrl, String.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Nullable
    public String getMapping() {
        try {
            return restTemplate.getForObject(mappingUrl, String.class);
        } catch (Exception e) {
            return null;
        }
    }
}
