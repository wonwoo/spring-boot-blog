package me.wonwoo.support.condition;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
class OnProfilesCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
    List<String> activeProfiles = addAll(context);
    List<String> onProfiles = getCandidates(metadata, ConditionalOnProfiles.class);
    if (onProfiles != null && !onProfiles.isEmpty()) {
      if (contains(activeProfiles.toArray(), onProfiles.toArray())) {
        return ConditionOutcome.match("found activeProfiles");
      }
    }

    List<String> missingProfiles = getCandidates(metadata, ConditionalOnMissingProfiles.class);

    if (missingProfiles != null && !missingProfiles.isEmpty()) {
      if (!contains(activeProfiles.toArray(), missingProfiles.toArray())) {
        return ConditionOutcome.match("found activeProfiles");
      }
    }
    return ConditionOutcome.noMatch("not found activeProfiles");

  }

  private List<String> addAll(ConditionContext context) {
    List<String> active = new ArrayList<>();
    active.addAll(Arrays.asList(context.getEnvironment().getActiveProfiles()));
    active.addAll(Arrays.asList(context.getEnvironment().getDefaultProfiles()));
    return active;
  }

  private static boolean contains(Object[] o1, Object[] o2) {
    for (Object o : o2) {
      if (ArrayUtils.contains(o1, o)) {
        return true;
      }
    }
    return false;
  }

  private List<String> getCandidates(AnnotatedTypeMetadata metadata,
                                     Class<?> annotationType) {
    MultiValueMap<String, Object> attributes = metadata
        .getAllAnnotationAttributes(annotationType.getName(), true);

    if (attributes == null) {
      return Collections.emptyList();
    }
    List<String> list = new ArrayList<>();
    addAll(list, attributes.get("value"));
    return list;
  }

  private void addAll(List<String> list, List<Object> itemsToAdd) {
    if (itemsToAdd != null) {
      for (Object item : itemsToAdd) {
        Collections.addAll(list, (String[]) item);
      }
    }
  }
}