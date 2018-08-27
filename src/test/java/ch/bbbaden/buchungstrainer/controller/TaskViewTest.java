package ch.bbbaden.buchungstrainer.controller;


import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adrian Wilhelm
 */
public class TaskViewTest extends ApplicationTest {

    private final static String FXMLFILE = "/fxml/Task.fxml";
    private final static String TITLE = "#lblTitle";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
    }

}
