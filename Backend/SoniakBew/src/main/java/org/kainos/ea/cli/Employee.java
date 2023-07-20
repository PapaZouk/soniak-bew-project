package org.kainos.ea.cli;

import java.math.BigDecimal;

public class Employee {
    private int id;
    private int jobId;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String backAccount;
    private String nationalNationalInsurance;

    public Employee(
            int id,
            int jobId,
            String firstName,
            String lastName,
            BigDecimal salary,
            String backAccount,
            String nationalNationalInsurance
    ) {
        this.id = id;
        this.jobId = jobId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.backAccount = backAccount;
        this.nationalNationalInsurance = nationalNationalInsurance;
    }

    public static EmployeeBuilder builder() {
        return new EmployeeBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getBackAccount() {
        return backAccount;
    }

    public void setBackAccount(String backAccount) {
        this.backAccount = backAccount;
    }

    public String getNationalNationalInsurance() {
        return nationalNationalInsurance;
    }

    public void setNationalNationalInsurance(String nationalNationalInsurance) {
        this.nationalNationalInsurance = nationalNationalInsurance;
    }

    public static class EmployeeBuilder {
        private int id;
        private int jobId;
        private String firstName;
        private String lastName;
        private BigDecimal salary;
        private String backAccount;
        private String nationalNationalInsurance;

        EmployeeBuilder() {}

        public EmployeeBuilder id(int id) {
            this.id = id;
            return this;
        }

        public EmployeeBuilder jobId(int jobId) {
            this.jobId = jobId;
            return this;
        }

        public EmployeeBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public EmployeeBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public EmployeeBuilder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public EmployeeBuilder bankAccount(String backAccount) {
            this.backAccount = backAccount;
            return this;
        }

        public EmployeeBuilder nationalNationalInsurance(String nationalNationalInsurance) {
            this.nationalNationalInsurance = nationalNationalInsurance;
            return this;
        }

        public Employee build() {
            return new Employee(
                    this.id,
                    this.jobId,
                    this.firstName,
                    this.lastName,
                    this.salary,
                    this.backAccount,
                    this.nationalNationalInsurance
            );
        }
    }
}
