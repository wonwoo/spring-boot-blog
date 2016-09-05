package me.wonwoo.github.asciidoc;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Set;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-05
 * Time : 오후 6:17
 * desc :
 */
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
