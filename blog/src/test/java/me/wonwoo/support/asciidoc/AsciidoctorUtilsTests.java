package me.wonwoo.support.asciidoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AsciidoctorUtilsTests {

	@Mock
	private GuideOrganization org;
	
	private AsciidoctorUtils asciidoctorUtils = new AsciidoctorUtils();

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void getReadme() {
		Readme readme = new Readme();
		readme.setName("wonwoo");
		given(org.getReadme(any())).willReturn(readme);
		Readme result = asciidoctorUtils.getReadme(org, "/test");
		assertThat(result.getName()).isEqualTo("wonwoo");
	}

	@Test
	public void getReadmeException() {
		exception.expect(RuntimeException.class);
		given(org.getReadme(any())).willThrow(new RuntimeException());
		asciidoctorUtils.getReadme(org, "/test");
	}

	@Test
	public void getDocument() {
		Set<String> tags = new HashSet<>();
		tags.add("spring");
		tags.add("spring boot");
		Set<String> projects = new HashSet<>();
		projects.add("spring-boot");
		projects.add("spring-test");

		AsciidocGuide asciidocGuide =
				new AsciidocGuide("test content", tags, projects, "left table", new HashMap<>());

		given(org.getAsciidocGuide(any())).willReturn(asciidocGuide);
		AsciidocGuide result = asciidoctorUtils.getDocument(org, "/test");
		assertThat(result.getTags()).isEqualTo(tags);
		assertThat(result.getProjects()).isEqualTo(projects);
		assertThat(result.getContent()).isEqualTo("test content");
		assertThat(result.getTableOfContents()).isEqualTo("left table");
		assertThat(result.getUnderstandingDocs()).isNotNull();
	}

	@Test
	public void getDocumentException() {
		exception.expect(RuntimeException.class);
		given(org.getAsciidocGuide(any())).willThrow(new RuntimeException());
		asciidoctorUtils.getDocument(org, "/test");
	}


	@Test
	public void generateDynamicSidebar() {
		Set<String> tags = new HashSet<>();
		tags.add("spring");
		tags.add("spring boot");
		Set<String> projects = new HashSet<>();
		projects.add("spring-boot");
		projects.add("spring-test");
		AsciidocGuide asciidocGuide =
				new AsciidocGuide("test content", tags, projects, "left table", new HashMap<>());
		String result = asciidoctorUtils.generateDynamicSidebar(asciidocGuide);
		assertThat(result).contains("spring boot", "/guides?filter=spring", "Table of sidebar");

	}
}