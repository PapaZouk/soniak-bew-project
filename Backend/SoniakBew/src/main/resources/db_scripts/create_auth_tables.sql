DELIMITER $$
DROP PROCEDURE IF EXISTS create_table_project $$
CREATE PROCEDURE create_table_project()
BEGIN
    START TRANSACTION;

     CREATE TABLE role
     (
         roleId				TINYINT				NOT NULL,
         name				VARCHAR(64)			NOT NULL,
         PRIMARY KEY (roleId)
     );

     INSERT INTO role (roleId, Name) VALUES (1, 'Admin');
     INSERT INTO role (roleId, Name) VALUES (2, 'User');

     CREATE TABLE user
     (
         username			VARCHAR(64)			NOT NULL,
         password			VARCHAR(64)			NOT NULL,
         roleId				TINYINT				NOT NULL,
         PRIMARY KEY (username),
         FOREIGN KEY (roleId) REFERENCES role (roleId)
     );

     INSERT INTO user (username, password, roleID) VALUES ('admin', 'admin', 1);
     INSERT INTO user (username, password, roleID) VALUES ('user', 'user', 2);

     CREATE TABLE token
     (
        username			VARCHAR(64)			NOT NULL,
         token				VARCHAR(64)			NOT NULL,
         expiry				DATETIME			NOT NULL,
         FOREIGN KEY (username) REFERENCES user (username)
     );


    GET DIAGNOSTICS @rows = ROW_COUNT;
    IF @row = 0 THEN
        ROLLBACK;
        SELECT 'Transaction rolled back due to error';
    ELSE
        COMMIT;
        SELECT 'Transaction commited succesffully';
    END IF;

END $$
DELIMITER ;
CALL create_table_project();