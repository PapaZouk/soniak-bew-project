package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class SalesEmployee {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String bankAccount;
    private String nationalInsuranceNumber;
    private Integer commissionRate;

    @JsonCreator
    public SalesEmployee(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("salary") BigDecimal salary,
            @JsonProperty("bank_account_number") String bankAccount,
            @JsonProperty("national_insurance_number") String nationalInsuranceNumber,
            @JsonProperty("commissionRate") Integer commissionRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.bankAccount = bankAccount;
        this.nationalInsuranceNumber = nationalInsuranceNumber;
        this.commissionRate = commissionRate;
    }

    public static SalesEmployeeBuilder builder() {
        return new SalesEmployeeBuilder();
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
    }

    public static class SalesEmployeeBuilder {
        private String firstName;
        private String lastName;
        private BigDecimal salary;
        private String bankAccount;
        private String nationalInsuranceNumber;
        private Integer commissionRate;

        SalesEmployeeBuilder() {}

        public SalesEmployeeBuilder firstName(String firstName) {
            this.firstName= firstName;
            return this;
        }
        public SalesEmployeeBuilder lastName(String lastName) {
            this.lastName= lastName;
            return this;
        }
        public SalesEmployeeBuilder salary(BigDecimal salary) {
            this.salary= salary;
            return this;
        }
        public SalesEmployeeBuilder bankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }
        public SalesEmployeeBuilder nationalInsuranceNumber(String nationalInsuranceNumber) {
            this.nationalInsuranceNumber= nationalInsuranceNumber;
            return this;
        }

        public SalesEmployeeBuilder commissionRate(Integer commissionRate) {
            this.commissionRate = commissionRate;
            return this;
        }

        public SalesEmployee build() {
            return new SalesEmployee(
                    this.firstName,
                    this.lastName,
                    this.salary,
                    this.bankAccount,
                    this.nationalInsuranceNumber,
                    this.commissionRate
            );
        }

    }

}
