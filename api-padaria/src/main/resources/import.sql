INSERT INTO tb_category (name) VALUES ('Salgados');
INSERT INTO tb_category (name) VALUES ('Doces');
INSERT INTO tb_category (name) VALUES ('Bebidas');

INSERT INTO tb_product (name, description, price, img_url) VALUES ('Pão Francês', 'Quentinho e crocante', 7.0, 'www.padaria.com');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Pão Sonho', 'Macio e recheado', 4.0, 'www.padaria.com');
INSERT INTO tb_product (name, description, price, img_url) VALUES ('Suco de laranja e morango', 'Frutas frescas ', 7.70, 'www.padaria.com');

INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 3);

INSERT INTO tb_role (authority) VALUES ('Boss');
INSERT INTO tb_role (authority) VALUES ('Admin');
INSERT INTO tb_role (authority) VALUES ('Employe');

INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Nanci', 'Maria', 'nanci@gmail.com', '1234567');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Mario', 'Gonçalves', 'mario@gmail.com', '1234567');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Igor', 'Gonçalves', 'igor@gmail.com', '1234567');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 3);