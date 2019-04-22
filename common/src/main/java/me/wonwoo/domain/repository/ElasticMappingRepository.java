package me.wonwoo.domain.repository;

import java.util.Optional;
import me.wonwoo.domain.model.ElasticMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElasticMappingRepository extends JpaRepository<ElasticMapping, Long> {

    Optional<ElasticMapping> findByEnabled(String enabled);
}
