package me.wonwoo.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@ToString(exclude = {"user", "post"})
@EqualsAndHashCode(exclude = {"user", "post"})
public class Comment {
    @Id @GeneratedValue
    private Long id;

    private String content;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Comment(String content, Post post, User user){
        this.content = content;
        this.post = post;
        this.user = user;
    }
    Comment(){

    }
}
