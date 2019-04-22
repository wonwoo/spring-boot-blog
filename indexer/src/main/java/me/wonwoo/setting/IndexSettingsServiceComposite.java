package me.wonwoo.setting;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class IndexSettingsServiceComposite implements IndexSettingsService {

    private final List<IndexSettingsService> indexSettingsServices;

    public IndexSettingsServiceComposite(List<IndexSettingsService> indexSettingsServices) {
        this.indexSettingsServices = indexSettingsServices;
    }

    @Override
    public String getSetting() {
        return mapping(IndexSettingsService::getSetting);
    }

    @Override
    public String getMapping() {
        return mapping(IndexSettingsService::getMapping);
    }

    private String mapping(Function<IndexSettingsService, String> map) {
        return indexSettingsServices
            .stream()
            .map(map)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
