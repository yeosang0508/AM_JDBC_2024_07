DROP DATABASE IF EXISTS `am_jdbc_2024_07`;

CREATE DATABASE `am_jdbc_2024_07`;

USE `am_jdbc_2024_07`;

SHOW TABLES;

CREATE TABLE `article`(
                          id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                          regDate DATETIME NOT NULL,
                          updateDate DATETIME NOT NULL,
                          title CHAR(200) NOT NULL,
                          `body` CHAR(200) NOT NULL
);

DESC article;

CREATE TABLE `member`(
                         id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         loginId CHAR(30) NOT NULL,
                         loginPw CHAR(200) NOT NULL,
                         `name` CHAR(100) NOT NULL
);

SELECT *
FROM `member`;