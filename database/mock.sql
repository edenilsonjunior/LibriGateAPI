INSERT INTO address (id, zip_code, city, complement, district, state, street, number) VALUES
(1, '01000000', 'São Paulo', 'Apto 101', 'Centro', 'SP', 'Rua das Flores', 123),
(2, '22000000', 'Rio de Janeiro', 'Sala 202', 'Copacabana', 'RJ', 'Avenida Atlântica', 456),
(3, '30100000', 'Belo Horizonte', NULL, 'Savassi', 'MG', 'Rua Pernambuco', 789),
(4, '80000000', 'Curitiba', 'Bloco B', 'Batel', 'PR', 'Rua XV de Novembro', 101);


INSERT INTO person (cpf, address_id, first_name, last_name, birth_date, gender, active, login, password, telephone) VALUES
('11491821051', 1, 'Carlos', 'Silva', '1985-06-15', 'M', TRUE, 'carlos.silva', 'senha123', '(11) 98765-4321'),
('93597708099', 2, 'Fernanda', 'Oliveira', '1990-11-22', 'F', TRUE, 'fernanda.oliveira', 'senha456', '(21) 99876-5432'),
('80013547097', 3, 'João', 'Pereira', '1978-03-10', 'M', FALSE, 'joao.pereira', 'senha789', '(31) 91234-5678'),
('12345678901', 4, 'Ana', 'Costa', '1995-07-20', 'F', TRUE, 'ana.costa', 'senhaabc', '(41) 98765-4321');


INSERT INTO employee (cpf, role) VALUES
('11491821051', 'Gerente'),
('93597708099', 'Vendedor');


INSERT INTO customer (cpf, registration_date) VALUES
('80013547097', '2024-01-15'),
('12345678901', '2024-02-20');


INSERT INTO book (isbn, title, publisher, description, category, launch_date, edition) VALUES
('9780156012195', 'O Pequeno Príncipe', 'Editora X', 'Um conto filosófico sobre um jovem príncipe e suas aventuras.', 'Infantil', '1943-04-06', '1'),
('9780545582889', 'Harry Potter e a Pedra Filosofal', 'Editora Y', 'A primeira aventura do jovem mago Harry Potter na escola de magia Hogwarts.', 'Fantasia', '1997-06-26', '1');


INSERT INTO book_authors_name (book_isbn, authors) VALUES
('9780156012195', 'Antoine de Saint-Exupéry'),
('9780545582889', 'J.K. Rowling');


INSERT INTO restock (id, restock_date, price, employee_cpf) VALUES
(1, '2024-07-01', 320.00, '11491821051'),
(2, '2024-07-15', 220.00, '11491821051'),
(3, '2024-08-01', 200.00, '11491821051');


INSERT INTO book_copy (id, isbn, copy_number, price, restock_id, buy_id, status) VALUES
(1, '9780156012195', 1, 64.00, 1, NULL, 'AVAILABLE'),
(2, '9780156012195', 2, 64.00, 1, NULL, 'AVAILABLE'),
(3, '9780156012195', 3, 64.00, 1, NULL, 'AVAILABLE'),
(4, '9780156012195', 4, 64.00, 1, NULL, 'AVAILABLE'),
(5, '9780156012195', 5, 64.00, 1, NULL, 'AVAILABLE'),
(6, '9780545582889', 1, 62.50, 2, NULL, 'AVAILABLE'),
(7, '9780545582889', 2, 62.50, 2, NULL, 'AVAILABLE'),
(8, '9780545582889', 3, 62.50, 2, NULL, 'AVAILABLE'),
(9, '9780545582889', 4, 62.50, 2, NULL, 'AVAILABLE'),
(10, '9780156012195', 6, 70.00, 3, NULL, 'AVAILABLE'),
(11, '9780156012195', 7, 70.00, 3, NULL, 'AVAILABLE'),
(12, '9780545582889', 5, 60.00, 3, NULL, 'AVAILABLE');


INSERT INTO rent (id, rent_date, devolution_date, customer_cpf, given_back_at, status) VALUES
(1, '2024-09-02', '2024-09-20', '80013547097', '2024-07-20', 'RETURNED'),
(2, '2024-09-02', '2024-09-20', '80013547097', '2024-08-01', 'RETURNED'),
(3, '2024-09-02', '2024-09-20', '12345678901', NULL, 'RENTED');


INSERT INTO rent_book(rent_id, book_copy_id) VALUES
(1, 1),
(1, 6),
(2, 2),
(3, 3),
(3, 7);

UPDATE book_copy SET status = 'RENTED' WHERE id = 7;


INSERT INTO buy (id, customer_cpf, buy_date, due_date, paid_at, status, total_price) VALUES
(1, '80013547097', '2024-09-03', '2024-09-10', '2024-07-04', 'APPROVED', 126.50),
(2, '80013547097', '2024-09-03', '2024-09-10', NULL, 'PENDING', 60.00);

UPDATE book_copy SET buy_id = 1, status = 'SOLD' WHERE id = 5;
UPDATE book_copy SET buy_id = 1, status = 'SOLD' WHERE id = 8;
UPDATE book_copy SET buy_id = 2, status = 'SOLD' WHERE id = 12;
UPDATE book_copy SET buy_id = 2, status = 'SOLD' WHERE id = 2;


INSERT INTO review (id, description, rating, book_isbn, customer_id) VALUES
(1, 'Excelente livro, muito inspirador.', 5, '9780156012195', '80013547097'),
(2, 'Muito bom, mas com alguns clichês.', 4, '9780545582889', '80013547097'),
(3, 'Um clássico que todo mundo deveria ler.', 5, '9780156012195', '12345678901');
