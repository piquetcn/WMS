INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC01', '库位1', '10m*10m*10m', '', 'xx路xx号xx楼01室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC02', '库位2', '10m*10m*10m', '', 'xx路xx号xx楼02室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC03', '库位3', '10m*10m*10m', '', 'xx路xx号xx楼03室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC04', '库位4', '10m*10m*10m', '', 'xx路xx号xx楼04室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC05', '库位5', '10m*10m*10m', '', 'xx路xx号xx楼05室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC06', '库位6', '10m*10m*10m', '', 'xx路xx号xx楼06室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC07', '库位7', '10m*10m*10m', '', 'xx路xx号xx楼07室', 1, '');

INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('只因水果', '', '蔡徐坤', '10086', '');
INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('强子百货', '', '刘华强', '10010', '');
INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('混元服饰', '', '马保国', '10000', '');

INSERT INTO t_category (category_name, category_acronyms) VALUES ('水果', 'SG');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('干果', 'GG');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('服装', 'FZ');

INSERT INTO t_platform (platform_name) VALUES ('天猫');
INSERT INTO t_platform (platform_name) VALUES ('京东');
INSERT INTO t_platform (platform_name) VALUES ('拼多多');
INSERT INTO t_platform (platform_name) VALUES ('抖音');

INSERT INTO t_store (store_name, platform_id) VALUE ('郝哥天猫旗舰店', 1);
INSERT INTO t_store (store_name, platform_id) VALUE ('郝哥水果天猫专卖店', 1);
INSERT INTO t_store (store_name, platform_id) VALUE ('郝哥京东自营旗舰店', 2);
INSERT INTO t_store (store_name, platform_id) VALUE ('郝哥干果旗舰店', 3);
INSERT INTO t_store (store_name, platform_id) VALUE ('郝哥服饰', 4);

INSERT INTO t_logistics (logistics_name) VALUES ('顺丰快递');
INSERT INTO t_logistics (logistics_name) VALUES ('中通快递');
INSERT INTO t_logistics (logistics_name) VALUES ('申通快递');
INSERT INTO t_logistics (logistics_name) VALUES ('圆通快递');
INSERT INTO t_logistics (logistics_name) VALUES ('韵达快递');
INSERT INTO t_logistics (logistics_name) VALUES ('极兔快递');
INSERT INTO t_logistics (logistics_name) VALUES ('邮政EMS');