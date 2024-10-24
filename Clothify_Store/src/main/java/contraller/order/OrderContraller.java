package contraller.order;

import contraller.item.ItemContraller;
import db.DBConnection;
import javafx.scene.control.Alert;
import lombok.*;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderContraller {

    private static OrderContraller instance;
    private OrderContraller(){

    }
    public static OrderContraller getInstance(){
        return instance == null ? instance = new OrderContraller(): instance;
    }

    public boolean placeOrder(Order order){
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.setAutoCommit(false);
            String sql = "insert into orders values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1,order.getOrderID());
            preparedStatement.setObject(2,order.getDate());
            preparedStatement.setObject(3,order.getEmp_ID());
            boolean isOrederAdd = preparedStatement.executeUpdate()>0;

            if(isOrederAdd){
                boolean isOrderDetailAdd = new OrderDetailContraller().addOrderDetail(order.getOrderDetailList());
                if (isOrderDetailAdd){
                    boolean isStockUpdate = ItemContraller.getInstance().updateStock(order.getOrderDetailList());
                    if (isStockUpdate){
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION,"Order Placed").show();
                        return true;
                    }
                }
            }
            connection.rollback();
            new Alert(Alert.AlertType.ERROR,"Order not placed").show();
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
