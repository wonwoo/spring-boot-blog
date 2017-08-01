package me.wonwoo.support.github;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GitHubRepo implements Serializable {

  private long id;

  private String url;

  private String htmlUrl;

  private String cloneUrl;

  private String gitUrl;

  private String sshUrl;

  private String svnUrl;

  public GitHubRepo() {

  }

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

  public long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public String getCloneUrl() {
    return cloneUrl;
  }

  public String getGitUrl() {
    return gitUrl;
  }

  public String getSshUrl() {
    return sshUrl;
  }

  public String getSvnUrl() {
    return svnUrl;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}