-- MySQL dump 10.13  Distrib 5.7.28, for Win64 (x86_64)
--
-- Host: localhost    Database: cancontento
-- ------------------------------------------------------
-- Server version	5.7.28-log

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
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulo` (
  `id_art` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nom_art` varchar(50) NOT NULL,
  `des_art` varchar(200) NOT NULL,
  `fot_art` blob,
  `pre_art` float NOT NULL,
  `exi_art` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_art`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
INSERT INTO `articulo` VALUES (1,'Shampoo','Shampoo anti pulgas para el baño de tu mascota',_binary 'null',200,20),(2,'Correa','correas de materiales artificiales, gran durabilidad y resistencia',_binary 'null',150,20),(3,'Collar','Collar de material artificial, gran durabilidad y resistencia',_binary 'null',300,20),(4,'Collar Anti-Pulgas','collar con propiedades plagicidas para evitar el contagio de pulgas',_binary 'null',350,20),(5,'Pipeta Anti_Pulgas','pipeta que se coloca en la piel de tu mascota para erradicar pulgas',_binary 'null',400,20),(6,'Comida','comida para perro de alta calidad',_binary 'null',280,20),(7,'Perfume','perfume especializado para perros sin erupciones ni reacciones alergicas',_binary 'null',160,20);
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cita` (
  `id_cit` smallint(6) NOT NULL AUTO_INCREMENT,
  `fec_cit` date NOT NULL,
  `hor_cit` time NOT NULL,
  `est_cit` tinyint(1) NOT NULL,
  `codi_cit` blob,
  `codc_cit` varchar(10) DEFAULT NULL,
  `fin_cit` tinyint(1) NOT NULL,
  `id_per` tinyint(4) NOT NULL,
  `cor_usu` varchar(320) NOT NULL,
  PRIMARY KEY (`id_cit`),
  KEY `id_per` (`id_per`),
  KEY `cor_usu` (`cor_usu`),
  CONSTRAINT `cita_ibfk_1` FOREIGN KEY (`id_per`) REFERENCES `perro` (`id_per`),
  CONSTRAINT `cita_ibfk_2` FOREIGN KEY (`cor_usu`) REFERENCES `usuario` (`cor_usu`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,'2002-05-20','12:00:00',0,_binary 'codigocita',NULL,0,67,'jela'),(2,'2020-03-27','14:02:00',0,_binary 'algoritmodecodigoxd',NULL,0,67,'jela'),(3,'2020-03-24','14:02:00',0,_binary 'algoritmodecodigoxd',NULL,0,67,'jela'),(4,'2020-03-26','13:09:00',0,_binary 'algoritmodecodigoxd',NULL,0,67,'jela');
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descripcion`
--

DROP TABLE IF EXISTS `descripcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descripcion` (
  `id_des` smallint(6) NOT NULL AUTO_INCREMENT,
  `com_des` varchar(280) DEFAULT NULL,
  `cal_des` float NOT NULL,
  `id_cit` smallint(6) NOT NULL,
  `id_ser` tinyint(4) NOT NULL,
  `id_ven` smallint(6) NOT NULL,
  PRIMARY KEY (`id_des`),
  KEY `id_cit` (`id_cit`),
  KEY `id_ser` (`id_ser`),
  KEY `id_ven` (`id_ven`),
  CONSTRAINT `descripcion_ibfk_1` FOREIGN KEY (`id_cit`) REFERENCES `cita` (`id_cit`) ON UPDATE CASCADE,
  CONSTRAINT `descripcion_ibfk_2` FOREIGN KEY (`id_ser`) REFERENCES `servicio` (`id_ser`) ON UPDATE CASCADE,
  CONSTRAINT `descripcion_ibfk_3` FOREIGN KEY (`id_ven`) REFERENCES `venta` (`id_ven`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descripcion`
--

LOCK TABLES `descripcion` WRITE;
/*!40000 ALTER TABLE `descripcion` DISABLE KEYS */;
/*!40000 ALTER TABLE `descripcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `id_men` mediumint(9) NOT NULL AUTO_INCREMENT,
  `men_men` text NOT NULL,
  `fec_men` datetime NOT NULL,
  `cor_env` varchar(320) NOT NULL,
  `cor_rec` varchar(320) NOT NULL,
  PRIMARY KEY (`id_men`),
  KEY `cor_env` (`cor_env`),
  KEY `cor_rec` (`cor_rec`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`cor_env`) REFERENCES `usuario` (`cor_usu`) ON UPDATE CASCADE,
  CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`cor_rec`) REFERENCES `usuario` (`cor_usu`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perro`
--

DROP TABLE IF EXISTS `perro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perro` (
  `id_per` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nom_per` varchar(50) NOT NULL,
  `fot_per` tinytext,
  `nac_per` date DEFAULT NULL,
  `gen_per` tinyint(1) DEFAULT NULL,
  `tall_per` varchar(20) DEFAULT NULL,
  `cod_per` tinyint(4) NOT NULL,
  `cor_usu` varchar(320) NOT NULL,
  PRIMARY KEY (`id_per`),
  KEY `cor_usu` (`cor_usu`),
  CONSTRAINT `perro_ibfk_1` FOREIGN KEY (`cor_usu`) REFERENCES `usuario` (`cor_usu`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perro`
--

LOCK TABLES `perro` WRITE;
/*!40000 ALTER TABLE `perro` DISABLE KEYS */;
INSERT INTO `perro` VALUES (67,'elchicodenew','C:\\Users\\jela3\\Documents\\NetBeansProjects\\Preciso\\build\\web\\Img\\jela\\elchicodenew.png','2012-12-23',1,'Chico',0,'jela'),(69,'fi','C:\\Users\\jela3\\Documents\\NetBeansProjects\\Preciso\\build\\web\\Img\\jela\\fi.png','2017-12-20',1,'Grande',0,'jela'),(70,'elpug','C:\\Users\\jela3\\Documents\\NetBeansProjects\\Preciso\\build\\web\\Img\\jela\\elpepe.png','2020-02-02',1,'Chico',0,'jela');
/*!40000 ALTER TABLE `perro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persona` (
  `id_pers` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nom_pers` varchar(50) NOT NULL,
  `tel_pers` varchar(10) DEFAULT NULL,
  `dir_pers` varchar(100) NOT NULL,
  `cor_usu` varchar(320) NOT NULL,
  PRIMARY KEY (`id_pers`),
  KEY `cor_usu` (`cor_usu`),
  CONSTRAINT `persona_ibfk_1` FOREIGN KEY (`cor_usu`) REFERENCES `usuario` (`cor_usu`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (24,'jela','6767676767','jela','jela');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicio`
--

DROP TABLE IF EXISTS `servicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicio` (
  `id_ser` tinyint(4) NOT NULL AUTO_INCREMENT,
  `nom_ser` varchar(50) NOT NULL,
  `des_ser` varchar(200) NOT NULL,
  `pre_ser` float NOT NULL,
  PRIMARY KEY (`id_ser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicio`
--

LOCK TABLES `servicio` WRITE;
/*!40000 ALTER TABLE `servicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticked`
--

DROP TABLE IF EXISTS `ticked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticked` (
  `id_tic` smallint(6) NOT NULL AUTO_INCREMENT,
  `can_tic` tinyint(4) NOT NULL,
  `id_ven` smallint(6) NOT NULL,
  `id_art` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_tic`),
  KEY `id_ven` (`id_ven`),
  KEY `id_art` (`id_art`),
  CONSTRAINT `ticked_ibfk_1` FOREIGN KEY (`id_ven`) REFERENCES `venta` (`id_ven`) ON UPDATE CASCADE,
  CONSTRAINT `ticked_ibfk_2` FOREIGN KEY (`id_art`) REFERENCES `articulo` (`id_art`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticked`
--

LOCK TABLES `ticked` WRITE;
/*!40000 ALTER TABLE `ticked` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipousuario`
--

DROP TABLE IF EXISTS `tipousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tipousuario` (
  `id_tip` tinyint(4) NOT NULL AUTO_INCREMENT,
  `des_tip` varchar(30) NOT NULL,
  PRIMARY KEY (`id_tip`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipousuario`
--

LOCK TABLES `tipousuario` WRITE;
/*!40000 ALTER TABLE `tipousuario` DISABLE KEYS */;
INSERT INTO `tipousuario` VALUES (1,'Encargado'),(2,'Empleado'),(3,'Cliente');
/*!40000 ALTER TABLE `tipousuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `cor_usu` varchar(320) NOT NULL,
  `con_usu` varchar(30) NOT NULL,
  `id_tip` tinyint(4) NOT NULL,
  PRIMARY KEY (`cor_usu`),
  KEY `id_tip` (`id_tip`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_tip`) REFERENCES `tipousuario` (`id_tip`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('jela','jela',3);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venta` (
  `id_ven` smallint(6) NOT NULL AUTO_INCREMENT,
  `fin_ven` tinyint(1) NOT NULL,
  `cor_usu` varchar(320) NOT NULL,
  PRIMARY KEY (`id_ven`),
  KEY `cor_usu` (`cor_usu`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`cor_usu`) REFERENCES `usuario` (`cor_usu`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-20 22:09:23
