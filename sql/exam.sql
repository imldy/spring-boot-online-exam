/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50717 (5.7.17)
 Source Host           : localhost:3306
 Source Schema         : exam

 Target Server Type    : MySQL
 Target Server Version : 50717 (5.7.17)
 File Encoding         : 65001

 Date: 21/04/2025 00:15:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action`  (
  `action_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '前端页面操作表主键id',
  `action_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '前端操作的名字',
  `action_description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面操作的描述',
  `default_check` tinyint(1) NOT NULL DEFAULT 0 COMMENT '当前操作是否需要校验,true为1,0为false',
  PRIMARY KEY (`action_id`) USING BTREE,
  UNIQUE INDEX `action_name`(`action_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '前端操作比如增删改查等的权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action` VALUES (1, 'add', '新增', 0);
INSERT INTO `action` VALUES (2, 'query', '查询', 0);
INSERT INTO `action` VALUES (3, 'get', '详情', 0);
INSERT INTO `action` VALUES (4, 'update', '修改', 0);
INSERT INTO `action` VALUES (5, 'delete', '删除', 0);
INSERT INTO `action` VALUES (6, 'import', '导入', 0);
INSERT INTO `action` VALUES (7, 'export', '导出', 0);

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `exam_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试表的主键',
  `exam_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试名称',
  `exam_avatar` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试的预览图',
  `exam_description` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试描述',
  `exam_question_ids` varchar(2048) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '当前考试下的题目的id用-连在一起地字符串',
  `exam_question_ids_partI` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part I Writing',
  `exam_question_ids_partII_a` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part II Listening Comprehension Section A',
  `exam_question_ids_partII_b` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part II Listening Comprehension Section B',
  `exam_question_ids_partII_c` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part II Listening Comprehension Section C',
  `exam_question_ids_partIII_a` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part III Reading Comprehension Section A',
  `exam_question_ids_partIII_b` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part III Reading Comprehension Section B',
  `exam_question_ids_partIII_c` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part III Reading Comprehension Section C',
  `exam_question_ids_partIV` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Part IV Translation',
  `exam_score` int(11) NOT NULL DEFAULT 0 COMMENT '当前考试的总分数',
  `exam_creator_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试创建者的用户id',
  `exam_time_limit` int(11) NOT NULL DEFAULT 0 COMMENT '考试的时间限制，单位为分钟',
  `exam_start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考试有效期开始时间',
  `exam_end_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考试有效期结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `exam_question_ids_partiia` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_question_ids_partiib` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_question_ids_partiic` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_question_ids_partiiia` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_question_ids_partiiib` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_question_ids_partiiic` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `exam_score_parti` int(11) NULL DEFAULT NULL,
  `exam_score_partiia` int(11) NULL DEFAULT NULL,
  `exam_score_partiib` int(11) NULL DEFAULT NULL,
  `exam_score_partiic` int(11) NULL DEFAULT NULL,
  `exam_score_partiiia` int(11) NULL DEFAULT NULL,
  `exam_score_partiiib` int(11) NULL DEFAULT NULL,
  `exam_score_partiiic` int(11) NULL DEFAULT NULL,
  `exam_score_partiv` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`exam_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '考试的详细信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('008dbfee729f4155b4c96d1b094089de', 'Test', '<p><br></p>', 'Test', '---b85668ca4634452cb5089fbb98c2a320-a2088a8e1a67435d91b9e9b7a1920432-41d03dea02394335ad7e4220719b43c0-1ab760a2b5084197b47b9841699d471d-2eae03c824664d79abb6779d024f7643-c3254638b4d5436d8bac846c6b787c0e-be9553b3a6fb46e18e149bbed00f8191-01601557feb94ef5a8f2db974710c47b', 'b85668ca4634452cb5089fbb98c2a320', NULL, NULL, NULL, NULL, NULL, NULL, '01601557feb94ef5a8f2db974710c47b', 40, 'a1b661031adf4a8f969f1869d479fe74', 130, '2025-04-19 18:21:55', '2025-04-19 18:21:55', '2025-04-19 18:21:55', '2025-04-19 18:21:55', 'a2088a8e1a67435d91b9e9b7a1920432', '41d03dea02394335ad7e4220719b43c0', '1ab760a2b5084197b47b9841699d471d', '2eae03c824664d79abb6779d024f7643', 'c3254638b4d5436d8bac846c6b787c0e', 'be9553b3a6fb46e18e149bbed00f8191', 5, 5, 5, 5, 5, 5, 5, 5);

-- ----------------------------
-- Table structure for exam_record
-- ----------------------------
DROP TABLE IF EXISTS `exam_record`;
CREATE TABLE `exam_record`  (
  `exam_record_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试记录表的主键',
  `exam_joiner_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试参与者的用户id',
  `exam_join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '参加考试的时间',
  `exam_time_cost` int(11) NULL DEFAULT 0 COMMENT '完成考试所用的时间,单位分钟',
  `exam_join_score` int(11) NOT NULL DEFAULT 0 COMMENT '参与考试的实际得分',
  `exam_result_level` int(11) NULL DEFAULT 0 COMMENT '考试结果的等级',
  `answer_option_ids` varchar(4096) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `exam_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`exam_record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '考试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_record
-- ----------------------------
INSERT INTO `exam_record` VALUES ('0c263cbd6f6849adbcbab15c20894003', 'a1b661031adf4a8f969f1869d479fe74', '2025-04-19 20:02:09', NULL, 0, NULL, 'b85668ca4634452cb5089fbb98c2a320@False_Part I 写作', '008dbfee729f4155b4c96d1b094089de');
INSERT INTO `exam_record` VALUES ('f301b528ba0940df8c735a8bf63744ed', '79392778a90d4639a297dbd0bae0f779', '2025-04-12 17:37:32', NULL, 15, NULL, '092a26ac743f4fb783df45295951ced7@True_d6678cbea3e941c9b5ebabde7985158e$93a06eedddc94412a2e3bf89307d6ed2@True_1807669e881b4aa5b4ce1f0815598207$94e912412190473d8b1eaab2b4d8f873@True_6a21da16a8334dad8801e0ed614377a8-92425fffcf2a4840bf4e99d27f14cdc3', 'f08f4643d5ab4ec0a55af3269da346cb');

-- ----------------------------
-- Table structure for exam_record_level
-- ----------------------------
DROP TABLE IF EXISTS `exam_record_level`;
CREATE TABLE `exam_record_level`  (
  `exam_record_level_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考试结果等级表的主键',
  `exam_record_level_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '考试结果等级的名称',
  `exam_record_level_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试结果等级的详细阐述',
  PRIMARY KEY (`exam_record_level_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '考试结果的等级' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of exam_record_level
-- ----------------------------
INSERT INTO `exam_record_level` VALUES (1, 'excellent', '优秀');
INSERT INTO `exam_record_level` VALUES (2, 'good', '良好');
INSERT INTO `exam_record_level` VALUES (3, 'normal', '一般');
INSERT INTO `exam_record_level` VALUES (4, 'pass', '及格');
INSERT INTO `exam_record_level` VALUES (5, 'fail', '不及格');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page`  (
  `page_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '前端页面表主键id',
  `page_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '页面的名称,要唯一',
  `page_description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面的功能性描述',
  `action_ids` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '页面对应的操作权限列表，用-连接action的id',
  PRIMARY KEY (`page_id`) USING BTREE,
  UNIQUE INDEX `page_name`(`page_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '前端页面表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of page
-- ----------------------------
INSERT INTO `page` VALUES (1, 'dashboard', '仪表盘', '1-2-3-4-5');
INSERT INTO `page` VALUES (2, 'exam-card', '考试列表', '1-6-3-4');
INSERT INTO `page` VALUES (3, 'exam-record-list', '考试记录', '1-6-3-4');
INSERT INTO `page` VALUES (4, 'question-admin', '问题管理', '1-6-3-4');
INSERT INTO `page` VALUES (5, 'exam-table-list', '考试管理', '1-6-3-4');
INSERT INTO `page` VALUES (6, 'user', '个人页', '1-6-3-4-5-7');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `question_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '题目的主键',
  `question_name` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '题目的名字',
  `question_score` int(11) NOT NULL DEFAULT 0 COMMENT '题目的分数',
  `question_creator_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '题目创建者的用户id',
  `question_level_id` int(11) NOT NULL DEFAULT 0 COMMENT '题目难易度级别',
  `question_type_id` int(11) NOT NULL DEFAULT 0 COMMENT '题目的类型，比如单选、多选、判断等',
  `question_category_id` int(11) NOT NULL DEFAULT 0 COMMENT '题目的类型，比如数学、英语、政治等',
  `question_description` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '题目额外的描述',
  `question_option_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '题目的选项，用选项的id用-连在一起表示答案',
  `question_answer_option_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '题目的答案，用选项的id用-连在一起表示答案',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `audio_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '考试题目表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('01601557feb94ef5a8f2db974710c47b', '<p class=\"MsoNormal\"><span lang=\"EN-US\">In China, innovation is flourishing in an\nunprecedented speed. In order to surpass the developed countries in the world\nas fast as it can in science and technology, China has increased funds for\nresearch and development substantially in recent years. Chinese universities\nand institutes are actively carrying out innovation research, which cover\nhigh-technology fields such as big data, biochemistry, new energy and robots,\netc. They are also cooperating with science and technology parks in different\nregions so as to commercialize the fruits of their innovation. Meanwhile, to\nadapt to the constantly changing and increasing needs of foreign and domestic\nmarkets, Chinese entrepreneurs are also competing to be pioneers in innovating\nboth products and business models</span></p>', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 711, 11, '<p><br></p>', '', '', '2025-04-19 17:59:55', '2025-04-19 11:50:09', NULL);
INSERT INTO `question` VALUES ('1ab760a2b5084197b47b9841699d471d', '听力C', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 531, 11, '<p><br></p>', '14aff85135934664a89b9232824ef2e7', '14aff85135934664a89b9232824ef2e7', '2025-04-19 17:45:23', '2025-04-19 17:45:23', '/api/file/download/get?filePath=upload_files/audio/ECO112801_0126692yDG.mp3');
INSERT INTO `question` VALUES ('2eae03c824664d79abb6779d024f7643', '阅读A', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 611, 11, '<p><br></p>', '0b42cc801a5a47a0be5bd5f930145f0f', '0b42cc801a5a47a0be5bd5f930145f0f', '2025-04-19 17:47:28', '2025-04-19 09:49:11', NULL);
INSERT INTO `question` VALUES ('41d03dea02394335ad7e4220719b43c0', '<p>听力B</p>', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 521, 11, '<p><br></p>', 'ff83b105b66145ffa26faf94a8832dcd', 'ff83b105b66145ffa26faf94a8832dcd', '2025-04-19 17:37:32', '2025-04-19 09:44:26', '/api/file/download/get?filePath=upload_files/audio/ECO112801_0126692yDG.mp3');
INSERT INTO `question` VALUES ('a2088a8e1a67435d91b9e9b7a1920432', '<p class=\"MsoNormal\"><b><span lang=\"EN-US\">Questions\n1 to 4 are based on the conversation you have just heard.<o:p></o:p></span></b></p><p class=\"MsoNormal\"><span lang=\"EN-US\">1. A) Project organizer.&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;B) Public relations officer.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">C) Marketing manager. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D) Market research consultant.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">2. A) Quantitative advertising research. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;B)\nQuestionnaire design.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">C) Research methodology. &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D) Interviewer training.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">3. A) They are intensive studies of\npeople’s spending habits.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">B) They examine relations between producers\nand customers.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">C) They look for new and effective ways to\npromote products.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">D) They study trends or customer\nsatisfaction over a long period.<o:p></o:p></span></p><p class=\"MsoNormal\"><span lang=\"EN-US\">4. A) The lack of promotion\nopportunity.&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;B) Checking charts and tables.<o:p></o:p></span></p><p class=\"MsoNormal\">\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n</p><p class=\"MsoNormal\"><span lang=\"EN-US\">C) Designing questionnaires.&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D) The persistent intensity.<o:p></o:p></span></p>', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 511, 11, '<p><br></p>', '81f07fc80f6645edae2cde618c1701cb-cc7920eb00bb40d2909de6065543525a-af25ea442acb44c095ff63a4fcc8cd83-7be5f3178e0b42cc9de646e2d022be39-dd97d7f03792429eb6b739aa954ee5f8-d5d70e2e892241b3b2fa61c002922cab', '81f07fc80f6645edae2cde618c1701cb-dd97d7f03792429eb6b739aa954ee5f8', '2025-04-19 17:08:22', '2025-04-19 12:47:11', '/api/file/download/get?filePath=upload_files/audio/ECO112801_0126692yDG.mp3');
INSERT INTO `question` VALUES ('b85668ca4634452cb5089fbb98c2a320', 'For this part, you are allowed 30 minutes to write a short essay on living in the virtual world. Try to imagine what will happen when people spend more and more time in the virtual world instead ofinteracting in the real world. You are required to write at least 150 words but no more than 200 words.', 20, '68042014e23c4ebea7234cb9c77cee5c', 1, 411, 11, '在这一部分，你有30分钟的时间写一篇关于生活在虚拟世界的短文。试想当人们在虚拟世界中花费越来越多的时间，而不是在现实世界中互动时，会发生什么。你需要写至少150词但不超过200词。', '', '', '2025-04-14 21:27:03', '2025-04-19 08:02:09', NULL);
INSERT INTO `question` VALUES ('be9553b3a6fb46e18e149bbed00f8191', '<p>阅读Caa</p>', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 631, 11, '<p><br></p>', '2c26fa107b574e8dbd01fd9e6d4b99cb', '2c26fa107b574e8dbd01fd9e6d4b99cb', '2025-04-19 17:58:36', '2025-04-19 11:53:35', NULL);
INSERT INTO `question` VALUES ('c3254638b4d5436d8bac846c6b787c0e', '<p>阅读B</p>', 5, 'a1b661031adf4a8f969f1869d479fe74', 1, 621, 11, '<p><br></p>', '940723faf96f4a00b67483a7664098c6', '940723faf96f4a00b67483a7664098c6', '2025-04-19 17:55:32', '2025-04-19 17:55:32', NULL);

-- ----------------------------
-- Table structure for question_category
-- ----------------------------
DROP TABLE IF EXISTS `question_category`;
CREATE TABLE `question_category`  (
  `question_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题类别表的主键',
  `question_category_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '问题类别名称',
  `question_category_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '问题类别的描述',
  PRIMARY KEY (`question_category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '题目类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_category
-- ----------------------------
INSERT INTO `question_category` VALUES (11, 'CET4', '大学英语四级');
INSERT INTO `question_category` VALUES (12, 'CET6', '大学英语六级');

-- ----------------------------
-- Table structure for question_level
-- ----------------------------
DROP TABLE IF EXISTS `question_level`;
CREATE TABLE `question_level`  (
  `question_level_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目难易度的主键',
  `question_level_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '题目难易度名称',
  `question_level_description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '题目难易度的描述',
  PRIMARY KEY (`question_level_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '问题的难易度级别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_level
-- ----------------------------
INSERT INTO `question_level` VALUES (1, 'high', '难');
INSERT INTO `question_level` VALUES (2, 'middle', '中');
INSERT INTO `question_level` VALUES (3, 'low', '易');

-- ----------------------------
-- Table structure for question_option
-- ----------------------------
DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option`  (
  `question_option_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '题目选项表的主键',
  `question_option_content` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '选项的内容',
  `question_option_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '选项的额外描述，可以用于题目答案解析',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`question_option_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '题目的选项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_option
-- ----------------------------
INSERT INTO `question_option` VALUES ('0830630418cf49878b0d76fffffcc305', 'A', NULL, '2025-04-19 09:13:43', '2025-04-19 09:13:43');
INSERT INTO `question_option` VALUES ('0b42cc801a5a47a0be5bd5f930145f0f', 'AA', NULL, '2025-04-19 09:49:11', '2025-04-19 09:49:11');
INSERT INTO `question_option` VALUES ('0fc59a0e732540809111352e21042822', 'B', NULL, '2025-04-19 09:12:55', '2025-04-19 09:12:55');
INSERT INTO `question_option` VALUES ('14aff85135934664a89b9232824ef2e7', 'CCC', NULL, '2025-04-19 09:45:23', '2025-04-19 09:45:23');
INSERT INTO `question_option` VALUES ('1807669e881b4aa5b4ce1f0815598207', 'A', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('2c26fa107b574e8dbd01fd9e6d4b99cb', 'CCCC', NULL, '2025-04-19 11:53:35', '2025-04-19 11:53:35');
INSERT INTO `question_option` VALUES ('3a731c642a084c77bb3593dd35c1403a', 'A', NULL, '2025-04-19 09:12:55', '2025-04-19 09:12:55');
INSERT INTO `question_option` VALUES ('6211de379df547f4bcf47096a98ed482', 'B', NULL, '2025-04-19 09:13:43', '2025-04-19 09:13:43');
INSERT INTO `question_option` VALUES ('6a21da16a8334dad8801e0ed614377a8', 'B', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('6d6046d10801419c833dfdb4911f33ac', 'D', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('74a6923145a64c74bfb1075d023a8ac8', 'C', NULL, '2025-04-19 09:20:58', '2025-04-19 09:20:58');
INSERT INTO `question_option` VALUES ('7be5f3178e0b42cc9de646e2d022be39', 'D) Market research consultant', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('81f07fc80f6645edae2cde618c1701cb', 'A) Project organizer.', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('84d277e4b3f84b86bc4d69d59c2cb1fa', '55', NULL, '2025-04-19 09:30:37', '2025-04-19 09:30:37');
INSERT INTO `question_option` VALUES ('92425fffcf2a4840bf4e99d27f14cdc3', 'A', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('940723faf96f4a00b67483a7664098c6', 'CC', NULL, '2025-04-19 09:55:32', '2025-04-19 09:55:32');
INSERT INTO `question_option` VALUES ('9bd42b630f004132bee591eb86962816', '4', NULL, '2025-04-19 09:29:30', '2025-04-19 09:29:30');
INSERT INTO `question_option` VALUES ('a72e8b6f8bd2474498ee19ee865bb0de', 'A', NULL, '2025-04-19 09:13:42', '2025-04-19 09:13:42');
INSERT INTO `question_option` VALUES ('add5639c8b6c4e779f390737414f7c83', '错误', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('af25ea442acb44c095ff63a4fcc8cd83', 'C) Marketing manager.', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('bed70e40fbe14e74a12b9ed753337e10', 'D', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('c45d206809e749d0896b75178b880183', 'A', NULL, '2025-04-19 09:20:58', '2025-04-19 09:20:58');
INSERT INTO `question_option` VALUES ('cc7920eb00bb40d2909de6065543525a', 'B) Public relations officer.', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('d475a63cc96c48c98f89e50f95ba78c6', 'C', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('d5d70e2e892241b3b2fa61c002922cab', 'B) Questionnaire design.', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('d6678cbea3e941c9b5ebabde7985158e', '正确', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('dcd33f4966a4485aa46a99d294beb7c1', 'B', NULL, '2025-04-19 09:13:42', '2025-04-19 09:13:42');
INSERT INTO `question_option` VALUES ('dd97d7f03792429eb6b739aa954ee5f8', 'A) Quantitative advertising research.', NULL, '2025-04-19 12:47:11', '2025-04-19 12:47:11');
INSERT INTO `question_option` VALUES ('e8f019688db847babcbe87a999d7d8f5', 'B', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('e915bf7fb099417ab5d6291c939b76d2', 'C', NULL, '2025-04-14 12:52:42', '2025-04-14 12:52:42');
INSERT INTO `question_option` VALUES ('f1cdd94d8fd0405eb021953a7c61cc70', '22', NULL, '2025-04-19 09:28:13', '2025-04-19 09:28:13');
INSERT INTO `question_option` VALUES ('ff83b105b66145ffa26faf94a8832dcd', 'AA', NULL, '2025-04-19 09:44:26', '2025-04-19 09:44:26');

-- ----------------------------
-- Table structure for question_type
-- ----------------------------
DROP TABLE IF EXISTS `question_type`;
CREATE TABLE `question_type`  (
  `question_type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目类型表的主键',
  `question_type_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '题目类型名称',
  `question_type_description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '题目类型的描述',
  PRIMARY KEY (`question_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 642 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '问题类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of question_type
-- ----------------------------
INSERT INTO `question_type` VALUES (411, 'PartI_Writing ', 'Part I 写作');
INSERT INTO `question_type` VALUES (511, 'PartII_ListeningComprehension_SectionA', 'Part II 听力理解_SectionA_短篇新闻');
INSERT INTO `question_type` VALUES (521, 'PartII_ListeningComprehension_SectionB', 'Part II 听力理解_SectionB_长对话');
INSERT INTO `question_type` VALUES (531, 'PartII_ListeningComprehension_SectionC', 'Part II 听力理解_SectionC_听力篇章');
INSERT INTO `question_type` VALUES (611, 'PartIII_ReadingComprehension_SectionA', 'Part III 阅读理解_SectionA_选词填空');
INSERT INTO `question_type` VALUES (621, 'PartIII_ReadingComprehension_SectionB', 'Part III 阅读理解_SectionB_长篇阅读');
INSERT INTO `question_type` VALUES (631, 'PartIII_ReadingComprehension_SectionC', 'Part III 阅读理解_SectionC_仔细阅读');
INSERT INTO `question_type` VALUES (711, 'PartIV_Translation', 'Part IV 翻译__汉译英');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表主键id',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色的描述',
  `role_detail` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色的详细功能阐述',
  `role_page_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '当前角色所能访问的页面的id集合',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员', '拥有教师和学生的所有权限', '1-2-3-4-5-6');
INSERT INTO `role` VALUES (2, 'teacher', '教师', '出题、组试卷、管理学生和试卷', '1-2-3-4-5-6');
INSERT INTO `role` VALUES (3, 'student', '学生', '参与考试，查看分数', '1-2-3-6');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id,主键，字符串型',
  `user_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `user_nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `user_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户秘密',
  `user_role_id` int(11) NOT NULL COMMENT '当前用户的角色的id',
  `user_avatar` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户的头像地址',
  `user_description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户的自我描述',
  `user_email` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_phone` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户手机号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_username`(`user_username`) USING BTREE,
  UNIQUE INDEX `user_email`(`user_email`) USING BTREE,
  UNIQUE INDEX `user_phone`(`user_phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('68042014e23c4ebea7234cb9c77cee5c', 'teacher', '李老师', 'YWRtaW4xMjM=', 2, 'https://i.loli.net/2019/11/02/OUfHKhMSwRv1ntX.jpg', '快乐就好', 'li@qq.com', '15555555555', '2019-05-06 18:03:27', '2025-04-12 06:33:40');
INSERT INTO `user` VALUES ('79392778a90d4639a297dbd0bae0f779', 'student', '小明', 'YWRtaW4xMjM=', 3, 'https://i.loli.net/2019/11/02/rCHKVJd4jTovzW9.jpg', '好好学习，天天向上', 'ming@qq.com', '17777777777', '2019-05-06 18:07:14', '2025-04-12 06:33:44');
INSERT INTO `user` VALUES ('a1b661031adf4a8f969f1869d479fe74', 'admin', '王主任', 'YWRtaW4xMjM=', 1, 'https://i.loli.net/2019/11/02/DvPiSRJrzoH1tkZ.gif', '绳锯木断，水滴石穿', 'wang@qq.com', '19999999999', '2019-05-06 17:57:44', '2025-04-12 06:45:19');

SET FOREIGN_KEY_CHECKS = 1;
