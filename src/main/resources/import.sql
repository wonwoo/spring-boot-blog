insert into USER (ID,AVATAR_URL,BIO,EMAIL,GITHUB,NAME) values(1,'https://avatars.githubusercontent.com/u/747472?v=3',null,'aoruqjfu@gamilc.com', 'wonwoo','wonwoo');

insert into category(ID, NAME, REG_DATE) values(1, 'spring', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(2, 'java', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(3, 'markdown', CURRENT_TIMESTAMP());

insert into post(ID, TITLE, CONTENT, REG_DATE, USER_ID,CATEGORY_ID) values(1, '테스트', '지금 포스팅은 테스트 포스팅 입니다.', CURRENT_TIMESTAMP(), 1, 1);
insert into post(ID, TITLE, CONTENT, REG_DATE, USER_ID,CATEGORY_ID) values(2, 'markdown', '<h1>An h1 header</h1><pf>Paragraphs are separated by a blank line.</p><p>2nd paragraph. <em>Italic</em>, <strong>bold</strong>, and <code>monospace</code>. Itemized listslook like:</p><ul><li>this one</li><li>that one</li><li>the other one</li></ul>', CURRENT_TIMESTAMP(), 1, 3);
