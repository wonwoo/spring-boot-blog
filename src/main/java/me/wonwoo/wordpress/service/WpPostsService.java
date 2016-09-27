
package me.wonwoo.wordpress.service;


import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.domain.WpPosts;
import me.wonwoo.wordpress.repostiry.WpPostsRepository;
import org.apache.lucene.search.*;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(transactionManager ="wordPressTransactionManager")
@RequiredArgsConstructor
public class WpPostsService {

    private final WpPostsRepository wpPostsRepository;

    @PersistenceContext(unitName = "wordPress")
    private EntityManager entityManager;

    public PageImpl findByPosts(String q, Pageable pageable){

        FullTextEntityManager fullTextEntityManager =
                Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(WpPosts.class).get();

        final BooleanJunction<BooleanJunction> outer = queryBuilder.bool();
        Optional.ofNullable(q)
                .map(f -> queryBuilder.keyword().onFields("postTitle", "postContent").matching(f).createQuery())
                .ifPresent(outer::must);
        Optional.of("publish")
                .map(f -> queryBuilder.range().onField("postStatus").above(f).createQuery())
                .ifPresent(outer::must);

        Optional.of("post")
                .map(f -> queryBuilder.range().onField("postType").above(f).createQuery())
                .ifPresent(outer::must);

        FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(outer.createQuery(), WpPosts.class);
        jpaQuery.setFirstResult(pageable.getOffset());
        jpaQuery.setMaxResults(pageable.getPageSize());
        jpaQuery.setSort(new Sort(new SortField("postDate", SortField.Type.STRING, true)));
        List results = jpaQuery.getResultList();
        return new PageImpl<>(results,pageable, jpaQuery.getResultSize());

//        return results;
//
//        WpPosts wpPosts = new WpPosts();
//        wpPosts.setPostTitle(q);
//        wpPosts.setPostStatus("publish");
//        wpPosts.setPostType("post");
//        Example<WpPosts> wpPostsExample = Example.of(wpPosts,
//                matching()
//                    .withMatcher("postTitle", ExampleMatcher.GenericPropertyMatcher::contains));
//        return wpPostsRepository.findAll(wpPostsExample, pageable);
    }

    public WpPosts findByPost(Long id) {
        return wpPostsRepository.findByIdAndPostStatusAndPostType(id, "publish", "post");
    }
}
