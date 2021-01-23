CREATE TABLE account (
  account_number VARCHAR(10) NOT NULL PRIMARY KEY,
  balance numeric NOT NULL
);

INSERT INTO account (account_number, balance) VALUES ('SG00000001', 5000);
INSERT INTO account (account_number, balance) VALUES ('SG00000004', 0);
INSERT INTO account (account_number, balance) VALUES ('SG00000003', -400);
