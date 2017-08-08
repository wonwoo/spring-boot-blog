package me.wonwoo.support.asciidoc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GettingStartedGuideTests {

	@Test
	public void gettingStartedGuide() {
		GuideMetadata guideMetadata = new DefaultGuideMetadata("wonwoo","100","spring-blog", "test");
		GettingStartedGuide gettingStartedGuide = new GettingStartedGuide();
		gettingStartedGuide.setContent("test content");
		gettingStartedGuide.setSidebar("test sidebar");
		gettingStartedGuide.setMetadata(guideMetadata);
		assertThat(gettingStartedGuide.getContent()).isEqualTo("test content");
		assertThat(gettingStartedGuide.getSidebar()).isEqualTo("test sidebar");
		assertThat(gettingStartedGuide.getTypeLabel()).isEqualTo("Getting Started");
		assertThat(gettingStartedGuide.getGitRepoHttpsUrl()).isEqualTo("https://github.com/wonwoo/spring-blog.git");
		assertThat(gettingStartedGuide.getGithubHttpsUrl()).isEqualTo("https://github.com/wonwoo/spring-blog");
		assertThat(gettingStartedGuide.getZipUrl()).isEqualTo("https://github.com/wonwoo/spring-blog/archive/master.zip");
		assertThat(gettingStartedGuide.getGitRepoSshUrl()).isEqualTo("git@github.com:wonwoo/spring-blog.git");
		assertThat(gettingStartedGuide.getGitRepoSubversionUrl()).isEqualTo("https://github.com/wonwoo/spring-blog");
		assertThat(gettingStartedGuide.getCiStatusImageUrl()).isEqualTo("https://travis-ci.org/wonwoo/spring-blog.svg?branch=master");
		assertThat(gettingStartedGuide.getCiLatestUrl()).isEqualTo("https://travis-ci.org/wonwoo/spring-blog");
	}
}