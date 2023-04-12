-- MariaDB dump 10.19  Distrib 10.6.5-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: usedbook
-- ------------------------------------------------------
-- Server version	10.6.5-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookcategory`
--

DROP TABLE IF EXISTS `bookcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookcategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcategory`
--

LOCK TABLES `bookcategory` WRITE;
/*!40000 ALTER TABLE `bookcategory` DISABLE KEYS */;
INSERT INTO `bookcategory` VALUES (3,'CARTOON'),(2,'HUMANITIES'),(1,'NOVEL');
/*!40000 ALTER TABLE `bookcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookpost`
--

DROP TABLE IF EXISTS `bookpost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookpost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `writeremail` varchar(50) NOT NULL,
  `bookname` varchar(50) NOT NULL,
  `bookcategory` varchar(50) NOT NULL,
  `bookprice` int(11) NOT NULL,
  `bookdescription` text NOT NULL,
  `createtime` datetime NOT NULL,
  `viewcount` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_bookpost_bookcategory_2` (`bookcategory`),
  KEY `FK_bookpost_member` (`writeremail`),
  CONSTRAINT `FK_bookpost_bookcategory_2` FOREIGN KEY (`bookcategory`) REFERENCES `bookcategory` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_bookpost_member` FOREIGN KEY (`writeremail`) REFERENCES `member` (`email`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `pricecheck` CHECK (`bookprice` >= 1000)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookpost`
--

LOCK TABLES `bookpost` WRITE;
/*!40000 ALTER TABLE `bookpost` DISABLE KEYS */;
INSERT INTO `bookpost` VALUES (22,'익명@admin','123','NOVEL',1231,'123','2021-12-27 13:38:10',1),(23,'익명@admin','2938','HUMANITIES',1956,'29djdnzlwo2','2021-12-27 17:53:12',5),(24,'익명@admin','346362','HUMANITIES',32523,'142123','2021-12-28 17:04:37',0),(25,'익명@admin','안녕','CARTOON',1606,'안녕','2021-12-28 17:06:30',0),(26,'익명@admin','ㅇㅇ','NOVEL',1200,'제곧내','2021-12-28 17:47:28',1),(39,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:26:57',0),(40,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:26:58',0),(41,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:00',0),(42,'pagetest@test','123','HUMANITIES',2342,'43lkjfs','2021-12-28 19:27:05',0),(43,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:11',0),(44,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:18',0),(45,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:23',0),(46,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:29',0),(47,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:35',0),(48,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:40',0),(49,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:41',1),(50,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:42',0),(51,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:43',0),(52,'pagetest@test','123','HUMANITIES',2342,'43lkjfs','2021-12-28 19:27:44',0),(53,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:45',0),(56,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:48',0),(57,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:49',2),(58,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:50',0),(59,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:51',0),(60,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:52',0),(61,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:55',0),(62,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:56',1),(63,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:27:59',0),(64,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:28:04',0),(65,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:28:08',1),(66,'pagetest@test','123','CARTOON',2342,'43lkjfs','2021-12-28 19:28:10',2),(69,'test@test.com','4545','CARTOON',6535,'ge3f1wef','2021-12-30 14:21:55',6),(70,'test@test.com','5656','CARTOON',4016,'fweffwe','2021-12-30 14:30:34',6),(71,'test@test.com','45','CARTOON',34543,'fddsfsv','2021-12-30 14:31:25',30),(72,'test@test.com','454545','CARTOON',34543,'fddsfsv','2021-12-30 14:31:26',12),(73,'test@test.com','45454545','CARTOON',34543,'fddsfsv','2021-12-30 14:31:27',49),(74,'test@test.com','4511','CARTOON',34543,'fddsfsv','2021-12-30 14:31:28',58),(78,'11@11','사진','NOVEL',1653,'30야너자','2022-01-01 16:13:01',7),(79,'hong@test.com','응애 나 애기 모코코','NOVEL',1004,'응애','2022-01-05 22:33:48',11),(80,'kang@kang','갤럭시s20 울트라 S급 판매합니다. 초특급 상태 좋음 매우 쌈','CARTOON',211600000,'갤럭시  s20 울트라 판매합니다.\r\n직거래는 울릉도 입니다\r\n상태 매우 좋습니다\r\n액정 나가고 전원 안들어오는것만 빼면 S급 입니다\r\n연락 주세요','2022-01-05 22:34:32',7),(81,'kang@kang','갤럭시s20 울트라 S급 판매합니다. 초특급 상태 좋음 매우 쌈','CARTOON',211599999,'갤럭시  s20 울트라 판매합니다.\r\n직거래는 울릉도 입니다\r\n상태 매우 좋습니다\r\n액정 나가고 전원 안들어오는것만 빼면 S급 입니다\r\n연락 주세요','2022-01-05 22:34:36',3),(82,'kang@kang','안녕하세요? 잘 자요... 그럼, 안녕 .....','NOVEL',1001,'그나저나 치토스 먹을 사람','2022-01-05 22:38:12',8),(83,'jeny@test.com','반오십','CARTOON',1000000,'반오십,, 개춘기보다 심한 시기예요. 돌 던져도 이해 해줘요.','2022-01-05 22:45:49',6);
/*!40000 ALTER TABLE `bookpost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookpostfile`
--

DROP TABLE IF EXISTS `bookpostfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookpostfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookpostid` bigint(20) NOT NULL,
  `writeremail` varchar(50) NOT NULL,
  `filepath` text NOT NULL,
  `filename` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bookpostfile_bookpost` (`bookpostid`),
  KEY `FK_bookpostfile_member` (`writeremail`),
  CONSTRAINT `FK_bookpostfile_bookpost` FOREIGN KEY (`bookpostid`) REFERENCES `bookpost` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_bookpostfile_member` FOREIGN KEY (`writeremail`) REFERENCES `member` (`email`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookpostfile`
--

LOCK TABLES `bookpostfile` WRITE;
/*!40000 ALTER TABLE `bookpostfile` DISABLE KEYS */;
INSERT INTO `bookpostfile` VALUES (34,22,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','b11c3f9c-ff6f-4546-900c-4a28efea70ea_10_캡처.JPG'),(35,22,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','69221ce3-e582-4ce5-9a56-3730b5ba53ec_9_코딩마지막.jpg'),(36,23,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','b1e81b3c-58be-4199-8c93-25557ddea3f5_10_20211225_223955.jpg'),(37,23,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','d46c22b9-8770-4d2b-858a-9ae6a4665532_9_20211225_223706.jpg'),(38,23,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','7b1d52b5-a9bf-4afc-944d-cc9effcba50a_8_20211217_202714.jpg'),(39,23,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','bae5326a-d6d0-4e75-bd58-54ce8e2327be_7_20211216_211714.jpg'),(40,23,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','ca863ec5-c834-4ac0-93c2-94b37a41d045_6_SNOW_20211216_152642_349.jpg'),(41,24,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','2490093a-466a-46c9-ae04-272c774bb670_10_pngegg.png'),(42,24,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','44c23729-8f72-4fb2-b2ef-7bdf4dd4d811_9_캡처.JPG'),(43,24,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','f621b6a9-a6dd-4889-94f7-9b270f09bc4a_8_코딩마지막.jpg'),(44,24,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','5bba6063-7eab-4264-a573-2da0a883b1b3_7_캡처.JPG'),(45,25,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','fb38a50b-ebe6-4ac2-90ee-265d08686ce5_10_1640500605363.jpg'),(46,25,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','f9a3d188-9fe5-43e8-8a40-42aae81c1335_9_1640500577320-24.jpg'),(47,25,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','8da22764-1e4b-4cce-952b-ecce5f7baf71_8_1640500577320-7.jpg'),(48,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','57600345-4d9e-4794-bb4f-cd2daaf4607d_10_20211223_205846.jpg'),(49,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','5758362e-3f93-4a27-8eed-473971b7631e_9_20211226_145825.jpg'),(50,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','3b025e58-3e04-44f2-b2fa-f44f775cf443_8_20211226_145651.jpg'),(51,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','1e7b8b34-cc25-4823-acd0-7576114b8c4d_7_20211226_145625.jpg'),(52,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','b7376b8a-5994-449a-95d5-275929aa4283_6_20211226_145631.jpg'),(53,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','b6a7e6fa-1143-403c-88fc-345abffa0919_5_20211226_145630.jpg'),(54,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','44636d3b-f7f2-453d-a2e2-f4e2a5c62d75_4_20211226_145642.jpg'),(55,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','70b16b7b-2526-4028-bb4f-110e9d9ec0ca_3_20211226_145641.jpg'),(56,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','03e266d6-b588-460d-b6f3-e86ef7df8a16_2_20211226_145640.jpg'),(57,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','12b01aa3-7961-4564-8e5d-24006088ba8b_1_20211226_145632.jpg'),(58,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','67663698-0444-41cd-a03b-59bbe6764d15_0_20211226_145817.jpg'),(59,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','445f45ec-c1c0-41c3-b4aa-e8c243a31581_-1_20211226_145651.jpg'),(60,26,'익명@admin','D:\\projectEn\\usedbook\\userUploadImg','d83d2fe6-c78c-4b56-8c3c-7ab1f7e448b9_-2_20211226_145647.jpg'),(69,78,'11@11','D:\\projectEn\\usedbook\\userUploadImg','356660e3-2a14-436f-bc36-941a93664487_9_1640500619149.jpg'),(70,78,'11@11','D:\\projectEn\\usedbook\\userUploadImg','326ba367-1842-402a-9631-da4c266e9310_8_1640500608074.jpg'),(71,79,'hong@test.com','D:\\projectEn\\usedbook\\userUploadImg','8f274c3e-4ff6-448f-9865-c1b6fbec952f_10_i13431108088.jpg'),(72,80,'kang@kang','D:\\projectEn\\usedbook\\userUploadImg','9ec4e512-2fa3-4036-ad1a-d1e22704d706_10_ms_account.jpg'),(73,81,'kang@kang','D:\\projectEn\\usedbook\\userUploadImg','ae966034-f4ea-4884-83b6-de800257f2c8_10_ms_account.jpg'),(74,82,'kang@kang','D:\\projectEn\\usedbook\\userUploadImg','a0576d8d-4ceb-4c06-9f80-cd3f7d928f1c_10_ms_account.jpg'),(75,83,'jeny@test.com','D:\\projectEn\\usedbook\\userUploadImg','4e7ec2c7-be6d-437e-8b8c-6cce0ad5aff4_10_FB_IMG_1641123074520.jpg');
/*!40000 ALTER TABLE `bookpostfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bookpostid` bigint(20) NOT NULL,
  `reid` bigint(20) NOT NULL,
  `retype` bigint(20) NOT NULL,
  `writer` varchar(50) NOT NULL,
  `content` varchar(200) NOT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_comment_bookpost` (`bookpostid`),
  CONSTRAINT `FK_comment_bookpost` FOREIGN KEY (`bookpostid`) REFERENCES `bookpost` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (2,74,0,0,'익명댓글@admin','fwef','2021-12-30 18:42:01'),(3,74,0,0,'익명댓글@admin','<a href=\"#\">hello</a>','2021-12-30 18:47:21'),(4,72,0,0,'익명댓글@admin','vkffuTskdy?','2021-12-30 18:48:35'),(5,72,0,0,'익명댓글@admin','테스트2','2021-12-30 18:51:35'),(6,73,0,0,'익명댓글@admin','테스트3','2021-12-30 18:52:27'),(7,73,0,0,'익명댓글@admin','테스트4','2021-12-30 18:58:03'),(8,73,0,0,'익명댓글@admin','테스트5 78790890','2021-12-30 19:00:12'),(9,70,0,0,'익명댓글@admin','테스트12','2021-12-30 19:00:25'),(10,70,0,0,'익명댓글@admin','테스트13','2021-12-30 19:00:36'),(11,70,0,0,'익명댓글@admin','테스트14','2021-12-30 19:00:48'),(12,70,0,0,'익명댓글@admin','테스트15','2021-12-30 19:00:53'),(13,70,0,0,'익명댓글@admin','16','2021-12-30 19:05:01'),(14,70,0,0,'익명댓글@admin','17','2021-12-30 19:05:10'),(15,70,0,0,'익명댓글@admin','18','2021-12-30 19:05:17'),(16,71,0,0,'익명댓글@admin','1','2021-12-30 19:05:57'),(17,71,0,0,'익명댓글@admin','1','2021-12-30 19:06:01'),(19,71,0,0,'익명댓글@admin','4','2021-12-30 19:08:45'),(20,73,0,0,'익명댓글@admin','테스트6','2021-12-30 20:13:51'),(24,73,0,0,'익명댓글@admin','xptmxm1090','2021-12-30 20:39:50'),(25,69,0,0,'익명댓글@admin','댓글','2021-12-30 20:45:10'),(40,72,4,1,'익명댓글@admin','테스트','2022-01-01 01:33:26'),(51,23,0,0,'익명댓글@admin','ㅎㅇ','2022-01-05 22:32:25'),(52,23,0,0,'익명댓글@admin','?','2022-01-05 22:32:35'),(53,79,0,0,'익명댓글@admin','멋지군여','2022-01-05 22:35:14'),(54,80,0,0,'익명댓글@admin','팔렸나여?','2022-01-05 22:37:48'),(55,82,0,0,'익명댓글@admin','치토스 저요','2022-01-05 22:39:12'),(56,83,0,0,'익명댓글@admin','반오십 금지어요','2022-01-05 22:46:56'),(57,83,56,1,'익명댓글@admin','하프 오 텐,,,,,,','2022-01-05 22:47:47');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'11@11','13','11'),(2,'22@22','22','22'),(3,'33@33','33','33'),(8,'pig@naver.com','뱃살공쥬','ansthwjd'),(9,'kang@kang','강강','21711011'),(10,'hong@test.com','대왕쥐가오리','dlwlghd464'),(11,'jeny@test.com','씬제니','Wldms@981103'),(12,'pagetest@test','pagetest','pagetest'),(13,'test@test.com','tester','tester'),(14,'익명@admin','admin','admin');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-08 16:59:13