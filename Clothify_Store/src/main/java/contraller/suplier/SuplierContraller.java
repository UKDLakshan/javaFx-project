package contraller.suplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.Suplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuplierContraller implements SuplierService{
    @Override
    public boolean addSuplier(Suplier suplier) {
        String sql = "insert into suplier values (?,?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    suplier.getSup_ID(),
                    suplier.getName(),
                    suplier.getEmail(),
                    suplier.getCompany(),
                    suplier.getItem_ID()
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteSuplier(String id) {
        String sql = "delete from suplier where Sup_ID = '"+id+"'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateSuplier(Suplier suplier) {
        try {
            return CrudUtil.execute("update suplier set  Name=?,Email=?, Company=?,item_id=? where  Sup_ID=?", suplier.getName(), suplier.getEmail(), suplier.getCompany(), suplier.getItem_ID(), suplier.getSup_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Suplier> getAllSuplierDetails() {
        ObservableList<Suplier> suplierObservableList = FXCollections.observableArrayList();

        try {
            String sql = "select * from suplier";
            ResultSet rslt = CrudUtil.execute(sql);

           while(rslt.next()){
               suplierObservableList.add(
                       new Suplier(
                               rslt.getString(1),
                               rslt.getString(2),
                               rslt.getString(3),
                               rslt.getString(4),
                               rslt.getString(5)
                       )
               );
           }
           return suplierObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Suplier searchSuplier(String id) {
        try {
            ResultSet rslt = CrudUtil.execute("select * from suplier where Sup_ID = '" + id + "'");
            while (rslt.next()){
              return new Suplier(
                        rslt.getString(1),
                        rslt.getString(2),
                        rslt.getString(3),
                        rslt.getString(4),
                        rslt.getString(5)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
