# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: beecodedb
# ------------------------------------------------------
# Server version 5.1.50-community-log

DROP DATABASE IF EXISTS `beecodedb`;
CREATE DATABASE `beecodedb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `beecodedb`;

#
# Source for table coupon
#

DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `coupon_id` bigint(20) NOT NULL,
  `acct_mobile` varchar(16) DEFAULT NULL,
  `acct_name` varchar(10) DEFAULT NULL,
  `acct_no` varchar(24) DEFAULT NULL,
  `business_type` varchar(2) DEFAULT NULL,
  `check_code` varchar(16) DEFAULT NULL,
  `coupon_status` int(11) DEFAULT NULL,
  `gen_time` datetime DEFAULT NULL,
  `mms_desc` varchar(100) DEFAULT NULL,
  `mms_id` varchar(20) DEFAULT NULL,
  `mms_status` int(11) DEFAULT NULL,
  `rebate_rate` decimal(2,2) DEFAULT NULL,
  `remain_times` int(11) DEFAULT NULL,
  `serial_no` varchar(36) DEFAULT NULL,
  `sms_desc` varchar(100) DEFAULT NULL,
  `sms_status` int(11) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `marketing_act` bigint(20) DEFAULT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `back_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`coupon_id`),
  KEY `FKAF42D826DDF44BC0` (`marketing_act`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table coupon
#

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (438146081879,'18650071122','邵信仁','8661081019901000011','00',NULL,4,'2011-05-27 14:46:23',NULL,NULL,0,0,0,'d91efa8d-068f-497b-978c-ad3abdd77ba9',NULL,0,1,1,68,NULL,NULL);
INSERT INTO `coupon` VALUES (438147037353,'18605089033','唐尧','4512893300082105','00',NULL,4,'2011-05-27 14:46:23',NULL,NULL,0,0,0,'ec0e91bd-4548-4ba7-8adf-72140111fa16',NULL,0,1,1,68,NULL,NULL);
INSERT INTO `coupon` VALUES (438148009883,'13788920047','aaa','8661081019901000000','00',NULL,1,'2011-05-27 14:46:23',NULL,NULL,0,0,1,'4323094f-dfbf-4206-8d1f-e6f1a27d8129',NULL,0,1,0,68,NULL,NULL);
INSERT INTO `coupon` VALUES (438149075868,'13788920048','bbbb','8661081019901000011','00',NULL,4,'2011-05-27 14:46:23',NULL,NULL,0,0,0,'54a5176c-fa78-46be-941a-395ed8a598ea',NULL,0,1,1,68,NULL,NULL);
INSERT INTO `coupon` VALUES (438150068334,'13788920049','ccc','8661081019901000011','00',NULL,4,'2011-05-27 14:46:23',NULL,NULL,0,0,0,'508ca0cf-2573-438a-ab59-e655dce81b46',NULL,0,1,1,68,NULL,NULL);
INSERT INTO `coupon` VALUES (441147032840,'18650071122','邵信仁','****************','01',NULL,4,'2011-05-27 14:54:32',NULL,NULL,0,0.65,0,'4e4ade9f-4ff1-46b5-b63d-4e1dfe94cb98',NULL,0,1,1,69,NULL,NULL);
INSERT INTO `coupon` VALUES (441148021803,'18605089033','唐尧','4512893300082105','01',NULL,1,'2011-05-27 14:54:32',NULL,NULL,0,0.65,1,'dd78f4b3-885d-46d7-b836-9db5cb4456a6',NULL,0,1,0,69,NULL,NULL);
INSERT INTO `coupon` VALUES (441149060791,'13788920047','aaa','8661081019901000000','01',NULL,1,'2011-05-27 14:54:32',NULL,NULL,0,0.65,1,'526f0795-e565-41eb-bebb-351ac9b4b49c',NULL,0,1,0,69,NULL,NULL);
INSERT INTO `coupon` VALUES (441150021840,'13788920048','bbbb','8661081019901000011','01',NULL,1,'2011-05-27 14:54:32',NULL,NULL,0,0.65,1,'d0cbba55-c979-4e9c-a2e7-e60feaaf267a',NULL,0,1,0,69,NULL,NULL);
INSERT INTO `coupon` VALUES (441151084965,'13788920049','ccc','8661081019901000011','01',NULL,1,'2011-05-27 14:54:32',NULL,NULL,0,0.65,1,'7e70e93e-f6f5-4946-8907-75c951f4bb45',NULL,0,1,0,69,NULL,NULL);
INSERT INTO `coupon` VALUES (444148036690,'18650071122','邵信仁','8661081019901000011','02',NULL,1,'2011-05-27 15:08:32',NULL,NULL,0,0,1,'3a7aca08-f180-4702-9601-1710b2803de2',NULL,0,1,0,70,NULL,150);
INSERT INTO `coupon` VALUES (444149047117,'18605089033','唐尧','4512893300082105','02',NULL,1,'2011-05-27 15:08:32',NULL,NULL,0,0,1,'f8bc165d-b9e7-4506-8eab-742de19f8b0f',NULL,0,1,0,70,NULL,1000);
INSERT INTO `coupon` VALUES (444150032510,'13788920047','aaa','****************','02',NULL,1,'2011-05-27 15:08:32',NULL,NULL,0,0,1,'68d5ad8e-ed49-4901-a76f-70cf5dc3c0c1',NULL,0,1,0,70,NULL,1000);
INSERT INTO `coupon` VALUES (444151040788,'13788920048','bbbb','****************','02',NULL,4,'2011-05-27 15:08:32',NULL,NULL,0,0,0,'8cdfc2d3-69fd-431c-b1c6-fcdb88bcafb5',NULL,0,1,1,70,NULL,1000);
INSERT INTO `coupon` VALUES (444152047988,'13788920049','ccc','****************','02',NULL,4,'2011-05-27 15:08:32',NULL,NULL,0,0,0,'32b2f99e-6d88-47af-bcef-4c0d85d07a20',NULL,0,1,1,70,NULL,1000);
INSERT INTO `coupon` VALUES (447149094873,'18650071122','邵信仁','8661081019901000011','00',NULL,1,'2011-05-29 17:21:13',NULL,NULL,0,0,1,'ed35734f-77a2-409b-932b-7051b4f8366f',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (447150016317,'18605089033','唐尧','4512893300082105','00',NULL,1,'2011-05-29 17:21:15',NULL,NULL,0,0,1,'08184763-8915-42eb-8578-2de3c334fc99',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (447151048930,'13788920047','aaa','8661081019901000000','00',NULL,1,'2011-05-29 17:21:15',NULL,NULL,0,0,1,'fa446d6a-9a52-4157-b567-9b80815284f5',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (447152035695,'13788920048','bbbb','8661081019901000011','00',NULL,1,'2011-05-29 17:21:15',NULL,NULL,0,0,1,'974c46be-7ecb-4baf-ba47-e1746e1954d8',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (447153059185,'13788920049','ccc','8661081019901000011','00',NULL,1,'2011-05-29 17:21:15',NULL,NULL,0,0,1,'33321f5f-c8bd-4b6a-88b8-c2d90a42a08a',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (450150098351,'18650071122','邵信仁','8661081019901000011','00',NULL,1,'2011-05-29 17:26:36',NULL,NULL,0,0,1,'bbc879a8-5923-4e8c-b8ed-f3e7fb4391c7',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (450151092193,'18605089033','唐尧','4512893300082105','00',NULL,1,'2011-05-29 17:26:37',NULL,NULL,0,0,1,'5e7844b1-fd07-4097-8c7b-6531f03ed1e5',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (450152017111,'13788920047','aaa','8661081019901000000','00',NULL,1,'2011-05-29 17:26:37',NULL,NULL,0,0,1,'e4b83a3f-3614-42fa-a1df-14a6c34d3e13',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (450153081652,'13788920048','bbbb','8661081019901000011','00',NULL,1,'2011-05-29 17:26:37',NULL,NULL,0,0,1,'210b5653-a383-4d56-bb91-d1f32410c3e5',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (450154061011,'13788920049','ccc','8661081019901000011','00',NULL,1,'2011-05-29 17:26:37',NULL,NULL,0,0,1,'7a460048-456a-469a-9a69-6bad53246b4f',NULL,0,1,0,74,NULL,NULL);
INSERT INTO `coupon` VALUES (453151068600,'18650071122','邵信仁','8661081019901000011','00',NULL,1,'2011-05-31 20:53:13',NULL,NULL,0,0,1,'4b36b85e-462a-4bb0-89f5-c435652a887a',NULL,0,1,0,75,NULL,NULL);
INSERT INTO `coupon` VALUES (453152059984,'18605089033','唐尧','4512893300082105','00',NULL,1,'2011-05-31 20:53:14',NULL,NULL,0,0,1,'00aebb71-d2c9-4742-8a42-00eef608123d',NULL,0,1,0,75,NULL,NULL);
INSERT INTO `coupon` VALUES (453153055007,'13788920047','aaa','8661081019901000000','00',NULL,1,'2011-05-31 20:53:14',NULL,NULL,0,0,1,'eef8d3c1-102a-4778-aa46-70cfa3415dac',NULL,0,1,0,75,NULL,NULL);
INSERT INTO `coupon` VALUES (453154066194,'13788920048','bbbb','8661081019901000011','00',NULL,1,'2011-05-31 20:53:14',NULL,NULL,0,0,1,'3cb0ebd2-603d-4ca2-86b5-1979bb6244a8',NULL,0,1,0,75,NULL,NULL);
INSERT INTO `coupon` VALUES (453155063608,'13788920049','ccc','8661081019901000011','00',NULL,1,'2011-05-31 20:53:15',NULL,NULL,0,0,1,'fd483ecf-96db-4563-9f87-5bf0b6ee4222',NULL,0,1,0,75,NULL,NULL);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table coupont_ctrl
#

DROP TABLE IF EXISTS `coupont_ctrl`;
CREATE TABLE `coupont_ctrl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `batch_no` varchar(255) DEFAULT NULL,
  `business_type` varchar(2) NOT NULL,
  `check_date` datetime NOT NULL,
  `check_day` date DEFAULT NULL,
  `coupon_id` bigint(20) DEFAULT NULL,
  `device_no` varchar(16) NOT NULL,
  `encode_version` varchar(2) NOT NULL,
  `partner_no` varchar(32) NOT NULL,
  `rebate_rate` decimal(19,2) DEFAULT NULL,
  `serial_no` varchar(45) DEFAULT NULL,
  `trace_no` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `void_flag` varchar(255) DEFAULT NULL,
  `back_amount` decimal(10,2) DEFAULT NULL,
  `off_amount` decimal(10,2) DEFAULT NULL,
  `original_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

#
# Dumping data for table coupont_ctrl
#

LOCK TABLES `coupont_ctrl` WRITE;
/*!40000 ALTER TABLE `coupont_ctrl` DISABLE KEYS */;
INSERT INTO `coupont_ctrl` VALUES (13,NULL,'000001','00','2011-05-27 14:47:50','2011-05-27',438146081879,'11000007','2','104110058120007',0,'d91efa8d-068f-497b-978c-ad3abdd77ba9','000103',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `coupont_ctrl` VALUES (14,NULL,'000001','00','2011-05-27 14:48:40','2011-05-27',438147037353,'11000007','2','104110058120007',0,'ec0e91bd-4548-4ba7-8adf-72140111fa16','000105',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `coupont_ctrl` VALUES (15,100,'000001','00','2011-05-27 14:51:59','2011-05-27',438150068334,'11000007','2','104110058120007',0,'508ca0cf-2573-438a-ab59-e655dce81b46','000107',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `coupont_ctrl` VALUES (16,0,'000001','01','2011-05-27 15:05:05','2011-05-27',441147032840,'11000007','2','104110058120007',0.65,'4e4ade9f-4ff1-46b5-b63d-4e1dfe94cb98','000114',NULL,NULL,NULL,7800,12000);
INSERT INTO `coupont_ctrl` VALUES (17,0,'000001','02','2011-05-27 15:10:40','2011-05-27',444152047988,'11000007','2','104110058120007',0,'32b2f99e-6d88-47af-bcef-4c0d85d07a20','000117',NULL,NULL,1000,NULL,100);
INSERT INTO `coupont_ctrl` VALUES (18,0,'000001','02','2011-05-27 15:12:23','2011-05-27',444151040788,'11000007','2','104110058120007',0,'8cdfc2d3-69fd-431c-b1c6-fcdb88bcafb5','000119',NULL,NULL,1000,NULL,1000);
INSERT INTO `coupont_ctrl` VALUES (19,100,'000001','00','2011-05-27 15:15:21','2011-05-27',438149075868,'11000007','2','104110058120007',0,'54a5176c-fa78-46be-941a-395ed8a598ea','000122',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `coupont_ctrl` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table customer
#

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `old_name` varchar(100) DEFAULT NULL,
  `new_name` varchar(20) DEFAULT NULL,
  `count` bigint(10) DEFAULT '0',
  `act_no` bigint(20) NOT NULL DEFAULT '0',
  `file_status` bigint(2) DEFAULT '0',
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;

#
# Dumping data for table customer
#

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (55,'新大陆.xls','681306478752484',5,68,1,1);
INSERT INTO `customer` VALUES (56,'新大陆.xls','691306479263375',5,69,1,1);
INSERT INTO `customer` VALUES (57,'新大陆.xls','701306480104390',5,70,1,1);
INSERT INTO `customer` VALUES (58,'新大陆.xls','741306660861296',5,74,1,1);
INSERT INTO `customer` VALUES (59,'新大陆.xls','741306660977781',5,74,1,1);
INSERT INTO `customer` VALUES (60,'新大陆.xls','751306846387046',5,75,1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table hibernate_sequences
#

DROP TABLE IF EXISTS `hibernate_sequences`;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table hibernate_sequences
#

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('actNo',1);
INSERT INTO `hibernate_sequences` VALUES ('operNo',100);
INSERT INTO `hibernate_sequences` VALUES ('marketing_act',76);
INSERT INTO `hibernate_sequences` VALUES ('coupon',152);
INSERT INTO `hibernate_sequences` VALUES ('oper',23);
INSERT INTO `hibernate_sequences` VALUES ('partner',16);
INSERT INTO `hibernate_sequences` VALUES ('customer',61);
INSERT INTO `hibernate_sequences` VALUES ('mms_send_list',14);
INSERT INTO `hibernate_sequences` VALUES ('resp_status',62);
INSERT INTO `hibernate_sequences` VALUES ('send_list',14);
INSERT INTO `hibernate_sequences` VALUES ('partner_catalog',3);
INSERT INTO `hibernate_sequences` VALUES ('marketing_catalog',10);
INSERT INTO `hibernate_sequences` VALUES ('mms_template',3);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_act
#

DROP TABLE IF EXISTS `marketing_act`;
CREATE TABLE `marketing_act` (
  `act_no` bigint(20) NOT NULL,
  `act_detail` varchar(100) NOT NULL,
  `act_name` varchar(30) NOT NULL,
  `act_status` int(11) DEFAULT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `bind_card` varchar(2) NOT NULL,
  `biz_no` varchar(2) NOT NULL,
  `check_code` varchar(200) DEFAULT NULL,
  `coupon_sum` bigint(20) DEFAULT '0',
  `end_date` datetime NOT NULL,
  `file_name` varchar(1000) DEFAULT NULL,
  `gen_time` datetime DEFAULT NULL,
  `mms_content` varchar(400) NOT NULL,
  `mms_send_sum` bigint(20) DEFAULT NULL,
  `mms_title` varchar(40) NOT NULL,
  `oper_no` bigint(20) DEFAULT NULL,
  `rebate_rate` decimal(2,2) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `sms_send_sum` bigint(20) DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `times` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `marketing_catalog` bigint(20) NOT NULL,
  `mms_sending` int(11) DEFAULT '0',
  `sms_sending` int(11) DEFAULT '0',
  `max_amount` decimal(10,2) DEFAULT NULL,
  `back_rate` decimal(2,2) DEFAULT NULL,
  `exchange_name` varchar(100) DEFAULT NULL,
  `mms_template_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`act_no`),
  KEY `FKE5A6EEF9BC896CCE` (`marketing_catalog`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_act
#

LOCK TABLES `marketing_act` WRITE;
/*!40000 ALTER TABLE `marketing_act` DISABLE KEYS */;
INSERT INTO `marketing_act` VALUES (68,'11111111','兑换券',7,100,'0','00','',NULL,'2011-06-05',NULL,'2011-05-27 14:45:40','尊敬的XXX你好！，对方答复XXXX大幅度飞XXXXXXXXXXXX的的的SYYYY-MM-DD---EYYYY-MM-DD地方地方的',NULL,'牙刷',NULL,0,NULL,NULL,'2011-04-25',1,2,2,0,0,NULL,NULL,'牙刷',NULL);
INSERT INTO `marketing_act` VALUES (69,'11111111','折扣券',7,0,'1','01','62225*;622***',NULL,'2011-06-05',NULL,'2011-05-27 14:54:16','尊敬的XXX您好1111XXXX1111XXXXXXXXXXXX11111SYYYY-MM-DD---EYYYY-MM-DD11111111',NULL,'折扣券',NULL,0.65,NULL,NULL,'2011-04-25',1,2,2,0,0,NULL,NULL,'1',NULL);
INSERT INTO `marketing_act` VALUES (70,'111111111','抵用券',7,0,'1','02','6222**;62****;52****',NULL,'2011-06-05',NULL,'2011-05-27 15:08:18','111XXX111111XXXX111111XXXXXXXXXXXX1111111SYYYY-MM-DD---EYYYY-MM-DD111111111',NULL,'抵用券',NULL,0,NULL,NULL,'2011-04-25',1,2,2,0,0,1000,0.15,'1',NULL);
INSERT INTO `marketing_act` VALUES (71,'11111111','1111111',0,100,'0','00','',NULL,'2011-06-05',NULL,'2011-05-29 14:14:11','11111XXX11111XXXX111111XXXXXXXXXXXX11111111SYYYY-MM-DD---EYYYY-MM-DD111111111',NULL,'11111',NULL,0,NULL,NULL,'2011-04-25',1,0,2,0,0,NULL,NULL,'毛巾',NULL);
INSERT INTO `marketing_act` VALUES (74,'1111111111','测试测试',7,100,'0','00','',NULL,'2011-06-05',NULL,'2011-05-29 16:58:00','',NULL,'1111',NULL,0,NULL,NULL,'2011-04-25',1,7,2,0,0,NULL,NULL,'被子',1);
INSERT INTO `marketing_act` VALUES (75,'111111','发送测试',7,100,'0','00','',NULL,'2011-06-05',NULL,'2011-05-31 20:52:57','',NULL,'111',NULL,0,NULL,NULL,'2011-04-25',1,2,2,0,0,NULL,NULL,'牙刷',2);
/*!40000 ALTER TABLE `marketing_act` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_act_partners
#

DROP TABLE IF EXISTS `marketing_act_partners`;
CREATE TABLE `marketing_act_partners` (
  `marketing_act` bigint(20) NOT NULL,
  `partners` bigint(20) NOT NULL,
  PRIMARY KEY (`marketing_act`,`partners`),
  KEY `FK43F7C73196520058` (`partners`),
  KEY `FK43F7C731DDF44BC0` (`marketing_act`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_act_partners
#

LOCK TABLES `marketing_act_partners` WRITE;
/*!40000 ALTER TABLE `marketing_act_partners` DISABLE KEYS */;
INSERT INTO `marketing_act_partners` VALUES (68,6);
INSERT INTO `marketing_act_partners` VALUES (68,7);
INSERT INTO `marketing_act_partners` VALUES (68,8);
INSERT INTO `marketing_act_partners` VALUES (68,9);
INSERT INTO `marketing_act_partners` VALUES (68,10);
INSERT INTO `marketing_act_partners` VALUES (68,11);
INSERT INTO `marketing_act_partners` VALUES (68,12);
INSERT INTO `marketing_act_partners` VALUES (68,13);
INSERT INTO `marketing_act_partners` VALUES (68,14);
INSERT INTO `marketing_act_partners` VALUES (68,15);
INSERT INTO `marketing_act_partners` VALUES (68,20);
INSERT INTO `marketing_act_partners` VALUES (68,23);
INSERT INTO `marketing_act_partners` VALUES (69,6);
INSERT INTO `marketing_act_partners` VALUES (69,7);
INSERT INTO `marketing_act_partners` VALUES (69,8);
INSERT INTO `marketing_act_partners` VALUES (69,9);
INSERT INTO `marketing_act_partners` VALUES (69,10);
INSERT INTO `marketing_act_partners` VALUES (69,11);
INSERT INTO `marketing_act_partners` VALUES (69,12);
INSERT INTO `marketing_act_partners` VALUES (69,13);
INSERT INTO `marketing_act_partners` VALUES (69,14);
INSERT INTO `marketing_act_partners` VALUES (69,15);
INSERT INTO `marketing_act_partners` VALUES (69,20);
INSERT INTO `marketing_act_partners` VALUES (69,23);
INSERT INTO `marketing_act_partners` VALUES (70,6);
INSERT INTO `marketing_act_partners` VALUES (70,7);
INSERT INTO `marketing_act_partners` VALUES (70,8);
INSERT INTO `marketing_act_partners` VALUES (70,9);
INSERT INTO `marketing_act_partners` VALUES (70,10);
INSERT INTO `marketing_act_partners` VALUES (70,11);
INSERT INTO `marketing_act_partners` VALUES (70,12);
INSERT INTO `marketing_act_partners` VALUES (70,13);
INSERT INTO `marketing_act_partners` VALUES (70,14);
INSERT INTO `marketing_act_partners` VALUES (70,15);
INSERT INTO `marketing_act_partners` VALUES (70,20);
INSERT INTO `marketing_act_partners` VALUES (70,23);
INSERT INTO `marketing_act_partners` VALUES (71,6);
INSERT INTO `marketing_act_partners` VALUES (71,7);
INSERT INTO `marketing_act_partners` VALUES (71,8);
INSERT INTO `marketing_act_partners` VALUES (71,9);
INSERT INTO `marketing_act_partners` VALUES (71,10);
INSERT INTO `marketing_act_partners` VALUES (71,11);
INSERT INTO `marketing_act_partners` VALUES (71,12);
INSERT INTO `marketing_act_partners` VALUES (71,13);
INSERT INTO `marketing_act_partners` VALUES (71,14);
INSERT INTO `marketing_act_partners` VALUES (71,15);
INSERT INTO `marketing_act_partners` VALUES (71,20);
INSERT INTO `marketing_act_partners` VALUES (71,23);
INSERT INTO `marketing_act_partners` VALUES (74,6);
INSERT INTO `marketing_act_partners` VALUES (74,7);
INSERT INTO `marketing_act_partners` VALUES (74,8);
INSERT INTO `marketing_act_partners` VALUES (74,9);
INSERT INTO `marketing_act_partners` VALUES (74,10);
INSERT INTO `marketing_act_partners` VALUES (74,11);
INSERT INTO `marketing_act_partners` VALUES (74,12);
INSERT INTO `marketing_act_partners` VALUES (74,13);
INSERT INTO `marketing_act_partners` VALUES (74,14);
INSERT INTO `marketing_act_partners` VALUES (74,15);
INSERT INTO `marketing_act_partners` VALUES (74,20);
INSERT INTO `marketing_act_partners` VALUES (74,23);
INSERT INTO `marketing_act_partners` VALUES (75,6);
INSERT INTO `marketing_act_partners` VALUES (75,7);
INSERT INTO `marketing_act_partners` VALUES (75,8);
INSERT INTO `marketing_act_partners` VALUES (75,9);
INSERT INTO `marketing_act_partners` VALUES (75,10);
INSERT INTO `marketing_act_partners` VALUES (75,11);
INSERT INTO `marketing_act_partners` VALUES (75,12);
INSERT INTO `marketing_act_partners` VALUES (75,13);
INSERT INTO `marketing_act_partners` VALUES (75,14);
INSERT INTO `marketing_act_partners` VALUES (75,15);
INSERT INTO `marketing_act_partners` VALUES (75,20);
INSERT INTO `marketing_act_partners` VALUES (75,23);
/*!40000 ALTER TABLE `marketing_act_partners` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table marketing_catalog
#

DROP TABLE IF EXISTS `marketing_catalog`;
CREATE TABLE `marketing_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catalog_name` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Dumping data for table marketing_catalog
#

LOCK TABLES `marketing_catalog` WRITE;
/*!40000 ALTER TABLE `marketing_catalog` DISABLE KEYS */;
INSERT INTO `marketing_catalog` VALUES (2,'院线活动','2011-04-13 09:18:31','2011-05-15 21:50:53',1);
INSERT INTO `marketing_catalog` VALUES (3,'食品促销2','2011-04-14 15:34:09','2011-05-12 12:01:04',1);
INSERT INTO `marketing_catalog` VALUES (5,'测试','2011-05-22 19:45:57','2011-05-22 19:45:57',0);
INSERT INTO `marketing_catalog` VALUES (7,'行内活动','2011-05-04 10:09:39','2011-05-04 10:09:39',0);
INSERT INTO `marketing_catalog` VALUES (9,'test','2011-05-22 19:55:23','2011-05-22 19:55:23',0);
INSERT INTO `marketing_catalog` VALUES (14,'aaaaa','2011-05-13 20:10:41','2011-05-13 20:10:41',0);
INSERT INTO `marketing_catalog` VALUES (15,'aaaaaaaaa','2011-05-13 20:20:15','2011-05-13 20:20:15',0);
INSERT INTO `marketing_catalog` VALUES (16,'aaaaaaaddaa','2011-05-13 20:50:47','2011-05-13 20:50:47',0);
INSERT INTO `marketing_catalog` VALUES (17,'aaaadfdaaaddaa','2011-05-13 20:56:59','2011-05-13 20:56:59',0);
INSERT INTO `marketing_catalog` VALUES (18,'aaaadfdsssssssaaaddaamm','2011-05-13 21:00:03','2011-05-13 21:00:03',0);
INSERT INTO `marketing_catalog` VALUES (19,'mmmmmm','2011-05-13 21:02:17','2011-05-13 21:02:17',0);
INSERT INTO `marketing_catalog` VALUES (23,'fdfdfdfd1','2011-05-13 22:42:38','2011-05-13 22:42:38',0);
INSERT INTO `marketing_catalog` VALUES (24,'fdfdfdfd2','2011-05-13 22:42:38','2011-05-13 22:42:38',0);
INSERT INTO `marketing_catalog` VALUES (25,'fdfdfdfd3','2011-05-13 22:43:39','2011-05-13 22:43:39',0);
INSERT INTO `marketing_catalog` VALUES (26,'fdfdfdfd4','2011-05-13 22:43:39','2011-05-13 22:43:39',0);
INSERT INTO `marketing_catalog` VALUES (27,'fdfdhhhhfdfd0','2011-05-13 22:44:25','2011-05-13 22:44:25',0);
INSERT INTO `marketing_catalog` VALUES (28,'fdfdhhhhfdfd1','2011-05-13 22:44:25','2011-05-13 22:44:25',0);
INSERT INTO `marketing_catalog` VALUES (29,'fdfdhhhhfdfd2','2011-05-13 22:44:25','2011-05-13 22:44:25',0);
INSERT INTO `marketing_catalog` VALUES (30,'fdfdhhhhfdfd3','2011-05-13 22:44:25','2011-05-13 22:44:25',0);
INSERT INTO `marketing_catalog` VALUES (31,'fdfdhhhhfdfd4','2011-05-13 22:44:25','2011-05-13 22:44:25',0);
INSERT INTO `marketing_catalog` VALUES (32,'fdfdddhhhhfdfd0','2011-05-13 22:45:50','2011-05-13 22:45:50',0);
INSERT INTO `marketing_catalog` VALUES (33,'fdfdddhhhhfdfd1','2011-05-13 22:45:50','2011-05-13 22:45:50',0);
INSERT INTO `marketing_catalog` VALUES (34,'fdfdddhhhhfdfd2','2011-05-13 22:45:50','2011-05-13 22:45:50',0);
INSERT INTO `marketing_catalog` VALUES (35,'fdfdddhhhhfddddfd0','2011-05-13 22:51:12','2011-05-13 22:51:12',0);
/*!40000 ALTER TABLE `marketing_catalog` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table mms_template
#

DROP TABLE IF EXISTS `mms_template`;
CREATE TABLE `mms_template` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `title1` varchar(200) NOT NULL,
  `title2` varchar(200) NOT NULL,
  `card_before` varchar(200) NOT NULL,
  `coupon_id_before` varchar(200) NOT NULL,
  `value_before` varchar(200) NOT NULL,
  `period_before` varchar(200) NOT NULL,
  `ending` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table mms_template
#

LOCK TABLES `mms_template` WRITE;
/*!40000 ALTER TABLE `mms_template` DISABLE KEYS */;
INSERT INTO `mms_template` VALUES (1,'111','111','1111','11111','111111','1111','111111');
INSERT INTO `mms_template` VALUES (2,'1111','1111','111','11111','111111','1111111','1111111111');
/*!40000 ALTER TABLE `mms_template` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table oper
#

DROP TABLE IF EXISTS `oper`;
CREATE TABLE `oper` (
  `oper_no` bigint(20) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `gen_time` datetime DEFAULT NULL,
  `oper_name` varchar(24) NOT NULL,
  `oper_pwd` varchar(255) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`oper_no`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table oper
#

LOCK TABLES `oper` WRITE;
/*!40000 ALTER TABLE `oper` DISABLE KEYS */;
INSERT INTO `oper` VALUES (1,b'1',NULL,'admin','123',2);
INSERT INTO `oper` VALUES (2,b'1',NULL,'shaoxr','123',1);
INSERT INTO `oper` VALUES (3,b'1',NULL,'wwa','123',4);
INSERT INTO `oper` VALUES (20,b'1',NULL,'aaaaaab','123',1);
INSERT INTO `oper` VALUES (21,b'0','2011-05-19 09:58:00','test','123',0);
INSERT INTO `oper` VALUES (22,b'1','2011-05-21 17:21:03','send','123',0);
/*!40000 ALTER TABLE `oper` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table oper_roles
#

DROP TABLE IF EXISTS `oper_roles`;
CREATE TABLE `oper_roles` (
  `oper` bigint(20) NOT NULL,
  `roles` bigint(20) NOT NULL,
  PRIMARY KEY (`oper`,`roles`),
  KEY `FKFCBCA58C3771F99F` (`roles`),
  KEY `FKFCBCA58CD87CD7D7` (`oper`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table oper_roles
#

LOCK TABLES `oper_roles` WRITE;
/*!40000 ALTER TABLE `oper_roles` DISABLE KEYS */;
INSERT INTO `oper_roles` VALUES (1,2);
INSERT INTO `oper_roles` VALUES (1,5);
INSERT INTO `oper_roles` VALUES (2,1);
INSERT INTO `oper_roles` VALUES (3,1);
INSERT INTO `oper_roles` VALUES (16,1);
INSERT INTO `oper_roles` VALUES (17,2);
INSERT INTO `oper_roles` VALUES (17,3);
INSERT INTO `oper_roles` VALUES (18,2);
INSERT INTO `oper_roles` VALUES (18,3);
INSERT INTO `oper_roles` VALUES (19,1);
INSERT INTO `oper_roles` VALUES (20,1);
INSERT INTO `oper_roles` VALUES (20,2);
INSERT INTO `oper_roles` VALUES (20,3);
INSERT INTO `oper_roles` VALUES (21,2);
INSERT INTO `oper_roles` VALUES (22,5);
/*!40000 ALTER TABLE `oper_roles` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table partner
#

DROP TABLE IF EXISTS `partner`;
CREATE TABLE `partner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `partner_name` varchar(32) NOT NULL,
  `partner_no` varchar(32) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `partner_catalog` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD0BCDCC846B5434E` (`partner_catalog`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

#
# Dumping data for table partner
#

LOCK TABLES `partner` WRITE;
/*!40000 ALTER TABLE `partner` DISABLE KEYS */;
INSERT INTO `partner` VALUES (4,'测试','133',0,2);
INSERT INTO `partner` VALUES (6,'123','12322',0,12);
INSERT INTO `partner` VALUES (7,'12','12',0,12);
INSERT INTO `partner` VALUES (8,'13','13',0,12);
INSERT INTO `partner` VALUES (9,'14','14',0,12);
INSERT INTO `partner` VALUES (10,'15','15',0,12);
INSERT INTO `partner` VALUES (11,'16','16',0,12);
INSERT INTO `partner` VALUES (12,'17','17',0,12);
INSERT INTO `partner` VALUES (13,'18','18',0,12);
INSERT INTO `partner` VALUES (14,'19','19',0,12);
INSERT INTO `partner` VALUES (15,'办事处测试','104110058120007',0,12);
INSERT INTO `partner` VALUES (19,'123444','123',2,32);
INSERT INTO `partner` VALUES (20,'11111111111','111111111232323',1,12);
INSERT INTO `partner` VALUES (23,'fdfdf0','11111101',0,12);
/*!40000 ALTER TABLE `partner` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table partner_catalog
#

DROP TABLE IF EXISTS `partner_catalog`;
CREATE TABLE `partner_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `catalog_name` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

#
# Dumping data for table partner_catalog
#

LOCK TABLES `partner_catalog` WRITE;
/*!40000 ALTER TABLE `partner_catalog` DISABLE KEYS */;
INSERT INTO `partner_catalog` VALUES (2,'测试','2011-05-22 19:37:00','2011-05-22 19:37:00',0);
INSERT INTO `partner_catalog` VALUES (12,'食品的','2011-04-18 09:50:49','2011-05-15 21:46:12',5);
INSERT INTO `partner_catalog` VALUES (32,'test','2011-04-28 14:03:45','2011-05-12 10:50:54',1);
INSERT INTO `partner_catalog` VALUES (37,'testtest','2011-05-12 12:10:32','2011-05-12 12:10:32',0);
/*!40000 ALTER TABLE `partner_catalog` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table resp_status
#

DROP TABLE IF EXISTS `resp_status`;
CREATE TABLE `resp_status` (
  `id` bigint(16) NOT NULL,
  `coupon_id` bigint(20) NOT NULL DEFAULT '0',
  `resp_status` varchar(2) DEFAULT '0',
  `resp_desc` varchar(500) DEFAULT NULL,
  `mms_send_list_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table resp_status
#

LOCK TABLES `resp_status` WRITE;
/*!40000 ALTER TABLE `resp_status` DISABLE KEYS */;
INSERT INTO `resp_status` VALUES (26,381127025747,'1','success',2);
INSERT INTO `resp_status` VALUES (27,381129020661,'1','success',2);
INSERT INTO `resp_status` VALUES (28,381128012100,'1','success',2);
INSERT INTO `resp_status` VALUES (29,381130047153,'1','success',2);
INSERT INTO `resp_status` VALUES (30,381131013121,'1','success',2);
INSERT INTO `resp_status` VALUES (31,384128019757,'1','success',3);
INSERT INTO `resp_status` VALUES (32,384130052663,'1','success',3);
INSERT INTO `resp_status` VALUES (33,384129019784,'1','success',3);
INSERT INTO `resp_status` VALUES (34,384131031410,'1','success',3);
INSERT INTO `resp_status` VALUES (35,384132096770,'1','success',3);
INSERT INTO `resp_status` VALUES (36,387130040648,'1','success',6);
INSERT INTO `resp_status` VALUES (37,387129000508,'1','success',6);
INSERT INTO `resp_status` VALUES (38,387131034371,'1','success',6);
INSERT INTO `resp_status` VALUES (39,387130040648,'1','success',7);
INSERT INTO `resp_status` VALUES (40,387131034371,'1','success',7);
INSERT INTO `resp_status` VALUES (41,387129000508,'1','success',7);
INSERT INTO `resp_status` VALUES (42,405135060365,'1','success',8);
INSERT INTO `resp_status` VALUES (43,405135060365,'1','success',9);
INSERT INTO `resp_status` VALUES (44,405136068267,'1','success',10);
INSERT INTO `resp_status` VALUES (45,405137006386,'1','success',10);
INSERT INTO `resp_status` VALUES (46,405138092535,'1','success',10);
INSERT INTO `resp_status` VALUES (47,405139036404,'1','success',10);
INSERT INTO `resp_status` VALUES (48,405141010574,'1','success',10);
INSERT INTO `resp_status` VALUES (49,405140001122,'1','success',10);
INSERT INTO `resp_status` VALUES (50,405136068267,'1','success',11);
INSERT INTO `resp_status` VALUES (51,405138092535,'1','success',11);
INSERT INTO `resp_status` VALUES (52,405137006386,'1','success',11);
INSERT INTO `resp_status` VALUES (53,405139036404,'1','success',11);
INSERT INTO `resp_status` VALUES (54,405141010574,'1','success',11);
INSERT INTO `resp_status` VALUES (55,405140001122,'1','success',11);
INSERT INTO `resp_status` VALUES (56,405135060365,'1','success',12);
INSERT INTO `resp_status` VALUES (57,426144001104,'1','success',13);
INSERT INTO `resp_status` VALUES (58,426142097891,'1','success',13);
INSERT INTO `resp_status` VALUES (59,426143004932,'1','success',13);
INSERT INTO `resp_status` VALUES (60,426146048118,'1','success',13);
INSERT INTO `resp_status` VALUES (61,426145055465,'1','success',13);
/*!40000 ALTER TABLE `resp_status` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table roles
#

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(24) NOT NULL,
  `role_no` varchar(16) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

#
# Dumping data for table roles
#

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'系统管理员','ROLE_ADMIN',30);
INSERT INTO `roles` VALUES (2,'业务员','ROLE_BUSINESS',14);
INSERT INTO `roles` VALUES (3,'审核员','ROLE_CHECK',14);
INSERT INTO `roles` VALUES (4,'系统查询','ROLE_QUERY',7);
INSERT INTO `roles` VALUES (5,'发送员','ROLE_SEND',10);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table send_list
#

DROP TABLE IF EXISTS `send_list`;
CREATE TABLE `send_list` (
  `id` bigint(16) NOT NULL,
  `total_count` bigint(16) DEFAULT '0',
  `success_count` bigint(16) DEFAULT '0',
  `send_status` int(11) DEFAULT '0',
  `submit_time` datetime DEFAULT NULL,
  `act_name` varchar(100) DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `ms_type` int(11) DEFAULT NULL,
  `act_no` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Dumping data for table send_list
#

LOCK TABLES `send_list` WRITE;
/*!40000 ALTER TABLE `send_list` DISABLE KEYS */;
INSERT INTO `send_list` VALUES (2,5,NULL,1,'2011-05-20 16:26:20','1111','2011-05-20 16:26:40',1,47);
INSERT INTO `send_list` VALUES (3,5,NULL,1,'2011-05-20 17:54:56','1111','2011-05-20 17:54:56',0,47);
INSERT INTO `send_list` VALUES (4,3,NULL,1,'2011-05-21 10:46:23','发送测试','2011-05-21 10:46:23',0,48);
INSERT INTO `send_list` VALUES (5,3,NULL,1,'2011-05-21 10:49:08','发送测试','2011-05-21 10:49:08',0,48);
INSERT INTO `send_list` VALUES (6,3,NULL,1,'2011-05-21 10:51:13','发送测试','2011-05-21 10:51:14',0,48);
INSERT INTO `send_list` VALUES (7,3,NULL,1,'2011-05-21 10:52:22','发送测试','2011-05-21 10:52:23',1,48);
INSERT INTO `send_list` VALUES (8,1,NULL,1,'2011-05-22 19:44:45','wwa测试','2011-05-22 19:44:47',0,56);
INSERT INTO `send_list` VALUES (9,1,NULL,1,'2011-05-22 19:45:03','wwa测试','2011-05-22 19:45:03',1,56);
INSERT INTO `send_list` VALUES (10,6,NULL,1,'2011-05-22 20:04:55','测试','2011-05-22 20:04:56',1,58);
INSERT INTO `send_list` VALUES (11,6,NULL,1,'2011-05-22 20:05:18','测试','2011-05-22 20:05:18',0,58);
INSERT INTO `send_list` VALUES (12,1,NULL,1,'2011-05-22 20:13:31','wwa测试','2011-05-22 20:13:31',0,56);
INSERT INTO `send_list` VALUES (13,5,NULL,1,'2011-05-27 13:20:19','测试测试','2011-05-27 13:20:21',1,65);
/*!40000 ALTER TABLE `send_list` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table t_dictionary
#

DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyname` varchar(80) DEFAULT NULL,
  `keyvalue` varchar(80) DEFAULT NULL,
  `CLASS_NAME` varchar(80) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

#
# Dumping data for table t_dictionary
#

LOCK TABLES `t_dictionary` WRITE;
/*!40000 ALTER TABLE `t_dictionary` DISABLE KEYS */;
INSERT INTO `t_dictionary` VALUES (1,'3','审核失败','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (2,'4','已作废','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (3,'1','待审核','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (4,'5','待发放','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (5,'7','已发放','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (6,'8','已过期','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (7,'9','已关闭','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (8,'0','无效','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (9,'1','有效','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (10,'2','过期','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (11,'3','挂失','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (12,'4','已用完','COUPON_STATUS',0);
INSERT INTO `t_dictionary` VALUES (13,'1','已经发送','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (16,'1','限定卡bin','BIND_CARD',0);
INSERT INTO `t_dictionary` VALUES (17,'0','不限定卡','BIND_CARD',0);
INSERT INTO `t_dictionary` VALUES (18,'0','等待发送','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (19,'2','发送失败','COUPON_MMS_STATUS',0);
INSERT INTO `t_dictionary` VALUES (20,'00','兑换券','BUSINESS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (21,'01','优惠券','BUSINESS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (24,'6','发放中','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (25,'0','待添加客户','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (26,'2','追加客户','ACT_STATUS',0);
INSERT INTO `t_dictionary` VALUES (27,'0','发送中','MMS_SEND_LIST_STATUS',0);
INSERT INTO `t_dictionary` VALUES (28,'1','发送完毕','MMS_SEND_LIST_STATUS',0);
INSERT INTO `t_dictionary` VALUES (29,'0','发送成功','0',0);
INSERT INTO `t_dictionary` VALUES (30,'1','发送失败','1',0);
INSERT INTO `t_dictionary` VALUES (31,'0','短信','MS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (32,'1','彩信','MS_TYPE',0);
INSERT INTO `t_dictionary` VALUES (33,'02','抵用券','BUSINESS_TYPE',0);
/*!40000 ALTER TABLE `t_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
