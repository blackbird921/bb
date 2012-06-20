INSERT INTO REF_PAYMENT_TX_TYPE VALUES (1, '充值', 0);
INSERT INTO REF_PAYMENT_TX_TYPE VALUES (2, '兑现', 0);

INSERT INTO REF_PAYMENT_TYPE VALUES (1, '信用卡', 0);
INSERT INTO REF_PAYMENT_TYPE VALUES (2, '支付宝', 0);

INSERT INTO REF_SEX VALUES (1, '', 0);
INSERT INTO REF_SEX VALUES (2, '男', 0);
INSERT INTO REF_SEX VALUES (3, '女', 0);

INSERT INTO COMPANY (ID, NAME, VERSION) VALUES (1, '青鸟', 0);

INSERT INTO CONFIGURATION (ID, NAME, VALUE, VERSION) VALUES (1, 'CHECKIN_VALID_RANGE_IN_METER', 30, 0);

INSERT INTO LOCATION ( ID, ADDRESS, CITY, CONTACT_NAME, CREATE_DATE, LATITUDE, LONGITUDE, NAME, PHONE, REJECTION_REASON, STATUS, VERSION, CUSTOMER ) VALUES ( 1, '某某健身馆地址', '北京', '张某某', '2012-03-07 00:00:00', 3.3333, 4.4444, '某某健身馆', '01000000', '', 0, 0, NULL );

INSERT INTO CARD ( ID, CREATE_DATE, DESCRIPTION, END_DATE, MAX_USAGE, NAME, START_DATE, VERSION, COMPANY, LOCATION ) VALUES ( 1, NULL, '青鸟三周免费健身卡的介绍，青鸟三周免费健身卡的介绍，青鸟三周免费健身卡的介绍，青鸟三周免费健身卡的介绍，青鸟三周免费健身卡的介绍。', NULL, 1, '青鸟三周免费健身卡', '2012-03-07 00:00:00', 0, 1, 1 );
INSERT INTO CARD ( ID, CREATE_DATE, DESCRIPTION, END_DATE, MAX_USAGE, NAME, START_DATE, VERSION, COMPANY, LOCATION ) VALUES ( 2, NULL, '未来一种新的健身卡的介绍', NULL, 1, '未来一种新的健身卡', '2012-03-07 00:00:00', 0, 1, null );

INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (1, 1, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (2, 2, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (3, 3, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (4, 4, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (5, 5, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (6, 6, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (7, 7, true, 0);

INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (1, true, 5, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (2, true, 10, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (3, true, 20, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (4, true, 30, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (5, true, 40, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (6, true, 50, 0);

INSERT INTO CUSTOMER ( ID, ADDRESS, BIO, BIRTHDAY, CITY, CUSTOMER_ROLE, DISABLE_END_DATE, DISABLE_REASON, DISABLE_START_DATE, EMAIL, NAME, PASSWORD, PHONE, REGISTRATION_DATE, STATUS, USERNAME, VERSION, SEX ) VALUES ( 1, '张三地址', '', NULL, '北京', 0, NULL, '', NULL, 'zq72@yahoo.com', '张三', '0000', '010100000', '2012-03-07 00:00:00', 0, 'a1', 0, 2 );
INSERT INTO CUSTOMER ( ID, ADDRESS, BIO, BIRTHDAY, CITY, CUSTOMER_ROLE, DISABLE_END_DATE, DISABLE_REASON, DISABLE_START_DATE, EMAIL, NAME, PASSWORD, PHONE, REGISTRATION_DATE, STATUS, USERNAME, VERSION, SEX ) VALUES ( 2, 'a2 address', '', NULL, '北京', 0, NULL, '', NULL, 'a2@a.com', 'a2 x', '0000', '010100000', '2012-03-07 00:00:00', 0, 'a2', 0, 2 );
INSERT INTO CUSTOMER ( ID, ADDRESS, BIO, BIRTHDAY, CITY, CUSTOMER_ROLE, DISABLE_END_DATE, DISABLE_REASON, DISABLE_START_DATE, EMAIL, NAME, PASSWORD, PHONE, REGISTRATION_DATE, STATUS, USERNAME, VERSION, SEX ) VALUES ( 3, 'a3 address', '', NULL, '北京', 0, NULL, '', NULL, 'a3@a.com', 'a3 x', '0000', '010100000', '2012-03-07 00:00:00', 0, 'a3', 0, 3 );

INSERT INTO CUSTOMER_PRODUCT ( ID , END_DATE , START_DATE , VERSION , CUSTOMER , PRODUCT_COMMIT , PRODUCT_STAKE ) VALUES ( 1 , NULL , '2012-03-07 00:00:00' , 0 , 1 , 3 , 1 );
INSERT INTO CUSTOMER_PRODUCT ( ID , END_DATE , START_DATE , VERSION , CUSTOMER , PRODUCT_COMMIT , PRODUCT_STAKE ) VALUES ( 2 , NULL , '2012-03-07 00:00:00' , 0 , 2 , 3 , 1 );
INSERT INTO CUSTOMER_PRODUCT ( ID , END_DATE , START_DATE , VERSION , CUSTOMER , PRODUCT_COMMIT , PRODUCT_STAKE ) VALUES ( 3 , NULL , '2012-03-07 00:00:00' , 0 , 3 , 3 , 1 );

INSERT INTO CUSTOMER_CARD (ID, USED_DATE, VERSION, CARD, CUSTOMER, ISSUED_DATE, STATUS) VALUES (1, '2012-03-07 00:00:00', 0, 1, 1, '2012-03-07 00:00:00', 'NOT USED');
INSERT INTO CUSTOMER_CARD (ID, USED_DATE, VERSION, CARD, CUSTOMER, ISSUED_DATE, STATUS) VALUES (2, null, 0, 1, 2, '2012-03-07 00:00:00', 'NOT USED');
INSERT INTO CUSTOMER_CARD (ID, USED_DATE, VERSION, CARD, CUSTOMER, ISSUED_DATE, STATUS) VALUES (3, null, 0, 1, 3, '2012-03-07 00:00:00', 'NOT USED');

INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 1, '2012-03-07 00:00:00', 0, false, '2012-03-07 00:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 2, '2012-03-07 00:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 11, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 12, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 13, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 14, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 15, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 16, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 17, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 18, '2012-03-07 02:00:00', 1, true,  '2012-03-07 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 3, '2012-03-17 00:00:00', 0, false, '2012-03-17 00:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 4, '2012-03-27 00:00:00', 1, false, '2012-03-27 01:00:00', 0, 1, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 5, '2012-03-07 00:00:00', 0, false, '2012-03-07 00:00:00', 0, 2, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 6, '2012-03-07 00:00:00', 1, true,  '2012-03-07 01:00:00', 0, 2, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 7, '2012-03-17 00:00:00', 0, false, '2012-03-17 00:00:00', 0, 2, 1 );
INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 8, '2012-03-27 00:00:00', 1, false, '2012-03-27 01:00:00', 0, 2, 1 );

INSERT INTO CUSTOMER_PAYMENT ( ID, ACCOUNT_ID, END_DATE, START_DATE, VERSION, CUSTOMER, PAYMENT_TYPE ) VALUES ( 1, '124564545465', '2013-03-07 00:00:00', '2012-03-07 00:00:00', 0, 1, 1 );

INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 1, 10, '2012-03-11 00:00:00', '2012-03-04 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 2, 10, '2012-03-18 00:00:00', '2012-03-11 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 3, 10, '2012-03-25 00:00:00', '2012-03-18 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 4, 10, '2012-04-02 00:00:00', '2012-03-25 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 5, 0, '2012-03-11 00:00:00', '2012-03-04 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 6, 0, '2012-03-18 00:00:00', '2012-03-11 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 7, 0, '2012-03-25 00:00:00', '2012-03-18 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 8, 5, '2012-04-01 00:00:00', '2012-03-25 00:00:00', 0, 1 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 9, 15, '2012-03-25 00:00:00', '2012-03-18 00:00:00', 0, 3 );
INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 10, -10, '2012-04-01 00:00:00', '2012-03-25 00:00:00', 0, 2 );


INSERT INTO CUSTOMER_TRANSACTION ( ID, AMOUNT, TRANSACTION_DATE, TRANSACTION_ERROR, VERSION, CUSTOMER, CUSTOMER_PAYMENT, TRANSACTION_TYPE ) VALUES ( 1, 10, '2012-03-07 00:00:00', '', 0, 1, 1, 1 );

-- INSERT INTO CONFIGURATIONS values(1,'mail_from', 'wheel4_team@126.com', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(2,'mail_smtp_port', '25', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(3,'mail_smtp_host', 'smtp.126.com', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(4,'mail_smtp_userName', 'wheel4_team', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(5,'mail_smtp_password', 'wheel4', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(6,'mail_smtp_auth', 'true', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(7,'web_host', 'http://124.237.77.248:8080/wheel4', 'WEB_HOST');
-- INSERT INTO CONFIGURATIONS values(8,'mail_content_file_path', '/WEB-INF/classes/emails', 'MAIL');
-- INSERT INTO CONFIGURATIONS values(9,'mail_content_file', 'emailContent.vm', 'MAIL');

-- INSERT INTO FAQ (ID, ANSWER, QUESTION, QUESTION_ORDER, VERSION) VALUES (, '', '', , 0);

INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (1,  '健身达人是什么概念？', '我们很多人都曾经有过这样的健身经历：雄心勃勃地购卡加入健身会所，轰轰烈烈地开始，然后逐渐松懈，最后草草收场。健身达人网的目标，是通过奖罚金制度，来激励大家更加坚定、持之以恒地遵守自己最初地计划。只要你按照承诺地出勤了，就能赢得奖金，而奖金，就是对那些懒惰的人处罚而得来的。具体而言，有如下几个步骤：<br><ul><li> 注册；<li> 设定每周出勤几次（1到7次）；<li> 设定每次缺勤的罚金（5到50元）；<li> 缴纳本周所需的押金（金额为出勤次数×每次罚金，比如设定一周出勤3次，每次罚金5元，即需押金15元）；<li> 按时出勤；<li> 每周日晚结算，收集罚金，分配奖金；</ul>',  1, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (2,  '适用于哪些健身场所？', '绝大多数的普通健身场所都适用。但有些健身场所会因为以下原因不适用：<br><ul><li> 与居住区域或者办公区域太过靠近，以至于GPS信号无法准确定位（如位于居民大楼或者办公大楼内的健身场所）；<li>所在地点无法使用手机定位的（如某些位于地下的场馆，或者没有手机信号的场馆）；<li> 我们无法验证为真实健身场所的地点；<li> 经我们确认，有过与不法分子串通提供虚假出勤的场所；</ul>',  2, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (3,  '我去的健身场所无法在手机应用中找到怎么办？', '你可以在手机应用中添加一个新的健身场所，并可以立即使用该场所。我们随后确认该场所真实有效后，会认可该次出勤，并添加该场所到我们的资料库。',  3, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (4,  '用什么样的手机可以考勤？', '目前只支持iPhone应用考勤，基于安卓系统的考勤应用正在开发中，敬请期待。',  4, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (5,  '如何用手机考勤？', '在你到达你的健身场所后，在手机上进入你的iPhone应用，点击"考勤"，计时即开始（系统会使用你当时所在的地理位置信息进行验证）。iPhone计时达到30分钟后，系统会自动记录一次有效考勤。请注意，每天最多只能考勤一次。（健身贵在坚持，切勿一曝十寒哦！）',  5, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (6,  '奖金何时结算？', '奖金每周结算一次。每个周日晚上23：00到次日凌晨，为系统结算时间，在此期间，系统无法接受考勤。',  6, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (7,  '奖金如何计算和分配？', '对未实现健身计划（即缺勤）的参与者，没收相应次数的罚金（比如承诺出勤3次，每次罚金5元，而实际该周出勤2次，即没收罚金5元。）。收集所有罚金后，对实现健身的（即全勤）的参与者，每人平均分配奖金。',  7, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (8,  '奖金如何兑现？', '奖金分配后，会自动进入注册会员在健身达人网的个人账户。会员可以选择兑现（转入个人的私人支付帐号如支付宝），或者留在账户中将来再兑现。',  8, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (9,  '中途可以修改计划吗？', '可以。但是修改会从下一周开始生效。',  9, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (10, '出差或者生病，不能去健身怎么办？如何请假？', '注册会员登录后，进入“我的空间”中的“请假”页面，即可请假。请注明请假原因。', 10, 0);
INSERT INTO FAQ (ID, QUESTION, ANSWER, QUESTION_ORDER, VERSION) VALUES (11, '如何退出活动？', '注册会员登录后，进入“我的空间”中的“请假”页面，即可请假。请假时如果未设定“请假结束”日期，即视为永久性请假，即退出活动。', 11, 0);



