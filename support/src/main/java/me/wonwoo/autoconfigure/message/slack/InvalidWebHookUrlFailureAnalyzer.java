package me.wonwoo.autoconfigure.message.slack;

import me.wonwoo.support.message.InvalidWebHookUrlException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * Created by wonwoo on 2017. 2. 26..
 */
class InvalidWebHookUrlFailureAnalyzer extends AbstractFailureAnalyzer<InvalidWebHookUrlException> {
  @Override
  protected FailureAnalysis analyze(Throwable rootFailure, InvalidWebHookUrlException cause) {
    return new FailureAnalysis(
        String.format("The web hook url could not be auto-configured properly: '%s' is an invalid url",
            cause.getWebHookUrl()),
        "web-hook-url 프로퍼티가 URL 형식에 맞지 않습니다. url 형식에 맞게 입력 하세요!", cause);
  }
}