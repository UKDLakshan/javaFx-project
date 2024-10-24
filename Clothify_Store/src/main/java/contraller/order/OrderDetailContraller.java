package contraller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailContraller {
    public boolean addOrderDetail(List<OrderDetail> orderDetailList) {
        for(OrderDetail orderDetail:orderDetailList){
            boolean isOrderDetailAdd =addOrderDetail(orderDetail);
            if (!isOrderDetailAdd){
                return false;
            }
        }
        return true;
    }
    public boolean addOrderDetail(OrderDetail orderDetail){
        try {
            String sql = "insert into order_Details values (?,?,?)";
            return CrudUtil.execute(sql,
                    orderDetail.getOrderID(),
                    orderDetail.getItemID(),
                    orderDetail.getQty()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
