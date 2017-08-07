package me.wonwoo.dto;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wonwoolee on 2017. 8. 7..
 */
public class SearchFormTests {

  @Test
  public void searchEmpty() {
    SearchForm searchForm = new SearchForm();
    assertThat(searchForm.getQ()).isEqualTo("");
  }

  @Test
  public void search() {
    SearchForm searchForm = new SearchForm();
    searchForm.setQ("spring");
    assertThat(searchForm.getQ()).isEqualTo("spring");
  }

}