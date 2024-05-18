# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 11.3.2-MariaDB-1:11.3.2+maria~ubu2204)
# Database: zero
# Generation Time: 2024-05-12 10:05:41 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group`;

CREATE TABLE `group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_name` text NOT NULL,
  `class_num` text NOT NULL,
  `password` text DEFAULT NULL,
  `deadline` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `group` WRITE;
/*!40000 ALTER TABLE `group` DISABLE KEYS */;

INSERT INTO `group` (`id`, `class_name`, `class_num`, `password`, `deadline`, `created_at`, `updated_at`)
VALUES
	(1,'인터넷과웹기초','101','1234','2024-07-30 00:00:00','2024-05-11 16:04:39','2024-05-11 16:04:39'),
	(2,'인터넷과웹기초','201','1234','2024-07-15 00:00:00','2024-05-11 16:04:39','2024-05-11 16:04:39'),
	(3,'인터넷과웹기초','301','1234','2024-08-31 00:00:00','2024-05-11 16:04:39','2024-05-11 16:04:39'),
	(4,'인터넷과웹기초','401','1234','2024-07-30 00:00:00','2024-05-11 16:05:06','2024-05-11 16:05:06'),
	(5,'인터넷과웹기초','501','1234','2024-07-15 00:00:00','2024-05-11 16:05:06','2024-05-11 16:05:06'),
	(6,'인터넷과웹기초','601','1234','2024-08-31 00:00:00','2024-05-11 16:05:06','2024-05-11 16:05:06');

/*!40000 ALTER TABLE `group` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table group_member
# ------------------------------------------------------------

DROP TABLE IF EXISTS `group_member`;

CREATE TABLE `group_member` (
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `FK_group_TO_group_member_1` (`group_id`),
  KEY `FK_user_TO_group_member_1` (`user_id`),
  CONSTRAINT `FK_group_TO_group_member_1` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`),
  CONSTRAINT `FK_user_TO_group_member_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(255) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `title` text NOT NULL,
  `desc` text DEFAULT NULL,
  `sub_domain` text NOT NULL,
  `static_file_src` text NOT NULL,
  `dynamic_file_src` text DEFAULT NULL,
  `role` text DEFAULT 'private',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_project_sub_domain` (`sub_domain`) USING HASH,
  KEY `fk_project_group` (`group_id`),
  KEY `fk_project_user` (`user_id`),
  CONSTRAINT `fk_project_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`),
  CONSTRAINT `fk_project_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL,
  `student_id` text NOT NULL,
  `name` text NOT NULL,
  `password` text NOT NULL,
  `user_state` text NOT NULL,
  `user_role` text NOT NULL DEFAULT 'Regular User',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`(255)),
  UNIQUE KEY `student_id` (`student_id`(255))
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `email`, `student_id`, `name`, `password`, `user_state`, `user_role`, `created_at`, `updated_at`)
VALUES
	(16,'yungs0917@naver.com','201924515','교수','{bcrypt}$2a$10$hkGvGEJn4CXVus5VqIWHbuDUqDesPpyY/2nuq3/uQwOvSsgsfAM6.','Active','Admin','2024-05-11 14:56:07','2024-05-12 10:04:15'),
	(30,'juniper0917@gmail.com','201924516','유승훈','{bcrypt}$2a$10$P6R9ytJVvAqKe6MLKk6OeOgJxChar21e7z/GJxeA58W.ZowUFPvBW','Active','RegularUser','2024-05-12 07:43:44','2024-05-12 07:43:55');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
