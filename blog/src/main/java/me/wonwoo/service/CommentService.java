package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Comment;
import me.wonwoo.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Comment createComment(Comment comment) {
    return commentRepository.save(comment);
  }

  public void deleteComment(Long commentId) {
    commentRepository.delete(commentId);
  }
}
