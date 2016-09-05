package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.PostProperties;
import me.wonwoo.github.asciidoc.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 5:00
 * desc :
 */
@Controller
@RequestMapping("/guides")
@RequiredArgsConstructor
public class SpringGuidesController {

  private final GuideOrganization org;

  private AsciidoctorUtils asciidoctorUtils = new AsciidoctorUtils();
  private final MultiValueMap<String, String> tagMultimap = new LinkedMultiValueMap<>();

  private static final String REPO_BASE_PATH = "repos/%s/%s";
  private static final String README_PATH_ASC = REPO_BASE_PATH + "/zipball";

  private final PostProperties postProperties;

  @ModelAttribute("theme")
  public String theme() {
    return postProperties.getTheme();
  }


  @GetMapping("/{project}")
  public String guide(Model model, @PathVariable String project) {
    GettingStartedGuide guide = populate(create(findMetadata(project)));
    model.addAttribute("guide", guide);
    return "guides/guide";
  }

  @GetMapping
  public String guides(Model model) {
    model.addAttribute("guides", this.findAllMetadata());
    return "guides/guides";
  }

  public <T extends AbstractGuide> T populate(T tutorial) {
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

  public List<GuideMetadata> findAllMetadata() {

    return org.findRepositoriesByPrefix("gs-")
      .stream()
      .map(repo -> new DefaultGuideMetadata(org.getName(), repo.getName().replaceAll("^" + "gs-", ""),
        repo.getName(), repo.getDescription(), new HashSet<>(tagMultimap.getOrDefault(repo
        .getName(), Collections.emptyList()))))
      .collect(Collectors.toList());
  }


  protected GettingStartedGuide create(GuideMetadata metadata) {
    return new GettingStartedGuide(metadata);
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