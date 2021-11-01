/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.26 : Database - mvcdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mvcdb` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mvcdb`;

/*Table structure for table `class_grade` */

DROP TABLE IF EXISTS `class_grade`;

CREATE TABLE `class_grade` (
  `id` int NOT NULL AUTO_INCREMENT,
  `grade_name` varchar(10) NOT NULL DEFAULT '',
  `class_name` varchar(10) NOT NULL DEFAULT '',
  `classroom_real_id` varchar(10) NOT NULL DEFAULT '',
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_grade_class` (`school_name`,`grade_name`,`class_name`),
  KEY `idx_school_classroom` (`school_name`,`classroom_real_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

/*Data for the table `class_grade` */

insert  into `class_grade`(`id`,`grade_name`,`class_name`,`classroom_real_id`,`school_name`) values (1,'一年级','一班','101','青青草原第一小学'),(2,'一年级','二班','102','青青草原第一小学'),(3,'二年级','三班','203','青青草原第一小学'),(4,'二年级','三班','203','熊森林第一小学');

/*Table structure for table `class_grade_teacher` */

DROP TABLE IF EXISTS `class_grade_teacher`;

CREATE TABLE `class_grade_teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `class_grade_id` int DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_grade_class_teacher` (`school_name`,`class_grade_id`,`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;

/*Data for the table `class_grade_teacher` */

insert  into `class_grade_teacher`(`id`,`class_grade_id`,`teacher_id`,`school_name`) values (21,4,11,'熊森林第一小学'),(22,4,12,'熊森林第一小学'),(23,4,13,'熊森林第一小学'),(1,1,1,'青青草原第一小学'),(2,1,2,'青青草原第一小学'),(3,1,3,'青青草原第一小学'),(4,1,4,'青青草原第一小学'),(5,1,5,'青青草原第一小学'),(6,1,6,'青青草原第一小学'),(7,1,7,'青青草原第一小学'),(8,1,8,'青青草原第一小学'),(9,1,9,'青青草原第一小学'),(10,1,10,'青青草原第一小学'),(16,2,6,'青青草原第一小学'),(17,2,7,'青青草原第一小学'),(18,2,8,'青青草原第一小学'),(19,2,9,'青青草原第一小学'),(20,2,10,'青青草原第一小学'),(11,3,1,'青青草原第一小学'),(12,3,2,'青青草原第一小学'),(13,3,3,'青青草原第一小学'),(14,3,4,'青青草原第一小学'),(15,3,5,'青青草原第一小学');

/*Table structure for table `classroom` */

DROP TABLE IF EXISTS `classroom`;

CREATE TABLE `classroom` (
  `id` int NOT NULL AUTO_INCREMENT,
  `real_id` varchar(10) NOT NULL DEFAULT '',
  `grade_name` varchar(10) NOT NULL DEFAULT '',
  `class_name` varchar(10) NOT NULL DEFAULT '',
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_real_id` (`school_name`,`real_id`),
  KEY `idx_school_grade_class` (`school_name`,`grade_name`,`class_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

/*Data for the table `classroom` */

insert  into `classroom`(`id`,`real_id`,`grade_name`,`class_name`,`school_name`) values (1,'000','','','青青草原第一小学'),(2,'101','一年级','一班','青青草原第一小学'),(3,'102','一年级','二班','青青草原第一小学'),(4,'203','二年级','三班','青青草原第一小学'),(5,'203','二年级','三班','熊森林第一小学');

/*Table structure for table `school` */

DROP TABLE IF EXISTS `school`;

CREATE TABLE `school` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

/*Data for the table `school` */

insert  into `school`(`id`,`name`) values (2,'熊森林第一小学'),(1,'青青草原第一小学');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `real_id` varchar(10) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `grade_name` varchar(10) NOT NULL DEFAULT '',
  `class_name` varchar(10) NOT NULL DEFAULT '',
  `classroom_real_id` varchar(10) NOT NULL DEFAULT '',
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_garde_class_realid` (`school_name`,`grade_name`,`class_name`,`real_id`),
  KEY `idx_school_classroom_real_id` (`school_name`,`classroom_real_id`,`real_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

/*Data for the table `student` */

insert  into `student`(`id`,`real_id`,`name`,`grade_name`,`class_name`,`classroom_real_id`,`school_name`) values (1,'2021010101','卞彤怡','一年级','一班','101','青青草原第一小学'),(2,'2021010102','苏晓婷','一年级','一班','101','青青草原第一小学'),(3,'2021010103','平梦','一年级','一班','101','青青草原第一小学'),(4,'2021010104','姚旭','一年级','一班','101','青青草原第一小学'),(5,'2021010105','施清','一年级','一班','101','青青草原第一小学'),(6,'2021010201','计允宁','一年级','二班','102','青青草原第一小学'),(7,'2021010202','窦茹德','一年级','二班','102','青青草原第一小学'),(8,'2021010203','盛萱渊','一年级','二班','102','青青草原第一小学'),(9,'2021010204','李楠清','一年级','二班','102','青青草原第一小学'),(10,'2021010205','邹艺','一年级','二班','102','青青草原第一小学'),(11,'2021020301','褚憧珠','二年级','三班','203','青青草原第一小学'),(12,'2021020302','喻荣瑾','二年级','三班','203','青青草原第一小学'),(13,'2021020303','余荣','二年级','三班','203','青青草原第一小学'),(14,'2021020304','伏腾晴','二年级','三班','203','青青草原第一小学'),(15,'2021020305','邱妍美','二年级','三班','203','青青草原第一小学'),(16,'2021020301','梅皑育','二年级','三班','203','熊森林第一小学'),(17,'2021020301','苗宏黛','二年级','三班','203','熊森林第一小学'),(18,'2021020301','冯霄皑','二年级','三班','203','熊森林第一小学'),(19,'2021020301','殷凤','二年级','三班','203','熊森林第一小学'),(20,'2021020301','吕腾娟','二年级','三班','203','熊森林第一小学');

/*Table structure for table `student_teacher` */

DROP TABLE IF EXISTS `student_teacher`;

CREATE TABLE `student_teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_student_teacher` (`school_name`,`student_id`,`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb3;

/*Data for the table `student_teacher` */

insert  into `student_teacher`(`id`,`student_id`,`teacher_id`,`school_name`) values (101,16,11,'熊森林第一小学'),(106,16,12,'熊森林第一小学'),(111,16,13,'熊森林第一小学'),(102,17,11,'熊森林第一小学'),(107,17,12,'熊森林第一小学'),(112,17,13,'熊森林第一小学'),(103,18,11,'熊森林第一小学'),(108,18,12,'熊森林第一小学'),(113,18,13,'熊森林第一小学'),(104,19,11,'熊森林第一小学'),(109,19,12,'熊森林第一小学'),(114,19,13,'熊森林第一小学'),(105,20,11,'熊森林第一小学'),(110,20,12,'熊森林第一小学'),(115,20,13,'熊森林第一小学'),(1,1,1,'青青草原第一小学'),(6,1,2,'青青草原第一小学'),(11,1,3,'青青草原第一小学'),(16,1,4,'青青草原第一小学'),(21,1,5,'青青草原第一小学'),(26,1,6,'青青草原第一小学'),(31,1,7,'青青草原第一小学'),(36,1,8,'青青草原第一小学'),(41,1,9,'青青草原第一小学'),(46,1,10,'青青草原第一小学'),(2,2,1,'青青草原第一小学'),(7,2,2,'青青草原第一小学'),(12,2,3,'青青草原第一小学'),(17,2,4,'青青草原第一小学'),(22,2,5,'青青草原第一小学'),(27,2,6,'青青草原第一小学'),(32,2,7,'青青草原第一小学'),(37,2,8,'青青草原第一小学'),(42,2,9,'青青草原第一小学'),(47,2,10,'青青草原第一小学'),(3,3,1,'青青草原第一小学'),(8,3,2,'青青草原第一小学'),(13,3,3,'青青草原第一小学'),(18,3,4,'青青草原第一小学'),(23,3,5,'青青草原第一小学'),(28,3,6,'青青草原第一小学'),(33,3,7,'青青草原第一小学'),(38,3,8,'青青草原第一小学'),(43,3,9,'青青草原第一小学'),(48,3,10,'青青草原第一小学'),(4,4,1,'青青草原第一小学'),(9,4,2,'青青草原第一小学'),(14,4,3,'青青草原第一小学'),(19,4,4,'青青草原第一小学'),(24,4,5,'青青草原第一小学'),(29,4,6,'青青草原第一小学'),(34,4,7,'青青草原第一小学'),(39,4,8,'青青草原第一小学'),(44,4,9,'青青草原第一小学'),(49,4,10,'青青草原第一小学'),(5,5,1,'青青草原第一小学'),(10,5,2,'青青草原第一小学'),(15,5,3,'青青草原第一小学'),(20,5,4,'青青草原第一小学'),(25,5,5,'青青草原第一小学'),(30,5,6,'青青草原第一小学'),(35,5,7,'青青草原第一小学'),(40,5,8,'青青草原第一小学'),(45,5,9,'青青草原第一小学'),(50,5,10,'青青草原第一小学'),(51,6,6,'青青草原第一小学'),(56,6,7,'青青草原第一小学'),(61,6,8,'青青草原第一小学'),(66,6,9,'青青草原第一小学'),(71,6,10,'青青草原第一小学'),(52,7,6,'青青草原第一小学'),(57,7,7,'青青草原第一小学'),(62,7,8,'青青草原第一小学'),(67,7,9,'青青草原第一小学'),(72,7,10,'青青草原第一小学'),(53,8,6,'青青草原第一小学'),(58,8,7,'青青草原第一小学'),(63,8,8,'青青草原第一小学'),(68,8,9,'青青草原第一小学'),(73,8,10,'青青草原第一小学'),(54,9,6,'青青草原第一小学'),(59,9,7,'青青草原第一小学'),(64,9,8,'青青草原第一小学'),(69,9,9,'青青草原第一小学'),(74,9,10,'青青草原第一小学'),(55,10,6,'青青草原第一小学'),(60,10,7,'青青草原第一小学'),(65,10,8,'青青草原第一小学'),(70,10,9,'青青草原第一小学'),(75,10,10,'青青草原第一小学'),(76,11,1,'青青草原第一小学'),(81,11,2,'青青草原第一小学'),(86,11,3,'青青草原第一小学'),(91,11,4,'青青草原第一小学'),(96,11,5,'青青草原第一小学'),(77,12,1,'青青草原第一小学'),(82,12,2,'青青草原第一小学'),(87,12,3,'青青草原第一小学'),(92,12,4,'青青草原第一小学'),(97,12,5,'青青草原第一小学'),(78,13,1,'青青草原第一小学'),(83,13,2,'青青草原第一小学'),(88,13,3,'青青草原第一小学'),(93,13,4,'青青草原第一小学'),(98,13,5,'青青草原第一小学'),(79,14,1,'青青草原第一小学'),(84,14,2,'青青草原第一小学'),(89,14,3,'青青草原第一小学'),(94,14,4,'青青草原第一小学'),(99,14,5,'青青草原第一小学'),(80,15,1,'青青草原第一小学'),(85,15,2,'青青草原第一小学'),(90,15,3,'青青草原第一小学'),(95,15,4,'青青草原第一小学'),(100,15,5,'青青草原第一小学');

/*Table structure for table `teach_course_info` */

DROP TABLE IF EXISTS `teach_course_info`;

CREATE TABLE `teach_course_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(20) NOT NULL DEFAULT '',
  `classroom_real_id` varchar(10) NOT NULL DEFAULT '',
  `grade_name` varchar(10) NOT NULL DEFAULT '',
  `class_name` varchar(10) NOT NULL DEFAULT '',
  `course_name` varchar(30) DEFAULT NULL,
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_grade_class_id` (`school_name`,`grade_name`,`class_name`,`id`),
  KEY `idx_school_classroom_id` (`school_name`,`classroom_real_id`,`id`),
  KEY `idx_school_teacher` (`school_name`,`teacher_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3;

/*Data for the table `teach_course_info` */

insert  into `teach_course_info`(`id`,`teacher_name`,`classroom_real_id`,`grade_name`,`class_name`,`course_name`,`school_name`) values (1,'褚悦真','101','一年级','一班','数学','青青草原第一小学'),(2,'汤妍翔','101','一年级','一班','英语','青青草原第一小学'),(3,'阮舒琛','101','一年级','一班','语文','青青草原第一小学'),(4,'秦致','101','一年级','一班','品德','青青草原第一小学'),(5,'韦莲','101','一年级','一班','科学','青青草原第一小学'),(6,'顾姣英','101','一年级','一班','数学','青青草原第一小学'),(7,'郭卿启','101','一年级','一班','英语','青青草原第一小学'),(8,'江云','101','一年级','一班','语文','青青草原第一小学'),(9,'邬瑞','101','一年级','一班','品德','青青草原第一小学'),(10,'韦仪凡','101','一年级','一班','科学','青青草原第一小学'),(11,'顾姣英','102','一年级','二班','数学','青青草原第一小学'),(12,'郭卿启','102','一年级','二班','英语','青青草原第一小学'),(13,'江云','102','一年级','二班','语文','青青草原第一小学'),(14,'邬瑞','102','一年级','二班','品德','青青草原第一小学'),(15,'韦仪凡','102','一年级','二班','科学','青青草原第一小学'),(16,'褚悦真','203','二年级','三班','数学','青青草原第一小学'),(17,'汤妍翔','203','二年级','三班','英语','青青草原第一小学'),(18,'阮舒琛','203','二年级','三班','语文','青青草原第一小学'),(19,'秦致','203','二年级','三班','品德','青青草原第一小学'),(20,'韦莲','203','二年级','三班','科学','青青草原第一小学'),(21,'卞雄辰','203','二年级','三班','数学','熊森林第一小学'),(22,'韦瑶蕊','203','二年级','三班','英语','熊森林第一小学'),(23,'周雪','203','二年级','三班','语文','熊森林第一小学'),(24,'江云','000','一年级','二班','语文','青青草原第一小学');

/*Table structure for table `teacher` */

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` int NOT NULL AUTO_INCREMENT,
  `real_id` varchar(6) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `teach_course_name` varchar(30) DEFAULT NULL,
  `school_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_school_realid` (`school_name`,`real_id`),
  KEY `idx_school_course_name` (`school_name`,`teach_course_name`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;

/*Data for the table `teacher` */

insert  into `teacher`(`id`,`real_id`,`name`,`teach_course_name`,`school_name`) values (1,'202000','褚悦真','数学','青青草原第一小学'),(2,'202000','汤妍翔','英语','青青草原第一小学'),(3,'202000','阮舒琛','语文','青青草原第一小学'),(4,'202000','秦致','品德','青青草原第一小学'),(5,'202000','韦莲','科学','青青草原第一小学'),(6,'202000','顾姣英','数学','青青草原第一小学'),(7,'202000','郭卿启','英语','青青草原第一小学'),(8,'202000','江云','语文','青青草原第一小学'),(9,'202000','邬瑞','品德','青青草原第一小学'),(10,'202001','韦仪凡','科学','青青草原第一小学'),(11,'202101','卞雄辰','数学','熊森林第一小学'),(12,'202101','韦瑶蕊','英语','熊森林第一小学'),(13,'202101','周雪','语文','熊森林第一小学');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
