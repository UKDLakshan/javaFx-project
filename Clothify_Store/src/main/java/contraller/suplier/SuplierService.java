package contraller.suplier;

import javafx.collections.ObservableList;
import model.Item;
import model.Suplier;

public interface SuplierService {
    boolean addSuplier(Suplier suplier);
    boolean deleteSuplier(String id);
    boolean updateSuplier(Suplier suplier);
    ObservableList<Suplier> getAllSuplierDetails();
    Suplier searchSuplier(String id);
}
