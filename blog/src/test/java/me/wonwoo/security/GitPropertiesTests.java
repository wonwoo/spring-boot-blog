package me.wonwoo.security;

import java.util.Arrays;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GitPropertiesTests {

	@Test
	public void gitPropertiesIsEmpty() {
		GitProperties properties = new GitProperties();
		assertThat(properties.getSecurity().getAdmins()).contains("wonwoo");
	}

	@Test
	public void gitProperties() {
		GitProperties properties = new GitProperties();
		properties.getGithub().setToken("test token");
		properties.getSecurity().setAdmins(Arrays.asList("kevin", "tester"));
		assertThat(properties.getGithub().getToken()).isEqualTo("test token");
		assertThat(properties.getSecurity().getAdmins()).contains("kevin", "tester");

	}
}