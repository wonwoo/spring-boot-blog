package me.wonwoo.service;

import lombok.RequiredArgsConstructor;
import me.wonwoo.github.asciidoc.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by wonwoo on 2016. 9. 5..
 */
@Service
@RequiredArgsConstructor
public class GuidesService {

  private final GuideOrganization org;

  private AsciidoctorUtils asciidoctorUtils = new AsciidoctorUtils();
  private final MultiValueMap<String, String> tagMultimap = new LinkedMultiValueMap<>();

  private static final String REPO_BASE_PATH = "repos/%s/%s";
  private static final String README_PATH_ASC = REPO_BASE_PATH + "/zipball";

  @Cacheable("spring.guides")
  public List<GuideMetadata> findAllMetadata() {

    return org.findRepositoriesByPrefix("gs-")
      .stream()
      .map(repo -> new DefaultGuideMetadata(org.getName(), repo.getName().replaceAll("^" + "gs-", ""),
        repo.getName(), repo.getDescription(), new HashSet<>(tagMultimap.getOrDefault(repo
        .getName(), Collections.emptyList()))))
      .collect(Collectors.toList());
  }

  @Cacheable("spring.guide")
  public GettingStartedGuide find(String tutorial) {
    return populate(create(findMetadata(tutorial)));
  }

  private GettingStartedGuide create(GuideMetadata metadata) {
    return new GettingStartedGuide(metadata);
  }


  private <T extends AbstractGuide> T populate(T tutorial) {
    String repoName = tutorial.getRepoName();

    AsciidocGuide asciidocGuide = asciidoctorUtils.getDocument(org, String.format(README_PATH_ASC, org.getName(),
      repoName));
    tagMultimap.merge(repoName, new ArrayList<>(asciidocGuide.getTags()), (source, target) -> {
      Set<String> tags = new LinkedHashSet<>(target);
      tags.addAll(source);
      return new ArrayList<>(tags);
    });
    tutorial.setContent(asciidocGuide.getContent());
    return tutorial;
  }

  public GuideMetadata findMetadata(String tutorial) {
    String repoName = "gs-" + tutorial;
    String description = getRepoDescription(repoName);
    Set<String> tags = tagMultimap.get(repoName) != null ? new HashSet<>(tagMultimap.get(repoName))
      : Collections.emptySet();
    return new DefaultGuideMetadata(org.getName(), tutorial, repoName, description, tags);
  }

  protected String getRepoDescription(String repoName) {
    String description;
    try {
      description = org.getRepoInfo(repoName).getDescription();
    } catch (RestClientException ex) {
      description = "";
    }
    return description;
  }
}
