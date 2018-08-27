/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Adrian Wilhelm
 */
public class FXMLUtil {

    public static final String CHOICE = "/fxml/Choice.fxml";
    public static final String LOGIN = "/fxml/Login.fxml";
    public static final String REGISTER = "/fxml/Register.fxml";
    public static final String CREATEACCOUNTLIST = "/fxml/AccountCreate.fxml";
    public static final String CREATESET = "/fxml/SetCreate.fxml";
    public static final String SET = "/fxml/Set.fxml";
    public static final String TASK = "/fxml/Task.fxml";
    public static final String TASKSTART = "/fxml/TaskStart.fxml";
    public static final String BILANZINCOME = "/fxml/BilanceAndIncome.fxml";
    public static final String STATS = "/fxml/Stats.fxml";
    public static final String STATSBAI = "/fxml/StatsBAI.fxml";

    public static void changeScene(Stage stage, FXMLLoader loader) throws IOException {

        Parent root = loader.load();

        Scene scene = new Scene(root);

        double height = scene.getHeight();
        double width = scene.getWidth();

        stage.setTitle("Buchungstrainer");
        stage.setScene(scene);

        stage.setHeight(height);
        stage.setWidth(width);

        stage.show();
    }

}
