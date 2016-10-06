package me.wonwoo.jooq.util;

import org.jooq.util.DefaultGeneratorStrategy;
import org.jooq.util.Definition;

/**
 * Created by wonwoo on 2016. 10. 6..
 */
public class TangleFreeGeneratorStrategy extends DefaultGeneratorStrategy {

  @Override
  public String getJavaPackageName(Definition definition, Mode mode) {
    return getTargetPackage();
  }

}