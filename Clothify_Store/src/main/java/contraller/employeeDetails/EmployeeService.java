package contraller.employeeDetails;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import model.Employee;

public interface EmployeeService {
    boolean addNewEmployee(Employee employee);
    ObservableList<Employee> getAllEmployee();
    boolean delete(String id);
    boolean update(Employee employee);
}
