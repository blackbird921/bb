INSERT INTO REF_PAYMENT_TX_TYPE VALUES (1, '罚金', 0);
INSERT INTO REF_PAYMENT_TX_TYPE VALUES (2, '奖金', 0);

INSERT INTO REF_PAYMENT_TYPE VALUES (1, '信用卡', 0);
INSERT INTO REF_PAYMENT_TYPE VALUES (2, '支付宝', 0);

INSERT INTO REF_SEX VALUES (1, '', 0);
INSERT INTO REF_SEX VALUES (2, '男', 0);
INSERT INTO REF_SEX VALUES (3, '女', 0);

INSERT INTO COMPANY (ID, NAME, VERSION) VALUES (1, '青鸟', 0);

INSERT INTO LOCATION ( ID, ADDRESS, CITY, CONTACT_NAME, CREATE_DATE, GPS_INFO, NAME, PHONE, REJECTION_REASON, STATUS, VERSION, CUSTOMER ) VALUES ( 1, '某某健身馆地址', '北京', '张某某', '2012-03-07 00:00:00', '', '某某健身馆', '01000000', '', 0, 0, NULL );

INSERT INTO CARD ( ID, CREATE_DATE, DESCRIPTION, END_DATE, MAX_USAGE, NAME, START_DATE, VERSION, COMPANY, LOCATION ) VALUES ( 1, NULL, '青鸟三周免费体验卡的介绍，青鸟三周免费体验卡的介绍，青鸟三周免费体验卡的介绍，青鸟三周免费体验卡的介绍，青鸟三周免费体验卡的介绍。', NULL, 1, '青鸟三周免费体验卡', '2012-03-07 00:00:00', 0, 1, 1 );

INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (1, 1, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (2, 2, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (3, 3, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (4, 4, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (5, 5, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (6, 6, true, 0);
INSERT INTO PRODUCT_COMMIT (ID, COMMITS, IS_ACTIVE, VERSION) VALUES (7, 7, true, 0);

INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (1, true, 5, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (2, true, 10, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (3, true, 15, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (4, true, 20, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (5, true, 25, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (6, true, 30, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (7, true, 40, 0);
INSERT INTO PRODUCT_STAKE (ID, IS_ACTIVE, STAKES, VERSION) VALUES (8, true, 50, 0);

INSERT INTO CUSTOMER ( ID, ADDRESS, BIO, BIRTHDAY, CITY, CUSTOMER_ROLE, DISABLE_END_DATE, DISABLE_REASON, DISABLE_START_DATE, EMAIL, NAME, PASSWORD, PHONE, REGISTRATION_DATE, STATUS, USERNAME, VERSION, SEX ) VALUES ( 1, '张三地址', '', NULL, '北京', 0, NULL, '', NULL, 'zq72@yahoo.com', '张三', '0000', '010100000', '2012-03-07 00:00:00', 0, '张阿三', 0, 2 );
INSERT INTO CUSTOMER ( ID, ADDRESS, BIO, BIRTHDAY, CITY, CUSTOMER_ROLE, DISABLE_END_DATE, DISABLE_REASON, DISABLE_START_DATE, EMAIL, NAME, PASSWORD, PHONE, REGISTRATION_DATE, STATUS, USERNAME, VERSION, SEX ) VALUES ( 2, 'abc', '', NULL, '北京', 0, NULL, '', NULL, 'a@a.com', '张三', '0000', '010100000', '2012-03-07 00:00:00', 0, 'abc', 0, 2 );

INSERT INTO CUSTOMER_PRODUCT ( ID , END_DATE , START_DATE , VERSION , CUSTOMER , PRODUCT_COMMIT , PRODUCT_STAKE ) VALUES ( 1 , NULL , '2012-03-07 00:00:00' , 0 , 1 , 1 , 1 );

INSERT INTO CUSTOMER_CARD (ID, USED_DATE, VERSION, CARD, CUSTOMER) VALUES (1, '2012-03-07 00:00:00', 0, 1, 1);

INSERT INTO CUSTOMER_CHECKIN ( ID, END_DATE, END_TYPE, IS_APPROVED, START_DATE, VERSION, CUSTOMER, LOCATION ) VALUES ( 1, '2012-03-07 00:00:00', 0, false, '2012-03-07 00:00:00', 0, 1, 1 );

INSERT INTO CUSTOMER_PAYMENT ( ID, ACCOUNT_ID, END_DATE, START_DATE, VERSION, CUSTOMER, PAYMENT_TYPE ) VALUES ( 1, '124564545465', '2013-03-07 00:00:00', '2012-03-07 00:00:00', 0, 1, 1 );

INSERT INTO CUSTOMER_PROFIT ( ID, AMOUNT, END_DATE, START_DATE, VERSION, CUSTOMER ) VALUES ( 1, 10, '2012-03-14 00:00:00', '2012-03-07 00:00:00', 0, 1 );

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


