package me.wonwoo.service;

import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.ElasticMapping;
import me.wonwoo.domain.repository.ElasticMappingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final ElasticMappingRepository elasticMappingRepository;

    public String getSetting() {
        return converter(ElasticMapping::getSetting);
    }

    public String getMapping() {
        return converter(ElasticMapping::getMapping);
    }

    private String converter(Function<ElasticMapping, String> map) {
        return elasticMappingRepository.findByEnabled("Y")
            .map(map)
            .orElse(null);
    }
}
