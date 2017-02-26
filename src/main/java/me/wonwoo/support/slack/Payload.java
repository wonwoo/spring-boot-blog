package me.wonwoo.support.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
@Data
public class Payload {

  private final String channel;

  private final String username;

  private final String text;

  @JsonProperty("icon_emoji")
  private final String iconEmoji;
}