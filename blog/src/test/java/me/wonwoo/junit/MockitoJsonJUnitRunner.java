package me.wonwoo.junit;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.mockito.internal.runners.InternalRunner;
import org.mockito.internal.runners.StrictRunner;

/**
 * Created by wonwoo on 2017. 2. 12..
 */
public class MockitoJsonJUnitRunner extends Runner {

  private final InternalRunner runner;

  public MockitoJsonJUnitRunner(Class<?> clazz) throws Exception {
    this(new StrictRunner(new JsonRunner(clazz), clazz));
  }

  public MockitoJsonJUnitRunner(InternalRunner runner) {
    this.runner = runner;
  }

  @Override
  public void run(final RunNotifier notifier) {
    runner.run(notifier);
  }

  @Override
  public Description getDescription() {
    return runner.getDescription();
  }
}