INSERT INTO USERS(username,password,enabled,email) VALUES('user1','{noop}secret',TRUE ,'a@a.com');
INSERT INTO USERS(username,password,enabled,email) VALUES('user2','{noop}secret',TRUE ,'b@a.com');
INSERT INTO USERS(username,password,enabled,email) VALUES('user3','{noop}secret',TRUE ,'c@a.com');
INSERT INTO USERS(username,password,enabled,email) VALUES('user4','{noop}secret',FALSE ,'d@a.com');
INSERT INTO USERS(username,password,enabled,email) VALUES('user5','{noop}secret',FALSE ,'e@a.com');

INSERT INTO AUTHORITIES(id,username,authority) VALUES(1,'user1','ROLE_USER');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(2,'user2','ROLE_USER');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(3,'user3','ROLE_USER');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(4,'user4','ROLE_USER');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(5,'user5','ROLE_USER');

INSERT INTO AUTHORITIES(id,username,authority) VALUES(6,'user3','ROLE_EDITOR');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(7,'user4','ROLE_EDITOR');
INSERT INTO AUTHORITIES(id,username,authority) VALUES(8,'user5','ROLE_EDITOR');

INSERT INTO AUTHORITIES(id,username,authority) VALUES(9,'user5','ROLE_ADMIN');

INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(1,20.50,'Eat',949,'2018-10-20','','user3');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(2,0.50,'Eat',949,'2018-10-20','','user3');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(3,50,'Eat',949,'2018-10-20','','user3');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(4,-20,'Cigaret',949,'2018-10-20','','user3');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(5,-20.5,'Cigaret',949,'2018-10-20','','user3');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(6,2000,'Other',949,'2018-10-20','','user3');

INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(7,-2000,'Car',949,'2018-10-20','','user4');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(8,-10000.5,'Car',949,'2018-10-20','','user4');
INSERT INTO INCOME_EXPENSE(id,amount,cost_type,currency_code,date,explanation,username) VALUES(9,20000,'Other',949,'2018-10-20','','user4');

