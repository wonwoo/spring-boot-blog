package me.wonwoo.autoconfigure.message.telegram;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@Data
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

}