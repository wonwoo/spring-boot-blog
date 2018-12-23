[![Build Status](https://semaphoreci.com/api/v1/wonwoo/spring-boot-blog-all/branches/master/badge.svg)](https://semaphoreci.com/wonwoo/spring-boot-blog-all)
[![Build Status](https://travis-ci.org/wonwoo/spring-boot-blog.svg?branch=master)](https://travis-ci.org/wonwoo/spring-boot-blog)
[![Coverage Status](https://coveralls.io/repos/github/wonwoo/spring-boot-blog/badge.svg?branch=master)](https://coveralls.io/github/wonwoo/spring-boot-blog?branch=master)

## Spring boot blog 

### used 
 - Spring boot 2.1.x
 - Spring Security
 - ehcache
 - Spring Data jpa (with querydsl)
 - Spring data elasticsearch
 - thymeleaf

### install 

```sh
 mvn -Dmaven.test.skip=true clean install -P all
```

### with docker 

```sh 
docker-compose up
```

### Run 
- windows : http://192.168.99.100:8080
- mac os : http://localhost:8080


### Default Login
 - username : wonwoo
 - password : wonwoo
