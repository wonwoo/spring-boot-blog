package me.wonwoo.wordpress.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Data
@Document(indexName = "wordpress", type = "post", shards = 1, replicas = 0, refreshInterval = "-1")
public class WpPosts {
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

    private String highlightedContent;

    private String tableOfContent;
}
