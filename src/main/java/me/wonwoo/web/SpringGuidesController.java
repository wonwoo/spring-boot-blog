package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.config.PostProperties;
import me.wonwoo.github.asciidoc.*;
import me.wonwoo.service.GuidesService;
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
@Navigation(Section.GUIDES)
public class SpringGuidesController {

  private final PostProperties postProperties;
  private final GuidesService guidesService;

  public static final String GS = "gs";


  @ModelAttribute("theme")
  public String theme() {
    return postProperties.getTheme();
  }


  @GetMapping("/{project}")
  public String guide(Model model, @PathVariable String project) {
    GettingStartedGuide guide = guidesService.findGs(project);
    model.addAttribute("guide", guide);
    return "guides/guide";
  }


  @GetMapping
  public String guides(Model model) {
    model.addAttribute("guides", guidesService.findAllMetadata());
    model.addAttribute("type", GS);
    return "guides/guides";
  }
}
