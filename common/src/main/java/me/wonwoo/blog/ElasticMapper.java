package me.wonwoo.blog;

public interface ElasticMapper<T, R> {

    R map(T m);
}
