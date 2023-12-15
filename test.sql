INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC01', '库位1', '10m*10m*10m', '', 'xx路xx号xx楼01室', 1, '');
INSERT INTO t_warehouse (warehouse_code, warehouse_name, warehouse_size, warehouse_pic, warehouse_location, state, remarks)
VALUES ('QC02', '库位2', '10m*10m*10m', '', 'xx路xx号xx楼02室', 1, '');

INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('只因水果', '', '蔡徐坤', '10086', '');
INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('强子百货', '', '刘华强', '10010', '');
INSERT INTO t_supplier (supplier_name, supplier_addr, contacts, phone, remarks) VALUES ('混元服饰', '', '马保国', '10000', '');

INSERT INTO t_category (category_name, category_acronyms) VALUES ('水果', 'SG');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('糖果', 'TG');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('干果', 'GG');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('T恤', 'TX');
INSERT INTO t_category (category_name, category_acronyms) VALUES ('夹克', 'JK');

INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('SG001', '苹果', '5kg', '箱', 1, '', '', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('SG002', '桃子', '3kg', '箱', 1, '', '', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('TG001', '牛奶糖', '1kg', '包', 2, '', '大白兔', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('TG002', '巧克力', '1kg', '盒', 2, '', '徐福记', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('GG001', '花生', '10kg', '袋', 3, '', '', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('GG002', '开心果', '5kg', '袋', 3, '', '', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('TX001', '纯白T恤', 'L', '件', 4, '', '', 50, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('TX002', '纯黑T恤', 'XL', '件', 4, '', '', 50, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('TX003', '红黑格T恤', 'M', '件', 4, '', '', 20, 1, '');
INSERT INTO t_goods (goods_code, goods_name, goods_spec, goods_unit, category_id, goods_pic, manufacturer, min_inventory, state, remarks)
VALUES ('JK001', '机车夹克', 'XXL', '件', 5, '', '', 20, 1, '');

INSERT INTO t_inbound_summary (inbound_code, supplier_id, operator, added_time, update_time, remarks)
VALUES ('QC0100001', 1, 1, now(), now(), '');
INSERT INTO t_inbound_summary (inbound_code, supplier_id, operator, added_time, update_time, remarks)
VALUES ('QC0100002', 2, 1, now(), now(), '');
INSERT INTO t_inbound_summary (inbound_code, supplier_id, operator, added_time, update_time, remarks)
VALUES ('QC0200001', 3, 1, now(), now(), '');
INSERT INTO t_inbound_summary (inbound_code, supplier_id, operator, added_time, update_time, remarks)
VALUES ('QC0100003', 2, 1, now(), now(), '');
INSERT INTO t_inbound_summary (inbound_code, supplier_id, operator, added_time, update_time, remarks)
VALUES ('QC0200002', 3, 1, now(), now(), '');

INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (1, 1, 10, 25, 250, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (1, 2, 10, 24, 240, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (2, 3, 50, 5, 250, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (2, 4, 50, 9, 450, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (3, 7, 50, 6, 300, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (3, 8, 50, 6.5, 325, 1, 0);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (4, 5, 30, 80, 2400, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (4, 6, 30, 60, 1800, 1, 1);
INSERT INTO t_inbound_detail (summary_id, goods_id, quantity, price, total, warehouse_id, state)
VALUES (5, 10, 20, 20, 400, 1, 0);

