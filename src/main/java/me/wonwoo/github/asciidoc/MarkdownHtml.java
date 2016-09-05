package me.wonwoo.github.asciidoc;

import java.io.Serializable;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 2:17
 * desc :
 */
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