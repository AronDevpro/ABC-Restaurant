-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurants
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `facilities`
--

DROP TABLE IF EXISTS `facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `restaurantID` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `category` varchar(100) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `restaurantID` (`restaurantID`),
  CONSTRAINT `facilities_ibfk_1` FOREIGN KEY (`restaurantID`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilities`
--

LOCK TABLES `facilities` WRITE;
/*!40000 ALTER TABLE `facilities` DISABLE KEYS */;
INSERT INTO `facilities` VALUES (1,1,'Indoor Dining','Main dining space with tables and chairs.','dining','/uploads/facility/indoor.jpg','active','2024-09-04 14:55:09','2024-09-04 15:20:01'),(2,2,'Private Dining Rooms','For special events or private gatherings','dining','/uploads/facility/4.jpg','active','2024-09-04 15:09:22','2024-09-04 15:09:22'),(3,4,'Bar Area','Space dedicated to serving drinks.','bar','/uploads/facility/bar.jpg','active','2024-09-04 15:09:51','2024-09-04 15:20:01'),(4,6,'Play Areas','Kid-friendly zones with games and toys','kidZone','/uploads/facility/5.jpg','active','2024-09-04 15:10:58','2024-09-04 15:10:58'),(5,1,'Roof Top Dining','Roof Top Dining Areas for private gatherings.','dining','/uploads/facility/3.jfif','active','2024-09-04 15:11:43','2024-09-04 15:11:43'),(6,1,'Sea Face Dining Area','Dedicated Dining area for Sea Lovers.','dining','/uploads/facility/2.jpg','active','2024-09-04 15:12:21','2024-09-04 15:12:21'),(7,5,'Valet Service','An area for valet parking','valet','/uploads/facility/6.jpg','active','2024-09-04 15:14:05','2024-09-04 15:14:05');
/*!40000 ALTER TABLE `facilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gallery`
--

DROP TABLE IF EXISTS `gallery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gallery` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text,
  `category` varchar(100) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gallery`
--

LOCK TABLES `gallery` WRITE;
/*!40000 ALTER TABLE `gallery` DISABLE KEYS */;
INSERT INTO `gallery` VALUES (1,'Food','Food','Food','/uploads/gallery/1.png','active','2024-09-03 23:15:13','2024-09-03 23:15:13'),(2,'Gallery 2','Gallery 2','Food','/uploads/gallery/2.jpg','active','2024-09-03 23:18:47','2024-09-03 23:18:47'),(3,'Gallery 3','Gallery 3','Food','/uploads/gallery/3.png','active','2024-09-03 23:18:59','2024-09-03 23:18:59'),(4,'Gallery 4','Gallery 4','Food','/uploads/gallery/4.png','active','2024-09-03 23:19:15','2024-09-03 23:19:15'),(5,'Gallery 5','Gallery 5','Food','/uploads/gallery/5.jpg','active','2024-09-03 23:19:37','2024-09-03 23:19:37'),(6,'Gallery 6','Gallery 6','Food','/uploads/gallery/6.png','active','2024-09-03 23:19:47','2024-09-03 23:19:47'),(8,'Sushi','Sushi','ssss','/uploads/gallery/Screenshot_4.png','suspend','2024-09-10 13:56:59','2024-09-10 13:57:03');
/*!40000 ALTER TABLE `gallery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `offerName` varchar(50) DEFAULT NULL,
  `promoCode` varchar(20) DEFAULT NULL,
  `discountPercentage` decimal(5,2) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (1,'Private Dining Offer','Dinner-10',30.00,'party','/uploads/offers/private dining offer.jpg','active','2024-09-04 16:18:52','2024-09-04 16:18:52'),(2,'Cyber Monday','CYB-MON',10.00,'promotion','/uploads/offers/cyber monday.jpg','active','2024-09-04 16:19:23','2024-09-04 16:19:23'),(3,'Monday Special','MON-SPEC',25.00,'delivery','/uploads/offers/monday offer.jpg','active','2024-09-04 16:20:11','2024-09-04 16:20:11'),(4,'Special Offer for Events','SPEC-EVE',40.00,'event','/uploads/offers/special-offer.jpg','active','2024-09-04 16:20:46','2024-09-04 16:26:42'),(5,'Black Friday Offer','Black-30',30.00,'promotion','/uploads/offers/black friday.png','active','2024-09-04 16:21:11','2024-09-04 16:21:11');
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `orderId` int NOT NULL,
  `item` varchar(50) NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`orderId`,`item`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES (1,'Chicken Kottu',3),(1,'Sushi',3),(2,'Chicken Kottu',3),(2,'Sushi',3),(3,'Chicken Kottu',3),(3,'Sushi',3),(4,'Sushi',3),(5,'Sushi',3);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderUUID` char(36) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phoneNumber` varchar(100) NOT NULL,
  `deliverDate` date NOT NULL,
  `deliverTime` time NOT NULL,
  `deliveryMethod` varchar(30) NOT NULL,
  `restaurantSelect` varchar(100) DEFAULT NULL,
  `streetAddress` varchar(150) DEFAULT NULL,
  `zip` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `customerId` int DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'pending'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'93979e7b-ca22-4b52-ba28-31015e66059a','Arosha','Sahan','ideatidy@gmail.com','0712252125','2024-09-07','16:38:00','Dine In','Flavorscape',NULL,NULL,NULL,7500.00,14,'confirm','2024-09-07 16:38:01','2024-09-07 22:34:30'),(2,'24ed1d4a-c555-41b2-a882-85ca4f37a384','Arosha','Sahan','ideatidy@gmail.com','0712252125','2024-09-07','16:40:00','Dine In','Flavorscape',NULL,NULL,NULL,7500.00,14,'delivered','2024-09-07 16:40:47','2024-09-07 22:44:32'),(3,'f8aa4c0d-3a04-4d73-834f-f1329901a512','Arosha','Sahan','ideatidy@gmail.com','0712252125','2024-09-07','16:42:00','Dine In','Flavorscape',NULL,NULL,NULL,7500.00,14,'cancel','2024-09-07 16:42:24','2024-09-07 22:45:33'),(4,'84b6a41a-b3ac-4e1e-8028-a29342048ac6','Arosha','Sahan','ideatidy@gmail.com','0711234567','2024-09-01','14:00:00','Dine In','Flavorscape',NULL,NULL,NULL,4500.00,69,'pending','2024-09-07 21:39:41','2024-09-07 21:39:41'),(5,'519752c5-8d43-4ffa-aee6-ccb56ddb9f08','Arosha','Sahan','ideatidy@gmail.com','0711234567','2024-09-01','14:00:00','Deliver',NULL,'399','50000','Anuradhapura',4500.00,69,'pending','2024-09-07 21:41:37','2024-09-07 21:41:37');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `category` varchar(100) DEFAULT NULL,
  `imagePath` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Sushi','Japanese Sushi','sushi','/uploads/products/sushi.jpg',1500.00,'active','2024-09-04 16:41:22','2024-09-04 16:48:46'),(2,'Chicken Kottu','Chicken Kottu','rice & kottu','/uploads/products/chicken kottu.jfif',1000.00,'active','2024-09-04 16:44:44','2024-09-04 16:44:44'),(3,'Mix Kottu','Mix Kottu','rice & kottu','/uploads/products/kottu.jfif',1500.00,'active','2024-09-04 16:45:00','2024-09-04 16:45:00'),(4,'Vegetable Rice','Vegetable Rice','rice & kottu','/uploads/products/veg rice.jpg',800.00,'active','2024-09-04 16:45:21','2024-09-04 16:45:21'),(5,'Mix Fried Rice','Mix Fried Rice','rice & kottu','/uploads/products/mix rice.jpg',2200.00,'active','2024-09-04 16:45:44','2024-09-04 16:45:44'),(6,'Chicken Fried Rice','Chicken Fried Rice','rice & kottu','/uploads/products/fried rice.jpg',1200.00,'active','2024-09-04 16:46:05','2024-09-04 16:46:05'),(7,'Watalappan','Watalappan','dessert','/uploads/products/watallapan.jpg',500.00,'active','2024-09-04 16:46:25','2024-09-04 16:46:25'),(8,'Caramel Pudding','Caramel Pudding','dessert','/uploads/products/keramel.jpg',380.00,'active','2024-09-04 16:46:45','2024-09-04 16:46:45'),(9,'Sprite','Sprite','food & beverage','/uploads/products/sprite.jfif',180.00,'active','2024-09-04 16:47:02','2024-09-04 16:47:02'),(10,'Barista Coffee','Barista Coffee','food & beverage','/uploads/products/coffee.jfif',800.00,'active','2024-09-04 16:48:00','2024-09-04 16:48:00'),(11,'King Burger','King Burger','bakery','/uploads/products/burger.jpg',800.00,'active','2024-09-04 16:48:28','2024-09-04 16:48:28');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `queries`
--

DROP TABLE IF EXISTS `queries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `queries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `subject` varchar(50) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `orderId` varchar(100) DEFAULT NULL,
  `description` text NOT NULL,
  `response` text,
  `customerId` int NOT NULL,
  `staffId` int DEFAULT NULL,
  `status` enum('Answered','Closed','Pending') DEFAULT (_utf8mb4'Pending'),
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  KEY `staffId` (`staffId`),
  CONSTRAINT `queries_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`),
  CONSTRAINT `queries_ibfk_2` FOREIGN KEY (`staffId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `queries`
--

LOCK TABLES `queries` WRITE;
/*!40000 ALTER TABLE `queries` DISABLE KEYS */;
INSERT INTO `queries` VALUES (1,'Deliver Issue','0715544545','','Need Help','We will check about it and Contact you ASAP.',3,NULL,'Answered','2024-09-02 12:28:58','2024-09-10 07:16:20'),(2,'Reservation','0785545454','333','Need Help','We will check about it and Contact you ASAP.',3,NULL,'Answered','2024-09-02 12:29:18','2024-09-10 07:16:20'),(3,'Account Issue','0712265456','222','Need Help','',3,NULL,'Answered','2024-09-02 12:50:12','2024-09-10 07:16:12'),(4,'Need help','0665456545','33','Need Help',NULL,3,NULL,'Closed','2024-09-02 12:52:49','2024-09-10 07:16:12'),(5,'ongoing order','0715523212','33','Need Help','We will check about it and Contact you ASAP.',3,3,'Answered','2024-09-02 12:53:23','2024-09-10 07:16:20'),(6,'Order','0711225450','','ww',NULL,3,NULL,'Pending','2024-09-02 12:53:51','2024-09-05 15:06:14'),(7,'Regarding order','0712252222','194f2d38-6a39-420b-8f4b-f8fda1d2c4d8','My order is not received yet. kindly check and let me know.',NULL,14,NULL,'Closed','2024-09-05 13:48:40','2024-09-07 17:18:27'),(8,'Regarding Order','0718827372','','I didn’t receive the last order.”,','We will check about it and Contact you ASAP.',14,NULL,'Answered','2024-09-05 20:09:46','2024-09-05 20:16:14');
/*!40000 ALTER TABLE `queries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reservationId` char(36) DEFAULT NULL,
  `customerId` int NOT NULL,
  `restaurantId` int NOT NULL,
  `name` varchar(50) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `date` varchar(20) NOT NULL,
  `time` varchar(20) NOT NULL,
  `noOfPeople` int DEFAULT NULL,
  `status` varchar(30) DEFAULT (_utf8mb4'pending'),
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `restaurantId` (`restaurantId`),
  KEY `customerId` (`customerId`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`restaurantId`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`customerId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,'f50cc58b-cffe-4db2-b74a-d7e7d1064821',14,1,'Arosha Sahan','0712578256','2024-09-13','19:10',4,'confirm','2024-09-05 19:11:10','2024-09-07 22:50:45'),(2,'56168d8e-9145-4e39-a2be-7dc55a92abfd',14,6,'Arosha Sahan','0712212121','2024-09-13','19:42',2,'cancel','2024-09-06 00:42:45','2024-09-07 22:50:56'),(3,'6866d557-1999-498d-8261-52fcbbc8bf19',14,6,'Arosha Sahan','0712255451','2024-09-13','19:42',2,'pending','2024-09-06 00:43:11','2024-09-06 00:43:11'),(4,'d64d0f8e-4180-43e5-ab0e-abe185138df8',14,6,'Arosha Sahan','3121286800','2024-09-12','12:47',5,'pending','2024-09-06 00:50:15','2024-09-06 00:50:15'),(5,'24da4da0-6857-4aeb-bb37-59ef27f1da6b',69,1,'Arosha Sahan','0711234567','2024-09-30','14:30',5,'pending','2024-09-07 21:34:34','2024-09-07 21:34:34');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `openTime` varchar(100) DEFAULT NULL,
  `closeTime` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `image` varchar(200) NOT NULL,
  `capacity` int DEFAULT NULL,
  `images` varchar(255) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'Flavorscape','Flavorscape','10:00','23:00','Colombo 5','+94 71 555 5555','/uploads/restaurants/1.jpg',10,NULL,'active','2024-09-04 11:17:35','2024-09-04 11:17:35'),(2,'Culinary Collective','Culinary Collective','11:30','00:30','Kurunegala New Town','+94 77 222 2225','/uploads/restaurants/2.jpg',20,NULL,'active','2024-09-04 11:18:46','2024-09-04 12:02:15'),(3,'Palate Bistro','Palate Bistro','16:30','02:30','Stage 3, Anuradhapura','+94 025 555 2555','/uploads/restaurants/3.jpg',5,NULL,'active','2024-09-04 11:19:54','2024-09-04 11:19:54'),(4,'Golden Fork','Golden Fork','13:30','03:30','Langkawi Island, Malaysia','+60 205 5565','/uploads/restaurants/4.jpg',30,NULL,'active','2024-09-04 11:22:02','2024-09-04 11:22:02'),(5,'Taste Haven','Taste Haven','09:30','21:30','Galle','+94 71 223 2223','/uploads/restaurants/5.jpg',5,NULL,'active','2024-09-04 11:22:51','2024-09-04 11:22:51'),(6,'Epicurean Eats','Epicurean Eats','08:00','23:30','Kandy','+94 71 112 1112','/uploads/restaurants/6.jpg',2,NULL,'active','2024-09-04 11:23:51','2024-09-06 00:42:04');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `siteName` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `logoPath` text NOT NULL,
  `siteEmail` varchar(255) NOT NULL,
  `siteStreetAddress` varchar(255) NOT NULL,
  `siteZip` varchar(255) NOT NULL,
  `siteCity` varchar(255) NOT NULL,
  `serverEmail` varchar(255) NOT NULL,
  `serverPassword` varchar(255) NOT NULL,
  `UpdatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (1,'ABC RESTAURANT','Crafting Culinary Memories, One Plate at a Time','/uploads/logo/logo new.png','info@abcres.com','391/11','11201','Colombo 5','example@gmail.com','example123','2024-09-10 15:02:30');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `orderId` int NOT NULL,
  `paymentMethod` varchar(255) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'success'),
  `CreatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `orderId` (`orderId`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,3,'card',7500.00,'success','2024-09-07 16:42:24'),(2,4,'cash',4500.00,'success','2024-09-07 21:39:41'),(3,5,'card',4500.00,'success','2024-09-07 21:41:37');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `lastName` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `NIC` varchar(15) DEFAULT NULL,
  `status` varchar(15) DEFAULT (_utf8mb4'active'),
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `accountType` enum('Admin','Staff','Customer') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Tharindu','Perera','ideatidy1@gmail.comf','12345678','Colombo','0711154555',NULL,NULL,'active','2024-08-17 17:25:44','2024-09-10 12:44:02','Customer'),(3,'arosha','sahan','ideatidy22@gmail.com','12345678','31/1, Anuradhapura','0775555545',NULL,NULL,'active','2024-08-31 10:48:47','2024-09-10 12:44:02','Customer'),(4,'Kalana','Gamage','kalan a@gmail.com','12345678','Kurunegala','071558987','chef','9482829389V','active','2024-09-03 11:26:38','2024-09-10 12:44:02','Staff'),(5,'Vidura','Shehan','admin@gmail.com','12345678','Nugegoda','0754454545',NULL,'948828382V','active','2024-09-03 18:32:05','2024-09-10 12:44:02','Admin'),(13,'Chathura','Alwis','shan11@gmail.com','ABC12345','Kandy','0721145454',NULL,NULL,'active','2024-09-03 21:53:28','2024-09-10 12:44:02','Customer'),(14,'Arosha','Sahan','arosha@gmail.com','12345678','Anuradhapura','0712252125',NULL,NULL,'active','2024-09-03 21:59:03','2024-09-10 12:44:02','Staff'),(31,'Kalana','Perera','kalana@gmail.com','12345678','Kurunegala','0712215454','Cashier','91221545422V','active','2024-09-06 02:07:24','2024-09-06 02:07:24','Staff'),(69,'Aron','Sahan','ideatidy@gmail.com','12345678','Anuradhapura','0718821212',NULL,NULL,'active','2024-09-07 21:28:36','2024-09-10 12:44:02','Customer');
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

-- Dump completed on 2024-09-10 15:02:44
