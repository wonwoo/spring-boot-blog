package me.wonwoo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
@ConfigurationProperties("git")
public class GitProperties {
  private final Github github = new Github();

  private final Security security = new Security();

  public Github getGithub() {
    return this.github;
  }

  public Security getSecurity() {
    return this.security;
  }


  public static class Github {

    /**
     * Access token ("username:access_token") to query public github endpoints.
     */
    private String token;

    public String getToken() {
      return this.token;
    }

    public void setToken(String token) {
      this.token = token;
    }
  }

  public static class Security {

    /**
     * Github users that have admin rights.
     */
    private List<String> admins = Arrays.asList("wonwoo", "bclozel");

    public List<String> getAdmins() {
      return admins;
    }

    public void setAdmins(List<String> admins) {
      this.admins = admins;
    }

  }
}
