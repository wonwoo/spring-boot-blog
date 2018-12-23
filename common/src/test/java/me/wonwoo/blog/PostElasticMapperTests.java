package me.wonwoo.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.support.elasticsearch.WpPosts;
import org.junit.Test;

public class PostElasticMapperTests {

    @Test
    public void map() {
        PostElasticMapper postElasticMapper = new PostElasticMapper();
        final Post post = new Post("post title", "post content",
            "code", "Y", new Category(1L, "spring"),
            new User(), Collections.emptyList());
        post.setId(1L);
        WpPosts wpPosts = postElasticMapper.map(post);
        assertThat(wpPosts.getId()).isEqualTo(1);
        assertThat(wpPosts.getPostTitle()).isEqualTo("post title");
        assertThat(wpPosts.getPostContent()).isEqualTo("post content");
        assertThat(wpPosts.getPostContentFiltered()).isEqualTo("code");
    }
}