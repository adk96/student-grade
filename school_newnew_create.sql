-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema school_db_newnew
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema school_db_newnew
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `school_db_newnew` DEFAULT CHARACTER SET utf8 ;
USE `school_db_newnew` ;

-- -----------------------------------------------------
-- Table `class`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `class` ;

CREATE TABLE IF NOT EXISTS `class` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 118
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `subject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subject` ;

CREATE TABLE IF NOT EXISTS `subject` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `class_subject_connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `class_subject_connection` ;

CREATE TABLE IF NOT EXISTS `class_subject_connection` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `class_id` INT(11) NOT NULL,
  `subject_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_idx` (`class_id` ASC),
  INDEX `fk_subject_idx` (`subject_id` ASC),
  CONSTRAINT `fk_class_subject_connection_class`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_subject_connection_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 29
DEFAULT CHARACTER SET = utf8
COMMENT = '		';


-- -----------------------------------------------------
-- Table `grade_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `grade_value` ;

CREATE TABLE IF NOT EXISTS `grade_value` (
  `id` INT(11) NOT NULL,
  `values` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `student` ;

CREATE TABLE IF NOT EXISTS `student` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `class_id` INT(11) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `surname` VARCHAR(45) NULL DEFAULT NULL,
  `patron` VARCHAR(45) NULL DEFAULT NULL,
  `birthday` DATE NULL DEFAULT NULL,
  `phone` VARCHAR(45) NULL DEFAULT NULL,
  `image` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_class_idx` (`class_id` ASC),
  CONSTRAINT `fk_student_class`
    FOREIGN KEY (`class_id`)
    REFERENCES `class` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 190163
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teacher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teacher` ;

CREATE TABLE IF NOT EXISTS `teacher` (
  `id` INT(11) NOT NULL,
  `fullname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `grade`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `grade` ;

CREATE TABLE IF NOT EXISTS `grade` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `grade_id` INT(11) NULL DEFAULT NULL,
  `subject_id` INT(11) NULL DEFAULT NULL,
  `teacher_id` INT(11) NULL DEFAULT NULL,
  `student_id` INT(11) NULL DEFAULT NULL,
  `grade_date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_mark_idx` (`grade_id` ASC),
  INDEX `fk_subject_idx` (`subject_id` ASC),
  INDEX `fk_teacher_idx` (`teacher_id` ASC),
  INDEX `fk_pupil_idx` (`student_id` ASC),
  CONSTRAINT `fk_mark`
    FOREIGN KEY (`grade_id`)
    REFERENCES `grade_value` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `student` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 191118
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `teacher_subject_connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `teacher_subject_connection` ;

CREATE TABLE IF NOT EXISTS `teacher_subject_connection` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `subject_id` INT(11) NULL DEFAULT NULL,
  `teacher_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_teacher_idx` (`teacher_id` ASC),
  INDEX `fk_subject_idx` (`subject_id` ASC),
  CONSTRAINT `fk_teacher_subject_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_teacher_subject_teacher`
    FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `class`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `class` (`id`, `name`) VALUES (111, '1A');
INSERT INTO `class` (`id`, `name`) VALUES (112, '1B');
INSERT INTO `class` (`id`, `name`) VALUES (113, '1C');
INSERT INTO `class` (`id`, `name`) VALUES (114, '2A');
INSERT INTO `class` (`id`, `name`) VALUES (115, '2B');
INSERT INTO `class` (`id`, `name`) VALUES (116, '3A');
INSERT INTO `class` (`id`, `name`) VALUES (117, '3B');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subject`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `subject` (`id`, `name`) VALUES (11, 'Algebra');
INSERT INTO `subject` (`id`, `name`) VALUES (12, 'Geometry');
INSERT INTO `subject` (`id`, `name`) VALUES (13, 'Chemistry');
INSERT INTO `subject` (`id`, `name`) VALUES (14, 'Informatix');
INSERT INTO `subject` (`id`, `name`) VALUES (15, 'History');
INSERT INTO `subject` (`id`, `name`) VALUES (16, 'Biology');
INSERT INTO `subject` (`id`, `name`) VALUES (17, 'English');
INSERT INTO `subject` (`id`, `name`) VALUES (18, 'Physics');
INSERT INTO `subject` (`id`, `name`) VALUES (19, 'Physical Culture');
INSERT INTO `subject` (`id`, `name`) VALUES (20, 'Labour Lesson');
INSERT INTO `subject` (`id`, `name`) VALUES (21, 'Art');
INSERT INTO `subject` (`id`, `name`) VALUES (22, 'Literature');
INSERT INTO `subject` (`id`, `name`) VALUES (23, 'Music');
INSERT INTO `subject` (`id`, `name`) VALUES (24, 'Social studies');
INSERT INTO `subject` (`id`, `name`) VALUES (25, 'Science');
INSERT INTO `subject` (`id`, `name`) VALUES (26, 'Psychology');
INSERT INTO `subject` (`id`, `name`) VALUES (27, 'Reading');
INSERT INTO `subject` (`id`, `name`) VALUES (28, 'Health');
INSERT INTO `subject` (`id`, `name`) VALUES (29, 'Geography');
INSERT INTO `subject` (`id`, `name`) VALUES (30, 'Mathematics');

COMMIT;


-- -----------------------------------------------------
-- Data for table `class_subject_connection`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 111, 29);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 111, 24);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 111, 28);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 116, 29);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 116, 12);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 115, 14);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 114, 15);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 113, 17);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 114, 17);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 115, 18);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 113, 20);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 115, 23);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 112, 21);
INSERT INTO `class_subject_connection` (`id`, `class_id`, `subject_id`) VALUES (DEFAULT, 116, 22);

COMMIT;


-- -----------------------------------------------------
-- Data for table `grade_value`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `grade_value` (`id`, `values`) VALUES (1, 1);
INSERT INTO `grade_value` (`id`, `values`) VALUES (2, 2);
INSERT INTO `grade_value` (`id`, `values`) VALUES (3, 3);
INSERT INTO `grade_value` (`id`, `values`) VALUES (4, 4);
INSERT INTO `grade_value` (`id`, `values`) VALUES (5, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `student`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190111, 111, 'Don', 'Falcone', NULL, '1996-11-21', '87757668875');
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190112, 111, 'Akzhol', 'Zhanabayev', NULL, '1995-11-27', '87756517852');
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190113, 112, 'Amina', 'Keysin', NULL, '1998-07-15', '87775687175');
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190114, 111, 'Temirlan', 'Sapin', NULL, '1997-10-05', '87475678153');
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190115, 113, 'Kamilya', 'Nurgayanova', NULL, '1999-05-07', '87776871354');
INSERT INTO `student` (`id`, `class_id`, `name`, `surname`, `patron`, `birthday`, `phone`) VALUES (190116, 116, 'Vasya', 'Pupkin', NULL, '1997-12-11', '87757669736');

COMMIT;


-- -----------------------------------------------------
-- Data for table `teacher`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810119, 'Rid Richards');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810118, 'Peter Parker');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810117, 'Bruce Wayne');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810116, 'Bruce Banner');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810115, 'Army Hammer');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810114, 'Tony Stark');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810113, 'Ilon Mask');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810112, 'Bill Gates');
INSERT INTO `teacher` (`id`, `fullname`) VALUES (810111, 'Steve Jobs');

COMMIT;


-- -----------------------------------------------------
-- Data for table `grade`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191116, 4, 18, 810119, 190111, '2018-12-05');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191115, 5, 29, 810116, 190111, '2019-01-12');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191114, 3, 11, 810113, 190111, '2018-12-25');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191113, 5, 28, 810112, 190116, '2018-12-30');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191112, 2, 16, 810113, 190116, '2019-02-01');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191111, 4, 11, 810113, 190113, '2019-02-05');
INSERT INTO `grade` (`id`, `grade_id`, `subject_id`, `teacher_id`, `student_id`, `grade_date`) VALUES (191117, 1, 29, 810116, 190111, '2019-01-12');

COMMIT;


-- -----------------------------------------------------
-- Data for table `teacher_subject_connection`
-- -----------------------------------------------------
START TRANSACTION;
USE `school_db_newnew`;
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (7, 28, 810119);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (6, 21, 810118);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (4, 17, 810117);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (3, 12, 810116);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (1, 11, 810115);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (9, 18, 810114);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (5, 16, 810113);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (2, 15, 810112);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (8, 29, 810111);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (10, 13, 810111);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (11, 14, 810118);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (12, 19, 810115);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (13, 22, 810113);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (14, 23, 810111);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (15, 24, 810119);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (16, 25, 810114);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (17, 26, 810112);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (18, 27, 810118);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (19, 30, 810115);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (20, 20, 810117);
INSERT INTO `teacher_subject_connection` (`id`, `subject_id`, `teacher_id`) VALUES (21, 29, 810116);

COMMIT;