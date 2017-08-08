package me.wonwoo.autoconfigure.message.slack;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackMessagePropertiesTests {

	@Test
	public void slackMessagePropertiesIsEmpty () {
		SlackMessageProperties properties = new SlackMessageProperties();
		assertThat(properties.getWebHookUrl()).isNull();
		assertThat(properties.getChannel()).isNull();
		assertThat(properties.getUsername()).isEqualTo("webhookbot");
		assertThat(properties.getIconEmoji()).isEqualTo(":disappointed:");
	}

	@Test
	public void slackMessageProperties() {
		SlackMessageProperties properties = new SlackMessageProperties();
		properties.setWebHookUrl("http://localhost:8888");
		properties.setChannel("test");
		properties.setUsername("wonwoo");
		properties.setIconEmoji(":test:");
		assertThat(properties.getWebHookUrl()).isEqualTo("http://localhost:8888");
		assertThat(properties.getChannel()).isEqualTo("test");
		assertThat(properties.getUsername()).isEqualTo("wonwoo");
		assertThat(properties.getIconEmoji()).isEqualTo(":test:");
	}

}