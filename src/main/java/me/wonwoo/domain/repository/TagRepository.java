package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wonwoo on 2016. 9. 17..
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
