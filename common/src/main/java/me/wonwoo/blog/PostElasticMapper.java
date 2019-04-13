package me.wonwoo.blog;

import java.util.function.Function;
import me.wonwoo.domain.model.Post;
import me.wonwoo.support.elasticsearch.WpPosts;

public class PostElasticMapper implements Function<Post, WpPosts> {

    @Override
    public WpPosts apply(Post post) {
        WpPosts wpPosts = new WpPosts();
        wpPosts.setPostContent(post.getContent());
        wpPosts.setPostTitle(post.getTitle());
        wpPosts.setId(post.getId().intValue());
        wpPosts.setPostContentFiltered(post.getCode());
        wpPosts.setPostDate(post.getRegDate());
        return wpPosts;
    }
}
