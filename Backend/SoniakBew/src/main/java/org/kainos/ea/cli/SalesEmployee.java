package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SalesEmployee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String bankAccount;
    private String nationalInsuranceNumber;
    private Integer commissionRate;

    @JsonCreator
    public SalesEmployee(
            @JsonProperty("employeeId") int employeeId,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("salary") BigDecimal salary,
            @JsonProperty("bank_account_number") String bankAccount,
            @JsonProperty("national_insurance_number") String nationalInsuranceNumber,
            @JsonProperty("commissionRate") Integer commissionRate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.bankAccount = bankAccount;
        this.nationalInsuranceNumber = nationalInsuranceNumber;
        this.commissionRate = commissionRate;
    }

}
