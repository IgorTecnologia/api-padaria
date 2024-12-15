INSERT INTO tb_category (id, name) VALUES (UUID(), 'Salgados');
INSERT INTO tb_category (id, name) VALUES (UUID(), 'Doces');
INSERT INTO tb_category (id, name) VALUES (UUID(), 'Bebidas');

INSERT INTO tb_product (id, name, description, price, img_url) VALUES (UUID(), 'Pão Francês', 'Quentinho e crocante', 7.0, 'www.padaria.com');
INSERT INTO tb_product (id, name, description, price, img_url) VALUES (UUID(), 'Pão Sonho', 'Macio e recheado', 4.0, 'www.padaria.com');
INSERT INTO tb_product (id, name, description, price, img_url) VALUES (UUID(), 'Suco de laranja e morango', 'Frutas frescas ', 7.70, 'www.padaria.com');

SET @category1_id(SELECT id FROM tb_category WHERE name = 'Salgados');
SET @category2_id(SELECT id FROM tb_category WHERE name = 'Doces');
SET @category3_id(SELECT id FROM tb_category WHERE name = 'Bebidas');

SET @product1_id(SELECT id FROM tb_product WHERE name = 'Pão Francês');
SET @product2_id(SELECT id FROM tb_product WHERE name = 'Pão Sonho');
SET @product3_id(SELECT id FROM tb_product WHERE name = 'Suco de laranja e morango');

INSERT INTO tb_product_category (product_id, category_id) VALUES (@product1_id, @category1_id);
INSERT INTO tb_product_category (product_id, category_id) VALUES (@product2_id, @category2_id);
INSERT INTO tb_product_category (product_id, category_id) VALUES (@product3_id, @category3_id);

INSERT INTO tb_role (id, authority) VALUES (UUID(), 'Boss');
INSERT INTO tb_role (id, authority) VALUES (UUID(), 'Admin');
INSERT INTO tb_role (id, authority) VALUES (UUID(), 'Seller');

INSERT INTO tb_user (id, first_name, last_name, email, password) VALUES (UUID(), 'Nanci', 'Maria', 'nanci@gmail.com', '1234567');
INSERT INTO tb_user (id, first_name, last_name, email, password) VALUES (UUID(), 'Mario', 'Gonçalves', 'mario@gmail.com', '1234567');
INSERT INTO tb_user (id, first_name, last_name, email, password) VALUES (UUID(), 'Igor', 'Gonçalves', 'igor@gmail.com', '1234567');
INSERT INTO tb_user (id, first_name, last_name, email, password) VALUES (UUID(), 'Vitória', 'Gonçalves', 'vitória@gmail.com', '1234567');

SET @role1_id(SELECT id FROM tb_role WHERE authority = 'Boss');
SET @role2_id(SELECT id FROM tb_role WHERE authority = 'Admin');
SET @role3_id(SELECT id FROM tb_role WHERE authority = 'Seller');

SET @user1_id(SELECT id FROM tb_user WHERE first_name = 'Nanci');
SET @user2_id(SELECT id FROM tb_user WHERE first_name = 'Mario');
SET @user3_id(SELECT id FROM tb_user WHERE first_name = 'Igor');
SET @user4_id(SELECT id FROM tb_user WHERE first_name = 'Vitória');

INSERT INTO tb_user_role (user_id, role_id) VALUES (@user1_id, @role1_id);
INSERT INTO tb_user_role (user_id, role_id) VALUES (@user2_id, @role2_id);
INSERT INTO tb_user_role (user_id, role_id) VALUES (@user3_id, @role3_id);
INSERT INTO tb_user_role (user_id, role_id) VALUES (@user4_id, @role3_id);
