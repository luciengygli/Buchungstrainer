package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adrian Wilhelm
 */
public class ChoiceViewTest extends ApplicationTest {

    private final static String FXMLFILE = "/fxml/Choice.fxml";
    private final static String TITLE = "#lblTitle";
    private final static String STUDENTBUTTON = "#btn-student";
    private final static String TEACHERBUTTON = "#btn-teacher";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
    }

    @Test
    public void testGoStudentButton() {
        clickOn(STUDENTBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

    @Test
    public void testGoTeacherButton() {
        clickOn(TEACHERBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

}
