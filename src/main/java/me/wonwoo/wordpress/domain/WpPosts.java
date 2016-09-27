package me.wonwoo.wordpress.domain;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.SortableField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@Indexed
public class WpPosts {
    @Id @GeneratedValue
    @Column(name = "ID")
    private Long id;
    @Field
    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_author")
    private String postAuthor;

    @Column(name = "post_content")
    @Field
    private String postContent;
    @Column(name = "post_date")

    @Field
    @SortableField
    private LocalDateTime postDate;

    @Column(name = "post_type")
    @Field
    private String postType;

    @Field
    @Column(name = "post_status")
    private String postStatus;

    @Column(name ="post_content_filtered")
    private String postContentFiltered;
}
