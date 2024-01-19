CREATE TABLE `books` (
    `id` INT NOT NULL AUTO_INCREMENT UNIQUE,
    `nome` VARCHAR(255) NOT NULL,
    `autor` VARCHAR(255) NOT NULL,
    `quantidade` INT NOT NULL,
    PRIMARY KEY (`id`)
);