package me.wonwoo.autoconfigure.message.slack;

import static org.assertj.core.api.Assertions.assertThat;

import me.wonwoo.support.message.InvalidWebHookUrlException;
import org.junit.Test;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;
import org.springframework.boot.diagnostics.LoggingFailureAnalysisReporter;

public class InvalidWebHookUrlFailureAnalyzerTests {

	private final FailureAnalyzer analyzer = new InvalidWebHookUrlFailureAnalyzer();

	@Test
	public void failureAnalysis() {
		FailureAnalysis failureAnalysis = analyzeFailure(createFailure());
		assertThat(failureAnalysis).isNotNull();
		assertThat(failureAnalysis.getDescription())
			.isEqualTo("The web hook url could not be auto-configured properly: 'testasdfasdflkj' is an invalid url");
		assertThat(failureAnalysis.getAction())
			.contains("web-hook-url 프로퍼티가 URL 형식에 맞지 않습니다. url 형식에 맞게 입력 하세요!");

	}

	private final Exception createFailure() {
		return new InvalidWebHookUrlException("testasdfasdflkj");
	}

	private FailureAnalysis analyzeFailure(Exception failure) {
		FailureAnalysis analysis = this.analyzer.analyze(failure);
		if (analysis != null) {
			new LoggingFailureAnalysisReporter().report(analysis);
		}
		return analysis;
	}
}