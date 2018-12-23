package me.wonwoo.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by wonwoo on 2016. 8. 15..
 */
@Entity
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "reg_date")
  private LocalDateTime regDate;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Post> post = new ArrayList<>();

  Category() {
  }

  public Category(Long id) {
    this.id = id;
  }

  public Category(String name) {
    this.name = name;
  }

  public Category(Long id, String name) {
    this.name = name;
    this.id = id;
  }

  public Category(Long id, String name, LocalDateTime regDate) {
    this.name = name;
    this.id = id;
    this.regDate = regDate;
  }

  public void setName(String name) {
    this.name = name;
  }

}
