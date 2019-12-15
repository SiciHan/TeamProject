create database if not exists CA;
use CA;
/*Phyu Phyu*/
CREATE TABLE `department` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



INSERT INTO `ca`.`department` (`id`, `name`) VALUES ('1', 'Computing');
INSERT INTO `ca`.`department` (`id`, `name`) VALUES ('2', 'Chemical');
INSERT INTO `ca`.`department` (`id`, `name`) VALUES ('3', 'Mechanical');
INSERT INTO `ca`.`department` (`id`, `name`) VALUES ('4 ', 'Information Technology');

CREATE TABLE `semester` (
  `semesterid` int(11) NOT NULL,
  `label` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`semesterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `ca`.`semester` (`semesterid`, `label`) VALUES ('1', 'AY2017/2018Sem1');
INSERT INTO `ca`.`semester` (`semesterid`, `label`) VALUES ('2', 'AY2017/2018Sem2');
INSERT INTO `ca`.`semester` (`semesterid`, `label`) VALUES ('3', 'AY2018/2019Sem1');
INSERT INTO `ca`.`semester` (`semesterid`, `label`) VALUES ('4', 'AY2019/2020Sem1');
INSERT INTO `ca`.`semester` (`semesterid`, `label`) VALUES ('5', 'AY2019/2020Sem2');


CREATE TABLE `status` (
  `statusid` int(11) NOT NULL,
  `label` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`statusid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('1', 'Enrolled');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('2', 'OnLeave');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('3', 'Graduated');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('4', 'Pending');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('5', 'Cancelled');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('6', 'Approved');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('7', 'Rejected');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('8', 'Undergoing');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('9', 'Completed');
INSERT INTO `ca`.`status` (`statusid`, `label`) VALUES ('10', 'Present');

/*YZ & YH*/
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `birth_date` date NOT NULL,
  `degree` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `name` varchar(50) NOT NULL,
  `semester` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `FKrh553i4eit6fh7q3mcf00hr6q` (`semester`),
  KEY `FK4ars6th2ialcy82gkarfd12c4` (`status`),
  CONSTRAINT `FK4ars6th2ialcy82gkarfd12c4` FOREIGN KEY (`status`) REFERENCES `status` (`statusid`),
  CONSTRAINT `FKrh553i4eit6fh7q3mcf00hr6q` FOREIGN KEY (`semester`) REFERENCES `semester` (`semesterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `faculties` (
  `faculty_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `department_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`faculty_id`),
  KEY `FKc61mqvfd2mwkgx3os7bsuvik7` (`department_id`),
  KEY `FK9pipcyyn5xgcrcqdkxm61kh7o` (`status`),
  CONSTRAINT `FK9pipcyyn5xgcrcqdkxm61kh7o` FOREIGN KEY (`status`) REFERENCES `status` (`statusid`),
  CONSTRAINT `FKc61mqvfd2mwkgx3os7bsuvik7` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `ca`.`faculties` (`faculty_id`, `name`, `department_id`, `status`) VALUES ('101', 'Teo Chee Guan', '2', '10');
INSERT INTO `ca`.`faculties` (`faculty_id`, `name`, `department_id`, `status`) VALUES ('102', 'Billie Griffith', '4', '10');
INSERT INTO `ca`.`faculties` (`faculty_id`, `name`, `department_id`, `status`) VALUES ('103', 'Wee Kai Lie', '4', '2');
INSERT INTO `ca`.`faculties` (`faculty_id`, `name`, `department_id`, `status`) VALUES ('104', 'Michelle Sun', '1', '2');
INSERT INTO `ca`.`faculties` (`faculty_id`, `name`, `department_id`, `status`) VALUES ('105', 'Kelly Hunter', '3', '10');

/*
-- Query: SELECT * FROM ca.students
LIMIT 0, 1000

-- Date: 2019-12-06 20:58
*/
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10001,'1 Simei Avenue North','1995-12-15','Bachelor','huangyuzhe2019@163.com','M','8612-1550','Terry Foo',5,10);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10002,'80 Serangoon North View','1996-04-18','Bachelor','E0457814@u.nus.edu','F','8743-8613','Madison Quek',5,9);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10003,'29 Jalan Hitam Manis','1996-07-04','Bachelor','shutong.han@u.nus.edu','M','9330-3194','Stephan Leng',1,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10004,'Blk 44 Hougang Street 80, #02-45','1996-11-15','Master','E0003064@u.nus.edu','M','9924-5896','Anthony Hong',5,2);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10005,'Blk 255 Bukit Merah Street 11, #17-42','1997-12-23','Phd','E0457801@u.nus.edu','M','9787-0844','Gene Gupta',5,10);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10006,'Blk 47 Lorong 1 Marsiling, #14-43','1996-08-17','Master','E0457834@u.nus.edu','F','8951-2508','Sheryl Ho',2,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10007,'Blk 394 Bedok Street 82, #15-17','1996-02-14','Master','JoleneChai@gmail.com','F','9951-0220','Jolene Chai',5,8);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10008,'31 Marsiling Lane, #09-36','1995-05-22','Master','OhGuohuaHudson@gmail.com','M','8844-1072','Oh Guohua Hudson',5,4);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10009,'Blk 162 Geylang Street 28, #12-43','1997-08-31','Master','JosephZhou@gmail.com','M','9593-0823','Joseph Zhou',2,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10010,'Blk 37 Clementi Street 33, #03-05','1996-05-01','Master','PhuaBeeChoo@gmail.com','F','9356-0624','Phua Bee Choo',5,4);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10011,'Blk 258 Ang Mo Kio Street 13, #13-25','1997-10-18','Master','MargieTan@gmail.com','F','8591-6625','Margie Tan',5,6);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10012,'54 Choa Chu Kang Avenue','1997-01-18','Bachelor','ZikriOthman@gmail.com','M','8059-6132','Zikri Othman',5,2);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10013,'8 Bukit Timah Lane','1996-01-25','Bachelor','JamieLoh@gmail.com','F','9104-3567','Jamie Loh',5,5);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10014,'73 Yishun Gate','1996-04-12','Bachelor','GlendaWoo@gmail.com','F','9917-6801','Glenda Woo',5,1);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10015,'1 Jalan Pelatina','1997-04-07','Bachelor','MichealAbisheganaden@gmail.com','M','9662-4743','Micheal Abisheganaden',1,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10016,'Blk 44 Hougang Street 80, #02-45','1996-02-22','Master','AnthonyHong@gmail.com','M','9924-5896','Anthony Hong',5,10);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10017,'Blk 255 Bukit Merah Street 11, #17-42','1996-02-23','Master','GeneGupta@gmail.com','M','9787-0844','Gene Gupta',5,4);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10018,'84 Chin Bee Avenue North, #18-23','1997-01-04','Bachelor','LeonardLeow@gmail.com','F','8536-5216','Leonard Leow',5,7);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10019,'42 Buangkok Park','1997-04-29','Bachelor','KeeMun-weiCaroline@gmail.com','F','9089-9263','Kee Mun-wei Caroline',1,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10020,'Blk 225 Bedok Street 36, #08-22','1997-09-30','Bachelor','ToyPinPin@gmail.com','F','9203-2828','Toy Pin Pin',5,7);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10021,'4 Jalan Chempedak','1996-06-13','Phd','RuthYap@gmail.com','F','9258-9344','Ruth Yap',5,6);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10022,'Blk 100 Lorong 12 Hillview, #11-37','1996-02-26','Bachelor','IsabellIswaran@gmail.com','F','8940-9526','Isabell Iswaran',2,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10023,'54 Choa Chu Kang Avenue','1996-08-16','Bachelor','ZikriOthman@gmail.com','M','8059-6132','Zikri Othman',5,8);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10024,'84 Rochor Green','1996-12-12','Bachelor','ConniePoon@gmail.com','F','9857-3726','Connie Poon',1,3);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10025,'3 Geylang East Road','1996-03-14','Master','MadisonYongBeeLian@gmail.com','F','8932-0198','Madison Yong Bee Lian',5,8);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10026,'2 Pasir Panjang Terrace, #10-09','1996-03-12','Bachelor','NatalieZhang@gmail.com','F','8098-7234','Natalie Zhang',5,9);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10027,'4 Jalan Kwok Min','1997-03-14','Bachelor','RobbKok@gmail.com','M','9909-4788','Robb Kok',5,8);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10028,'3 Jalan Chempah','1997-04-03','Bachelor','GohShiwenMin@gmail.com','F','9763-8441','Goh Shiwen Min',5,2);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10029,'74 Jalan Simpang Bedok','1997-12-05','Master','CaroleLau@gmail.com','F','9738-4290','Carole Lau',5,6);
INSERT INTO `ca`.`students` (`student_id`,`address`,`birth_date`,`degree`,`email`,`gender`,`mobile`,`name`,`semester`,`status`) VALUES (10030,'6 Pasir Panjang Park','1997-11-19','Bachelor','KohGuoxingAdam@gmail.com','M','9560-1448','Koh Guoxing Adam',5,4);

/*ST*/
CREATE TABLE `allusers` (
  `username` varchar(50) NOT NULL,
  `passwordHash` varchar(60) DEFAULT NULL,
  `userType` varchar(45) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('issl', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Admin', '105', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('isssuria', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Faculty', '103', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('isstcw', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Faculty', '102', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('isstin', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Faculty', '101', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('issyk', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Faculty', '104', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsa', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10007', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsb', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10008', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsc', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10009', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsd', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10010', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhse', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10011', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsf', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10012', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsg', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10013', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsh', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10014', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhshst', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10001', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsi', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10015', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsj', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10016', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsjames', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10003', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsk', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10017', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsl', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10018', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsm', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10019', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsn', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10020', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhso', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10021', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsp', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10022', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsq', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10023', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsr', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10024', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhss', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10025', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhst', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10026', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsu', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10027', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsv', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10028', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsw', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10029', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsx', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10030', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsyinghuai', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10005', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsyk', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10002', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsysh', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10006', '1');
INSERT INTO `ca`.`allusers`(`username`,`passwordHash`,`userType`,`id`,`enabled`)VALUES	('nhsyuzhe', '$2a$10$kZnlY7KeuShrxd0YF.E/LOmCsnv55SX8LVXG/ZwNeSJvanKmf.FqW', 'Student', '10004', '1');

/*James*/
CREATE TABLE `leaves` (
  `startdate` date NOT NULL,
  `usertype` varchar(45) NOT NULL,
  `id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `enddate` date NOT NULL,
  `reason` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`startdate`,`usertype`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2018-12-31', 'Faculty', '101', '6', '2019-01-01', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-01-02', 'Faculty', '102', '6', '2019-01-02', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-01-03', 'Faculty', '103', '6', '2019-01-03', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-01-04', 'Faculty', '104', '6', '2019-01-04', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-01-05', 'Faculty', '105', '7', '2019-01-05', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-01-06', 'Faculty', '101', '6', '2019-01-06', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-31', 'Faculty', '102', '6', '2020-01-02', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2020-02-02', 'Faculty', '103', '4', '2020-03-02', 'Unpaid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-01', 'Faculty', '104', '6', '2020-01-01', 'Paid leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-01', 'Student', '10001', '6', '2019-12-01', 'Medical leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-02', 'Student', '10002', '6', '2019-12-02', 'Medical leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-03', 'Student', '10003', '6', '2019-12-18', 'Medical leave');
INSERT INTO `ca`.`leaves` (`startdate`, `usertype`, `id`, `status`, `enddate`, `reason`) VALUES ('2019-12-04', 'Student', '10004', '7', '2019-12-04', 'No reason');

CREATE TABLE `courseruns` (
  `courseName` varchar(45) NOT NULL,
  `courseCode` varchar(8) DEFAULT NULL,
  `semester` int(11) DEFAULT NULL,
  `facultyId` int(11) DEFAULT NULL,
  `courseUnit` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseName`),
  KEY `semester_idx` (`semester`),
  KEY `facultyId_idx` (`facultyId`),
  CONSTRAINT `facultyId` FOREIGN KEY (`facultyId`) REFERENCES `faculties` (`faculty_id`),
  CONSTRAINT `semester` FOREIGN KEY (`semester`) REFERENCES `semester` (`semesterid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Chemical Engineering AY17/18S1','CCE01',1,101,16);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Chemical Engineering AY18/19S1','CCE01',3,101,16);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Chemical Engineering AY19/20S1','CCE01',4,101,16);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Digital Leadership AY17/18S1','ITDL01',1,102,6);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Digital Leadership AY17/18S2','ITDL02',2,102,6);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Digital Leadership AY18/19S1','ITDL01',3,102,6);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Digital Leadership AY19/20S1','ITDL01',4,102,6);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Digital Leadership AY19/20S2','ITDL02',5,102,6);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Information System AY17/18S1','ITIS01',1,103,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Information System AY17/18S2','ITIS02',2,103,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Information System AY18/19S1','ITIS01',3,103,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Information System AY19/20S1','ITIS01',4,103,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Information System AY19/20S2','ITIS02',5,103,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Intelligent System AY17/18S1','CIS01',1,104,10);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Intelligent System AY17/18S2','CIS02',2,104,10);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Intelligent System AY18/19S1','CIS01',3,104,10);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Intelligent System AY19/20S1','CIS01',4,104,10);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Intelligent System AY19/20S2','CIS02',5,104,10);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Manufacturing Engineering AY17/18S1','MME01',1,105,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Manufacturing Engineering AY17/18S2','MME02',2,105,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Manufacturing Engineering AY18/19S1','MME01',3,105,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Manufacturing Engineering AY19/20S1','MME01',4,105,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Manufacturing Engineering AY19/20S2','MME02',5,105,8);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Software Engineering AY17/18S2','CSE02',2,104,12);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Software Engineering AY17/19S1','CSE201',1,104,12);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Software Engineering AY19/20S1','CSE01',4,104,12);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Software Engineering AY19/20S2','CSE02',5,104,12);
INSERT INTO `ca`.`courseruns` (`courseName`,`courseCode`,`semester`,`facultyId`,`courseUnit`) VALUES ('Software Engineerng AY18/19S1','CSE201',3,104,12);



/* Shaohang*/
CREATE TABLE `courserunstudents` (
  `courseName` varchar(50) NOT NULL,
  `studentId` int(11) NOT NULL,
  `grade` varchar(2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`courseName`,`studentId`),
  KEY `studentId_idx` (`studentId`),
  KEY `status_idx` (`status`),
  CONSTRAINT `courseName` FOREIGN KEY (`courseName`) REFERENCES `courseruns` (`courseName`),
  CONSTRAINT `status` FOREIGN KEY (`status`) REFERENCES `status` (`statusid`),
  CONSTRAINT `studentId` FOREIGN KEY (`studentId`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*
-- Query: SELECT * FROM ca.courserunstudents
LIMIT 0, 1000

-- Date: 2019-12-06 20:39
*/
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY17/18S1',10001,'A+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY17/18S1',10002,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY17/18S1',10003,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY18/19S1',10001,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY18/19S1',10002,'B-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY18/19S1',10003,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY19/20S1',10001,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY19/20S1',10002,'D',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Chemical Engineering AY19/20S1',10003,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S1',10004,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S1',10005,'B-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S1',10006,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S2',10004,'D+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S2',10005,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY17/18S2',10006,'B-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY18/19S1',10004,'C+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY18/19S1',10005,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY18/19S1',10006,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S1',10007,'D',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S1',10008,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S1',10009,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S2',10007,'N',8);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S2',10008,'N',4);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Digital Leadership AY19/20S2',10009,'N',5);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S1',10007,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S1',10008,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S1',10009,'B-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S2',10010,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S2',10011,'D',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY17/18S2',10012,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY18/19S1',10010,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY18/19S1',10011,'C+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY18/19S1',10012,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S1',10010,'B-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S1',10011,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S1',10012,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S2',10013,'N',6);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S2',10014,'N',7);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Information System AY19/20S2',10015,'N',4);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S1',10013,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S1',10014,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S1',10015,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S2',10013,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S2',10014,'A+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY17/18S2',10015,'C+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY18/19S1',10016,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY18/19S1',10017,'A+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY18/19S1',10018,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S1',10016,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S1',10017,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S1',10018,'C+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S2',10016,'N',6);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S2',10017,'N',5);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Intelligent System AY19/20S2',10018,'N',6);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S1',10019,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S1',10020,'D+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S1',10021,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S2',10019,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S2',10020,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY17/18S2',10021,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY18/19S1',10019,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY18/19S1',10020,'D',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY18/19S1',10021,'A+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S1',10022,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S1',10023,'D',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S1',10024,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S2',10022,'N',4);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S2',10023,'N',7);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Manufacturing Engineering AY19/20S2',10024,'N',4);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/18S2',10025,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/18S2',10026,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/18S2',10027,'A-',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/19S1',10025,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/19S1',10026,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY17/19S1',10027,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S1',10025,'B',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S1',10026,'C',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S1',10027,'A+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S2',10028,'N',7);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S2',10029,'N',5);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineering AY19/20S2',10030,'N',4);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineerng AY18/19S1',10028,'B+',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineerng AY18/19S1',10029,'A',9);
INSERT INTO `ca`.`courserunstudents` (`courseName`,`studentId`,`grade`,`status`) VALUES ('Software Engineerng AY18/19S1',10030,'B+',9);

CREATE TABLE `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `coursename` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;