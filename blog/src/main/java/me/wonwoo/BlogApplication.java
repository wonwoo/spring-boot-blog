package me.wonwoo;

import me.wonwoo.config.PostProperties;
import me.wonwoo.security.GitProperties;
import me.wonwoo.support.weather.WeatherAppProperties;
import net.minidev.json.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;


@SpringBootApplication
@EnableConfigurationProperties({GitProperties.class, PostProperties.class, WeatherAppProperties.class})
@EnableCaching
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(GitProperties gitProperties) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(
				new GithubAppTokenInterceptor(gitProperties.getGithub().getToken())));
		return restTemplate;
	}

	private static class GithubAppTokenInterceptor implements ClientHttpRequestInterceptor {

		private final String token;

		GithubAppTokenInterceptor(String token) {
			this.token = token;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
																				ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
			if (StringUtils.hasText(this.token)) {
				byte[] basicAuthValue = this.token.getBytes(StandardCharsets.UTF_8);
				httpRequest.getHeaders().set(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString(basicAuthValue));
			}
			return clientHttpRequestExecution.execute(httpRequest, bytes);
		}
	}

	@Bean
	public JSONParser jsonParser(){
		return new JSONParser(DEFAULT_PERMISSIVE_MODE);
	}
}
