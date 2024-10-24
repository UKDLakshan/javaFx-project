package contraller.account;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Account;

public class AccountFormContraller {

    @FXML
    private ImageView colthImageView;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label lblCreateAccount;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtPassword;

    Image image = new Image("img/shop1.jpg");

    AccountService service = new AccountContraller();


    @FXML
    void btnCreateOnAction(ActionEvent event) {
        if(txtID.getText() != null && txtEmail.getText()!= null && txtPassword.getText()!= null){
            Account account = new Account(
                    txtID.getText(),
                    txtEmail.getText(),
                    txtPassword.getText()
            );
            if(service.createAccount(account)){
                new Alert(Alert.AlertType.INFORMATION,"Account is created").show();
                lblCreateAccount.setText("create Account......");
            }else{
                new Alert(Alert.AlertType.ERROR).show();
                lblCreateAccount.setText("Invalid Email........");
            }
        }else{
            lblCreateAccount.setText("empty field");
        }


    }

}
