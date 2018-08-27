/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 *
 * @author Adrian Wilhelm
 */
public abstract class Controller implements Initializable {

    public void changeScene(Stage stage, String scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(scene));
        FXMLUtil.changeScene(stage, loader);
    }

}
