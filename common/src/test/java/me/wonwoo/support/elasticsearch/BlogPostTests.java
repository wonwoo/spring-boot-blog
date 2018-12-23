package me.wonwoo.support.elasticsearch;

import java.time.LocalDateTime;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogPostTests {

	@Test
	public void wpPosts(){
		BlogPost blogPost = new BlogPost();
		blogPost.setPostContentFiltered("<p>content</p>");
		blogPost.setPostContent("content");
		blogPost.setPostAuthor(1);
		blogPost.setId(1);
		blogPost.setPostTitle("title");
		blogPost.setPostStatus("publish");
		blogPost.setPostType("post");
		blogPost.setHighlightedContent("test <highlight>content</highlight>");
		blogPost.setTableOfContent("<p>table content</p>");
		blogPost.setPostDate(LocalDateTime.now());

		assertThat(blogPost.getPostContentFiltered()).isEqualTo("<p>content</p>");
		assertThat(blogPost.getPostContent()).isEqualTo("content");
		assertThat(blogPost.getId()).isEqualTo(1);
		assertThat(blogPost.getPostTitle()).isEqualTo("title");
		assertThat(blogPost.getPostStatus()).isEqualTo("publish");
		assertThat(blogPost.getPostType()).isEqualTo("post");
		assertThat(blogPost.getHighlightedContent()).isEqualTo("test <highlight>content</highlight>");
		assertThat(blogPost.getTableOfContent()).isEqualTo("<p>table content</p>");
		assertThat(blogPost.getPostAuthor()).isEqualTo(1);
		assertThat(blogPost.getPostDate()).isNotNull();
	}
}