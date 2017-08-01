package me.wonwoo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.wonwoo.domain.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
