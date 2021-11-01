CREATE DATABASE IF NOT EXISTS mvcdb;

#drop database mvcdb;

USE mvcdb;

# 创建 主表

CREATE TABLE IF NOT EXISTS student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	real_id VARCHAR(10) NOT NULL DEFAULT "",
	`name` VARCHAR(20) NOT NULL DEFAULT "",
	grade_name VARCHAR(10) NOT NULL DEFAULT "",
	class_name VARCHAR(10) NOT NULL DEFAULT "",
	classroom_real_id VARCHAR(10) NOT NULL DEFAULT "",
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

CREATE TABLE IF NOT EXISTS teacher(
	id INT PRIMARY KEY AUTO_INCREMENT,
	real_id VARCHAR(6) NOT NULL DEFAULT "",
	`name` VARCHAR(20) NOT NULL DEFAULT "",
	teach_course_name VARCHAR(30),
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

CREATE TABLE IF NOT EXISTS class_grade(
	id INT PRIMARY KEY AUTO_INCREMENT,
	grade_name VARCHAR(10) NOT NULL DEFAULT "",
	class_name VARCHAR(10) NOT NULL DEFAULT "",
	classroom_real_id VARCHAR(10) NOT NULL DEFAULT "",
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

CREATE TABLE IF NOT EXISTS school(
	id INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL DEFAULT "" UNIQUE
);

CREATE TABLE IF NOT EXISTS classroom(
	id INT PRIMARY KEY AUTO_INCREMENT,
	real_id VARCHAR(10) NOT NULL DEFAULT "",
	grade_name VARCHAR(10) NOT NULL DEFAULT "",
	class_name VARCHAR(10) NOT NULL DEFAULT "",
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

CREATE TABLE IF NOT EXISTS teach_course_info(
	id INT PRIMARY KEY AUTO_INCREMENT,
	teacher_name VARCHAR(20) NOT NULL DEFAULT "",
	classroom_real_id VARCHAR(10) NOT NULL DEFAULT "",
	grade_name VARCHAR(10) NOT NULL DEFAULT "",
	class_name VARCHAR(10) NOT NULL DEFAULT "",
	course_name VARCHAR(30),
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

# 创建 关系表

CREATE TABLE IF NOT EXISTS class_grade_teacher(
	id INT PRIMARY KEY AUTO_INCREMENT,
	class_grade_id INT,
	teacher_id INT,
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

CREATE TABLE IF NOT EXISTS student_teacher(
	id INT PRIMARY KEY AUTO_INCREMENT,
	student_id INT,
	teacher_id INT,
	school_name VARCHAR(30) NOT NULL DEFAULT ""
);

#  创建 索引

ALTER TABLE student 
	ADD INDEX idx_school_garde_class_realid
	(school_name, grade_name, class_name, real_id);

ALTER TABLE student 
	ADD INDEX idx_school_classroom_real_id
	(school_name, classroom_real_id, real_id);

ALTER TABLE teacher
	ADD INDEX idx_school_realid
	(school_name, real_id);
	
ALTER TABLE teacher
	ADD INDEX idx_school_course_name
	(school_name, teach_course_name, `name`);
	
ALTER TABLE class_grade
	ADD INDEX idx_school_grade_class
	(school_name, grade_name, class_name);
	
ALTER TABLE class_grade
	ADD INDEX idx_school_classroom
	(school_name, classroom_real_id);
	
ALTER TABLE classroom
	ADD INDEX idx_school_real_id
	(school_name, real_id);

ALTER TABLE classroom
	ADD INDEX idx_school_grade_class
	(school_name, grade_name, class_name);
	
ALTER TABLE teach_course_info
	ADD INDEX idx_school_grade_class_id
	(school_name, grade_name, class_name, id);

ALTER TABLE teach_course_info
	ADD INDEX idx_school_classroom_id
	(school_name, classroom_real_id, id);
	
ALTER TABLE teach_course_info
	ADD INDEX idx_school_teacher
	(school_name, teacher_name);
	
ALTER TABLE class_grade_teacher
	ADD INDEX idx_school_grade_class_teacher
	(school_name, class_grade_id, teacher_id);
	
ALTER TABLE student_teacher
	ADD INDEX idx_school_student_teacher
	(school_name, student_id, teacher_id);