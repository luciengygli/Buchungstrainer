package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.dao.LoginDAO;
import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController extends Controller {

    private final static boolean USER_EXISTS = true;

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;
    @FXML
    private Label lblRegister;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtEmail.setText(Settings.getSetting("last" + User.getInstance().getMode() + "login"));

        base.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    btnLogin.fire();
                }
            }
        });

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        User.getInstance().setMode("noMode");
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.CHOICE);
    }

    @FXML
    private void register(MouseEvent event) throws IOException {
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.REGISTER);
    }

    @FXML
    private void login(ActionEvent event) throws IOException {

        String mode = User.getInstance().getMode();
        String email = txtEmail.getText().toLowerCase();
        String password = escape(txtPassword.getText());

        if (LoginDAO.check(mode, email, password) == USER_EXISTS) {

            User.getInstance().setEmail(email);
            String setting = "last" + User.getInstance().getMode() + "login";
            Settings.setSetting(setting, email);

            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
        } else {
            lblError.setText("Email oder password ist falsch.");
        }

    }

    private String escape(String password) {
        return password.replace("<", "&lt;").replace(">", "&gt;");
    }

}
