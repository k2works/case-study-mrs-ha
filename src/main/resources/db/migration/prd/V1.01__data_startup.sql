-- 会議室
INSERT INTO meeting_room (room_name)
VALUES ('新木場');
INSERT INTO meeting_room (room_name)
VALUES ('辰巳');
INSERT INTO meeting_room (room_name)
VALUES ('豊洲');
INSERT INTO meeting_room (room_name)
VALUES ('月島');
INSERT INTO meeting_room (room_name)
VALUES ('新富町');
INSERT INTO meeting_room (room_name)
VALUES ('銀座一丁目');
INSERT INTO meeting_room (room_name)
VALUES ('有楽町');

-- 会議室の予約可能日(room_idが2〜6用のSQLは省略
-- room_id=1(新木場)の予約可能日
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE, 1);
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE + 1, 1);
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE - 1, 1);
-- room_id=7(有楽町)の予約可能日
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE, 7);
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE + 1, 7);
INSERT INTO reservable_room(reserved_date, room_id)
VALUES (CURRENT_DATE - 1, 7);

-- 認証確認用のテストユーザー(password = demo)
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('aaaa', 'Aaa', 'Aaa', 'USER', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK');
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('bbbb', 'Bbb', 'Bbb', 'USER', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK');
INSERT INTO usr (user_id, first_name, last_name, role_name, password)
VALUES ('cccc', 'Ccc', 'Ccc', 'ADMIN', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK');
