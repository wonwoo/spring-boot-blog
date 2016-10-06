package me.wonwoo.domain.model;

import org.jinq.orm.annotations.NoSideEffects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by wonwoo on 2016. 9. 17..
 */
@Embeddable
public class Tag implements Serializable {

  @Column(name = "tag")
  private String tag;

  Tag(){
  }

  public Tag(String tag){
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tag tag1 = (Tag) o;

    return tag != null ? tag.equals(tag1.tag) : tag1.tag == null;

  }

  @Override
  public int hashCode() {
    return tag != null ? tag.hashCode() : 0;
  }
}
