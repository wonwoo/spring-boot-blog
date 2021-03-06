package me.wonwoo.domain.repository.impl;

import com.querydsl.jpa.JPQLQuery;
import me.wonwoo.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.querydsl.QPageRequest;

import java.util.Collections;
import java.util.List;

/**
 * Created by wonwoolee on 2017. 8. 8..
 */
public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

  public PostRepositoryImpl() {
    super(Post.class);
  }

  @Override
  public Page<Post> findByPostsForCategory(Long id, Pageable pageable) {
    QPost post = QPost.post;
    QCategory category = QCategory.category;
    return readPage(getQuerydsl(), from(post)
        .join(post.category, category).fetchJoin()
        .where(category.id.eq(id)), pageable);
  }

  @Override
  public Page<Post> findByPostsForTag(String name, Pageable pageable) {
    QPost post = QPost.post;
    QTag tags = QTag.tag1;
    QCategory category = QCategory.category;
    return readPage(getQuerydsl(), from(post)
        .join(post.tags, tags)
        .join(post.category, category).fetchJoin()
        .where(tags.tag.eq(name)), pageable);
  }

  private <T> Page<T> readPage(Querydsl querydsl, JPQLQuery<T> query, Pageable pageable) {
    if (pageable == null) {
      return readPage(querydsl, query, new QPageRequest(0, Integer.MAX_VALUE));
    }
    long total = query.fetchCount();
    JPQLQuery<T> pagedQuery = querydsl.applyPagination(pageable, query);
    List<T> content = total > pageable.getOffset() ? pagedQuery.fetch() : Collections.emptyList();
    return new PageImpl<>(content, pageable, total);
  }
}
