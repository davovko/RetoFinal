-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema db_sophosbank
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_sophosbank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_sophosbank` DEFAULT CHARACTER SET utf8mb3 ;
USE `db_sophosbank` ;

-- -----------------------------------------------------
-- Table `db_sophosbank`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`identification_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`identification_types` (
  `identification_type_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`identification_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`customers` (
  `customer_id` INT NOT NULL AUTO_INCREMENT,
  `identification_type_id` INT NOT NULL,
  `identification_number` VARCHAR(50) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NULL DEFAULT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `second_last_name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `creation_user_id` INT NOT NULL,
  `modification_date` DATETIME NULL DEFAULT NULL,
  `modification_user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  INDEX `fk_identification_type_id_idx` (`identification_type_id` ASC) VISIBLE,
  INDEX `fk_creation_user_id_idx` (`creation_user_id` ASC) VISIBLE,
  INDEX `fk_modification_user_id_idx` (`modification_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_creation_user_id`
    FOREIGN KEY (`creation_user_id`)
    REFERENCES `db_sophosbank`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_identification_type_id`
    FOREIGN KEY (`identification_type_id`)
    REFERENCES `db_sophosbank`.`identification_types` (`identification_type_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_modification_user_id`
    FOREIGN KEY (`modification_user_id`)
    REFERENCES `db_sophosbank`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`product_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`product_types` (
  `product_type_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`product_type_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`status_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`status_account` (
  `status_account_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`status_account_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`products`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `product_type_id` INT NOT NULL,
  `account_number` VARCHAR(45) NOT NULL,
  `status_account_id` INT NOT NULL,
  `balance` DOUBLE NOT NULL,
  `available_balance` DOUBLE NOT NULL,
  `gmf_exempt` TINYINT NOT NULL,
  `creation_date` DATETIME NOT NULL,
  `creation_user_id` INT NOT NULL,
  `modification_date` DATETIME NULL DEFAULT NULL,
  `modification_user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  INDEX `fk_customer_id_idx` (`customer_id` ASC) VISIBLE,
  INDEX `fk_creation_user_id_idx` (`creation_user_id` ASC) VISIBLE,
  INDEX `fk_product_type_id_idx` (`product_type_id` ASC) VISIBLE,
  INDEX `fk_state_account_id_idx` (`status_account_id` ASC) VISIBLE,
  INDEX `fk_modification_user_id_product_idx` (`modification_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_creation_user_product_id`
    FOREIGN KEY (`creation_user_id`)
    REFERENCES `db_sophosbank`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `db_sophosbank`.`customers` (`customer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_modification_user_id_product`
    FOREIGN KEY (`modification_user_id`)
    REFERENCES `db_sophosbank`.`users` (`user_id`),
  CONSTRAINT `fk_product_type_id`
    FOREIGN KEY (`product_type_id`)
    REFERENCES `db_sophosbank`.`product_types` (`product_type_id`),
  CONSTRAINT `fk_state_account_id`
    FOREIGN KEY (`status_account_id`)
    REFERENCES `db_sophosbank`.`status_account` (`status_account_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`transaction_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`transaction_types` (
  `transaction_types_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`transaction_types_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `db_sophosbank`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_sophosbank`.`transactions` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `transaction_type_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `transaction_date` DATETIME NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `transaction_value` DOUBLE NOT NULL,
  `destination_product_id` INT NULL DEFAULT NULL,
  `origin_product_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `fk_product_id_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_destination_product_id_idx` (`destination_product_id` ASC) VISIBLE,
  INDEX `fk_transaction_type_id_idx` (`transaction_type_id` ASC) VISIBLE,
  CONSTRAINT `fk_destination_product_id`
    FOREIGN KEY (`destination_product_id`)
    REFERENCES `db_sophosbank`.`products` (`product_id`),
  CONSTRAINT `fk_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `db_sophosbank`.`products` (`product_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_transaction_type_id`
    FOREIGN KEY (`transaction_type_id`)
    REFERENCES `db_sophosbank`.`transaction_types` (`transaction_types_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
