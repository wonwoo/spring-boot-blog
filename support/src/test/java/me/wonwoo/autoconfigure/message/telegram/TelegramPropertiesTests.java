package me.wonwoo.autoconfigure.message.telegram;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TelegramPropertiesTests {

	@Test
	public void telegramProperties() {
		TelegramProperties properties = new TelegramProperties();
		properties.setApiUrl("http://localhost:8080");
		properties.setChatId("test1");
		assertThat(properties.getApiUrl()).isEqualTo("http://localhost:8080");
		assertThat(properties.getChatId()).isEqualTo("test1");
	}
}