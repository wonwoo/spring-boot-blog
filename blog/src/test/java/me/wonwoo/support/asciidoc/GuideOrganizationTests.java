package me.wonwoo.support.asciidoc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.wonwoo.support.github.GitHubRepo;
import me.wonwoo.support.github.GithubClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GuideOrganizationTests {

	@Mock
	private GithubClient githubClient;
	private GuideOrganization guideOrganization;

	@Before
	public void setUp() throws Exception {
		guideOrganization = new GuideOrganization("spring-blog", "org", githubClient, new ObjectMapper());
	}

	@Test
	public void getReadme() {
		given(githubClient.sendRequestForJson(any()))
				.willReturn("{\"name\" : \"test\"}");
		Readme readme = guideOrganization.getReadme("/path/test");
		assertThat(readme.getName()).isEqualTo("test");
	}

	@Test
	public void getRepoInfo() {
		given(githubClient.sendRequestForGithub(anyString()))
				.willReturn(new GitHubRepo());
		guideOrganization.getRepoInfo("spring-boot-blog");
		verify(githubClient).sendRequestForGithub("repos/spring-blog/spring-boot-blog");
	}

	@Test
	public void findAllRepositories() {
		given(githubClient.sendRequestForGithubs(anyString()))
				.willReturn(new GitHubRepo[0]);
		guideOrganization.findAllRepositories();
		verify(githubClient).sendRequestForGithubs("org/spring-blog/repos?per_page=100");
	}

}