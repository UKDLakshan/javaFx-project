package contraller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormCotraller implements Initializable {

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colSupID;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private JFXTextField txtItemID;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXTextField txtItemQty;

    @FXML
    private JFXTextField txtItemSize;

    @FXML
    private JFXTextField txtItemSupID;

    ItemService servise = ItemContraller.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupID.setCellValueFactory(new PropertyValueFactory<>("suplierID"));
        loadItemTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, item, t1) -> {
            txtItemID.setText(t1.getId());
            txtItemName.setText(t1.getName());
            txtItemSize.setText(t1.getSize());
            txtItemPrice.setText((t1.getPrice()).toString());
            txtItemQty.setText((t1.getQty()).toString());
            txtItemSupID.setText(t1.getSuplierID());
        });

    }

    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemID.getText(),
                txtItemName.getText(),
                txtItemSize.getText(),
                Double.parseDouble(txtItemPrice.getText()),
                Integer.parseInt(txtItemQty.getText()),
                txtItemSupID.getText()

        );
        if(servise.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
            loadItemTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }

    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) {
        if(servise.deleteItem(txtItemID.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
            loadItemTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }

    }

    @FXML
    void btnSearchItemOnAction(ActionEvent event) {
        Item item = servise.searchItem(txtItemID.getText());
        txtItemName.setText(item.getName());
        txtItemSize.setText(item.getSize());
        txtItemPrice.setText(item.getPrice().toString());
        txtItemQty.setText(item.getQty().toString());
        txtItemSupID.setText(item.getSuplierID());
    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {

        if(servise.updateItem(
                new Item(
                txtItemID.getText(),
                txtItemName.getText(),
                txtItemSize.getText(),
                Double.parseDouble(txtItemPrice.getText()),
                Integer.parseInt(txtItemQty.getText()),
                txtItemSupID.getText()
                )
             )
        ){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated").show();
            loadItemTable();
        }else{
            new Alert(Alert.AlertType.ERROR).show();
        }

    }
    public void loadItemTable(){
        tblItems.setItems(servise.getAllItem());
    }


}
