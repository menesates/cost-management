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