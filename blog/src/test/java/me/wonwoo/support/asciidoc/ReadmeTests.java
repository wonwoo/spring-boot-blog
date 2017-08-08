package me.wonwoo.support.asciidoc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadmeTests  {

	@Test
	public void readMe() {
		Readme readme = new Readme();
		readme.setName("README.md");
		assertThat(readme.getName()).isEqualTo("README.md");
	}
}