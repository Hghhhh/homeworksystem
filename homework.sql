/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.26-log : Database - homework_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`homework_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `homework_system`;

/*Table structure for table `tb_admin` */

DROP TABLE IF EXISTS `tb_admin`;

CREATE TABLE `tb_admin` (
  `account` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_admin` */

/*Table structure for table `tb_class` */

DROP TABLE IF EXISTS `tb_class`;

CREATE TABLE `tb_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '班级名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_class` */

insert  into `tb_class`(`id`,`name`) values (1,'21');

/*Table structure for table `tb_homework` */

DROP TABLE IF EXISTS `tb_homework`;

CREATE TABLE `tb_homework` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `requestId` int(10) unsigned NOT NULL,
  `studentId` varchar(50) NOT NULL,
  `content` text,
  `grade` int(11) DEFAULT NULL,
  `enclosure` varchar(200) DEFAULT NULL COMMENT '文件url',
  `state` tinyint(4) DEFAULT NULL COMMENT '0 待提交，1 待批改 ，2 已批改',
  `createTime` int(11) NOT NULL,
  `updateTime` int(11) NOT NULL,
  `simhash` char(64) DEFAULT NULL COMMENT 'hash串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_homework` */

/*Table structure for table `tb_homework_request` */

DROP TABLE IF EXISTS `tb_homework_request`;

CREATE TABLE `tb_homework_request` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `classId` int(10) unsigned NOT NULL,
  `format` varchar(10) DEFAULT 'md',
  `enclosure` varchar(200) DEFAULT NULL COMMENT '附件',
  `teacherId` varchar(20) NOT NULL,
  `deadline` int(11) DEFAULT NULL COMMENT '截至时间',
  `updateTime` int(11) DEFAULT NULL,
  `createTime` int(11) DEFAULT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0初始状态，1已完成',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_homework_request` */

insert  into `tb_homework_request`(`id`,`title`,`content`,`classId`,`format`,`enclosure`,`teacherId`,`deadline`,`updateTime`,`createTime`,`state`) values (1,'aaaa','web',1,'md',NULL,'1',1572923719,1572923719,1572923719,0);

/*Table structure for table `tb_teacher_class` */

DROP TABLE IF EXISTS `tb_teacher_class`;

CREATE TABLE `tb_teacher_class` (
  `teacherId` int(10) unsigned NOT NULL,
  `classId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`teacherId`,`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_teacher_class` */

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `account` varchar(20) CHARACTER SET utf8 NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 NOT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0 s,1 t',
  `classId` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_user` */

insert  into `tb_user`(`account`,`password`,`state`,`classId`) values ('1','11',0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
