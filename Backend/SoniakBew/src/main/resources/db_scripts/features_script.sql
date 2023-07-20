
-- 1. HR TEAM - Create new sales employee
INSERT INTO employee (job_id, first_name, last_name, salary, bank_account_number, national_insurance_number, commission_rate)
	VALUES (1,'Andrzej', 'Duda', 3000.00, "61109010140000071219812874", "AB123456C", 15);

-- 2. HR TEAM - Create new delivery employee
INSERT INTO employee (job_id, first_name, last_name, salary, bank_account_number, national_insurance_number, commission_rate)
    VALUES (2,'Marian', 'Pazdzioch', 10000.00, "61109010140000071219812833", "BB123456C", 0);

-- 3. SALES TEAM - Create new client
INSERT INTO client (phone_number, first_name, last_name, address) VALUES
('9912345667', 'Zenon', 'Zonda', 'Korkowa 9, Kozlice');

-- 4. SALES TEAM - Create new project
INSERT INTO project (tech_lead_id, client_id, name, value, status, start_date) VALUES
(5, 3, 'Another Great Project', 13000.99, "ACCEPTED", "2023-07-05");

-- 5. MANAGEMENT TEAM - Assign delivery employee to a project
INSERT INTO project_member (employee_id, project_id)
    VALUES  (8, 3); 

-- 6. MANAGEMENT TEAM - Assign delivery employee as a Tech Lead to a project
UPDATE project
	SET tech_lead_id = 2
    WHERE id = 3;

-- 7. MANAGEMENT TEAM - Remove delivery employee to a project - Record it
-- TODO : RECORD IT!
DELETE FROM project_member
    WHERE employee_id = 2 AND project_id = 2;

-- 8. SALES TEAM - Assign Client to a project
UPDATE project 
    SET client_id = 4 
    WHERE id = 1;

-- 9. QUERRY - SALES TEAM - List of clients with sales employees and projects that client has
-- TO DO : FIX IT
SELECT CONCAT(`client`.first_name, ' ', `client`.last_name) as 'Client Name', 
    CONCAT(employee.first_name, ' ', employee.last_name) as 'Employee Name',
    GROUP_CONCAT(project.name separator ', ') as 'Project Names'
    FROM `client` 
    LEFT JOIN project ON (`client`.id = project.client_id)
    RIGHT JOIN project_member ON (project.id = project_member.project_id)
    RIGHT JOIN employee ON (project_member.employee_id = employee.id)
    RIGHT JOIN job ON (employee.job_id = job.id)
    WHERE job.title = "Salesman"
    GROUP BY project.name;

-- 10. QUERRY - SALES TEAM - Get client with highest value of projects
SELECT CONCAT(c.first_name, ' ', c.last_name) AS `Client`, MAX(p.value) AS `Max project value`
FROM project AS p
    LEFT JOIN client AS c ON p.client_id = c.id
    GROUP BY `Client`
    ORDER BY `Max project value` DESC
    LIMIT 1;

-- 11. QUERRY - SALES TEAM - Get client with lowest value of projects
SELECT CONCAT(c.first_name, ' ', c.last_name) AS `Client`, MIN(p.value) AS `Max project value`
FROM project AS p
    LEFT JOIN client AS c ON p.client_id = c.id
    GROUP BY `Client`
    ORDER BY `Max project value` ASC
    LIMIT 1;

-- 12. QUERRY - SALES TEAM - List of clients with average value of projects
SELECT CONCAT(`client`.first_name, ' ', `client`.last_name) as 'Client Name', 
FORMAT(AVG(project.value), 2) as 'Average Value'
    FROM `client`
    JOIN project ON (`client`.id = project.client_id)
    GROUP BY `client`.id;


-- 13. QUERRY - HR TEAM - List of sales employees with commision they made this year
-- FIX NEEDED
SELECT CONCAT(e.first_name, ' ', e.last_name) AS `Employee`, p.complete_date
FROM project_member AS pm
	LEFT JOIN project AS p ON p.id = pm.project_id
    LEFT JOIN employee AS e ON pm.employee_id = e.id
WHERE p.complete_date > "2023-01-01";

-- 14. QUERRY - HR TEAM - List of sales employees with zero clients this year
SELECT e.id, CONCAT(e.first_name, ' ', e.last_name) AS 'Employees'
FROM employee AS e
	LEFT JOIN project_member AS pm ON e.id = pm.employee_id
  LEFT JOIN project AS p ON p.id = pm.project_id
	WHERE pm.employee_id IS NULL
  OR YEAR(p.start_date) < 2023;
    
-- 15. QUERRY - MANAGEMENT TEAM - List of delivery employees with no projects assigned who knows technology realted to current projects and that project name
-- FIX NEEDED
SELECT
	CONCAT(em.first_name, ' ', em.last_name) AS `Employee`,
    GROUP_CONCAT(DISTINCT t.name SEPARATOR ', ') AS `Tech that employee worked with`,
    GROUP_CONCAT(DISTINCT pr.name SEPARATOR ', ') AS `Projects worked on`,
    COUNT(CASE WHEN em.id NOT IN (SELECT employee_id FROM project_member) THEN 1 END) AS `Working on projects`
FROM project_member AS pm
	LEFT JOIN employee AS em ON em.id = pm.employee_id
    LEFT JOIN job AS j ON j.id = em.job_id
    LEFT JOIN project AS pr ON pr.id = pm.project_id
    LEFT JOIN project_tech AS pt ON pt.project_id = pr.id
    LEFT JOIN tech AS t ON t.id = pt.tech_id
WHERE j.title = 'Deliveryman'
	AND em.id NOT IN (
			SELECT pm.employee_id FROM project_member AS pm
				LEFT JOIN project AS pr ON pr.id = pm.project_id
                WHERE pr.status = "IN PROGRESS" OR pr.status = "ACCEPTED"
        )
	AND t.name IN (SELECT t.name FROM project_tech AS pt)
GROUP BY `Employee`;

-- 16. MANAGEMENT TEAM - Set a project as completed. Dont list completed projects
UPDATE project
	SET status = "COMPLETED" AND complete_date = CURDATE()
    WHERE id = 5;

-- 17. QUERRY - MANAGEMENT TEAM - List of projects, name of tech lead and all of delivery emplyees for that project
-- TO DO : FIX IT
SELECT project.name as 'Project Name',
(SELECT CONCAT(employee.first_name, ' ', employee.last_name) FROM employee WHERE project.tech_lead_id = employee.id) as 'Team Lead Name',
GROUP_CONCAT(employee.first_name, ' ', employee.last_name separator ', ') as 'Employees Names'
FROM project
RIGHT JOIN project_member ON (project.id = project_member.project_id)
RIGHT JOIN employee ON (project_member.employee_id = employee.id)
GROUP BY employee.id; 

-- 18. QUERRY - MANAGEMENT TEAM - Get a project with most delivery employees working on it
SELECT COUNT(pm.project_id) AS 'Project with most amount of delivery employees'
FROM project_member AS pm
	JOIN employee AS e ON e.id = pm.employee_id
	JOIN job AS j ON j.id = e.job_id
	WHERE j.title = 'Deliveryman'
	GROUP BY project_id
  ORDER BY COUNT(pm.project_id) DESC
  LIMIT 1;




