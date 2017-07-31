package me.wonwoo.support.github.asciidoc;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface Guide extends DocumentContent, GuideMetadata {

}