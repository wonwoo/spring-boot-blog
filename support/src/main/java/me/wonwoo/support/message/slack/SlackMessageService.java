package me.wonwoo.support.message.slack;

import me.wonwoo.autoconfigure.message.slack.SlackMessageProperties;
import me.wonwoo.support.message.MessageService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;


/**
 * Created by wonwoo on 2017. 2. 14..
 */
public class SlackMessageService implements MessageService {

  private final AsyncRestTemplate asyncRestTemplate;
  private final String channel;
  private final String username;
  private final String iconEmoji;
  private final String webHookUrl;
  private ListenableFutureCallback<ResponseEntity<?>> callback;


  public SlackMessageService(AsyncRestTemplate asyncRestTemplate, SlackMessageProperties slackMessageProperties) {
    this.asyncRestTemplate = asyncRestTemplate;
    this.channel = slackMessageProperties.getChannel();
    this.username = slackMessageProperties.getUsername();
    this.iconEmoji = slackMessageProperties.getIconEmoji();
    this.webHookUrl = slackMessageProperties.getWebHookUrl();
    urlValidator(this.webHookUrl);
  }


  @Override
  public void send(Throwable e) {
    HttpEntity<?> httpEntity = new HttpEntity<>(new Payload(channel, username, markdown(e), iconEmoji));
    ListenableFuture<ResponseEntity<Void>> future = asyncRestTemplate.postForEntity(webHookUrl, httpEntity, Void.class);
    if (callback != null) {
      future.addCallback(callback);
    }
  }

  @Override
  public void setCallback(ListenableFutureCallback<ResponseEntity<?>> callback) {
    this.callback = callback;
  }
}