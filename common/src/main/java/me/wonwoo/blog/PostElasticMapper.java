package me.wonwoo.blog;

import me.wonwoo.domain.model.Post;
import me.wonwoo.support.elasticsearch.WpPosts;

public class PostElasticMapper implements ElasticMapper<Post, WpPosts> {

    @Override
    public WpPosts map(Post post) {
        WpPosts wpPosts = new WpPosts();
        wpPosts.setPostContent(post.getContent());
        wpPosts.setPostTitle(post.getTitle());
        wpPosts.setPostId(post.getId());
        wpPosts.setPostContentFiltered(post.getCode());
        wpPosts.setPostDate(post.getRegDate());
        return wpPosts;
    }
}
