CREATE TABLE sales (
  id INT NOT NULL AUTO_INCREMENT,
  book_id INT NOT NULL,
  user_id INT NOT NULL,
  sales_date DATETIME NOT NULL,
  quantity INT NOT NULL,
  total_cost DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (book_id) REFERENCES livros (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
)