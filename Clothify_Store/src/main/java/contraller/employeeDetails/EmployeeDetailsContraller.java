package contraller.employeeDetails;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDetailsContraller implements EmployeeService{
    @Override
    public boolean addNewEmployee(Employee employee){

        try {
            String sql = "insert into Employee values (?,?,?,?)";
            return CrudUtil.execute(sql,
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getCompany()
                );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Employee> getAllEmployee() {
        ObservableList<Employee> employeeObservableList = FXCollections.observableArrayList();

        try {
            String sql = "select * from Employee";
            ResultSet rslt = CrudUtil.execute(sql);

           while(rslt.next()) {
               employeeObservableList.add(
                       new Employee(
                               rslt.getString(1),
                               rslt.getString(2),
                               rslt.getString(3),
                               rslt.getString(4)
                       )
               );

           }
           return employeeObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(String id) {
        String sql = "delete from Employee where  Emp_ID ='"+id+"'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Employee employee) {

        try {
            String sql = "update Employee set Name=? ,Email=? ,Company=? where Emp_ID = ? ";
            return CrudUtil.execute(sql,
                    employee.getName(),
                    employee.getEmail(),
                    employee.getCompany(),
                    employee.getId()
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
