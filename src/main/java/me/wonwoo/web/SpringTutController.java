package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.github.asciidoc.GettingStartedGuide;
import me.wonwoo.service.GuidesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/tut")
@Navigation(Section.TUTORIAL)
public class SpringTutController {
  private final GuidesService guidesService;
  public static final String TUT = "tut";

  @GetMapping
  public String tuts(Model model) {
    model.addAttribute("guides", guidesService.findTutAllMetadata());
    model.addAttribute("type", TUT);
    return "guides/guides";
  }

  @GetMapping("/{project}")
  public String tut(Model model, @PathVariable String project) {
    GettingStartedGuide guide = guidesService.findTut(project);
    model.addAttribute("guide", guide);
    return "guides/guide";
  }
}
