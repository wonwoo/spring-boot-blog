package me.wonwoo.domain.repository;

import me.wonwoo.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-08-25
 * Time : 오후 1:04
 * desc :
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
