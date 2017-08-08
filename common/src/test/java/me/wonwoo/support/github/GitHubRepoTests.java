package me.wonwoo.support.github;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GitHubRepoTests {

	@Test
	public void gitHubRepo() {
		GitHubRepo gitHubRepo = new GitHubRepo(1L, "http://github.com/repo", "http://github.com/html",
				"http://github.com/clone", "http://github.com/repo.git",
				"git@github.com:repo/blog", "test");
		assertThat(gitHubRepo.getId()).isEqualTo(1L);
		assertThat(gitHubRepo.getUrl()).isEqualTo("http://github.com/repo");
		assertThat(gitHubRepo.getHtmlUrl()).isEqualTo("http://github.com/html");
		assertThat(gitHubRepo.getCloneUrl()).isEqualTo("http://github.com/clone");
		assertThat(gitHubRepo.getGitUrl()).isEqualTo("http://github.com/repo.git");
		assertThat(gitHubRepo.getSshUrl()).isEqualTo("git@github.com:repo/blog");
		assertThat(gitHubRepo.getSvnUrl()).isEqualTo("test");

	}
}