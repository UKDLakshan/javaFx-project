package contraller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashFormContraller {

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(""))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
