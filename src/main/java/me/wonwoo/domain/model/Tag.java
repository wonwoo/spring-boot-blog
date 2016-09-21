package me.wonwoo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by wonwoo on 2016. 9. 17..
 */
@ToString
@EqualsAndHashCode
@Embeddable
@Getter
public class Tag {

  private String tag;

  Tag(){
  }

  public Tag(String tag){
    this.tag = tag;
  }
}
