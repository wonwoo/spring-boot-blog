package me.wonwoo.support.message.telegram;

import me.wonwoo.autoconfigure.message.telegram.TelegramProperties;
import me.wonwoo.support.message.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
public class TelegramMessageService implements MessageService {

  private final String apiUrl;
  private final String chatId;
  private final AsyncRestTemplate asyncRestTemplate;
  private ListenableFutureCallback<ResponseEntity<?>> callback;

  public TelegramMessageService(AsyncRestTemplate asyncRestTemplate, TelegramProperties telegramProperties) {
    this.asyncRestTemplate = asyncRestTemplate;
    this.apiUrl = telegramProperties.getApiUrl();
    this.chatId = telegramProperties.getChatId();
    urlValidator(this.apiUrl);
  }

  @Override
  public void send(Throwable e) {
    String request = String.format(apiUrl + "/sendmessage?chat_id=%s&text=%s&parse_mode=Markdown", chatId, markdown(e));
    ListenableFuture<ResponseEntity<Void>> future = asyncRestTemplate.getForEntity(request, Void.class);
    if (callback != null) {
      future.addCallback(callback);
    }
  }

  @Override
  public void setCallback(ListenableFutureCallback<ResponseEntity<?>> callback) {
    this.callback = callback;
  }
}