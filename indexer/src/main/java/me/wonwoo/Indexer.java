package me.wonwoo;

import org.springframework.transaction.annotation.Transactional;

public interface Indexer<T> {

	Iterable<T> indexItems();

	void indexItem(T index);

	@Transactional
	void save(T index);

	void error(T index, Throwable e);
}
