SELECT em.first_name, em.last_name, em.salary, em.bank_account_number, em.national_insurance_number
FROM employee AS em
	LEFT JOIN job AS j ON j.id = em.job_id
    WHERE j.title = 'Deliveryman';
    
SELECT em.id, em.job_id, em.first_name, em.last_name, j.title
FROM employee AS em
	LEFT JOIN job AS j ON j.id = em.job_id
    WHERE j.title = 'Salesman';

SELECT em.id, em.job_id, em.first_name, em.last_name, em.salary, em.bank_account_number, 
                em.national_insurance_number, em.commission_rate, j.title 
                FROM employee AS em 
                LEFT JOIN job AS j ON j.id = em.id
                WHERE j.title = 'Deliveryman'
                AND em.id = 2;

SELECT em.id, em.job_id, em.first_name, em.last_name, em.salary,
	em.bank_account_number, em.national_insurance_number
FROM employee AS em
	LEFT JOIN job AS j ON j.id = em.job_id
    WHERE j.title = 'Salesman' AND em.id = 5;
                
SELECT em.id, em.job_id, em.first_name, em.last_name, em.salary, em.bank_account_number,
	em.national_insurance_number
    FROM employee AS em
    LEFT JOIN job AS j ON j.id = em.job_id
    WHERE j.title = 'Deliveryman'
    AND em.id = 3;
    
UPDATE employee AS em
	SET em.first_name = 'Kazimierz', em.last_name = 'Bomba', em.salary = 52222, 
	em.bank_account_number = '999212312312', em.national_insurance_number = '91293213123'
	WHERE em.id = 5 AND job_id = 1;
    
SELECT * FROM employee;
DELETE FROM employee AS em
	WHERE em.id = 9 AND em.job_id = 2;