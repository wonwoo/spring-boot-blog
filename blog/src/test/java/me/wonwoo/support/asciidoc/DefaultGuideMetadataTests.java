package me.wonwoo.support.asciidoc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultGuideMetadataTests {

	@Test
	public void defaultGuideMetadata() {
		DefaultGuideMetadata guideMetadata = new DefaultGuideMetadata("wonwoo","100","spring-blog", "test");
		assertThat(guideMetadata.getGhOrgName()).isEqualTo("wonwoo");
		assertThat(guideMetadata.getGuideId()).isEqualTo("100");
		assertThat(guideMetadata.getRepoName()).isEqualTo("spring-blog");
		assertThat(guideMetadata.getDescription()).isEqualTo("test");
		assertThat(guideMetadata.getTitle()).isEqualTo("test");

		assertThat(guideMetadata.getGitRepoHttpsUrl()).isEqualTo("https://github.com/wonwoo/spring-blog.git");
		assertThat(guideMetadata.getGithubHttpsUrl()).isEqualTo("https://github.com/wonwoo/spring-blog");
		assertThat(guideMetadata.getZipUrl()).isEqualTo("https://github.com/wonwoo/spring-blog/archive/master.zip");
		assertThat(guideMetadata.getGitRepoSshUrl()).isEqualTo("git@github.com:wonwoo/spring-blog.git");
		assertThat(guideMetadata.getGitRepoSubversionUrl()).isEqualTo("https://github.com/wonwoo/spring-blog");
		assertThat(guideMetadata.getCiStatusImageUrl()).isEqualTo("https://travis-ci.org/wonwoo/spring-blog.svg?branch=master");
		assertThat(guideMetadata.getCiLatestUrl()).isEqualTo("https://travis-ci.org/wonwoo/spring-blog");

	}

}