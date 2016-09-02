package me.wonwoo.testing;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wonwoo on 2016. 9. 3..
 */
@Data
public class Counter {
  private final AtomicLong count;
  private final Long time;
}
