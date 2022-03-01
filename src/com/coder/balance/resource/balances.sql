DROP DATABASE IF EXISTS balance_fx;
CREATE DATABASE IF EXISTS balance_fx;
USE balance_fx;

DROP TABLE IF EXISTS `balances`;
CREATE TABLE `balances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `b_type` varchar(45) NOT NULL,
  `b_date` date NOT NULL,
  `amount` int NOT NULL,
  `category` varchar(45) NOT NULL,
  `remark` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

TRUNCATE TABLE balances ;
