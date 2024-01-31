CREATE TABLE `sales` (
    `uuid` INT NOT NULL AUTO_INCREMENT,
    `book_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `sales_date` DATE NOT NULL,
    `quantity` INT NOT NULL,
    `total_cost` INT NOT NULL,
    PRIMARY KEY (`uuid`),
    CONSTRAINT `sales_fk0` FOREIGN KEY (`book_id`) REFERENCES `books`(`id`),
    CONSTRAINT `sales_fk1` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);