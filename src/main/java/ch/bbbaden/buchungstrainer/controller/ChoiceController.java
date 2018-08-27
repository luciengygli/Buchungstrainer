package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChoiceController extends Controller {

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnStudent;
    @FXML
    private Button btnTeacher;
    @FXML
    private Label lblTitle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void goStudent(ActionEvent event) throws IOException {
        User.getInstance().setMode("student");
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.LOGIN);
    }

    @FXML
    private void goTeacher(ActionEvent event) throws IOException {
        User.getInstance().setMode("teacher");
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.LOGIN);
    }
}
