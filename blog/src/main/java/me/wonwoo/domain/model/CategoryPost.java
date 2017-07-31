package me.wonwoo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPost {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public CategoryPost(Category category, Post post){
        this.category = category;
        this.post = post;
    }
    public CategoryPost(Category category){
        this.category = category;
    }
}
