Insert INTO board_category(category_id, category_name) VALUES(1, '자유 게시판');
Insert INTO board_category(category_id, category_name) VALUES(2, '정보 공유 게시판');
Insert INTO board_category(category_id, category_name) VALUES(3, '갤러리');
Insert INTO board_category(category_id, category_name) VALUES(4, 'Hot 게시판');
Insert INTO board_category(category_id, category_name) VALUES(5, '공지 게시판');
Insert INTO board_category(category_id, category_name) VALUES(6, '추천 사이트');

INSERT INTO users(user_no, email, hp, name, nick_name, password) VALUES (1, 'sj@gmail.com','010987654321', 'NAME', 'Manager', '$2a$10$zA8p8GljNF/FlghsqE3NF.icXyxiYDOUmI2WWT8Sy9I4BkELCtm/m');

INSERT INTO board (title, author, content, created_at, updated_at, email, views, board_category_id, user_no) VALUES ('[필독]공지사항', 'Manager', '드루와~', now(), now(), 'sj@gmail.com', 0, 5, 1)



