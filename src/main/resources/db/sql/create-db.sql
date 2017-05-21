CREATE SCHEMA IF NOT EXISTS bathroom;

USE bathroom;

CREATE TABLE IF NOT EXISTS `sitting` (
    `id` int(11) AUTO_INCREMENT NOT NULL,
    `start_time` datetime NOT NULL,
    `end_time` datetime NOT NULL,
    PRIMARY KEY(`id`)
)