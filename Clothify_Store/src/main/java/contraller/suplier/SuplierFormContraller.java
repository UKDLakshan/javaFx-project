package contraller.suplier;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Suplier;

import java.net.URL;
import java.util.ResourceBundle;

public class SuplierFormContraller implements Initializable {

    @FXML
    private TableColumn<?, ?> colCompany;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colItemID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colSupID;

    @FXML
    private TableView<Suplier> tblSuplier;

    @FXML
    private JFXTextField txtCompany;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtItemID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtSupID;

    SuplierService service = new SuplierContraller();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupID.setCellValueFactory(new PropertyValueFactory<>("sup_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("item_ID"));
        loadSuplierTable();

        tblSuplier.getSelectionModel().selectedItemProperty().addListener((observableValue, suplier, t1) -> {
            addValuesToText(t1);
        });

    }
    public void addValuesToText(Suplier newValue){
        txtSupID.setText(newValue.getSup_ID());
        txtName.setText(newValue.getName());
        txtEmail.setText(newValue.getEmail());
        txtCompany.setText(newValue.getCompany());
        txtItemID.setText(newValue.getItem_ID());
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {

        Suplier suplier = new Suplier(
                txtSupID.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText(),
                txtItemID.getText()
        );
        if(service.addSuplier(suplier)){
            new Alert(Alert.AlertType.INFORMATION,"Suplier Added").show();
            loadSuplierTable();
            clearText();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (service.deleteSuplier(txtSupID.getText())){
            new Alert(Alert.AlertType.INFORMATION,"suplier Deleted").show();
            loadSuplierTable();
            clearText();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Suplier suplier = service.searchSuplier(txtSupID.getText());
        if(suplier!= null){
            new Alert(Alert.AlertType.INFORMATION,"Success").show();
            txtName.setText(suplier.getName());
            txtEmail.setText(suplier.getEmail());
            txtCompany.setText(suplier.getCompany());
            txtItemID.setText(suplier.getItem_ID());
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Suplier suplier = new Suplier(
                txtSupID.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtCompany.getText(),
                txtItemID.getText()
        );
        if(service.updateSuplier(suplier)){
            new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            loadSuplierTable();
            clearText();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }
    }
    public void loadSuplierTable(){
       tblSuplier.setItems(service.getAllSuplierDetails());
    }
    public void clearText(){
        txtSupID.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtCompany.setText("");
        txtItemID.setText("");
    }
}
