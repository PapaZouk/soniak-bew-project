package org.kainos.ea.api;

import org.kainos.ea.cli.DeliveryEmployee;
import org.kainos.ea.cli.SalesEmployee;
import org.kainos.ea.client.*;
import org.kainos.ea.db.EmployeeDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class EmployeeService {

    private final EmployeeDao employeeDao = new EmployeeDao();

    public List<SalesEmployee> getAllSalesEmployees()
            throws FailedToGetAllSalesmanEmployeesException {
        try {
            return employeeDao.getAllSalesEmployees();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetAllSalesmanEmployeesException();
        }
    }

    public List<DeliveryEmployee> getAllDeliveryEmployees()
            throws FailedToGetAllDeliverymanEmployeesException {
        try {
            return employeeDao.getAllDeliveryEmployees();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetAllDeliverymanEmployeesException();
        }
    }

    public DeliveryEmployee getDeliverymanById(int id)
            throws SQLException, EmployeeDoesNotExistException {
        try {
            DeliveryEmployee deliveryById = employeeDao.getDeliveryById(id);
            if (deliveryById == null) {
                throw new EmployeeDoesNotExistException();
            }
            return deliveryById;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new EmployeeDoesNotExistException();
        }
    }

    public SalesEmployee getSalesmanById(int id) throws EmployeeDoesNotExistException {
        try {
            SalesEmployee salesById = employeeDao.getSalesmanById(id);
            if (salesById == null) {
                throw new EmployeeDoesNotExistException();
            }
            return salesById;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new EmployeeDoesNotExistException();
        }
    }

    public int createNewSalesEmployee(SalesEmployee salesEmployee)
            throws FailedToCreateNewSalesEmployeeException {
        try {
            return employeeDao.createNewSalesman(salesEmployee);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateNewSalesEmployeeException();
        }
    }

    public int createNewDeliverymanEmployee(DeliveryEmployee deliveryEmployee)
            throws FailedToCreateNewDeliverymanEmployeeException {
        try {
            return employeeDao.createNewDeliveryman(deliveryEmployee);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateNewDeliverymanEmployeeException();
        }
    }

    public void updateSalesmanById(int id, SalesEmployee salesEmployee)
            throws FailedToUpdateSalesmanException, SalesmanDoesNotExistException {
        try {
            SalesEmployee salesmanToUpdate = employeeDao.getSalesmanById(id);

            if (Objects.isNull(salesEmployee)) {
                throw new SalesmanDoesNotExistException();
            }

            employeeDao.updateSalesmanById(id, salesEmployee);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateSalesmanException();
        }
    }

    public void updateDeliverymanById(int id, DeliveryEmployee deliveryEmployee)
            throws FailedToUpdateDeliverymanException, DeliverymanDoesNotExistException {
        try {
            DeliveryEmployee deliverymanToUpdate = employeeDao.getDeliveryById(id);

            if (Objects.isNull(deliveryEmployee)) {
                throw new DeliverymanDoesNotExistException();
            }
            employeeDao.updateDeliverymanById(id, deliveryEmployee);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateDeliverymanException();
        }
    }

    public void deleteDeliverymanById(int id)
            throws FailedToDeleteDeliverymanException, DeliverymanDoesNotExistException {
        try {
            DeliveryEmployee deliverymanToDelete = employeeDao.getDeliveryById(id);

            if (Objects.isNull(deliverymanToDelete)) {
                throw new DeliverymanDoesNotExistException();
            }

            employeeDao.deleteDeliverymanById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteDeliverymanException();
        }
    }

    public void deleteSalesmanById(int id)
            throws SalesmanDoesNotExistException, FailedToDeleteSalesmanException {
        try {
            SalesEmployee salesmanToDelete = employeeDao.getSalesmanById(id);

            if (Objects.isNull(salesmanToDelete)) {
                throw new SalesmanDoesNotExistException();
            }

            employeeDao.deleteSalesmanById(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToDeleteSalesmanException();
        }
    }

//    public SalesEmployee getSalesById (int id) throws SQLException, EmployeeDoesNotExistException {
//        try {
//            SalesEmployee salesById = employeeDao.getSalesById(id);
//            if (salesById == null) {
//                throw new EmployeeDoesNotExistException();
//            }
//            return salesById;
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            throw new EmployeeDoesNotExistException();
//        }
//    }
}
