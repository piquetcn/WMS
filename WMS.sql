DROP DATABASE WMS;

CREATE DATABASE WMS;
USE WMS;



-- 用户表
CREATE TABLE t_user(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	username VARCHAR(20) UNIQUE NOT NULL COMMENT '用户名',
	password VARCHAR(32) COMMENT '密码',
	role VARCHAR(10) DEFAULT('user') COMMENT '身份',
	nickname VARCHAR(20) UNIQUE COMMENT '昵称',
	email VARCHAR(128) DEFAULT('') COMMENT '邮箱',
	phone VARCHAR(11) DEFAULT('') COMMENT '手机号',
	user_pic VARCHAR(128) DEFAULT('') COMMENT '头像',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_time DATETIME NOT NULL COMMENT '修改时间',
	state INT DEFAULT(1) COMMENT '用户状态[0:停用|1:正常]'
) COMMENT '用户表';



-- 供应商表
CREATE TABLE t_supplier(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	supplier_name VARCHAR(50) UNIQUE NOT NULL COMMENT '供应商名称',
	supplier_addr VARCHAR(100) DEFAULT('') COMMENT '供应商地址',
	contacts VARCHAR(20) DEFAULT('') COMMENT '联系人',
	phone VARCHAR(20) NOT NULL COMMENT '联系电话',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '供应商表';



-- 商品分类表
CREATE TABLE t_category(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	category_name VARCHAR(20) UNIQUE NOT NULL COMMENT '分类名称',
	category_acronyms VARCHAR(20) UNIQUE NOT NULL COMMENT '分类简拼'
) COMMENT '商品分类表';



-- 商品表
CREATE TABLE t_goods(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	goods_code VARCHAR(20) UNIQUE NOT NULL COMMENT '商品编码',
	goods_name VARCHAR(50) NOT NULL COMMENT '商品名称',
	goods_spec VARCHAR(20) NOT NULL COMMENT '商品规格',
	goods_unit VARCHAR(10) NOT NULL COMMENT '商品单位',
	category_id INT UNSIGNED NOT NULL COMMENT '商品分类ID',
	goods_pic VARCHAR(128) DEFAULT('') COMMENT '商品图片',
	manufacturer VARCHAR(128) DEFAULT('') COMMENT '生产厂家',
	min_inventory INT NOT NULL COMMENT '库存预警',
	state INT DEFAULT(1) COMMENT '商品状态[0:已下架|1:在售中]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '商品表';



-- 库位表
CREATE TABLE t_warehouse(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	warehouse_code VARCHAR(20) UNIQUE NOT NULL COMMENT '仓库编码',
	warehouse_name VARCHAR(50) UNIQUE NOT NULL COMMENT '仓库名称',
	warehouse_size VARCHAR(20) DEFAULT('') COMMENT '仓库规格',
	warehouse_pic VARCHAR(128) DEFAULT('') COMMENT '仓库图片',
	warehouse_location VARCHAR(50) DEFAULT('') COMMENT '仓库位置',
	state INT DEFAULT(1) COMMENT '仓库状态[0:停用|1:正常]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '库位表';



-- 入库汇总
CREATE TABLE t_inbound_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	inbound_code VARCHAR(20) UNIQUE NOT NULL COMMENT '入库单号',
	-- supplier_name VARCHAR(50) NOT NULL COMMENT '供应商名称',
	supplier_id INT UNSIGNED NOT NULL COMMENT '供应商ID',
	-- operator VARCHAR(20) NOT NULL COMMENT '操作员',
	operator INT UNSIGNED NOT NULL COMMENT '操作员(用户)ID',
	added_time DATETIME NOT NULL COMMENT '创建时间',
	update_time DATETIME NOT NULL COMMENT '更新时间',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
-- 	CONSTRAINT fk_supplier_name FOREIGN KEY (supplier_name) REFERENCES t_supplier(supplier_name),
-- 	CONSTRAINT fk_operator FOREIGN KEY (operator) REFERENCES t_user(nickname)
) COMMENT '入库汇总';



-- 入库明细
CREATE TABLE t_inbound_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总表ID',
	-- goods_code VARCHAR(20) NOT NULL COMMENT '商品编码',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	price FLOAT NOT NULL COMMENT '单价',
	total FLOAT NOT NULL COMMENT '总金额',
	-- warehouse_code VARCHAR(20) NOT NULL COMMENT '仓库编码',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '仓库ID',
	state INT DEFAULT(0) COMMENT '入库状态[0:未入库|1:已入库]'
-- 	CONSTRAINT fk_goods_code FOREIGN KEY (goods_code) REFERENCES t_goods(goods_code),
-- 	CONSTRAINT fk_warehouse_code FOREIGN KEY (warehouse_code) REFERENCES t_warehouse(warehouse_code)
) COMMENT '入库明细';
