-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: db_sophosbank
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `identification_type_id` int NOT NULL,
  `identification_number` varchar(50) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `second_last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `creation_date` datetime NOT NULL,
  `creation_user_id` int NOT NULL,
  `modification_date` datetime DEFAULT NULL,
  `modification_user_id` int DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`),
  KEY `fk_identification_type_id_idx` (`identification_type_id`),
  KEY `fk_creation_user_id_idx` (`creation_user_id`),
  KEY `fk_modification_user_id_idx` (`modification_user_id`),
  CONSTRAINT `fk_creation_user_id` FOREIGN KEY (`creation_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_identification_type_id` FOREIGN KEY (`identification_type_id`) REFERENCES `identification_types` (`identification_type_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_modification_user_id` FOREIGN KEY (`modification_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (24,1,'1085292930','JAIRO','DAVID','VELASCO','CADENA','fasd@gasdfsa.co','1991-11-18','2022-12-17 15:13:09',1,NULL,NULL,'1'),(25,1,'1085292931','KEVIN','ESTEBAN','VELASCO','CADENA','fqafsd@fsdaedd.com','1998-09-06','2022-12-17 15:14:36',1,'2022-12-26 16:37:49',1,'1'),(26,1,'10852924234','ANDRES',NULL,'CASTRO',NULL,'davo.vko@gmail.com','1997-11-29','2022-12-19 09:11:23',1,'2022-12-26 16:18:55',1,'1'),(27,2,'1085292932','KEVIN','DAVID','CASTRO','CARDONA','fqafsd@fsdggdd.co','1998-12-01','2022-12-19 09:19:43',1,NULL,NULL,'0'),(28,1,'134123412341','JOSE','PABLO','ACOSTA','ARIAS','dfasd@fgsda.co','1990-12-16','2022-12-23 12:29:16',1,NULL,NULL,'1'),(29,1,'1234567876','ERNESTO',NULL,'PEREZ','CARDENAS','perez@as.co','1998-12-12','2022-12-24 11:47:22',1,'2023-01-02 17:03:33',1,'1'),(30,1,'9685857833','JAIR',NULL,'CASTRO',NULL,'dsso.vkffo@gmail.com','1990-12-13','2022-12-30 09:16:28',1,NULL,1,'0'),(31,1,'452342344','FRANCISCO','JAVIER','CORDOBA','PEREZ','francisco@gmail.com','1992-01-21','2023-01-02 09:07:41',1,'2023-01-02 09:08:29',1,'0'),(32,1,'1085292939','DANIEL',NULL,'MIDEROS',NULL,'daniel.mideros@gmail.com','1992-01-01','2023-01-02 12:07:57',1,NULL,1,'0');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identification_types`
--

DROP TABLE IF EXISTS `identification_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `identification_types` (
  `identification_type_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`identification_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identification_types`
--

LOCK TABLES `identification_types` WRITE;
/*!40000 ALTER TABLE `identification_types` DISABLE KEYS */;
INSERT INTO `identification_types` VALUES (1,'CEDULA DE CIUDADANIA'),(2,'CEDULA DE EXTRANJERIA'),(3,'PASAPORTE');
/*!40000 ALTER TABLE `identification_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_types`
--

DROP TABLE IF EXISTS `product_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_types` (
  `product_type_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`product_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_types`
--

LOCK TABLES `product_types` WRITE;
/*!40000 ALTER TABLE `product_types` DISABLE KEYS */;
INSERT INTO `product_types` VALUES (1,'CUENTA DE AHORRO'),(2,'CUENTA CORRIENTE');
/*!40000 ALTER TABLE `product_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `product_type_id` int NOT NULL,
  `account_number` varchar(45) NOT NULL,
  `status_account_id` int NOT NULL,
  `balance` double NOT NULL,
  `available_balance` double NOT NULL,
  `gmf_exempt` tinyint NOT NULL,
  `creation_date` datetime NOT NULL,
  `creation_user_id` int NOT NULL,
  `modification_date` datetime DEFAULT NULL,
  `modification_user_id` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_customer_id_idx` (`customer_id`),
  KEY `fk_creation_user_id_idx` (`creation_user_id`),
  KEY `fk_product_type_id_idx` (`product_type_id`),
  KEY `fk_state_account_id_idx` (`status_account_id`),
  KEY `fk_modification_user_id_product_idx` (`modification_user_id`),
  CONSTRAINT `fk_creation_user_product_id` FOREIGN KEY (`creation_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_modification_user_id_product` FOREIGN KEY (`modification_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_product_type_id` FOREIGN KEY (`product_type_id`) REFERENCES `product_types` (`product_type_id`),
  CONSTRAINT `fk_state_account_id` FOREIGN KEY (`status_account_id`) REFERENCES `status_account` (`status_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (29,24,1,'4600000001',2,2000,1992,0,'2022-12-19 09:35:03',1,'2023-01-02 14:13:32',1),(30,24,2,'2300000001',1,-3032.064,0,0,'2022-12-19 09:35:18',1,'2023-01-02 14:09:30',1),(31,25,1,'4600000031',1,0,0,1,'2022-12-19 16:18:38',1,'2022-12-19 17:39:26',1),(32,29,1,'4600000032',1,0,0,0,'2022-12-24 21:05:13',1,'2023-01-02 09:47:43',1),(33,29,2,'2300000033',3,0,0,0,'2022-12-24 21:05:23',1,'2023-01-02 09:21:24',1),(34,29,1,'4600000034',1,0,0,1,'2023-01-02 09:20:56',1,'2023-01-02 09:47:43',1),(35,24,1,'4600000035',3,0,0,0,'2023-01-02 12:16:15',1,'2023-01-02 12:16:15',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_account`
--

DROP TABLE IF EXISTS `status_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_account` (
  `status_account_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`status_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_account`
--

LOCK TABLES `status_account` WRITE;
/*!40000 ALTER TABLE `status_account` DISABLE KEYS */;
INSERT INTO `status_account` VALUES (1,'ACTIVA'),(2,'INACTIVA'),(3,'CANCELADA');
/*!40000 ALTER TABLE `status_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_types`
--

DROP TABLE IF EXISTS `transaction_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_types` (
  `transaction_types_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`transaction_types_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_types`
--

LOCK TABLES `transaction_types` WRITE;
/*!40000 ALTER TABLE `transaction_types` DISABLE KEYS */;
INSERT INTO `transaction_types` VALUES (1,'CONSIGNACIÓN'),(2,'RETIRO'),(3,'TRANSFERENCIA ENTRE CUENTAS');
/*!40000 ALTER TABLE `transaction_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `transaction_type_id` int NOT NULL,
  `product_id` int NOT NULL,
  `transaction_date` datetime NOT NULL,
  `description` varchar(45) NOT NULL,
  `transaction_value` double NOT NULL,
  `destination_product_id` int DEFAULT NULL,
  `origin_product_id` int DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_product_id_idx` (`product_id`),
  KEY `fk_destination_product_id_idx` (`destination_product_id`),
  KEY `fk_transaction_type_id_idx` (`transaction_type_id`),
  CONSTRAINT `fk_destination_product_id` FOREIGN KEY (`destination_product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_type_id` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_types` (`transaction_types_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,1,30,'2023-01-02 14:06:43','CONSIGNACION 1',1000,NULL,NULL),(2,2,30,'2023-01-02 14:09:00','RETIRO',-3000,NULL,NULL),(3,2,30,'2023-01-02 14:09:00','COBRO GMF / 4X1000',-12,NULL,NULL),(4,3,30,'2023-01-02 14:09:30','TRANSFERENCIA',-1000,29,NULL),(5,2,30,'2023-01-02 14:09:30','COBRO GMF / 4X1000',-4,NULL,NULL),(6,1,29,'2023-01-02 14:09:30','TRANSFERENCIA CUENTA N° 2300000001',1000,NULL,30),(7,1,29,'2023-01-02 14:13:32','CONSIGNACION ',1000,NULL,NULL);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2a$10$GhYk.7IIbtRcdWq0xquBouoX7BJqDYQikU6FYA2K2NPaxIbmYQ8.y','davo.vko@gmail.com','David Velasco'),(2,'user2','$2a$10$yiG2Nl4gnA8v35MM1.mQkuDpnhi1m7YYZ/HScl5rpbk3t8Bns4VQa','joe.vko@gmail.com','Joe Velasco');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-05 16:29:40
