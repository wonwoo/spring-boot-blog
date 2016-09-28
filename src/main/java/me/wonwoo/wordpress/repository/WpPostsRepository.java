/*
 * ****************************************************************************
 *
 *
 *  Copyright(c) 2015 Helloworld. All rights reserved.
 *
 *  This software is the proprietary information of Helloworld.
 *
 *
 * ***************************************************************************
 */

package me.wonwoo.wordpress.repository;

import me.wonwoo.wordpress.domain.WpPosts;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * Created by Helloworld
 * User : wonwoo
 * Date : 2016-09-28
 * Time : 오후 6:41
 * desc :
 */
public interface WpPostsRepository extends ElasticsearchCrudRepository<WpPosts, Long> {
}
