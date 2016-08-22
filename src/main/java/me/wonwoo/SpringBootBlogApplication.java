package me.wonwoo;

import lombok.RequiredArgsConstructor;
import me.wonwoo.domain.model.Category;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.repository.CategoryRepository;
import me.wonwoo.domain.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Entity;
import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
@EntityScan(basePackageClasses = {SpringBootBlogApplication.class, Jsr310JpaConverters.class })
public class SpringBootBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogApplication.class, args);
	}

	private final CategoryRepository categoryRepository;
	private final PostRepository postRepository;

	//test data
	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
			Category category = new Category("spring");
			categoryRepository.save(category);
			postRepository.save(Arrays.asList(
				new Post("first Title", "<pre><code class=\"language-java\"><span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">static</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">main</span><span class=\"hljs-params\">()</span></span>{\n\n}\n</code>\n</pre>", category),
				new Post("second Title", "second Content", category),
				new Post("third Title", "third Content", category)
			));
		};
	}
}
