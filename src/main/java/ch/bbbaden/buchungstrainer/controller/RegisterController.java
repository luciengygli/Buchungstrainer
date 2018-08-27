package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.dao.RegisterDAO;
import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.validator.EmailValidator;

public class RegisterController extends Controller {

    private static final Logger LOG = Logger.getLogger(RegisterController.class.getName());

    @FXML
    private AnchorPane base;
    @FXML
    private Label lblTitle;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Label lblError;
    @FXML
    private Button btnRegister;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Tooltip tip = new Tooltip("Das Passwort muss mindestens 8 Zeichen lang sein.");
        txtNewPassword.setTooltip(tip);
        tip = new Tooltip("Geben Sie das Passwort erneut ein.");
        txtConfirmPassword.setTooltip(tip);

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.LOGIN);
    }

    @FXML
    private void register(ActionEvent event) throws IOException {

        String email = txtEmail.getText().toLowerCase();
        String password = escape(txtNewPassword.getText());
        String confirmPassword = escape(txtConfirmPassword.getText());

        lblError.setText("");

        if (validateEmail(email) && validatePassword(password, confirmPassword)) {

            LOG.log(Level.INFO, "Register new {0}: {1}", new Object[]{User.getInstance().getMode(), email});

            RegisterDAO.addUser(User.getInstance().getMode(), txtEmail.getText(), txtNewPassword.getText());
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.LOGIN);
        } else {
            LOG.log(Level.WARNING, "Could not regsiter new {0}.", User.getInstance().getMode());
        }
    }

    private String escape(String string) {
        if (string.contains("<") || string.contains(">")) {
            LOG.log(Level.WARNING, "Password contains bad characters. ESCAPE.");
        }
        return string.replace("<", "&lt;").replace(">", "&gt;");
    }

    private boolean validateEmail(String email) {
        if (EmailValidator.getInstance().isValid(email)) {
            LOG.log(Level.INFO, "Email \"{0}\" is valid.", email);
            return true;
        } else {
            lblError.setText("Wählen Sie eine valide Email.\n");
            LOG.log(Level.WARNING, "Email \"{0}\" is invalid.", email);
            return false;
        }
    }

    private boolean validatePassword(String password, String confirmPassword) {

        if (password.length() < 8) {
            lblError.setText(lblError.getText() + "Das Passwort muss mindestens 8 Zeichen lang sein.\n");
            LOG.log(Level.WARNING, "Password too short.");
        }
        if (!password.equals(confirmPassword)) {
            lblError.setText(lblError.getText() + "Die beiden Passwörter müssen übereinstimmen.");
            LOG.log(Level.WARNING, "Passwords didn't match.");
        }

        return lblError.getText().equals("");
    }

}
