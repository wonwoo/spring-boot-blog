package me.wonwoo.support.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Created by wonwoolee on 2017. 4. 2..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnProfilesCondition.class)
public @interface ConditionalOnProfiles {

  String[] value();
}