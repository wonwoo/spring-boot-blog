package me.wonwoo.support.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Data
public class Payload {

  private String channel;

  private String username;

  private String text;

  @JsonProperty("icon_emoji")
  private String iconEmoji;
}