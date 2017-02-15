package me.wonwoo.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
@EntityListeners(value = AuditingEntityListener.class)
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
public class Category {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @CreatedDate
  @Column(name = "reg_date")
  private LocalDateTime regDate;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<CategoryPost> post = new ArrayList<>();

  Category(){
  }

  public Category(Long id){
    this.id = id;
  }

  public Category(Long id, String name){
    this.name = name;
    this.id = id;
  }

  public Category(Long id, String name,LocalDateTime regDate){
    this.name = name;
    this.id = id;
    this.regDate = regDate;
  }

  public void setName(String name) {
    this.name = name;
  }

}
