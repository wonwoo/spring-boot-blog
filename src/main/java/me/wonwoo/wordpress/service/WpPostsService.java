
package me.wonwoo.wordpress.service;


import lombok.RequiredArgsConstructor;
import me.wonwoo.wordpress.domain.WpPosts;
import me.wonwoo.wordpress.repostiry.WpPostsRepository;
import org.pegdown.PegDownProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(transactionManager ="wordPressTransactionManager")
@RequiredArgsConstructor
public class WpPostsService {

    private final WpPostsRepository wpPostsRepository;

    public Page<WpPosts> findByPosts(Pageable pageable){
        return wpPostsRepository.findByPostStatus(pageable, "publish", "post");
    }

    public WpPosts findByPost(Long id) {
        return wpPostsRepository.findByIdAndPostStatusAndPostType(id, "publish", "post");
    }
}
