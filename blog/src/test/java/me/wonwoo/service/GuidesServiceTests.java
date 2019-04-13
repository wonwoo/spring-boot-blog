package me.wonwoo.service;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import me.wonwoo.junit.MockitoJsonJUnitRunner;
import me.wonwoo.support.asciidoc.AsciidoctorUtils;
import me.wonwoo.support.asciidoc.GuideMetadata;
import me.wonwoo.support.asciidoc.GuideOrganization;
import me.wonwoo.support.github.GitHubRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by wonwoo on 2017. 2. 12..
 */

@RunWith(MockitoJsonJUnitRunner.class)
public class GuidesServiceTests {

  @Mock
  private GuideOrganization org;

  @Mock
  private AsciidoctorUtils asciidoctorUtils;

  private GuidesService guidesService;

  @Before
  public void setup() {
    guidesService = new GuidesService(org);
  }
  @Test
  public void findAllMetadataTest() {
    GitHubRepo gitHubRepo = new GitHubRepo(1L,
        "http://localhost:8080",
        "http://localhost:8080/html/",
        "http://localhost:8080/clone",
        "http://localhost:8080/git",
        "http://localhost:8080/ssh",
        "http://localhost:8080/svn");
    gitHubRepo.setName("spring");
    gitHubRepo.setDescription("bla :: bla :: test");
    given(org.getName()).willReturn("spring");
    given(org.findRepositoriesByPrefix(any()))
        .willReturn(Collections.singletonList(gitHubRepo));
    final List<GuideMetadata> allMetadata = guidesService.findAllMetadata();
    assertThat(allMetadata.get(0).getTitle()).isEqualTo("bla");
    assertThat(allMetadata.get(0).getGuideId()).isEqualTo("spring");
    assertThat(allMetadata.get(0).getRepoName()).isEqualTo("spring");
    assertThat(allMetadata.get(0).getSubtitle()).isEqualTo("bla :: test");
  }

  @Test
  public void findTutAllMetadata() {
    GitHubRepo gitHubRepo = new GitHubRepo(1L,
        "http://localhost:8080",
        "http://localhost:8080/html",
        "http://localhost:8080/clone",
        "http://localhost:8080/git",
        "http://localhost:8080/ssh",
        "http://localhost:8080/svn");
    gitHubRepo.setName("spring");
    gitHubRepo.setDescription("bla :: bla :: test");
    given(org.getName()).willReturn("spring");
    given(org.findRepositoriesByPrefix(any()))
        .willReturn(Collections.singletonList(gitHubRepo));
    final List<GuideMetadata> allMetadata = guidesService.findTutAllMetadata();
    assertThat(allMetadata.get(0).getTitle()).isEqualTo("bla");
    assertThat(allMetadata.get(0).getGuideId()).isEqualTo("spring");
    assertThat(allMetadata.get(0).getRepoName()).isEqualTo("spring");
    assertThat(allMetadata.get(0).getSubtitle()).isEqualTo("bla :: test");
  }
}