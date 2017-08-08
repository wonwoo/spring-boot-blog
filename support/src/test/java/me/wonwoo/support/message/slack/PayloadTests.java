package me.wonwoo.support.message.slack;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PayloadTests {

	@Test
	public void payLoad() {
		Payload payload = new Payload("test", "wonwoo", "hi wonwoo", "test emoji");
		assertThat(payload.getChannel()).isEqualTo("test");
		assertThat(payload.getUsername()).isEqualTo("wonwoo");
		assertThat(payload.getText()).isEqualTo("hi wonwoo");
		assertThat(payload.getIconEmoji()).isEqualTo("test emoji");
	}
}