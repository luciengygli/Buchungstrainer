package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
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
public class LoginViewTest extends ApplicationTest {

    private final static String FXMLFILE = "/fxml/Login.fxml";
    private final static String TITLE = "#lblTitle";
    private final static String ERROR = "#lblError";
    private final static String EMAILTXT = "#txtEmail";
    private final static String PASSWORDTXT = "#txtPassword";
    private final static String BACKBUTTON = "#btnBack";
    private final static String LOGINBUTTON = "#btnLogin";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
        ((TextField) lookup(EMAILTXT).query()).setText("");
    }

    @Test
    public void backButtonTest() {
        clickOn(BACKBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Buchungstrainer"));
    }

    @Test
    public void loginTeacherTest() {

        User.getInstance().setMode("teacher");

        doubleClickOn(EMAILTXT).eraseText(0).write("adrian.wilhelm@stud.bbbaden.ch");
        doubleClickOn(PASSWORDTXT).write("admin");
        clickOn(LOGINBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Sets"));
    }

    @Test
    public void loginStudentTest() {

        User.getInstance().setMode("student");

        doubleClickOn(EMAILTXT).write("lucien.gygli@stud.bbbaden.ch");
        doubleClickOn(PASSWORDTXT).write("admin");
        clickOn(LOGINBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Sets"));
    }

    @Test
    public void loginWithoutPasswordTest() {

        User.getInstance().setMode("teacher");

        doubleClickOn(EMAILTXT).write("adrian.wilhelm@stud.bbbaden.ch");
        clickOn(LOGINBUTTON);
        FxAssert.verifyThat(ERROR, NodeMatchers.hasText("Email oder password ist falsch."));
    }

    @Test
    public void loginWithoutEmailTest() {

        User.getInstance().setMode("teacher");

        doubleClickOn(PASSWORDTXT).write("qwertz123");
        clickOn(LOGINBUTTON);
        FxAssert.verifyThat(ERROR, NodeMatchers.hasText("Email oder password ist falsch."));
    }

}
