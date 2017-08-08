package me.wonwoo.support.message;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidWebHookUrlExceptionTests {

	@Test
	public void invalidWebHookUrlException() {
		InvalidWebHookUrlException invalidWebHookUrlException = new InvalidWebHookUrlException("blabla");
		assertThat(invalidWebHookUrlException.getWebHookUrl()).isEqualTo("blabla");
	}
}