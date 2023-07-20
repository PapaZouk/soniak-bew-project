DELIMITER $$
DROP PROCEDURE IF EXISTS insert_data $$
CREATE PROCEDURE insert_data()
BEGIN
	START TRANSACTION;

    INSERT INTO job (title) VALUES ('Salesman');
	INSERT INTO job (title) VALUES ('Deliveryman');
	INSERT INTO job (title) VALUES ('Manager');

    INSERT INTO employee (job_id, first_name, last_name, salary, bank_account_number, national_insurance_number, commission_rate) VALUES
	(1,'Andrzej', 'Duda', 3000.00, "61109010140000071219812874", "AB123456C", 15),
    (3,'Mariusz', 'Rumunowicz', 12999.99, "61109010140000071219812875", "AB123456B", 5),
    (2,'Marian', 'Pazdzioch', 10000.00, "61109010140000071219812833", "BB123456C", 0),
    (2,'Arnold', 'Boczek', 5000.00, "61109010140000071219812833", "BB123456C", 0),
    (1,'Jan', 'Kowalski', 2000.00, "55109010140000071219812833", "BB123456E", 30),
    (2,'Ania', 'Kowalczyk', 9000.00, "55109010140000071219812999", "BB123456L", 0),
    (2,'Piotr', 'Ostrowski', 8200.00, "55209010910000071219822999", "BB123452F", 15),
    (2,'Marta', 'Wysoka', 7000.00, "55109010140000071219812990", "BB123488L", 0);


    INSERT INTO client (phone_number, first_name, last_name, address)
    VALUES
        ('1234567890', 'John', 'Doe', '123 Main St'),
        ('9876543210', 'Jane', 'Smith', '456 Elm St'),
        ('5555555555', 'Michael', 'Johnson', '789 Oak Ave'),
        ('1111111111', 'Emily', 'Williams', '321 Maple Ave'),
        ('9999999999', 'David', 'Brown', '567 Pine St'),
        ('7777777777', 'Jennifer', 'Davis', '890 Cedar Ave'),
        ('2222222222', 'Daniel', 'Taylor', '432 Birch St'),
        ('8888888888', 'Sarah', 'Miller', '765 Willow Ave'),
        ('4444444444', 'Jessica', 'Anderson', '987 Walnut St'),
        ('6666666666', 'Christopher', 'Wilson', '654 Oakwood Ave'),
        ('3333333333', 'Amanda', 'Martinez', '876 Elmwood Dr'),
        ('5555555555', 'Matthew', 'Garcia', '234 Pinehurst Rd'),
        ('7777777777', 'Elizabeth', 'Robinson', '789 Rosewood Ln'),
        ('4444444444', 'Andrew', 'Clark', '432 Mulberry St'),
        ('2222222222', 'Olivia', 'Lewis', '567 Willowbrook Ave');


    INSERT INTO tech (name) VALUES
    ('InsureTech'),
    ('CloudSoftEnv'),
    ('CyberStrategy'),
    ('ERP Systems'),
    ('AutoTech'),
    ('MarTech'),
    ('SafeProdTransfer');


    INSERT INTO project (tech_lead_id, client_id, name, value, status, start_date)
    VALUES
    (2, 1,'Smart Sales', 54500.00, "IN PROGRESS", "2022-05-13"),
    (3, 2,'All Tech Delivery', 120000.00, 'ACCEPTED', "2023-02-13"),
    (4, 5,'Global Connectivity', 88690.00, 'IN PROGRESS', "2021-05-20"),
    (6, 5,'EcoTech Mobile', 74700.00, 'ACCEPTED', "2021-06-30"),
    (6, 6,'Innovation Sollutions', 230500.00, 'IN PROGRESS', "2022-11-11");

    INSERT INTO project (tech_lead_id, client_id, name, value, status, start_date, complete_date)
    VALUES
	(2, 7,'GoTech Apparature', 65650.00, 'COMPLETED', "2022-03-10", "2022-09-20"),
	(3, 7,'AI Driver Sales', 11600.00, 'COMPLETED', "2020-02-10", "2023-04-01"),
	(1, 3,'Best Solutions 23', 29000.00, 'COMPLETED', "2022-10-11", "2022-12-22");

    INSERT INTO project_member (employee_id, project_id)
    VALUES  (1, 1), -- Example values, replace with actual data
            (2, 2),
            (3, 1),
            (4, 1),
            (4, 5),
            (5, 2),
            (7, 8),
            (6, 2);


    INSERT INTO project_tech (project_id, tech_id) VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (2, 4),
    (3, 1),
    (3, 5),
    (3, 7),
    (4, 6),
    (5, 2),
    (5, 3),
    (6, 7),
    (7, 5),
    (7, 7),
    (8, 1);

    GET DIAGNOSTICS @rows = ROW_COUNT;
    IF @rows = 0 THEN
        ROLLBACK;
        SELECT 'Transaction rolled back due error';
    ELSE
        COMMIT;
        SELECT 'Transaction commited succesfully';
    END IF;
END $$
DELIMITER ;
CALL insert_data();

