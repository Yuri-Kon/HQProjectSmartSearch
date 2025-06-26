-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: hgd
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `bill_id` int NOT NULL,
  `bill_time` varchar(20) DEFAULT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `bill_income` double DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `company_name` varchar(20) NOT NULL,
  `company_key` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `company_tele` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES ('HQcompany','qwertyuiop','HQwins','123-333-444'),('Dajun','qweasd','Dajun','199-9999-999'),('QWcompany','aaaaaaaaa','qw','15978129878');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` varchar(20) NOT NULL,
  `exe_id` varchar(20) DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `course_info` varchar(150) DEFAULT NULL,
  `course_fee` float DEFAULT NULL,
  `course_state` varchar(20) DEFAULT NULL,
  `course_start` varchar(20) DEFAULT NULL,
  `course_end` varchar(20) DEFAULT NULL,
  `course_place` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES ('AS2023','2022111000',15462,'英语','好',11.11,'报名中','2021-12-12','2021-12-30','正心231'),('AS2024','2022111000',54615,'nihao','sds',22.22,'报名中','2004-3-3','2005-6-6','sdji'),('CS101','2024112001',101,'人工智能基础','适合零基础入门，内容包括搜索、分类与神经网络',100,'报名中','2025-07-01','2025-08-15','正心231'),('CS102','2024112002',102,'数据科学导论','偏重实战编程，使用Python进行数据清洗和分析',120,'报名中','2025-07-03','2025-08-20','正心212'),('CS103','2024112003',103,'数学建模实训','适合数学基础扎实的学生，含大量案例分析',90,'报名已满','2025-07-05','2025-08-10','致知101'),('CS104','2024112004',104,'自然语言处理','内容涵盖语言模型、语义理解、ChatGPT简介',150,'报名中','2025-06-28','2025-08-12','信义401'),('CS105','2024112005',105,'机器人操作与控制','涉及ROS框架与实际操作，需一定C++基础',180,'报名中','2025-07-10','2025-08-25','工学楼A102'),('CS106','2024112006',101,'AI课程进阶','以强化学习与深度学习为主，适合有基础者',200,'报名中','2025-07-15','2025-08-30','正心231'),('CS107','2024112007',101,'AI与教育','聚焦教育场景下的智能化应用，课程项目丰富',130,'报名中','2025-07-08','2025-08-22','创新楼305'),('CS201','2024112008',201,'人工智能入门','适合初学者，内容包括智能系统基本概念',99,'报名中','2025-07-02','2025-08-10','教学楼101'),('CS202','2024112009',202,'数据分析基础','介绍数据处理方法，适合零基础学习者',88,'报名中','2025-07-03','2025-08-15','实验楼202'),('CS203','2024112010',203,'数据库系统概论','内容包括关系模型、SQL语句',109,'报名中','2025-07-05','2025-08-20','教学楼103'),('CS204','2024112011',204,'计算机网络技术','学习网络协议、传输机制',120,'报名中','2025-07-06','2025-08-25','实验楼201'),('CS205','2024112012',201,'人工智能应用','课程包含图像识别与语言处理基础',130,'报名中','2025-07-07','2025-08-28','教学楼105'),('LD2022','2022111000',15462,'音乐(鼓)','很好',11.11,'报名中','2022-06-04','2022-08-06','致知213');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluate` (
  `eva_id` int NOT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  `stu_id` int DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `eva_score` int DEFAULT NULL,
  `eva_content` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`eva_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

LOCK TABLES `evaluate` WRITE;
/*!40000 ALTER TABLE `evaluate` DISABLE KEYS */;
INSERT INTO `evaluate` VALUES (21293,'AS2023',12341,15462,1,'213');
/*!40000 ALTER TABLE `evaluate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `executor`
--

DROP TABLE IF EXISTS `executor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `executor` (
  `exe_id` int NOT NULL,
  `exe_name` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`exe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `executor`
--

LOCK TABLES `executor` WRITE;
/*!40000 ALTER TABLE `executor` DISABLE KEYS */;
INSERT INTO `executor` VALUES (12343435,'Lana','sdhjs'),(2022111000,'soyo','Cry');
/*!40000 ALTER TABLE `executor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grade` (
  `grade_id` int NOT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  `stu_id` int DEFAULT NULL,
  `stu_score` int DEFAULT NULL,
  `teacher_evaluate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`grade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (12312,'AS2023',12341,11,'asd');
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `report_id` int NOT NULL,
  `report_start` varchar(10) DEFAULT NULL,
  `report_end` varchar(10) DEFAULT NULL,
  `report_income` float DEFAULT NULL,
  `teacher_num` int DEFAULT NULL,
  `course_num` int DEFAULT NULL,
  `stu_num` int DEFAULT NULL,
  `company_num` int DEFAULT NULL,
  `executor_num` int DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signin`
--

DROP TABLE IF EXISTS `signin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signin` (
  `signin_id` int NOT NULL,
  `stu_id` int DEFAULT NULL,
  `course_id` varchar(20) DEFAULT NULL,
  `signin_OK` int DEFAULT NULL,
  `signin_state` int DEFAULT NULL,
  PRIMARY KEY (`signin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signin`
--

LOCK TABLES `signin` WRITE;
/*!40000 ALTER TABLE `signin` DISABLE KEYS */;
/*!40000 ALTER TABLE `signin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signup`
--

DROP TABLE IF EXISTS `signup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signup` (
  `signup_id` int NOT NULL,
  `stu_id` int DEFAULT NULL,
  `course_id` varchar(50) DEFAULT NULL,
  `signup_state` int DEFAULT NULL,
  PRIMARY KEY (`signup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signup`
--

LOCK TABLES `signup` WRITE;
/*!40000 ALTER TABLE `signup` DISABLE KEYS */;
INSERT INTO `signup` VALUES (33333,12341,'LD2022',0),(40934,12341,'AS2023',0);
/*!40000 ALTER TABLE `signup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `stu_id` int NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `stu_name` varchar(20) DEFAULT NULL,
  `stu_company` varchar(20) DEFAULT NULL,
  `stu_position` varchar(20) DEFAULT NULL,
  `stu_level` varchar(20) DEFAULT NULL,
  `stu_email` varchar(20) DEFAULT NULL,
  `stu_tele` varchar(20) DEFAULT NULL,
  `stu_state` int DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (12341,'GASSFY','HMZ','Dajun','垃圾程序员','high','123@321.com','188-2312-321',0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher` (
  `teacher_id` int NOT NULL,
  `teacher_name` varchar(50) DEFAULT NULL,
  `teacher_position` varchar(50) DEFAULT NULL,
  `teacher_email` varchar(50) DEFAULT NULL,
  `teacher_tele` varchar(50) DEFAULT NULL,
  `teacher_field` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES (101,'张伟','副教授','zhangwei@hit.edu.cn','13900001111','人工智能','zhangwei101'),(102,'李娜','讲师','lina@hit.edu.cn','13900002222','数据科学','lina102'),(103,'张伟','教授','zhangwei_math@hit.edu.cn','13900003333','数学建模','zhangweimath103'),(104,'王强','副教授','wangqiang@hit.edu.cn','13900004444','自然语言处理','wangqiang104'),(105,'周玲','讲师','zhouling@hit.edu.cn','13900005555','机器人学','zhouling105'),(201,'王芳','副教授','wangfang@hit.edu.cn','13900006666','人工智能','wangfang201'),(202,'刘洋','讲师','liuyang@hit.edu.cn','13900007777','数据分析','liuyang202'),(203,'赵强','副教授','zhaoqiang@hit.edu.cn','13900008888','数据库','zhaoqiang203'),(204,'孙悦','讲师','sunyue@hit.edu.cn','13900009999','计算机网络','sunyue204'),(15462,'taki','high-teahcer','mygo@123.com','114514','math','panda'),(54615,'ano_tokyo','资深讲师','123@132.com','133-3333-333','java','WCDE');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `usertype` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('GASSFY','123456','student'),('panda','123456','teacher'),('Dajun','123456','company'),('qjk','123','student'),('qw','123','company'),('WCDE','123','teacher'),('sdhjs','123','executor'),('Cry','777','executor'),('Power','123456','manager');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'hgd'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-26  0:06:44
