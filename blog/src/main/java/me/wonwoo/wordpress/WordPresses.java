package me.wonwoo.wordpress;

import lombok.Data;

import java.util.List;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@Data
public class WordPresses {
  private Long found;
  private List<WordPress> posts;
}
