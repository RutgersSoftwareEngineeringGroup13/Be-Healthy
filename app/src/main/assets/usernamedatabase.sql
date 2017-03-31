BEGIN TRANSACTION;
CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT 'en_US');
INSERT INTO `android_metadata` VALUES ('en_US');
CREATE TABLE "UserName" (
	`_id`	INTEGER,
	`username`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	PRIMARY KEY(`_id`)
);
INSERT INTO `UserName` VALUES (1,'admin','beHealthy');
CREATE TABLE "Food" (
	`_id`	INTEGER,
	`Food Name`	TEXT NOT NULL,
	`Calories`	INTEGER NOT NULL,
	`Fat`	DOUBLE NOT NULL,
	`Carbohydrates`	DOUBLE NOT NULL,
	`Protein`	INTEGER NOT NULL,
	PRIMARY KEY(`_id`)
);
INSERT INTO `Food` VALUES (1,'Chicken',300,8.0,2.0,25);
INSERT INTO `Food` VALUES (2,'Chicken Breast',236,5.1,44.29,0);
INSERT INTO `Food` VALUES (3,'Wild Alaskan Salmon 8 0z',322,14.37,44.97,0);
INSERT INTO `Food` VALUES (4,'Banana',89,0.3,1.1,22.8);
INSERT INTO `Food` VALUES (5,'Oatmeal (1 cup)',158,3.2,6.0,27);
INSERT INTO `Food` VALUES (6,'Brown Rice (1 cup)',216,1.8,5.0,45);
INSERT INTO `Food` VALUES (7,'Asaparagus (1 spear)',3,0.0,0.4,0.6);
COMMIT;
