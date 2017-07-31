package me.wonwoo.autoconfigure.message.slack;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Data
@ConfigurationProperties("slack.message")
public class SlackMessageProperties {

  /**
   * hook url
   */
  private String webHookUrl;

  /**
   *  channel name
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

}
