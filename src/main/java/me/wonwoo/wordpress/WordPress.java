package me.wonwoo.wordpress;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@Data
public class WordPress {
  private Long ID;
  private String title;
  private String content;
  private OffsetDateTime date;
  private WordPressLogin author;
}
