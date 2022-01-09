-- 用户信息表
CREATE TABLE user_info(
  id integer primary key autoincrement not null, -- 主键
  user_id text not null, --账户ID
  user_name text not null, -- 账户名
  user_pwd text not null, --账户访问的密码
  white_ips text default '' -- 访问ip白名单
);

INSERT INTO user_info (id, user_id, user_name, user_pwd) VALUES (null, 'xck', 'xck', 'xck123456');

-- 用户消费类型
CREATE TABLE consume_type(
  id integer primary key autoincrement not null, -- 主键
  type_id integer not null, -- 类型id
  type_name text not null, -- 类型名称
  parent_type_id integer not null -- 父类id
);

INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 1, '生活用品', 0);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 2, '出行', 0);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3, '食品', 0);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4, '住房', 0);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 5, '行为', 0);

INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 1001, '外卖', 3);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 1002, '餐馆', 3);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 1003, '自制', 3);

INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 2001, '地铁', 2);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 2002, '公交', 2);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 2003, '飞机', 2);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 2004, '步行', 2);

INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3001, '游戏', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3002, '学习', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3003, '睡觉', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3004, '运动', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3005, '看手机', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3006, '吃喝拉撒', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3007, '家务', 5);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 3008, '工作', 5);

INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4001, '日用品', 1);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4002, '电子产品', 1);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4003, '书籍', 1);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4004, '衣物', 1);
INSERT INTO consume_type (id, type_id, type_name, parent_type_id) VALUES (null, 4005, '家电', 1);

-- 用户购买信息表
CREATE TABLE user_consume_product_record(
  id integer primary key autoincrement not null, -- 主键
  user_id text not null, --对应user_info的user_id
  consume_id text not null, --消费id可
  product_name text not null, -- 商品名称
  consume_time text not null, -- 购买时间
  consume_number integer not null, -- 消费数量
  price real default 0, -- 商品单价
  comment text default '', -- 说明
  consume_type integer not null, -- 消费类型
  insert_time text not null -- 插入时间
);

-- 用户使用时间信息表
CREATE TABLE user_consume_time_record(
  id integer primary key autoincrement not null, -- 主键
  user_id text not null, --对应user_info的user_id
  consume_id text not null, --消费id
  product_name text not null, -- 商品名称
  start_time text not null, -- 开始时间
  end_time text not null, -- 结束时间
  use_time integer not null, -- 总耗时，单位分钟
  comment text default '', -- 说明
  consume_type integer not null, -- 消费类型
  insert_time text not null -- 插入时间
);