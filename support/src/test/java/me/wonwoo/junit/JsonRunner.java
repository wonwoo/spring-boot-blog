package me.wonwoo.junit;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.runners.InternalRunner;
import org.springframework.boot.test.json.JacksonTester;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by wonwoo on 2017. 2. 12..
 */
public class JsonRunner implements InternalRunner {
  BlockJUnit4ClassRunner runner;

  public JsonRunner(Class<?> klass) throws InitializationError {
    this.runner = new BlockJUnit4ClassRunner(klass) {
      @Override
      protected Object createTest() throws Exception {
        Object test = super.createTest();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(test, objectMapper);
        MockitoAnnotations.initMocks(test);
        return test;
      }
    };
  }

  public void run(RunNotifier notifier) {
    runner.run(notifier);
  }

  public Description getDescription() {
    return runner.getDescription();
  }

  public void filter(Filter filter) throws NoTestsRemainException {
    runner.filter(filter);
  }
}