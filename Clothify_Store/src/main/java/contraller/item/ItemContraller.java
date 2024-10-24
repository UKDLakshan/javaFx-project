package contraller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetail;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ItemContraller implements ItemService{

    private static ItemContraller instance;
    private ItemContraller(){}
    public static ItemContraller getInstance(){
        return instance == null ? instance = new ItemContraller() : instance;
    }
    @Override
    public boolean addItem(Item item) {

        try {
            String sql = "insert into item values (?,?,?,?,?,?)";
            return CrudUtil.execute(sql,
                    item.getId(),
                    item.getName(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty(),
                    item.getSuplierID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String id) {

        try {
            String sql = "delete from item where Item_Id = '"+id+"'";
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {

        try {
            String sql = "update item set name=?, size=?, price=? ,Qty=?, suplierID=? where Item_Id=?";
            return CrudUtil.execute(sql,
                    item.getName(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty(),
                    item.getSuplierID(),
                    item.getId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item searchItem(String id) {
        String sql ="select * from item where Item_Id='"+id+"'";
        try {
            ResultSet rslt = CrudUtil.execute(sql);
            while (rslt.next()){
                return new Item(
                        rslt.getString(1),
                        rslt.getString(2),
                        rslt.getString(3),
                        rslt.getDouble(4),
                        rslt.getInt(5),
                        rslt.getString(6)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {
            String sql = "select * from item";
            ResultSet execute = CrudUtil.execute(sql);
            while (execute.next()){
                itemObservableList.add(
                        new Item(
                                execute.getString(1),
                                execute.getString(2),
                                execute.getString(3),
                                execute.getDouble(4),
                                execute.getInt(5),
                                execute.getString(6)
                        )
                );
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<String> getAllItemIds(){
        ArrayList<String> itemIds = new ArrayList<>();
        ObservableList<Item> itemObservableList = getAllItem();
        itemObservableList.forEach(obj ->{
            itemIds.add(obj.getId());
        });
        return itemIds;
    }

    @Override
    public boolean updateStock(List<OrderDetail> orderDetailList) {
        for(OrderDetail orderDetail : orderDetailList){
            boolean isUpdate = updateStock(orderDetail);
            if (!isUpdate){
                return false;
            }
        }
        return true;
    }

    public boolean updateStock(OrderDetail orderDetail){
        String sql = "update item set  Qty =  Qty-? where  Item_Id = ?";
        try {
           return CrudUtil.execute(sql,orderDetail.getQty(),orderDetail.getItemID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
