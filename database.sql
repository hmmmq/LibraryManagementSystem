-- MySQL Script generated by MySQL Workbench
-- Mon Aug 19 11:46:54 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema librarymanagementsystem
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `librarymanagementsystem` ;

-- -----------------------------------------------------
-- Schema librarymanagementsystem
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `librarymanagementsystem` DEFAULT CHARACTER SET utf8mb4 ;
USE `librarymanagementsystem` ;

-- -----------------------------------------------------
-- Table `librarymanagementsystem`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librarymanagementsystem`.`book` ;

CREATE TABLE IF NOT EXISTS `librarymanagementsystem`.`book` (
  `idbook` INT NOT NULL,
  `bookname` VARCHAR(45) NOT NULL,
  `description` VARCHAR(500) NULL,
  `amount` INT NULL,
  PRIMARY KEY (`idbook`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarymanagementsystem`.`borrow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librarymanagementsystem`.`borrow` ;

CREATE TABLE IF NOT EXISTS `librarymanagementsystem`.`borrow` (
  `idborrow` INT NOT NULL,
  `totalamount` INT NULL,
  `borrowdate` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `userid` INT NOT NULL,
  PRIMARY KEY (`idborrow`),
  INDEX `userid_idx` (`userid` ASC),
  CONSTRAINT `userid`
    FOREIGN KEY (`userid`)
    REFERENCES `librarymanagementsystem`.`user` (`iduser`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarymanagementsystem`.`borrowitem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librarymanagementsystem`.`borrowitem` ;

CREATE TABLE IF NOT EXISTS `librarymanagementsystem`.`borrowitem` (
  `idborrowitem` INT NOT NULL,
  `userid` INT NOT NULL,
  `bookid` INT NOT NULL,
  `borrowdate` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `bookname` VARCHAR(45) NOT NULL,
  `bookdescription` VARCHAR(45) NOT NULL,
  `amount` INT NOT NULL,
  `is_returned` INT ZEROFILL NOT NULL,
  `parentid` INT NOT NULL,
  PRIMARY KEY (`idborrowitem`),
  INDEX `parentid_idx` (`parentid` ASC),
  INDEX `bookid_idx` (`bookid` ASC),
  CONSTRAINT `parentid`
    FOREIGN KEY (`parentid`)
    REFERENCES `librarymanagementsystem`.`borrow` (`idborrow`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `bookid`
    FOREIGN KEY (`bookid`)
    REFERENCES `librarymanagementsystem`.`book` (`idbook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `librarymanagementsystem`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `librarymanagementsystem`.`user` ;

CREATE TABLE IF NOT EXISTS `librarymanagementsystem`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `admin` INT ZEROFILL NOT NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
