insert into USER (ID,AVATAR_URL,BIO,EMAIL,GITHUB,NAME) values(1,'https://avatars.githubusercontent.com/u/747472?v=3',null,'aoruqjfu@gamilc.com', 'wonwoo','wonwoo');

insert into category(ID, NAME, REG_DATE) values(1, 'spring', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(2, 'java', CURRENT_TIMESTAMP());
insert into category(ID, NAME, REG_DATE) values(3, 'markdown', CURRENT_TIMESTAMP());

insert into post(ID, TITLE, CODE, CONTENT, YN, REG_DATE, USER_ID,CATEGORY_ID) values(1, '테스트', '지금 포스팅은 테스트 포스팅 입니다.', '지금 포스팅은 테스트 포스팅 입니다.', 'Y',CURRENT_TIMESTAMP(), 1, 1);
insert into tag(ID, TAG, POST_ID) values(1,'test',1);
-- insert into post(ID, TITLE, CODE, CONTENT, REG_DATE, USER_ID,CATEGORY_ID) values(2, 'markdown','An h1 header \r\n============\r\n\r\nParagraphs are separated by a blank line. \r\n\r\n2nd paragraph. *Italic*, **bold**, and `monospace`. Itemized lists \r\nlook like: \r\n\r\n * this one\r\n * that one\r\n * the other one', '<h1>An h1 header</h1><pf>Paragraphs are separated by a blank line.</p><p>2nd paragraph. <em>Italic</em>, <strong>bold</strong>, and <code>monospace</code>. Itemized listslook like:</p><ul><li>this one</li><li>that one</li><li>the other one</li></ul>', CURRENT_TIMESTAMP(), 1, 3);

insert into comment(ID, CONTENT, REG_DATE, POST_ID, USER_ID) values(1, '테스트 댓글', CURRENT_TIMESTAMP(), 1, 1);
-- insert into comment(ID, CONTENT, REG_DATE, POST_ID, USER_ID) values(2, 'markdown 댓글', CURRENT_TIMESTAMP(), 2, 1);

