package me.wonwoo.autoconfigure.message.telegram;

import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@ConfigurationProperties("telegram.message")
public class TelegramProperties {

  /**
   * hook url
   */
  private String apiUrl;

  /**
   * channel name
   */
  private String chatId;


  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  @Override
  public String toString() {
    return "TelegramProperties{" +
        "apiUrl='" + apiUrl + '\'' +
        ", chatId='" + chatId + '\'' +
        '}';
  }
}