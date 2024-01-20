DROP DATABASE WMS;

CREATE DATABASE WMS;
USE WMS;



-- �û���
CREATE TABLE t_user(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	username VARCHAR(20) UNIQUE NOT NULL COMMENT '�û���',
	password VARCHAR(32) COMMENT '����',
	role VARCHAR(10) DEFAULT('user') COMMENT '���',
	nickname VARCHAR(20) UNIQUE COMMENT '�ǳ�',
	email VARCHAR(128) DEFAULT('') COMMENT '����',
	phone VARCHAR(11) DEFAULT('') COMMENT '�ֻ���',
	user_pic VARCHAR(128) DEFAULT('') COMMENT 'ͷ��',
	create_time DATETIME NOT NULL COMMENT '����ʱ��',
	update_time DATETIME NOT NULL COMMENT '�޸�ʱ��',
	state INT DEFAULT(1) COMMENT '�û�״̬[0:ͣ��|1:����]'
) COMMENT '�û���';



-- ��Ӧ�̱�
CREATE TABLE t_supplier(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	supplier_name VARCHAR(50) UNIQUE NOT NULL COMMENT '��Ӧ������',
	supplier_addr VARCHAR(100) DEFAULT('') COMMENT '��Ӧ�̵�ַ',
	contacts VARCHAR(20) DEFAULT('') COMMENT '��ϵ��',
	phone VARCHAR(20) NOT NULL COMMENT '��ϵ�绰',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '��Ӧ�̱�';



-- ��Ʒ�����
CREATE TABLE t_category(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	category_name VARCHAR(20) UNIQUE NOT NULL COMMENT '��������',
	category_acronyms VARCHAR(20) UNIQUE NOT NULL COMMENT '�����ƴ'
) COMMENT '��Ʒ�����';



-- ��Ʒ��
CREATE TABLE t_goods(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	goods_code VARCHAR(20) UNIQUE NOT NULL COMMENT '��Ʒ����',
	goods_name VARCHAR(50) NOT NULL COMMENT '��Ʒ����',
	goods_spec VARCHAR(20) NOT NULL COMMENT '��Ʒ���',
	goods_unit VARCHAR(10) NOT NULL COMMENT '��Ʒ��λ',
	category_id INT UNSIGNED NOT NULL COMMENT '��Ʒ����ID',
	goods_pic VARCHAR(128) DEFAULT('') COMMENT '��ƷͼƬ',
	manufacturer VARCHAR(128) DEFAULT('') COMMENT '��������',
	min_inventory INT NOT NULL COMMENT '���Ԥ��',
	state INT DEFAULT(1) COMMENT '��Ʒ״̬[0:���¼�|1:������]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '��Ʒ��';



-- ��λ��
CREATE TABLE t_warehouse(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	warehouse_code VARCHAR(20) UNIQUE NOT NULL COMMENT '�ֿ����',
	warehouse_name VARCHAR(50) UNIQUE NOT NULL COMMENT '�ֿ�����',
	warehouse_size VARCHAR(20) DEFAULT('') COMMENT '�ֿ���',
	warehouse_pic VARCHAR(128) DEFAULT('') COMMENT '�ֿ�ͼƬ',
	warehouse_location VARCHAR(50) DEFAULT('') COMMENT '�ֿ�λ��',
	state INT DEFAULT(1) COMMENT '�ֿ�״̬[0:ͣ��|1:����]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '��λ��';



-- ������
CREATE TABLE t_inbound_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	inbound_code VARCHAR(20) UNIQUE NOT NULL COMMENT '��ⵥ��',
	supplier_id INT UNSIGNED NOT NULL COMMENT '��Ӧ��ID',
	operator INT UNSIGNED NOT NULL COMMENT '����Ա(�û�)ID',
	create_time DATETIME NOT NULL COMMENT '����ʱ��',
	update_time DATETIME NOT NULL COMMENT '����ʱ��',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '������';



-- �����ϸ
CREATE TABLE t_inbound_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '���ܱ�ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	price FLOAT NOT NULL COMMENT '����',
	total FLOAT NOT NULL COMMENT '�ܽ��',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '�ֿ�ID',
	state INT DEFAULT(0) COMMENT '���״̬[0:δ���|1:�����]'
) COMMENT '�����ϸ';



-- ƽ̨��
CREATE TABLE t_platform(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	platform_name VARCHAR(20) UNIQUE NOT NULL COMMENT 'ƽ̨����',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT 'ƽ̨��';



-- ���̱�
CREATE TABLE t_store(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	store_name VARCHAR(20) UNIQUE NOT NULL COMMENT '��������',
	platform_id INT UNSIGNED NOT NULL COMMENT 'ƽ̨ID',
	state INT DEFAULT(1) COMMENT '����״̬[0:ͣҵ|1:����]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '���̱�';



-- ������
CREATE TABLE t_logistics(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	logistics_name VARCHAR(20) UNIQUE NOT NULL COMMENT '��������',
	contacts VARCHAR(20) COMMENT '��ϵ��',
	phone VARCHAR(20) COMMENT '��ϵ�绰',
	address VARCHAR(50) COMMENT '�����ַ',
	state INT DEFAULT(1) COMMENT '����״̬[0:ͣ��|1:����]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '������';



-- ��������
CREATE TABLE t_order_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	store_id INT UNSIGNED NOT NULL COMMENT '����ID',
	order_code VARCHAR(30) NOT NULL COMMENT '��������',
	recipient_name VARCHAR(30) NOT NULL COMMENT '�ջ�������',
	recipient_phone VARCHAR(30) NOT NULL COMMENT '�ջ����ֻ�',
	recipient_address VARCHAR(100) NOT NULL COMMENT '�ջ��˵�ַ',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע',
	create_time DATETIME NOT NULL COMMENT '����ʱ��',
	payment_time DATETIME NOT NULL COMMENT '����ʱ��',
	update_time DATETIME NOT NULL COMMENT '����ʱ��',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]'
) COMMENT '��������';



-- ������ϸ
CREATE TABLE t_order_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	order_id INT UNSIGNED NOT NULL COMMENT '����ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	price FLOAT NOT NULL COMMENT '����',
	total FLOAT NOT NULL COMMENT '���',
	state INT DEFAULT(1) COMMENT '�Ӷ���״̬[0:�˿�|1:����]'
) COMMENT '������ϸ';



-- �������
CREATE TABLE t_outbound_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	outbound_code VARCHAR(30) NOT NULL COMMENT '�������',
	operator INT DEFAULT(0) COMMENT '����Ա(�û�ID)',
	create_time DATETIME NOT NULL COMMENT '����ʱ��',
	execute_time DATETIME COMMENT 'ִ��ʱ��',
	outbound_time DATETIME COMMENT '����ʱ��',
	execute INT DEFAULT(0) COMMENT 'ִ��״̬[0:δִ��|1:��ִ��]',
	outbound INT DEFAULT(0) COMMENT '����״̬[0:δ����|1:�ѳ���]',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]',
	remarks VARCHAR(200) DEFAULT('') COMMENT '��ע'
) COMMENT '�������';



-- ������ϸ
CREATE TABLE t_outbound_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '����ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '��λID',
	state INT DEFAULT(1) COMMENT '�ӵ���״̬[0:�ر�|1:����]'
) COMMENT '������ϸ';



-- ժҪ
CREATE TABLE t_abstract(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	abstract_name VARCHAR(20) NOT NULL COMMENT 'ժҪ����'
) COMMENT 'ժҪ';



-- ��Ʊ������
CREATE TABLE t_invoice_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	order_id INT UNSIGNED NOT NULL COMMENT '����ID',
	outbound_id INT UNSIGNED NOT NULL COMMENT '����ID',
	abstract_id INT UNSIGNED NOT NULL COMMENT 'ժҪID',
	logistics_id INT UNSIGNED DEFAULT(0) COMMENT '����ID',
	logistics_code VARCHAR(30) COMMENT '��������',
	invoice INT DEFAULT(0) COMMENT '��Ʊ״̬[0:δ��Ʊ|1:�ѿ�Ʊ]',
	invoice_time DATETIME COMMENT '��Ʊʱ��',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]'
) COMMENT '��Ʊ������';



-- �ƿⵥ
CREATE TABLE t_relocation_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	relocation_code VARCHAR(30) NOT NULL COMMENT '����',
	create_time DATETIME COMMENT '����ʱ��',
	update_time DATETIME COMMENT '����ʱ��',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]',
	remarks VARCHAR(200) COMMENT '��ע'
) COMMENT '�ƿ����';



-- �ƿ���ϸ
CREATE TABLE t_relocation_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '����ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	out_warehouse_id INT UNSIGNED NOT NULL COMMENT 'ת����λID',
	in_warehouse_id INT UNSIGNED NOT NULL COMMENT 'ת���λID',
	state INT DEFAULT(1) COMMENT '�ӵ���״̬[0:δת��|1:���ƿ�]'
) COMMENT '�ƿ���ϸ';



-- ����
CREATE TABLE t_damage_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	damage_code VARCHAR(30) NOT NULL COMMENT '����',
	create_time DATETIME COMMENT '����ʱ��',
	update_time DATETIME COMMENT '����ʱ��',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]',
	remarks VARCHAR(200) COMMENT '��ע'
) COMMENT '����';



-- ������ϸ
CREATE TABLE t_damage_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '����ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	price FLOAT NOT NULL COMMENT '����(��Ȩƽ����)',
	total FLOAT NOT NULL COMMENT '���',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '��λID'
) COMMENT '������ϸ';



-- ���絥
CREATE TABLE t_overflow_summary(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	overflow_code VARCHAR(30) NOT NULL COMMENT '����',
	create_time DATETIME COMMENT '����ʱ��',
	update_time DATETIME COMMENT '����ʱ��',
	state INT DEFAULT(1) COMMENT '����״̬[0:�ر�|1:����]',
	remarks VARCHAR(200) COMMENT '��ע'
) COMMENT '���絥';



-- ������ϸ
CREATE TABLE t_overflow_detail(
	id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
	summary_id INT UNSIGNED NOT NULL COMMENT '����ID',
	goods_id INT UNSIGNED NOT NULL COMMENT '��ƷID',
	quantity FLOAT NOT NULL COMMENT '����',
	price FLOAT NOT NULL COMMENT '����(��Ȩƽ����)',
	total FLOAT NOT NULL COMMENT '���',
	warehouse_id INT UNSIGNED NOT NULL COMMENT '��λID'
) COMMENT '������ϸ';



-- ��̬�����ͼ
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
-- ALTER VIEW v_dynamic_inventory COMMENT '��̬�����ͼ(ʵ�����-ʵ�ʳ���(����������ռ��))';



-- ��̬������ͼ
CREATE VIEW v_pending_outbound AS
SELECT id, outbound_code, create_time, remarks
FROM t_outbound_summary
WHERE execute = 0 AND state = 1;
-- ALTER VIEW v_pending_outbound COMMENT '��̬������ͼ(��ִ��)';


