
DROP TABLE IF EXISTS account_money;
CREATE TABLE account_money (
  account_id varchar(64) NOT NULL,
  account_money int(32) NOT NULL,
  PRIMARY KEY (account_id)
) 
INSERT INTO account_money VALUES ('001', 0.00);


