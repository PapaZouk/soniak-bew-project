package org.kainos.ea.db;

import org.kainos.ea.cli.DeliveryEmployee;
import org.kainos.ea.cli.SalesEmployee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao extends DatabaseConnector {

    public List<DeliveryEmployee> getAllDeliveryEmployees() throws SQLException {
        Connection c = getConnection();
        Statement st = c.createStatement();

        String query = "SELECT em.first_name, em.last_name, em.salary, em.bank_account_number, em.national_insurance_number " +
                "FROM employee AS em " +
                "LEFT JOIN job AS j ON j.id = em.job_id " +
                "WHERE j.title = 'Deliveryman'";

        ResultSet rs = st.executeQuery(query);

        List<DeliveryEmployee> employeeList = new ArrayList<>();

        while (rs.next()) {
            DeliveryEmployee employee = DeliveryEmployee.builder()
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .salary(rs.getBigDecimal("salary"))
                    .bankAccount(rs.getString("bank_account_number"))
                    .nationalInsuranceNumber(rs.getString("national_insurance_number"))
                    .build();

            employeeList.add(employee);
        }

        return employeeList;
    }

    public DeliveryEmployee getDeliveryById(int id) throws SQLException {
        Connection c = getConnection();

        String query = "SELECT em.id, em.job_id, em.first_name, em.last_name, em.salary, em.bank_account_number, " +
                "em.national_insurance_number " +
                "    FROM employee AS em " +
                "    LEFT JOIN job AS j ON j.id = em.job_id " +
                "    WHERE j.title = 'Deliveryman' " +
                "    AND em.id = ?";

        PreparedStatement st = c.prepareStatement(query);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return DeliveryEmployee.builder()
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .salary(rs.getBigDecimal("salary"))
                    .bankAccount(rs.getString("bank_account_number"))
                    .nationalInsuranceNumber(rs.getString("national_insurance_number"))
                    .build();
        }

        return null;
    }

    public int createNewDeliveryman(DeliveryEmployee deliveryman) throws SQLException {
        Connection conn = getConnection();

        String query = "INSERT INTO employee " +
                "(job_id, first_name, last_name, salary, bank_account_number, national_insurance_number, commission_rate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, 2);
        statement.setString(2, deliveryman.getFirstName());
        statement.setString(3, deliveryman.getLastName());
        statement.setBigDecimal(4, deliveryman.getSalary());
        statement.setString(5, deliveryman.getBankAccount());
        statement.setString(6, deliveryman.getNationalInsuranceNumber());
        statement.setInt(7, 0);

        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public List<SalesEmployee> getAllSalesEmployees() throws SQLException {
        Connection c = getConnection();
        Statement st = c.createStatement();

        String query = "SELECT em.first_name, em.last_name, em.salary, em.bank_account_number, " +
                "em.national_insurance_number, em.commission_rate " +
                "FROM employee AS em " +
                "LEFT JOIN job AS j ON j.id = em.job_id " +
                "WHERE j.title = 'Salesman'";

        ResultSet rs = st.executeQuery(query);

        List<SalesEmployee> employeeList = new ArrayList<>();

        while (rs.next()) {
            SalesEmployee employee = SalesEmployee.builder()
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .salary(rs.getBigDecimal("salary"))
                    .bankAccount(rs.getString("bank_account_number"))
                    .nationalInsuranceNumber(rs.getString("national_insurance_number"))
                    .commissionRate(rs.getInt("commission_rate"))
                    .build();

            employeeList.add(employee);
        }

        return employeeList;
    }

    public SalesEmployee getSalesmanById(int id) throws SQLException {
        Connection c = getConnection();

        String query = "SELECT em.id, em.job_id, em.first_name, em.last_name, em.salary, " +
                "em.bank_account_number, em.national_insurance_number, em.commission_rate " +
                "FROM employee AS em " +
                "LEFT JOIN job AS j ON j.id = em.job_id " +
                "WHERE j.title = 'Salesman' AND em.id = ?;";

        PreparedStatement st = c.prepareStatement(query);
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            return SalesEmployee.builder()
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .salary(rs.getBigDecimal("salary"))
                    .bankAccount(rs.getString("bank_account_number"))
                    .nationalInsuranceNumber(rs.getString("national_insurance_number"))
                    .commissionRate(rs.getInt("commission_rate"))
                    .build();
        }

        return null;
    }

    public int createNewSalesman(SalesEmployee salesman) throws SQLException {
        Connection conn = getConnection();

        String query = "INSERT INTO employee " +
                "(job_id, first_name, last_name, salary, bank_account_number, national_insurance_number, commission_rate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, 1);
        statement.setString(2, salesman.getFirstName());
        statement.setString(3, salesman.getLastName());
        statement.setBigDecimal(4, salesman.getSalary());
        statement.setString(5, salesman.getBankAccount());
        statement.setString(6, salesman.getNationalInsuranceNumber());
        statement.setInt(7, 15);

        statement.executeUpdate();
        ResultSet result = statement.getGeneratedKeys();

        if (result.next()) {
            return result.getInt(1);
        }
        return -1;
    }

    public void deleteDeliverymanById(int id) throws SQLException {
        Connection conn = getConnection();

        String query = "DELETE FROM employee AS em " +
                "WHERE em.id = ? AND em.job_id = 2;";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    public void deleteSalesmanById(int id) throws SQLException {
        Connection conn = getConnection();

        String query = "DELETE FROM employee AS em " +
                "WHERE em.id = ? AND em.job_id = 1;";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    public void updateSalesmanById(int id, SalesEmployee salesEmployee) throws SQLException {
        Connection conn = getConnection();

        String query = "UPDATE employee AS em " +
                "SET em.first_name = ?, em.last_name = ?, em.salary = ?, " +
                "em.bank_account_number = ?, em.national_insurance_number = ?, em.commission_rate = ? " +
                "WHERE em.id = ? AND job_id = 1";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, salesEmployee.getFirstName());
        statement.setString(2, salesEmployee.getLastName());
        statement.setBigDecimal(3, salesEmployee.getSalary());
        statement.setString(4, salesEmployee.getBankAccount());
        statement.setString(5, salesEmployee.getNationalInsuranceNumber());
        statement.setInt(6, 15);
        statement.setInt(7, id);

        statement.execute();
    }

    public void updateDeliverymanById(int id, DeliveryEmployee deliveryEmployee) throws SQLException {
        Connection conn = getConnection();

        String query = "UPDATE employee AS em " +
                "SET em.first_name = ?, em.last_name = ?, em.salary = ?, " +
                "em.bank_account_number = ?, em.national_insurance_number = ? " +
                "WHERE em.id = ? AND job_id = 2;";
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, deliveryEmployee.getFirstName());
        statement.setString(2, deliveryEmployee.getLastName());
        statement.setBigDecimal(3, deliveryEmployee.getSalary());
        statement.setString(4, deliveryEmployee.getBankAccount());
        statement.setString(5, deliveryEmployee.getNationalInsuranceNumber());
        statement.setInt(6, id);

        statement.execute();
    }
}