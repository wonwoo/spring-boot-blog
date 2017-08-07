package me.wonwoo.config.interceptor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import me.wonwoo.web.NewController;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonHandlerInterceptorTests {

	private HandlerInterceptorAdapter handlerInterceptorAdapter;

	@Rule
	public final OutputCapture capture = new OutputCapture();


	@Before
	public void setup() {
		handlerInterceptorAdapter = new CommonHandlerInterceptor();
	}

	@Test
	public void preHandle() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/test");
		request.setMethod("GET");
		MockHttpServletResponse response = new MockHttpServletResponse();
		NewController newController = new NewController(null);
		HandlerMethod handlerMethod = new HandlerMethod(newController, newController.getClass().getMethod("home", Model.class));
		boolean result = handlerInterceptorAdapter.preHandle(request, response, handlerMethod);
		assertThat(result).isTrue();
	}

	@Test
	public void postHandle() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		NewController newController = new NewController(null);
		HandlerMethod handlerMethod = new HandlerMethod(newController, newController.getClass().getMethod("home", Model.class));
		ModelAndView modelAndView = new ModelAndView();
		handlerInterceptorAdapter.postHandle(request, response, handlerMethod, modelAndView);
		Object sessionNav = request.getSession().getAttribute("navSection");
		Object modelNav = modelAndView.getModelMap().get("navSection");
		assertThat(sessionNav).isEqualTo("News");
		assertThat(modelNav).isEqualTo("News");

	}
}
