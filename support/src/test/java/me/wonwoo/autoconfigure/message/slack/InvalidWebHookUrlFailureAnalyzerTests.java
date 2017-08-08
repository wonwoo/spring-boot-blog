package me.wonwoo.autoconfigure.message.slack;

import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.AsyncRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidWebHookUrlFailureAnalyzerTests {


	@Test
	public void failureAnalysis() {
		FailureAnalysis failureAnalysis = performAnalysis(TestConfiguration.class);
		assertThat(failureAnalysis).isNotNull();
		assertThat(failureAnalysis.getDescription())
				.isEqualTo("The web hook url could not be auto-configured properly: 'testasdfasdflkj' is an invalid url");
		assertThat(failureAnalysis.getAction())
				.contains("web-hook-url 프로퍼티가 URL 형식에 맞지 않습니다. url 형식에 맞게 입력 하세요!");

	}

	@Test
	public void unrelatedIllegalStateException() {
		FailureAnalysis failureAnalysis = new InvalidWebHookUrlFailureAnalyzer()
				.analyze(new RuntimeException("foo", new IllegalStateException("bar")));
		assertThat(failureAnalysis).isNull();
	}


	private FailureAnalysis performAnalysis(Class<?> configuration) {
		BeanCreationException failure = createFailure(configuration);
		assertThat(failure).isNotNull();
		return new InvalidWebHookUrlFailureAnalyzer().analyze(failure);
	}

	private BeanCreationException createFailure(Class<?> configuration) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(context, "slack.message.web-hook-url=testasdfasdflkj", "slack.message.channel=test");
		context.register(configuration);
		try {
			context.refresh();
			context.close();
			return null;
		}
		catch (BeanCreationException ex) {
			return ex;
		}
	}

	@Configuration
	@ImportAutoConfiguration(SlackMessageAutoConfiguration.class)
	@Import(AsyncRestTemplate.class)
	static class TestConfiguration {

	}

}