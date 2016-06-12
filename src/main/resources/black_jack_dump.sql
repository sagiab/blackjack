CREATE DATABASE  IF NOT EXISTS `black_jack` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `black_jack`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: black_jack
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `player_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(25) NOT NULL,
  `nickname` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Den@Den','Den','$2a$10$fqXsfEVDctAI/39sk4BUFu3BUt9Lz1/dEg4ODBkPYbDrIyuGLKb5m',10000),(2,'Ned@Ned','Ned','$2a$10$fqXsfEVDctAI/39sk4BUFu3BUt9Lz1/dEg4ODBkPYbDrIyuGLKb5m',10000);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bet_player_communication`
--

DROP TABLE IF EXISTS `bet_player_communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet_player_communication` (
  `bet_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL,
  `bet_size` int(11) NOT NULL,
  PRIMARY KEY (`bet_id`),
  KEY `fk_otstavkiigroka_stoligrok_com_idx` (`table_id`),
  CONSTRAINT `fk_otstavkiigroka_stoligrok_com` FOREIGN KEY (`table_id`) REFERENCES `table_player_communication` (`table_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bet_player_communication`
--

LOCK TABLES `bet_player_communication` WRITE;
/*!40000 ALTER TABLE `bet_player_communication` DISABLE KEYS */;
INSERT INTO `bet_player_communication` VALUES (5,1,500),(6,1,500);
/*!40000 ALTER TABLE `bet_player_communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_game`
--

DROP TABLE IF EXISTS `table_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_game` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT,
  `bet_id` int(11) NOT NULL,
  `cards` varchar(20) NOT NULL,
  `player_type` varchar(6) NOT NULL,
  PRIMARY KEY (`step_id`),
  KEY `fk_stavka_stoligra_stavkaigrok_com_idx` (`bet_id`),
  CONSTRAINT `fk_stavka_stoligra_stavkaigrok_com` FOREIGN KEY (`bet_id`) REFERENCES `bet_player_communication` (`bet_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_game`
--

LOCK TABLES `table_game` WRITE;
/*!40000 ALTER TABLE `table_game` DISABLE KEYS */;
INSERT INTO `table_game` VALUES (124,5,'SIX.DIAMOND','PLAYER'),(125,5,'NINE.CLUB','DIALER'),(126,5,'TEN.SPADE','PLAYER'),(127,5,'SIX.CLUB','DIALER'),(128,5,'THREE.CLUB','PLAYER'),(129,5,'KING.SPADE','PLAYER'),(130,5,'ACE.HEART','PLAYER'),(131,5,'SIX.CLUB','DIALER'),(132,5,'EIGHT.CLUB','PLAYER'),(133,5,'KING.DIAMOND','DIALER'),(134,5,'FOUR.SPADE','PLAYER');
/*!40000 ALTER TABLE `table_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_player_communication`
--

DROP TABLE IF EXISTS `table_player_communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_player_communication` (
  `table_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_type_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`table_id`),
  KEY `fk_player_table_player_com_idx` (`player_id`),
  KEY `fk_tipstola_stoligrok_com_idx` (`table_type_id`),
  CONSTRAINT `fk_player_table_player_com` FOREIGN KEY (`player_id`) REFERENCES `account` (`player_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_tipstola_stoligrok_com` FOREIGN KEY (`table_type_id`) REFERENCES `table_type` (`table_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_player_communication`
--

LOCK TABLES `table_player_communication` WRITE;
/*!40000 ALTER TABLE `table_player_communication` DISABLE KEYS */;
INSERT INTO `table_player_communication` VALUES (1,2,1);
/*!40000 ALTER TABLE `table_player_communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_type`
--

DROP TABLE IF EXISTS `table_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_type` (
  `table_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `min_bet` int(11) NOT NULL,
  `max_bet` int(11) NOT NULL,
  PRIMARY KEY (`table_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_type`
--

LOCK TABLES `table_type` WRITE;
/*!40000 ALTER TABLE `table_type` DISABLE KEYS */;
INSERT INTO `table_type` VALUES (1,10,100),(2,50,500),(3,100,1000);
/*!40000 ALTER TABLE `table_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-12 14:51:34
