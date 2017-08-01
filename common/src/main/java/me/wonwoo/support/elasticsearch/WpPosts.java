package me.wonwoo.support.elasticsearch;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(indexName = "wordpress", type = "post", shards = 5, replicas = 0, refreshInterval = "-1")
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
