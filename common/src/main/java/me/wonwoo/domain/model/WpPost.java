package me.wonwoo.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Data;

@Entity
@Table(name = "wp_posts")
@Data
public class WpPost {

  @Id
  private Integer id;

  private String postTitle;

  private Integer postAuthor;

  private String postContent;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime postDate;

  private String postType;

  private String postStatus;

  private String postContentFiltered;

  private String indexing;

  private LocalDateTime postModified;
}
