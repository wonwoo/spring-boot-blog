package me.wonwoo.web;

import lombok.RequiredArgsConstructor;
import me.wonwoo.service.TemplateService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String setting() {
        return templateService.getSetting();
    }

    @GetMapping(value = "/mapping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String mapping() {
        return templateService.getMapping();
    }
}
