CREATE TABLE ACCOUNTS (
  ACCOUNT_NUMBER VARCHAR(10) PRIMARY KEY,
  BALANCE NUMERIC NOT NULL
);

INSERT INTO ACCOUNTS (ACCOUNT_NUMBER, BALANCE) VALUES ('SG00000001', 5000);
INSERT INTO ACCOUNTS (ACCOUNT_NUMBER, BALANCE) VALUES ('SG00000002', 0);
INSERT INTO ACCOUNTS (ACCOUNT_NUMBER, BALANCE) VALUES ('SG00000003', -400);
INSERT INTO ACCOUNTS (ACCOUNT_NUMBER, BALANCE) VALUES ('SG00000004', 100);

CREATE TABLE EVENTS (
  ID INT AUTO_INCREMENT PRIMARY KEY,
  ACCOUNT_NUMBER VARCHAR(10) NOT NULL,
  OPERATION VARCHAR(20) NOT NULL,
  DESCRIPTION VARCHAR(255) NOT NULL,
  CREATE_DATE TIMESTAMP NOT NULL,
  FOREIGN KEY (ACCOUNT_NUMBER) REFERENCES ACCOUNTS (ACCOUNT_NUMBER)
);
