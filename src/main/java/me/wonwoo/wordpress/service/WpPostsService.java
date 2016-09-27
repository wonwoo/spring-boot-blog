
package me.wonwoo.wordpress.service;


import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.domain.WpPosts;
import me.wonwoo.wordpress.repostiry.WpPostsRepository;
import org.pegdown.PegDownProcessor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.ExampleMatcher.matching;

@Service
@Transactional(transactionManager ="wordPressTransactionManager")
@RequiredArgsConstructor
public class WpPostsService {

    private final WpPostsRepository wpPostsRepository;

    public Page<WpPosts> findByPosts(String q, Pageable pageable){
        WpPosts wpPosts = new WpPosts();
        wpPosts.setPostTitle(q);
        wpPosts.setPostStatus("publish");
        wpPosts.setPostType("post");
        Example<WpPosts> wpPostsExample = Example.of(wpPosts,
                matching()
                    .withMatcher("postTitle", ExampleMatcher.GenericPropertyMatcher::contains));
        return wpPostsRepository.findAll(wpPostsExample, pageable);
    }

    public WpPosts findByPost(Long id) {
        return wpPostsRepository.findByIdAndPostStatusAndPostType(id, "publish", "post");
    }
}
