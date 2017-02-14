package me.wonwoo.support.github.asciidoc;

import java.io.Serializable;

public class MarkdownHtml implements Serializable {
    private String html;

    public MarkdownHtml(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
        return html;
    }
}