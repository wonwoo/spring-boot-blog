package me.wonwoo.web;


import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import me.wonwoo.config.PostProperties;
import me.wonwoo.service.GuidesService;
import me.wonwoo.support.asciidoc.DefaultGuideMetadata;
import me.wonwoo.support.asciidoc.GettingStartedGuide;
import me.wonwoo.support.asciidoc.GuideMetadata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpringGuidesController.class)
public class SpringGuidesControllerTests extends AbstractControllerTests {

	@MockBean
	private PostProperties postProperties;

	@MockBean
	private GuidesService guidesService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void guides() throws Exception {
		given(guidesService.findAllMetadata()).willReturn(Arrays.asList(new DefaultGuideMetadata()));
		MvcResult mvcResult = mockMvc
				.perform(get("/guides"))
				.andExpect(status().isOk())
				.andReturn();

		String type = (String) mvcResult.getModelAndView().getModel().get("type");
		assertThat(type).isEqualTo("gs");
		verify(guidesService).findAllMetadata();
	}

	@Test
	public void guide() throws Exception {
		GuideMetadata guideMetadata = new DefaultGuideMetadata("wonwoo","100","spring-blog", "test");
		GettingStartedGuide gettingStartedGuide = new GettingStartedGuide();
		gettingStartedGuide.setContent("test content");
		gettingStartedGuide.setSidebar("test sidebar");
		gettingStartedGuide.setMetadata(guideMetadata);
		given(guidesService.findGs(any())).willReturn(gettingStartedGuide);

		MvcResult mvcResult = mockMvc
				.perform(get("/guides/{project}", "spring-blog"))
				.andExpect(status().isOk())
				.andReturn();

		assertThat(mvcResult.getModelAndView().getViewName()).isEqualTo("guides/guide");
		verify(guidesService).findGs("spring-blog");
	}
}