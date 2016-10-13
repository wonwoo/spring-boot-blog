package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.dto.SearchForm;
import me.wonwoo.elasticsearch.PostElasticSearchService;
import me.wonwoo.wordpress.WordPressClient;
import me.wonwoo.wordpress.domain.WpPosts;
import org.pegdown.PegDownProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringEscapeUtils.unescapeHtml;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/wordPress")
@Navigation(Section.WORDPRESS)
public class WordPressController {

    private final WordPressClient wordPressClient;
    private final PegDownProcessor pegDownProcessor;
    private final PostElasticSearchService postElasticSearchService;

    @GetMapping("/http")
    public String findAllHttp(Model model, @PageableDefault(size = 3) Pageable pageable, @RequestParam(required = false, defaultValue = "") String q) {
        model.addAttribute("wordPresses", wordPressClient.findAll(pageable, q));
        return "wordpress/wordPresses";
    }

    @GetMapping("/http/{id}")
    public String findOneHttp(@PathVariable Long id, Model model) {
        model.addAttribute("wordPress", wordPressClient.findOne(id));
        return "wordpress/wordPress";
    }

//    @GetMapping
//    public String findAll(Model model, @PageableDefault(size = 3, sort = "post_date", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(required = false) String q) {
////        Page<WpPosts> wpPostses = wpPostsService.findByPosts(q, pageable);
////        List<WpPosts> wpPosts = wpPostses.getContent().stream().peek(content -> content.setPostContent(pegDownProcessor.markdownToHtml(unescapeHtml(content.getPostContent())))).collect(toList());
////        model.addAttribute("wordPresses", new PageImpl<>(wpPosts, pageable, wpPostses.getTotalElements()));
//        return "wordpress/wordPresses";
//    }

    @GetMapping
    public String findAll(Model model, @PageableDefault(size = 3, sort = "post_date", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<WpPosts> wpPostses = postElasticSearchService.wpPosts(pageable);
        List<WpPosts> wpPosts = wpPostses.getContent().stream().peek(content -> content.setPostContent(pegDownProcessor.markdownToHtml(unescapeHtml(content.getPostContentFiltered())))).collect(toList());
        model.addAttribute("wordPresses", new PageImpl<>(wpPosts, pageable, wpPostses.getTotalElements()));
        return "wordpress/wordPresses";
    }


    @GetMapping("/search")
    public String search(Model model, @PageableDefault(size = 3) Pageable pageable, @ModelAttribute SearchForm searchForm) {
        Page<WpPosts> wpPostses = postElasticSearchService.searchWpPosts(searchForm.getQ(), pageable);
        List<WpPosts> wpPosts = wpPostses.getContent().stream().peek(content -> content.setPostContent(pegDownProcessor.markdownToHtml(unescapeHtml(content.getPostContentFiltered())))).collect(toList());
//
//    WpPosts wpPosts1 = new WpPosts();
//    wpPosts1.setPostTitle("test");
//    wpPosts1.setPostContent("11111");
//    wpPosts1.setId(1);
//    wpPosts1.setPostDate(LocalDateTime.now());
//    List<WpPosts> list = Arrays.asList(wpPosts1);
        model.addAttribute("wordPresses", new PageImpl<>(wpPosts, pageable, wpPostses.getTotalElements()));
        return "wordpress/search";
    }


    @GetMapping("/{id}")
    public String findOne(@PathVariable Long id, Model model) {
        WpPosts wpPosts = postElasticSearchService.findOne(id);
        wpPosts.setPostContent(pegDownProcessor.markdownToHtml(unescapeHtml(wpPosts.getPostContentFiltered())));
        model.addAttribute("wordPress", wpPosts);
        return "wordpress/wordPress";
    }
}
