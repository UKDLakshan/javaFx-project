package contraller.employeeDetails;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import model.Employee;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeDetailsFormContraller implements Initializable {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<Employee> tblEmployeeDetails;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtCompanyAdd;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtEmailAdd;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNameAdd;

    EmployeeService service = new EmployeeDetailsContraller();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        loadTable();

        tblEmployeeDetails.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, t1) -> {
            if(t1!= null){
                setValueToText(t1);
            }
        });
    }

    private void setValueToText(Employee t1) {
        txtID.setText(t1.getId());
        txtName.setText(t1.getName());
        txtEmail.setText(t1.getEmail());
        txtCompany.setText(t1.getCompany());
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Employee emp = new Employee(
                "E002",
                txtNameAdd.getText(),
                txtEmailAdd.getText(),
                txtCompanyAdd.getText()
        );
        if(service.addNewEmployee(emp)){
            new Alert(Alert.AlertType.INFORMATION,"Employee is Added").show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(service.delete(txtID.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Employee is Removed").show();
            loadTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Employee emp = new Employee(
                txtID.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText()
        );
        if(service.update(emp)){
            new Alert(Alert.AlertType.INFORMATION,"Employee is Updated").show();
            loadTable();
        }else {
            new Alert(Alert.AlertType.ERROR).show();
        }

    }
    public void loadTable(){
       tblEmployeeDetails.setItems(service.getAllEmployee());
    }



}
