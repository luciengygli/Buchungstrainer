/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrian Wilhelm
 */
public class TaskStartController extends Controller implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblError;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblTask;
    @FXML
    private Label lblTask1;
    @FXML
    private AnchorPane base;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
    }

    @FXML
    private void next(ActionEvent event) throws IOException {
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.TASK);
    }

}
