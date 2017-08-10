
insert into user (ID,PASSWORD, AVATAR_URL,BIO,EMAIL,GITHUB,NAME, IS_ADMIN) values(1,'$2a$10$9f9ZjYeYVHObuzbSi93TOeM1lWP6A11schzLOgaVoii2ZVUgM2DUC', 'https://avatars.githubusercontent.com/u/747472?v=3',null,'aoruqjfu@gamilc.com', 'wonwoo','wonwoo', 1);

insert into category(ID, NAME, REG_DATE) values(1, 'spring', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(2, 'java', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(3, 'markdown', CURRENT_TIMESTAMP());


insert into post(ID, TITLE, CODE, CONTENT, YN, REG_DATE, USER_ID) values(1, '테스트', '지금 포스팅은 테스트 포스팅 입니다.', '지금 포스팅은 테스트 포스팅 입니다.', 'Y',CURRENT_TIMESTAMP(), 1);

insert into category_post(id, category_id, post_id) values(1,1,1);
insert into tag(TAG, POST_ID) values('test',1);

insert into comment(ID, CONTENT, REG_DATE, POST_ID, USER_ID) values(1, '테스트 댓글', CURRENT_TIMESTAMP(), 1, 1);

