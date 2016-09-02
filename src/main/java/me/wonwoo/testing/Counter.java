package me.wonwoo.testing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wonwoo on 2016. 9. 3..
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Counter {
  private final AtomicLong count;
  private final Long time;
}
