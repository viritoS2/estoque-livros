CREATE TABLE sales (
  uuid BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1)) PRIMARY KEY,
  book_id INT NOT NULL,
  user_id INT NOT NULL,
  sales_date DATETIME NOT NULL,
  quantity INT NOT NULL,
  total_cost DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (book_id) REFERENCES livros (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);