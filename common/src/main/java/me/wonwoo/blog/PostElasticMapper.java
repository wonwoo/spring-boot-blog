package me.wonwoo.blog;

import me.wonwoo.domain.model.Post;
import me.wonwoo.support.elasticsearch.BlogPost;

public class PostElasticMapper implements ElasticMapper<Post, BlogPost> {

    @Override
    public BlogPost map(Post post) {
        BlogPost blogPost = new BlogPost();
        blogPost.setPostContent(post.getContent());
        blogPost.setPostTitle(post.getTitle());
        blogPost.setPostId(post.getId());
        blogPost.setPostContentFiltered(post.getCode());
        blogPost.setPostDate(post.getRegDate());
        return blogPost;
    }
}
