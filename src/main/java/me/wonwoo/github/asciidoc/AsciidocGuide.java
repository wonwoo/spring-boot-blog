/*
 * ****************************************************************************
 *
 *
 *  Copyright(c) 2015 Helloworld. All rights reserved.
 *
 *  This software is the proprietary information of Helloworld.
 *
 *
 * ***************************************************************************
 */

package me.wonwoo.github.asciidoc;

import java.util.Map;
import java.util.Set;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 2:00
 * desc :
 */
public class AsciidocGuide {
    private final String content;
    private final Set<String> tags;
    private final Set<String> projects;
    private final String tableOfContents;
    private final Map<String, String> understandingDocs;

    public AsciidocGuide(String content, Set<String> tags, Set<String> projects, String tableOfContents,
                         Map<String, String> understandingDocs) {
        this.content = content;
        this.tags = tags;
        this.projects = projects;
        this.tableOfContents = tableOfContents;
        this.understandingDocs = understandingDocs;
    }

    public String getContent() {
        return this.content;
    }

    public Set<String> getTags() {
        return this.tags;
    }

    public Set<String> getProjects() {
        return this.projects;
    }

    public String getTableOfContents() {
        return this.tableOfContents;
    }

    public Map<String, String> getUnderstandingDocs() {
        return this.understandingDocs;
    }
}