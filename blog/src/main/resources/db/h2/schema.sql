DROP TABLE IF EXISTS `category_post`;

DROP TABLE IF EXISTS `category`;

DROP TABLE IF EXISTS `comment`;

DROP TABLE IF EXISTS `tag`;

DROP TABLE IF EXISTS `post`;

DROP TABLE IF EXISTS `user`;

DROP TABLE IF EXISTS `wp_posts`;

CREATE TABLE category
  ( 
     id       BIGINT NOT NULL auto_increment, 
     name     VARCHAR(255), 
     reg_date DATETIME, 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE category_post 
  ( 
     id          BIGINT NOT NULL auto_increment, 
     category_id BIGINT, 
     post_id     BIGINT, 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE comment 
  ( 
     id       BIGINT NOT NULL auto_increment, 
     content  VARCHAR(255), 
     reg_date DATETIME, 
     post_id  BIGINT, 
     user_id  BIGINT, 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE post 
  ( 
     id       BIGINT NOT NULL auto_increment, 
     code     LONGTEXT, 
     content  LONGTEXT NOT NULL, 
     indexing VARCHAR(255), 
     reg_date DATETIME, 
     title    VARCHAR(255) NOT NULL, 
     yn       VARCHAR(255), 
     user_id  BIGINT, 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE tag 
  ( 
     post_id BIGINT NOT NULL, 
     tag     VARCHAR(255) 
  ); 

CREATE TABLE user 
  ( 
     id         BIGINT NOT NULL auto_increment, 
     avatar_url VARCHAR(255), 
     bio        LONGTEXT, 
     email      VARCHAR(255), 
     github     VARCHAR(255), 
     is_admin   BIT NOT NULL, 
     name       VARCHAR(255), 
     password   VARCHAR(255), 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE wp_posts 
  ( 
     id                    INTEGER NOT NULL, 
     indexing              VARCHAR(255), 
     post_author           INTEGER, 
     post_content          VARCHAR(255), 
     post_content_filtered VARCHAR(255), 
     post_date             DATETIME, 
     post_modified         DATETIME, 
     post_status           VARCHAR(255), 
     post_title            VARCHAR(255), 
     post_type             VARCHAR(255), 
     PRIMARY KEY (id) 
  ); 

ALTER TABLE category_post 
  ADD CONSTRAINT fkgrlw4y7oywggm56qbnehfn4a4 FOREIGN KEY (category_id) 
  REFERENCES category (id); 

ALTER TABLE category_post 
  ADD CONSTRAINT fkmhkf2t5e9lcr9dl9ixc36x4b3 FOREIGN KEY (post_id) REFERENCES 
  post (id); 

ALTER TABLE comment 
  ADD CONSTRAINT fks1slvnkuemjsq2kj4h3vhx7i1 FOREIGN KEY (post_id) REFERENCES 
  post (id); 

ALTER TABLE comment 
  ADD CONSTRAINT fk8kcum44fvpupyw6f5baccx25c FOREIGN KEY (user_id) REFERENCES 
  user (id); 

ALTER TABLE post 
  ADD CONSTRAINT fk72mt33dhhs48hf9gcqrq4fxte FOREIGN KEY (user_id) REFERENCES 
  user (id); 

ALTER TABLE tag 
  ADD CONSTRAINT fk7tk5hi5tl1txftyn44dtq2mv6 FOREIGN KEY (post_id) REFERENCES 
  post (id); 