package me.wonwoo.web;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import me.wonwoo.config.PostProperties;
import me.wonwoo.domain.model.Post;
import me.wonwoo.domain.model.User;
import me.wonwoo.domain.repository.PostRepository;
import me.wonwoo.service.CategoryService;
import me.wonwoo.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by wonwoolee on 2017. 8. 8..
 */
@WebMvcTest(PostController.class)
public class PostControllerTests extends AbstractControllerTests{

	@MockBean
	private PostRepository postRepository;

	@MockBean
	private PostProperties postProperties;

	@MockBean
	private CategoryService categoryService;

	@MockBean
	private PostService postService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void findByPost() throws Exception {
		Post post = new Post("post test", "Y");
		post.setContent("text content");
		post.setRegDate(LocalDateTime.now());
		post.setCode("text content");
		post.setUser(new User(null, "wonwoo", null, null, null, true));
		given(postRepository.findByIdAndYn(any(), any()))
				.willReturn(post);
		final MvcResult mvcResult = mockMvc.perform(get("/posts/1"))
				.andExpect(status().isOk())
				.andReturn();

		Post result = (Post) mvcResult.getModelAndView().getModel().get("post");
		assertThat(result.getCode()).isEqualTo("text content");
		assertThat(result.getContent()).isEqualTo("text content");
		assertThat(result.getTitle()).isEqualTo("post test");
		assertThat(result.getYn()).isEqualTo("Y");
	}

	//TODO test
}