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
	supplier_id INT UNSIGNED NOT NULL COMMENT '供应商ID',
	operator INT UNSIGNED NOT NULL COMMENT '操作员(用户)ID',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	update_time DATETIME NOT NULL COMMENT '更新时间',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '入库汇总';



-- 入库明细
CREATE TABLE t_inbound_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总表ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	price FLOAT NOT NULL COMMENT '单价',
	total FLOAT NOT NULL COMMENT '总金额',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '仓库ID',
	state INT DEFAULT(0) COMMENT '入库状态[0:未入库|1:已入库]'
) COMMENT '入库明细';



-- 平台表
CREATE TABLE t_platform(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	platform_name VARCHAR(20) UNIQUE NOT NULL COMMENT '平台名称',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '平台表';



-- 店铺表
CREATE TABLE t_store(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	store_name VARCHAR(20) UNIQUE NOT NULL COMMENT '店铺名称',
	platform_id INT UNSIGNED NOT NULL COMMENT '平台ID',
	state INT DEFAULT(1) COMMENT '店铺状态[0:停业|1:正常]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '店铺表';



-- 物流表
CREATE TABLE t_logistics(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	logistics_name VARCHAR(20) UNIQUE NOT NULL COMMENT '物流名称',
	contacts VARCHAR(20) COMMENT '联系人',
	phone VARCHAR(20) COMMENT '联系电话',
	address VARCHAR(50) COMMENT '网点地址',
	state INT DEFAULT(1) COMMENT '物流状态[0:停用|1:正常]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '物流表';



-- 订单汇总
CREATE TABLE t_order_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	store_id INT UNSIGNED NOT NULL COMMENT '店铺ID',
	order_code VARCHAR(30) NOT NULL COMMENT '订单编码',
	recipient_name VARCHAR(30) NOT NULL COMMENT '收货人姓名',
	recipient_phone VARCHAR(30) NOT NULL COMMENT '收货人手机',
	recipient_address VARCHAR(100) NOT NULL COMMENT '收货人地址',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	payment_time DATETIME NOT NULL COMMENT '付款时间',
	update_time DATETIME NOT NULL COMMENT '更新时间',
	state INT DEFAULT(1) COMMENT '订单状态[0:关闭|1:正常]'
) COMMENT '订单汇总';



-- 订单明细
CREATE TABLE t_order_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	order_id INT UNSIGNED NOT NULL COMMENT '订单ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	price FLOAT NOT NULL COMMENT '单价',
	total FLOAT NOT NULL COMMENT '金额',
	state INT DEFAULT(1) COMMENT '子订单状态[0:退款|1:正常]'
) COMMENT '订单明细';



-- 出库汇总
CREATE TABLE t_outbound_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	outbound_code VARCHAR(30) NOT NULL COMMENT '出库编码',
	operator INT DEFAULT(0) COMMENT '操作员(用户ID)',
	create_time DATETIME NOT NULL COMMENT '创建时间',
	execute_time DATETIME COMMENT '执行时间',
	outbound_time DATETIME COMMENT '出库时间',
	execute INT DEFAULT(0) COMMENT '执行状态[0:未执行|1:已执行]',
	outbound INT DEFAULT(0) COMMENT '出库状态[0:未出库|1:已出库]',
	state INT DEFAULT(1) COMMENT '单据状态[0:关闭|1:正常]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '备注'
) COMMENT '出库汇总';



-- 出库明细
CREATE TABLE t_outbound_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '库位ID',
	state INT DEFAULT(1) COMMENT '子单据状态[0:关闭|1:正常]'
) COMMENT '出库明细';



-- 摘要
CREATE TABLE t_abstract(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	abstract_name VARCHAR(20) NOT NULL COMMENT '摘要名称'
) COMMENT '摘要';



-- 开票单汇总
CREATE TABLE t_invoice_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	order_id INT UNSIGNED NOT NULL COMMENT '订单ID',
	outbound_id INT UNSIGNED NOT NULL COMMENT '出库ID',
	abstract_id INT UNSIGNED NOT NULL COMMENT '摘要ID',
	logistics_id INT UNSIGNED DEFAULT(0) COMMENT '物流ID',
	logistics_code VARCHAR(30) COMMENT '物流单号',
	invoice INT DEFAULT(0) COMMENT '开票状态[0:未开票|1:已开票]',
	invoice_time DATETIME COMMENT '开票时间',
	state INT DEFAULT(1) COMMENT '单据状态[0:关闭|1:正常]'
) COMMENT '开票单汇总';



-- 移库单
CREATE TABLE t_relocation_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	relocation_code VARCHAR(30) NOT NULL COMMENT '编码',
	create_time DATETIME COMMENT '创建时间',
	update_time DATETIME COMMENT '更新时间',
	state INT DEFAULT(1) COMMENT '单据状态[0:关闭|1:正常]',
	remarks VARCHAR(200) COMMENT '备注'
) COMMENT '移库汇总';



-- 移库明细
CREATE TABLE t_relocation_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	out_warehouse_id INT UNSIGNED NOT NULL COMMENT '转出库位ID',
	in_warehouse_id INT UNSIGNED NOT NULL COMMENT '转入库位ID',
	state INT DEFAULT(1) COMMENT '子单据状态[0:未转移|1:已移库]'
) COMMENT '移库明细';



-- 报损单
CREATE TABLE t_damage_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	damage_code VARCHAR(30) NOT NULL COMMENT '编码',
	create_time DATETIME COMMENT '创建时间',
	update_time DATETIME COMMENT '更新时间',
	state INT DEFAULT(1) COMMENT '单据状态[0:关闭|1:正常]',
	remarks VARCHAR(200) COMMENT '备注'
) COMMENT '报损单';



-- 报损明细
CREATE TABLE t_damage_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	price FLOAT NOT NULL COMMENT '单价(加权平均价)',
	total FLOAT NOT NULL COMMENT '金额',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '库位ID'
) COMMENT '报损明细';



-- 报溢单
CREATE TABLE t_overflow_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	overflow_code VARCHAR(30) NOT NULL COMMENT '编码',
	create_time DATETIME COMMENT '创建时间',
	update_time DATETIME COMMENT '更新时间',
	state INT DEFAULT(1) COMMENT '单据状态[0:关闭|1:正常]',
	remarks VARCHAR(200) COMMENT '备注'
) COMMENT '报溢单';



-- 报溢明细
CREATE TABLE t_overflow_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '汇总ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '商品ID',
	quantity FLOAT NOT NULL COMMENT '数量',
	price FLOAT NOT NULL COMMENT '单价(加权平均价)',
	total FLOAT NOT NULL COMMENT '金额',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '库位ID'
) COMMENT '报溢明细';



-- 动态库存视图
CREATE VIEW v_dynamic_inventory AS
SELECT
    goods_id,
    warehouse_id,
    SUM(inbound_stock) AS total_inbound,
    SUM(outbound_stock) AS total_outbound,
    SUM(inbound_stock) - SUM(outbound_stock) AS inventory
FROM
    (
        SELECT goods_id, warehouse_id, quantity AS inbound_stock, 0 AS outbound_stock FROM t_inbound_detail WHERE state=1
        UNION ALL
        SELECT goods_id, warehouse_id, 0 AS inbound_stock, quantity AS outbound_stock FROM t_outbound_detail WHERE state=1
        UNION ALL
        SELECT goods_id, in_warehouse_id AS warehouse_id, quantity AS inbound_stock, 0 AS outbound_stock FROM t_relocation_detail WHERE state=1
        UNION ALL
        SELECT goods_id, out_warehouse_id AS warehouse_id, 0 AS inbound_stock, quantity AS outbound_stock FROM t_relocation_detail WHERE state=1
        UNION ALL
        SELECT goods_id, warehouse_id, quantity AS inbound_stock, 0 AS outbound_stock FROM t_overflow_detail WHERE (SELECT state FROM t_overflow_summary WHERE id=summary_id)=1
        UNION ALL
        SELECT goods_id, warehouse_id, 0 AS inbound_stock, quantity AS outbound_stock FROM t_damage_detail WHERE (SELECT state FROM t_damage_summary WHERE id=summary_id)=1
    ) stocks
GROUP BY
    goods_id, warehouse_id;
-- ALTER VIEW v_dynamic_inventory COMMENT '动态库存视图(实际入库-实际出库(包含待出库占用))';



-- 动态任务视图
CREATE VIEW v_pending_outbound AS
SELECT id, outbound_code, create_time, remarks
FROM t_outbound_summary
WHERE execute = 0 AND state = 1;
-- ALTER VIEW v_pending_outbound COMMENT '动态任务视图(待执行)';


