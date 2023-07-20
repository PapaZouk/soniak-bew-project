DELIMITER $$
DROP PROCEDURE IF EXISTS create_auth_tables $$
CREATE PROCEDURE create_auth_tables()
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
CALL create_auth_tables();