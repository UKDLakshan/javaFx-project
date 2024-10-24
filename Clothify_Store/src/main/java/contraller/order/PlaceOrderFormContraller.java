package contraller.order;

import com.jfoenix.controls.JFXTextField;
import contraller.item.ItemContraller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CartTm;
import model.Item;
import model.Order;
import model.OrderDetail;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormContraller implements Initializable {

    public JFXTextField txtStock;
    @FXML
    private TableColumn<?, ?> colItemID;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private ComboBox<String> cmbItemID;

    @FXML
    private TableColumn<?, ?> colOrderID;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<CartTm> tblOrderItems;

    @FXML
    private JFXTextField txtEmpID;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtOrdeId;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateandTime();
        loadItemIds();
        cmbItemID.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if(t1!= null){
                serchItem(t1);
            }
        });

    }
    ObservableList<CartTm> cartTms = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));


        String itemId = cmbItemID.getValue();
        Double price = Double.parseDouble(txtPrice.getText());
        String size = txtSize.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        String itemName = txtItemName.getText();
        Double total = price * qty;
        if(Integer.parseInt(txtStock.getText())<qty){
            new Alert(Alert.AlertType.ERROR,"invalid quantity").show();
            txtQty.setText("");
        }else{
            cartTms.add(new CartTm(itemId, total, size, qty, itemName));
            tblOrderItems.setItems(cartTms);
            calcTotalPrice();

            txtQty.setText("");
            txtSize.setText("");
            txtStock.setText("");
            txtPrice.setText("");
            txtItemName.setText("");


        }


    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/employee_details_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnItemOnAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/item_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = txtOrdeId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String emID = txtEmpID.getText();

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();

        cartTms.forEach(obj -> {
            orderDetails.add(
                    new OrderDetail(
                            txtOrdeId.getText(),
                            obj.getItemId(),
                            obj.getQty())
            );


        });

        Order order = new Order(orderId, date, emID, orderDetails);
        OrderContraller.getInstance().placeOrder(order);

        txtEmpID.setText("");
         txtItemName.setText("");
         txtStock.setText("");
         txtPrice.setText("");
         txtSize.setText("");
         txtQty.setText("");



    }

    @FXML
    void btnSuplierOnAction(ActionEvent event) {

        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/suplier_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnViewOrderOnAction(ActionEvent event) {

    }
   private void loadDateandTime(){
        Date date = new Date();
       SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
       lblDate.setText( s.format(date));

       Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
           LocalTime now = LocalTime.now();
           lblTime.setText(now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());

       }),
               new KeyFrame(Duration.seconds(1))
       );
       timeline.setCycleCount(Animation.INDEFINITE);
       timeline.play();

   }
   private void loadItemIds(){
       List<String> allItemIds = ItemContraller.getInstance().getAllItemIds();
       ObservableList<String> stringIds = FXCollections.observableArrayList();
       allItemIds.forEach(obj ->{
           stringIds.add(obj);
       });
       cmbItemID.setItems(stringIds);



   }
   private void serchItem(String id){
       Item item = ItemContraller.getInstance().searchItem(id);
       txtItemName.setText(item.getName());
       txtStock.setText(item.getQty().toString());
       txtSize.setText(item.getSize());
       txtPrice.setText(item.getPrice().toString());
   }

    private void calcTotalPrice(){
        Double netTotal = 0.0;
        for(CartTm cartTm : cartTms){
            netTotal+= cartTm.getPrice();
        }
        lblTotal.setText(netTotal.toString());
    }


}
