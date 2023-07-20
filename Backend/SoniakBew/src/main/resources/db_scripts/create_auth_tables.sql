DELIMITER $$
DROP PROCEDURE IF EXISTS create_table_project $$
CREATE PROCEDURE create_table_project()
BEGIN
    START TRANSACTION;

    CREATE TABLE IF NOT EXISTS  job (
            id INT AUTO_INCREMENT PRIMARY KEY,
            title VARCHAR(255) NOT NULL
        );

    CREATE TABLE IF NOT EXISTS  employee (
        id INT AUTO_INCREMENT PRIMARY KEY,
        job_id INT NOT NULL,
        first_name VARCHAR(255) NOT NULL,
        last_name VARCHAR(255) NOT NULL,
        salary DECIMAL(10,2) NOT NULL,
        bank_account_number varchar(255) NOT NULL,
        national_insurance_number varchar(255) NOT NULL,
        commission_rate DECIMAL(3,0),
        FOREIGN KEY (job_id) REFERENCES job(id)
    );

    CREATE TABLE IF NOT EXISTS client (
        id INT AUTO_INCREMENT PRIMARY KEY,
        phone_number VARCHAR(20) NOT NULL,
        first_name VARCHAR(20) NOT NULL,
        last_name VARCHAR(20) NOT NULL,
        address VARCHAR(30) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS tech
    (
        id                      INT             PRIMARY KEY AUTO_INCREMENT,
        name                    VARCHAR(128)    NOT NULL
    );


    CREATE TABLE IF NOT EXISTS project
    (
        id                      INT             PRIMARY KEY AUTO_INCREMENT,
        tech_lead_id            INT,
        client_id               INT,
        name                    VARCHAR(50)     NOT NULL,
        value                   DECIMAL(12,2)   NOT NULL,
        status                  ENUM            ("COMPLETED", "IN PROGRESS", "ACCEPTED"),
        start_date              DATE            NOT NULL,
        complete_date           DATE,
        CONSTRAINT fk_project_member_employee2
            FOREIGN KEY (tech_lead_id)
                REFERENCES employee (id)
                ON DELETE SET null,
        CONSTRAINT fk_project_project_client
            FOREIGN KEY (client_id)
                REFERENCES client (id)
                ON DELETE SET NULL
    );


    CREATE TABLE IF NOT EXISTS project_member (
        id INT AUTO_INCREMENT PRIMARY KEY,
        employee_id INT,
        project_id INT NOT NULL,
        CONSTRAINT fk_project_member_employee1
            FOREIGN KEY (employee_id)
                REFERENCES employee (id)
                ON DELETE SET NULL,
        CONSTRAINT fk_project_member_project
            FOREIGN KEY (project_id)
                REFERENCES project (id)
    );


    CREATE TABLE IF NOT EXISTS project_tech
    (
        id                      INT             PRIMARY KEY AUTO_INCREMENT,
        project_id              INT             NOT NULL,
        tech_id                 INT             NOT NULL,
        CONSTRAINT fk_project_tech_project
            FOREIGN KEY (project_id)
                REFERENCES project (id),
        CONSTRAINT fk_project_tech_tech
            FOREIGN KEY (tech_id)
                REFERENCES tech (id)
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