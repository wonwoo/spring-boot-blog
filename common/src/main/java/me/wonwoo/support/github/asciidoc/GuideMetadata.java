package me.wonwoo.support.github.asciidoc;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface GuideMetadata extends DocumentMetadata {

    String getTitle();

    String getSubtitle();

    Set<String> getTags();

    String getRepoName();

    String getGuideId();

    String getGitRepoHttpsUrl();

    String getGithubHttpsUrl();

    String getZipUrl();

    String getGitRepoSshUrl();

    String getGitRepoSubversionUrl();

    String getCiStatusImageUrl();

    String getCiLatestUrl();

}
