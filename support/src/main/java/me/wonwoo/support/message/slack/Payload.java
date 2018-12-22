package me.wonwoo.support.message.slack;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import lombok.Data;

/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class Payload {

  private final String channel;

  private final String username;

  private final String text;

  @JsonProperty("icon_emoji")
  private final String iconEmoji;

  public Payload(String channel, String username, String text, String iconEmoji) {
    this.channel = channel;
    this.username = username;
    this.text = text;
    this.iconEmoji = iconEmoji;
  }

  public String getChannel() {
    return channel;
  }

  public String getUsername() {
    return username;
  }

  public String getText() {
    return text;
  }

  public String getIconEmoji() {
    return iconEmoji;
  }

  @Override
  public String toString() {
    return "Payload{" +
        "channel='" + channel + '\'' +
        ", username='" + username + '\'' +
        ", text='" + text + '\'' +
        ", iconEmoji='" + iconEmoji + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payload payload = (Payload) o;
    return Objects.equals(channel, payload.channel) &&
        Objects.equals(username, payload.username) &&
        Objects.equals(text, payload.text) &&
        Objects.equals(iconEmoji, payload.iconEmoji);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channel, username, text, iconEmoji);
  }
}