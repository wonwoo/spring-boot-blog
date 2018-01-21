package me.wonwoo;

public interface Indexer<T> {

  Iterable<T> indexItems();

  void indexItem(T index);

  void save(T index);

  void error(T index, Throwable e);
}
