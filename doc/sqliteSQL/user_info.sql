CREATE TABLE user_info(
  id int primary key autoincrement not null, -- 主键
  user_id varchar(30) not null, --账户ID
  user_name varchar(30) not null, -- 账户名
);

CREATE TABLE user_source_list(
  id int primary key autoincrement not null, -- 主键
  user_id varchar(30) not null, --对应user_info的user_id
  source_path varchar(100) not null
);