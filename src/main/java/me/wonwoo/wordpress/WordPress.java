package me.wonwoo.wordpress;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
@Data
public class WordPress {

  @JsonProperty("ID")
  private Long id;
  private String title;
  private String content;
  private OffsetDateTime date;
  private WordPressLogin author;
  private Map<String, Object> tags;
}
