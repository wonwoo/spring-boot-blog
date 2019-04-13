package me.wonwoo.autoconfigure.message.slack;

import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@ConfigurationProperties("slack.message")
public class SlackMessageProperties {

  /**
   * hook url
   */
  private String webHookUrl;

  /**
   * channel name
   */
  private String channel;

  /**
   * username
   */
  private String username = "webhookbot";

  /**
   * icon emoji
   */
  private String iconEmoji = ":disappointed:";

  public String getWebHookUrl() {
    return webHookUrl;
  }

  public void setWebHookUrl(String webHookUrl) {
    this.webHookUrl = webHookUrl;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getIconEmoji() {
    return iconEmoji;
  }

  public void setIconEmoji(String iconEmoji) {
    this.iconEmoji = iconEmoji;
  }

  @Override
  public String toString() {
    return "SlackMessageProperties{" +
        "webHookUrl='" + webHookUrl + '\'' +
        ", channel='" + channel + '\'' +
        ", username='" + username + '\'' +
        ", iconEmoji='" + iconEmoji + '\'' +
        '}';
  }
}
