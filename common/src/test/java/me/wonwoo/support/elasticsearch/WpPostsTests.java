package me.wonwoo.support.elasticsearch;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WpPostsTests {

	@Test
	public void wpPosts(){
		WpPosts wpPosts = new WpPosts();
		wpPosts.setPostContentFiltered("<p>content</p>");
		wpPosts.setPostContent("content");
		wpPosts.setId(1);
		wpPosts.setPostTitle("title");
		wpPosts.setPostStatus("publish");
		wpPosts.setPostType("post");
		wpPosts.setHighlightedContent("test <highlight>content</highlight>");
		wpPosts.setTableOfContent("<p>table content</p>");

		assertThat(wpPosts.getPostContentFiltered()).isEqualTo("<p>content</p>");
		assertThat(wpPosts.getPostContent()).isEqualTo("content");
		assertThat(wpPosts.getId()).isEqualTo(1);
		assertThat(wpPosts.getPostTitle()).isEqualTo("title");
		assertThat(wpPosts.getPostStatus()).isEqualTo("publish");
		assertThat(wpPosts.getPostType()).isEqualTo("post");
		assertThat(wpPosts.getHighlightedContent()).isEqualTo("test <highlight>content</highlight>");
		assertThat(wpPosts.getTableOfContent()).isEqualTo("<p>table content</p>");
	}
}