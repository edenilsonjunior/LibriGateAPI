-- Inserção de endereços
INSERT INTO address (id, city, complement, district, state, street, zip_code, number)
VALUES 
(1, 'São Paulo', 'Apt 202', 'Centro', 'SP', 'Rua Augusta', '01304000', '123'),
(2, 'Rio de Janeiro', 'Casa', 'Copacabana', 'RJ', 'Avenida Atlântica', '22070001', '456');


-- Inserção de pessoas
INSERT INTO person (active, birth_date, address_id, cpf, first_name, gender, last_name, login, password, telephone)
VALUES 
(1, '1980-03-10', 1, '12345678909', 'Ana', 'Female', 'Pereira', 'ana.pereira', 'SecurePass2024!', '+5511998765432'),
(1, '1990-07-22', 2, '98765432100', 'João', 'Male', 'Silva', 'joao.silva', 'MyStrongPass2024!', '+5521998765432');


-- Inserção de funcionários
INSERT INTO employee (cpf, role)
VALUES ('12345678909', 'Supervisor'),
       ('98765432100', 'Vendedor');


-- Inserção de clientes
INSERT INTO customer (cpf, registration_date)
VALUES ('98765432100', CURRENT_DATE);


-- Inserção de livros
INSERT INTO book (isbn, title, publisher, description, category, launch_date, edition)
VALUES ('9780134685991', 'Effective Java', 'Addison-Wesley', 'A comprehensive guide to best practices in Java.', 'Programming', '2018-01-11', 3);


-- Inserção de autores de livros
INSERT INTO book_authors_name (book_isbn, authors)
VALUES ('9780134685991', 'Joshua Bloch'),
       ('9780134685991', 'Ted Neward');


-- Inserção de reabastecimentos
INSERT INTO restock (id, restock_date, price, employee_cpf)
VALUES (1, '2024-08-23', 120.00, '12345678909');


-- Inserção de livros físicos
INSERT INTO book_copy (isbn, copy_number, price, restock_id, buy_id, status)
VALUES ('9780134685991', 1, 30.00, 1, NULL, 'available'),
       ('9780134685991', 2, 30.00, 1, NULL, 'available'),
       ('9780134685991', 3, 30.00, 1, NULL, 'available'),
       ('9780134685991', 4, 30.00, 1, NULL, 'available'),
       ('9780134685991', 5, 30.00, 1, NULL, 'available');


-- Inserção de outro reabastecimento
INSERT INTO restock (id, restock_date, price, employee_cpf)
VALUES (2, '2024-08-23', 60.00, '12345678909');

-- Inserção de livros físicos adicionais
INSERT INTO book_copy (isbn, copy_number, price, restock_id, buy_id, status)
VALUES ('9780134685991', 6, 60.00, 2, NULL, 'available');

