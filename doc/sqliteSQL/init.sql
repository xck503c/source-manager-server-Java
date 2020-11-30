-- 用户信息表
CREATE TABLE user_info(
  id int primary key autoincrement not null, -- 主键
  user_id varchar(30) not null, --账户ID
  user_name varchar(30) not null, -- 账户名
  user_pwd varchar(100) not null, --账户访问的密码
);

INSERT INTO user_info VALUES (null, 'xck', 'xck', 'xck123456');

-- 用户资源表
CREATE TABLE user_source_list(
  id int primary key autoincrement not null, -- 主键
  user_id varchar(30) not null, --对应user_info的user_id
  source_path varchar(100) not null, -- 资源所在的文件路径
  source_name varchar(100) not null, -- 资源名
  source_type int not null, -- 资源类型，0-其他，1-文本，2-音频，3-视频，4-压缩文件，5-文件夹
  parent_id int default 0 not null, -- 表示父资源(对应父目录)，0表示基础路径
  is_source_encrypt tinyint default 1 not null, -- 资源访问加密控制，0-开启，1-关闭
  source_pwd varchar(100) default '' not null, -- 资源的访问密码
);