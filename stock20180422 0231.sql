-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.22-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema test
--

CREATE DATABASE IF NOT EXISTS test;
USE test;

--
-- Definition of table `clienttable`
--

DROP TABLE IF EXISTS `clienttable`;
CREATE TABLE `clienttable` (
  `c_id` int(5) NOT NULL auto_increment,
  `c_name` varchar(25) default NULL,
  `sex` varchar(8) default NULL,
  `dob` date default NULL,
  `fathers_name` varchar(25) default NULL,
  `mothers_name` varchar(25) default NULL,
  `occupation` varchar(15) default NULL,
  `phone` varchar(12) default NULL,
  `email` varchar(25) default NULL,
  `address` varchar(50) default NULL,
  PRIMARY KEY  (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clienttable`
--

/*!40000 ALTER TABLE `clienttable` DISABLE KEYS */;
INSERT INTO `clienttable` (`c_id`,`c_name`,`sex`,`dob`,`fathers_name`,`mothers_name`,`occupation`,`phone`,`email`,`address`) VALUES 
 (1,'Abir','Male','1992-12-12','Mohammad Mukul','Runu Akhter','Service','01658427795','abir@gmail.com','Dhaka,Bangladesh'),
 (2,'Sakib','Male','1992-06-12','Mohammad Salim','Shely Begum','Business','01681346628','sakib@gmail.com','Dhaka,Bamgladesh'),
 (3,'Nabila','Female','1998-01-28','Mohammad Afsar','Aleya Begum','House Wife','01915842658','namila@gmail.com','Chittagong,Bangladesh'),
 (4,'Moni','Female','1999-02-19','Abul Hosen','Sumy Akhter','House Wife','01847256452','moni@gmail.com','Chittagong,Bangladesh'),
 (5,'Rasel','Male','1285-07-03','rrthjuigkv hjgf','ioikdht xgfnv','Student','12458963257','ggsdthtyj@wer.com','rwtregfgmg,hduiykhs'),
 (6,'ghdfg zbxg','Female','1245-08-09','dhsgf dhdfer','reteyrt fjyrui','Business','12458963512','fsgfgsrtyjd@czk.com','rtrtsrefzxfdm, jfuiufydjh');
/*!40000 ALTER TABLE `clienttable` ENABLE KEYS */;


--
-- Definition of table `logintable`
--

DROP TABLE IF EXISTS `logintable`;
CREATE TABLE `logintable` (
  `user_id` int(11) NOT NULL auto_increment,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `logintable`
--

/*!40000 ALTER TABLE `logintable` DISABLE KEYS */;
INSERT INTO `logintable` (`user_id`,`user_name`,`password`) VALUES 
 (1,'Galib','12345'),
 (2,'idb','r35');
/*!40000 ALTER TABLE `logintable` ENABLE KEYS */;


--
-- Definition of table `transactiontable`
--

DROP TABLE IF EXISTS `transactiontable`;
CREATE TABLE `transactiontable` (
  `t_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `c_id` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  `com_name` varchar(45) NOT NULL,
  `u_price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `comm` double NOT NULL,
  `t_cost` double NOT NULL,
  PRIMARY KEY  (`t_id`),
  KEY `FK_transactionTable_cid` (`c_id`),
  CONSTRAINT `FK_transactionTable_cid` FOREIGN KEY (`c_id`) REFERENCES `clienttable` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactiontable`
--

/*!40000 ALTER TABLE `transactiontable` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactiontable` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
