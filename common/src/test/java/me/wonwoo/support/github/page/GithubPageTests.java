package me.wonwoo.support.github.page;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubPageTests {

	@Test
	public void githubPage() {
		GithubPage githubPage = new GithubPage();
		githubPage.setName("wonwoo");
		githubPage.setPath("/repo/blog");
		githubPage.setSize(120L);
		githubPage.setContent("test content");
		assertThat(githubPage.getName()).isEqualTo("wonwoo");
		assertThat(githubPage.getPath()).isEqualTo("/repo/blog");
		assertThat(githubPage.getSize()).isEqualTo(120L);
		assertThat(githubPage.getContent()).isEqualTo("test content");
	}
}