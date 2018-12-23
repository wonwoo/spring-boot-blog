package me.wonwoo;

public interface Indexer<T> {

  void preHandle() ;

  Iterable<T> indexItems();

  void indexItem(T index);

  void error(T index, Throwable e);

  void postHandle();
}
