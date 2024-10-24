package contraller.item;

import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetail;

import java.util.List;

public interface ItemService {
    boolean addItem(Item item);
    boolean deleteItem(String id);
    boolean updateItem(Item item);
    ObservableList<Item> getAllItem();
    Item searchItem(String id);
    List<String> getAllItemIds();

    boolean updateStock(List<OrderDetail> orderDetailList);
}
