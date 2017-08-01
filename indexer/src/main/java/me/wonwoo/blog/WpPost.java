package me.wonwoo.blog;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Data;

@Entity
@Table(name = "wp_posts")
@Data
public class WpPost {

	@Id
	@JsonProperty("ID")
	private Integer id;

	@JsonProperty("post_title")
	private String postTitle;

	@JsonProperty("post_author")
	private Integer postAuthor;

	@JsonProperty("post_content")
	private String postContent;

	@JsonProperty("post_date")
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
	private LocalDateTime postDate;

	@JsonProperty("post_type")
	private String postType;

	@JsonProperty("post_status")
	private String postStatus;

	@JsonProperty("post_content_filtered")
	private String postContentFiltered;

	private String indexing;
}
