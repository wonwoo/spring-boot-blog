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

import java.io.Serializable;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 6:33
 * desc :
 */
public class GitHubRepo implements Serializable {

    private final long id;

    private final String url;

    private final String htmlUrl;

    private final String cloneUrl;

    private final String gitUrl;

    private final String sshUrl;

    private final String svnUrl;

    public GitHubRepo(long id, String url, String htmlUrl, String cloneUrl, String gitUrl, String sshUrl,
                      String svnUrl) {
        this.id = id;
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.cloneUrl = cloneUrl;
        this.gitUrl = gitUrl;
        this.sshUrl = sshUrl;
        this.svnUrl = svnUrl;
    }

    private String name;

    private String description;

    public long getId() { return id; }

    public String getUrl() { return url; }

    public String getHtmlUrl() { return htmlUrl; }

    public String getCloneUrl() { return cloneUrl; }

    public String getGitUrl() { return gitUrl; }

    public String getSshUrl() { return sshUrl; }

    public String getSvnUrl() { return svnUrl; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

}