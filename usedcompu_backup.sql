-- MariaDB dump 10.19  Distrib 10.6.5-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: usedCompuCompo
-- ------------------------------------------------------
-- Server version	10.6.5-MariaDB

-- Table structure for table `compucategory`
--

DROP TABLE IF EXISTS `compucategory`;
create table compucategory
(
  id   bigint auto_increment
    primary key,
  name varchar(50) not null,
  constraint name
    unique (name)
)
  engine = InnoDB
  charset = utf8mb3;

LOCK TABLES `compucategory` WRITE;
INSERT INTO `compucategory` VALUES (1,'GRAPHIC'),(2,'CPU'),(3,'RAM');
UNLOCK TABLES;


--
-- Table structure for table `member`
--
DROP TABLE IF EXISTS `member`;
create table member
(
  id       bigint auto_increment
    primary key,
  email    varchar(50) not null,
  name     varchar(50) not null,
  password varchar(50) not null,
  constraint email
    unique (email)
)
  engine = InnoDB
  charset = utf8mb3;


--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
INSERT INTO `member` VALUES (1,'test@test','jj','1234'),(2,'익명@admin','admin','admin'),(3,'jeonb@gmail.com','jeon','j1234');
UNLOCK TABLES;

--
-- Table structure for table `compupost`
--

DROP TABLE IF EXISTS `compupost`;
create table compupost
(
  id               bigint auto_increment
    primary key,
  writeremail      varchar(50)   not null,
  compuname        varchar(50)   not null,
  compucategory    varchar(50)   not null,
  compuprice       int           not null,
  compudescription text          not null,
  createtime       datetime      not null,
  viewcount        int default 0 not null,
  constraint FK_compupost_compucategory_2
    foreign key (compucategory) references compucategory (name)
      on update cascade,
  constraint FK_compupost_member
    foreign key (writeremail) references member (email)
      on update cascade,
  constraint pricecheck
    check (`compuprice` >= 1000)
)
  engine = InnoDB
  charset = utf8mb3;

--
-- Dumping data for table `compupost`
--

LOCK TABLES `compupost` WRITE;
INSERT INTO `compupost` VALUES (1,'익명@admin','최상급RTX4070','GRAPHIC',700000,'최상급 RTX4070 팜 님선','2023-05-27 13:38:10',1),(2,'익명@admin','AMD 5600X','CPU',220000,'수율 좋은 5600X 팝니다','2023-05-27 13:39:10',5),(3,'익명@admin','삼성램 팜','RAM',100000,'삼성D램 16GB 팝니다','2023-05-27 13:48:10',4);
UNLOCK TABLES;

--
-- Table structure for table `compupostfile`
--
DROP TABLE IF EXISTS `compupostfile`;
create table compupostfile
(
  id          int auto_increment
    primary key,
  compupostid bigint      not null,
  writeremail varchar(50) not null,
  filename    text        not null,
  constraint FK_compupostfile_compupost
    foreign key (compupostid) references compupost (id)
      on delete cascade,
  constraint FK_compupostfile_member
    foreign key (writeremail) references member (email)
      on update cascade
)
  engine = InnoDB
  charset = utf8mb3;

--
-- Dumping data for table `compupostfile`
--

LOCK TABLES `compupostfile` WRITE;
INSERT INTO `compupostfile` VALUES (1,1,'익명@admin','C:\\project\\usedcompu\\uploadImg','b11c3f9c-ff6f-4546-900c-4a28efea70ea_10_캡처.JPG'),(2,2,'익명@admin','C:\\project\\usedcompu\\uploadImg','b11c3f9c-ff6f-4546-900c-4a28efea70ea_10_캡처.JPG'),(3,3,'익명@admin','C:\\project\\usedcompu\\uploadImg','b11c3f9c-ff6f-4546-900c-4a28efea70ea_10_캡처.JPG');
UNLOCK TABLES;

--
-- Table structure for table `comment`
--
DROP TABLE IF EXISTS `COMMENT`;
create table comment
(
  id          bigint auto_increment
    primary key,
  compupostid bigint       not null,
  reid        bigint       not null,
  retype      bigint       not null,
  writer      varchar(50)  not null,
  content     varchar(200) not null,
  createtime  datetime     not null,
  constraint FK_comment_compupost
    foreign key (compupostid) references compupost (id)
      on delete cascade
)
  engine = InnoDB
  charset = utf8mb3;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
INSERT INTO `comment` VALUES (1,1,0,0,'익명댓글@admin','hihi','2023-05-30 18:42:01'),(2,2,0,0,'익명댓글@admin','hihiyo','2023-05-30 14:02:01'),(3,3,0,0,'익명댓글@admin','hihitest','2023-05-31 17:42:01');
UNLOCK TABLES;

