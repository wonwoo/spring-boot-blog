package me.wonwoo;

import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexerService {
  private final ExecutorService executorService;

  public IndexerService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  //TODO 액츄에이터
  public <T> void index(final Indexer<T> indexer) {
    for (final T item : indexer.indexItems()) {
      executorService.submit(() -> {
        try {
          indexer.indexItem(item);
          indexer.save(item);
        } catch (Exception e) {
          indexer.error(item, e);
        }
      });
    }
  }
}
