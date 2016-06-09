CREATE DATABASE  IF NOT EXISTS `black_jack` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `black_jack`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: black_jack
-- ------------------------------------------------------
-- Server version	5.5.23

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
-- Table structure for table `тable_type`
--

DROP TABLE IF EXISTS `тable_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `тable_type` (
  `table_type_id` int(11) NOT NULL,
  `min_bet` int(11) NOT NULL,
  `max_bet` int(11) NOT NULL,
  PRIMARY KEY (`table_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `тable_type`
--

LOCK TABLES `тable_type` WRITE;
/*!40000 ALTER TABLE `тable_type` DISABLE KEYS */;
INSERT INTO `тable_type` VALUES (1,10,100),(2,50,500),(3,100,1000);
/*!40000 ALTER TABLE `тable_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `player_id` int(11) NOT NULL,
  `login` varchar(25) NOT NULL,
  `nickname` varchar(15) NOT NULL,
  `password` varchar(45) NOT NULL,
  `balance` int(11) NOT NULL,
  PRIMARY KEY (`player_id`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Den@Den','Den','123',10000),(2,'Ned@Ned','Ned','321',10000);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bet-player-communication`
--

DROP TABLE IF EXISTS `bet-player-communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet-player-communication` (
  `bet_id` int(11) NOT NULL,
  `table_id` int(11) NOT NULL,
  `bet_size` int(11) NOT NULL,
  PRIMARY KEY (`bet_id`),
  KEY `fk_otstavkiigroka_stoligrok_com_idx` (`table_id`),
  CONSTRAINT `fk_otstavkiigroka_stoligrok_com` FOREIGN KEY (`table_id`) REFERENCES `table-player-communication` (`table_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bet-player-communication`
--

LOCK TABLES `bet-player-communication` WRITE;
/*!40000 ALTER TABLE `bet-player-communication` DISABLE KEYS */;
INSERT INTO `bet-player-communication` VALUES (1,1,500);
/*!40000 ALTER TABLE `bet-player-communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table-player-communication`
--

DROP TABLE IF EXISTS `table-player-communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table-player-communication` (
  `table_id` int(11) NOT NULL,
  `table_type_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  PRIMARY KEY (`table_id`),
  KEY `fk_player_table_player_com_idx` (`player_id`),
  KEY `fk_tipstola_stoligrok_com_idx` (`table_type_id`),
  CONSTRAINT `fk_player_table_player_com` FOREIGN KEY (`player_id`) REFERENCES `account` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tipstola_stoligrok_com` FOREIGN KEY (`table_type_id`) REFERENCES `тable_type` (`table_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table-player-communication`
--

LOCK TABLES `table-player-communication` WRITE;
/*!40000 ALTER TABLE `table-player-communication` DISABLE KEYS */;
INSERT INTO `table-player-communication` VALUES (1,2,1);
/*!40000 ALTER TABLE `table-player-communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `table_game`
--

DROP TABLE IF EXISTS `table_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `table_game` (
  `step_id` int(11) NOT NULL,
  `bet_id` int(11) NOT NULL,
  `cards` varchar(20) NOT NULL,
  `player_type` varchar(6) NOT NULL,
  PRIMARY KEY (`step_id`),
  KEY `fk_stavka_stoligra_stavkaigrok_com_idx` (`bet_id`),
  CONSTRAINT `fk_stavka_stoligra_stavkaigrok_com` FOREIGN KEY (`bet_id`) REFERENCES `bet-player-communication` (`bet_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `table_game`
--

LOCK TABLES `table_game` WRITE;
/*!40000 ALTER TABLE `table_game` DISABLE KEYS */;
INSERT INTO `table_game` VALUES (1,1,'FIVE.CLUB','diller'),(2,1,'SEVEN.DIAMOND','player'),(3,1,'ACE.HEART','diller'),(4,1,'QUEEN.SPADE','player'),(5,1,'THREE.HEART','player'),(6,1,'FOUR.HEART','diller');
/*!40000 ALTER TABLE `table_game` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-08 21:06:19
