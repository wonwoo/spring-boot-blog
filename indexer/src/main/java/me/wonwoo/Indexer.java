package me.wonwoo;

public interface Indexer<T> {

	Iterable<T> indexItems();

	void indexItem(T index);
}
