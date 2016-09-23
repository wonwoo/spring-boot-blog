package me.wonwoo.wordpress.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class WpPosts {
    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Column(name = "post_title")
    private String postTitle;
    @Column(name = "post_author")
    private String postAuthor;
    @Column(name = "post_content")
    private String postContent;
    @Column(name = "post_date", columnDefinition = "datetime")
    private LocalDateTime postDate;
    @Column(name = "post_type")
    private String postType;
    @Column(name = "post_status")
    private String postStatus;
    @Column(name ="post_content_filtered")
    private String postContentFiltered;
}
