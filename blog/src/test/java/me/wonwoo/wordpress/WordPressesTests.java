package me.wonwoo.wordpress;

import java.util.Collections;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WordPressesTests {

	@Test
	public void wordpresses() {
		WordPresses wordPresses = new WordPresses();
		wordPresses.setFound(199L);
		WordPress wordPress = new WordPress();
		wordPress.setId(101L);
		wordPress.setContent("test content");
		wordPress.setTableOfContent("<p>content</p>");
		wordPress.setTitle("test title");
		WordPressLogin author = new WordPressLogin();
		author.setLogin("wonwoo");
		author.setName("wonwoo");
		wordPress.setAuthor(author);
		wordPresses.setPosts(Collections.singletonList(wordPress));
		assertThat(wordPresses.getPosts()).hasSize(1);
		assertThat(wordPresses.getFound()).isEqualTo(199L);

		assertThat(wordPresses.getPosts().get(0).getId()).isEqualTo(101L);
		assertThat(wordPresses.getPosts().get(0).getContent()).isEqualTo("test content");
		assertThat(wordPresses.getPosts().get(0).getTableOfContent()).isEqualTo("<p>content</p>");
		assertThat(wordPresses.getPosts().get(0).getTitle()).isEqualTo("test title");

		assertThat(wordPresses.getPosts().get(0).getAuthor().getLogin()).isEqualTo("wonwoo");
		assertThat(wordPresses.getPosts().get(0).getAuthor().getName()).isEqualTo("wonwoo");
	}

}