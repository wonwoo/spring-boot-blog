package me.wonwoo.domain.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-08-25
 * Time : 오후 1:01
 * desc :
 */
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class Comment {
    @Id @GeneratedValue
    private Long id;

    private String content;

    @CreatedDate
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
