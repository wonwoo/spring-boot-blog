package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.support.github.GithubClient;
import me.wonwoo.support.github.page.GithubPage;
import org.apache.commons.codec.binary.Base64;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/github/page")
@Navigation(Section.GITHUB)
public class GithubPageController {

  private final GithubClient githubClient;
  private final PegDownProcessor pegDownProcessor;

  @GetMapping
  public String githubPages(Model model, @Value("${github.username:wonwoo}") String githubName) {
    List<GithubPage> githubPages = Arrays.asList(githubClient
        .sendRequest("repos/" + githubName + "/github/contents/_post", GithubPage[].class));
    model.addAttribute("githubPages", githubPages
      .stream()
      .filter(githubPage -> githubPage.getName().contains(".md"))
      .peek(githubPage -> githubPage.setName(githubPage.getName().replace(".md", "")))
      .collect(toList()));
    return "github/list";
  }

  @GetMapping("/{name}")
  public String githubPage(@PathVariable String name, Model model,
                           @Value("${github.username:wonwoo}") String githubName) {
    GithubPage githubPage = githubClient
        .sendRequest("repos/" + githubName + "/github/contents/_post/" + name + ".md", GithubPage.class);
    githubPage.setContent(pegDownProcessor.markdownToHtml(new String(Base64.decodeBase64(githubPage.getContent()))));
    model.addAttribute("githubPage", githubPage);
    return "github/page";
  }
}
