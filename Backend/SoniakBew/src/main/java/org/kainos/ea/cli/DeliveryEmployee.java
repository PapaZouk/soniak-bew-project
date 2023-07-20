package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class DeliveryEmployee {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String bankAccount;
    private String nationalInsuranceNumber;

    @JsonCreator
    public DeliveryEmployee(
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("salary") BigDecimal salary,
            @JsonProperty("bank_account") String backAccount,
            @JsonProperty("national_insurance_number") String nationalNationalInsurance
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.bankAccount = backAccount;
        this.nationalInsuranceNumber = nationalNationalInsurance;
    }

    public static DeliveryEmployeeBuilder builder() {
        return new DeliveryEmployeeBuilder();
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getNationalInsuranceNumber() {
        return nationalInsuranceNumber;
    }

    public void setNationalInsuranceNumber(String nationalInsuranceNumber) {
        this.nationalInsuranceNumber = nationalInsuranceNumber;
    }

    public static class DeliveryEmployeeBuilder {
        private String firstName;
        private String lastName;
        private BigDecimal salary;
        private String bankAccount;
        private String nationalInsuranceNumber;

        DeliveryEmployeeBuilder() {}

        public DeliveryEmployeeBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public DeliveryEmployeeBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public DeliveryEmployeeBuilder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }
        public DeliveryEmployeeBuilder bankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
            return this;
        }
        public DeliveryEmployeeBuilder nationalInsuranceNumber(String nationalInsuranceNumber) {
            this.nationalInsuranceNumber = nationalInsuranceNumber;
            return this;
        }

        public DeliveryEmployee build() {
            return new DeliveryEmployee(
                    this.firstName,
                    this.lastName,
                    this.salary,
                    this.bankAccount,
                    this.nationalInsuranceNumber
            );
        }
    }
}
