DROP TABLE IF EXISTS `category_post`;

DROP TABLE IF EXISTS `category`;

DROP TABLE IF EXISTS `comment`;

DROP TABLE IF EXISTS `tag`;

DROP TABLE IF EXISTS `post`;

DROP TABLE IF EXISTS `user`;

DROP TABLE IF EXISTS `wp_posts`;

CREATE TABLE `user`
  (
     `id`         BIGINT(20) NOT NULL auto_increment,
     `avatar_url` VARCHAR(255) DEFAULT NULL,
     `bio`        LONGTEXT,
     `email`      VARCHAR(255) DEFAULT NULL,
     `github`     VARCHAR(255) DEFAULT NULL,
     `is_admin`   BIT(1) NOT NULL,
     `name`       VARCHAR(255) DEFAULT NULL,
     `password`   VARCHAR(255) DEFAULT NULL,
     PRIMARY KEY (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `wp_posts`
  (
     `id`                    INT(11) NOT NULL,
     `indexing`              VARCHAR(255) DEFAULT NULL,
     `post_author`           INT(11) DEFAULT NULL,
     `post_content`          VARCHAR(255) DEFAULT NULL,
     `post_content_filtered` VARCHAR(255) DEFAULT NULL,
     `post_date`             DATETIME DEFAULT NULL,
     `post_modified`         DATETIME DEFAULT NULL,
     `post_status`           VARCHAR(255) DEFAULT NULL,
     `post_title`            VARCHAR(255) DEFAULT NULL,
     `post_type`             VARCHAR(255) DEFAULT NULL,
     PRIMARY KEY (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `category`
  (
     `id`       BIGINT(20) NOT NULL auto_increment,
     `name`     VARCHAR(255) DEFAULT NULL,
     `reg_date` DATETIME DEFAULT NULL,
     PRIMARY KEY (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `post`
  (
     `id`       BIGINT(20) NOT NULL auto_increment,
     `code`     LONGTEXT,
     `content`  LONGTEXT NOT NULL,
     `indexing` VARCHAR(255) DEFAULT NULL,
     `reg_date` DATETIME DEFAULT NULL,
     `title`    VARCHAR(255) NOT NULL,
     `yn`       VARCHAR(255) DEFAULT NULL,
     `user_id`  BIGINT(20) DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `fk72mt33dhhs48hf9gcqrq4fxte` (`user_id`),
     CONSTRAINT `fk72mt33dhhs48hf9gcqrq4fxte` FOREIGN KEY (`user_id`) REFERENCES
     `user` (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `category_post`
  (
     `id`          BIGINT(20) NOT NULL auto_increment,
     `category_id` BIGINT(20) DEFAULT NULL,
     `post_id`     BIGINT(20) DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `fkgrlw4y7oywggm56qbnehfn4a4` (`category_id`),
     KEY `fkmhkf2t5e9lcr9dl9ixc36x4b3` (`post_id`),
     CONSTRAINT `fkgrlw4y7oywggm56qbnehfn4a4` FOREIGN KEY (`category_id`)
     REFERENCES `category` (`id`),
     CONSTRAINT `fkmhkf2t5e9lcr9dl9ixc36x4b3` FOREIGN KEY (`post_id`) REFERENCES
     `post` (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `comment`
  (
     `id`       BIGINT(20) NOT NULL auto_increment,
     `content`  VARCHAR(255) DEFAULT NULL,
     `reg_date` DATETIME DEFAULT NULL,
     `post_id`  BIGINT(20) DEFAULT NULL,
     `user_id`  BIGINT(20) DEFAULT NULL,
     PRIMARY KEY (`id`),
     KEY `fks1slvnkuemjsq2kj4h3vhx7i1` (`post_id`),
     KEY `fk8kcum44fvpupyw6f5baccx25c` (`user_id`),
     CONSTRAINT `fk8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES
     `user` (`id`),
     CONSTRAINT `fks1slvnkuemjsq2kj4h3vhx7i1` FOREIGN KEY (`post_id`) REFERENCES
     `post` (`id`)
  )
engine=innodb
DEFAULT charset=utf8;

CREATE TABLE `tag`
  (
     `post_id` BIGINT(20) NOT NULL,
     `tag`     VARCHAR(255) DEFAULT NULL,
     KEY `fk7tk5hi5tl1txftyn44dtq2mv6` (`post_id`),
     CONSTRAINT `fk7tk5hi5tl1txftyn44dtq2mv6` FOREIGN KEY (`post_id`) REFERENCES
     `post` (`id`)
  )
engine=innodb
DEFAULT charset=utf8;