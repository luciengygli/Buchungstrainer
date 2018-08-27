package ch.bbbaden.buchungstrainer.controller;


import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
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
public class RegisterViewTest extends ApplicationTest {

    private final static String FXMLFILE = "/fxml/Register.fxml";
    private final static String TITLE = "#lblTitle";
    private final static String ERROR = "#lblError";
    private final static String EMAIL = "#txtEmail";
    private final static String PASSWORD = "#txtNewPassword";
    private final static String PASSWORD_CONFIRM = "#txtConfirmPassword";
    private final static String BACKBUTTON = "#btnBack";
    private final static String REGISTER = "#btnRegister";

    @Override
    public void start(Stage stage) throws Exception {
        User.getInstance().setMode("student");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
    }

    // BACK
    @Test
    public void testBack() {
        clickOn(BACKBUTTON);
        verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

    // EMAIL
    @Test
    public void testEmailValidation() {
        doubleClickOn(EMAIL).write("test@test.com");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

    @Test
    public void testEmailValidationNoLocalPart() {
        doubleClickOn(EMAIL).write("@test.com");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Wählen Sie eine valide Email.\n"));
    }

    @Test
    public void testEmailValidationNoDomainPart() {
        doubleClickOn(EMAIL).write("test@.com");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Wählen Sie eine valide Email.\n"));
    }

    @Test
    public void testEmailValidationNoEndPart() {
        doubleClickOn(EMAIL).write("test@test");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Wählen Sie eine valide Email.\n"));
    }

    @Test
    public void testEmailNoEmail() {
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Wählen Sie eine valide Email.\n"));
    }

    // PASSWORD
    @Test
    public void testPasswordValidation() {
        doubleClickOn(EMAIL).write("test@test.com");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("asdfghjkl");
        clickOn(REGISTER);

        verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

    @Test
    public void testPasswordValidationNoPassword() {
        doubleClickOn(EMAIL).write("test@test.com");
        doubleClickOn(PASSWORD).write("");
        doubleClickOn(PASSWORD_CONFIRM).write("");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Das Passwort muss mindestens 8 Zeichen lang sein.\n"));
    }

    @Test
    public void testPasswordValidationNotSame() {
        doubleClickOn(EMAIL).write("test@test.com");
        doubleClickOn(PASSWORD).write("asdfghjkl");
        doubleClickOn(PASSWORD_CONFIRM).write("qwertzuiop");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Die beiden Passwörter müssen übereinstimmen."));
    }

    @Test
    public void testPasswordValidationBoth() {
        doubleClickOn(EMAIL).write("test@test.com");
        doubleClickOn(PASSWORD).write("asdf");
        doubleClickOn(PASSWORD_CONFIRM).write("qwer");
        clickOn(REGISTER);

        verifyThat(ERROR, NodeMatchers.hasText("Das Passwort muss mindestens 8 Zeichen lang sein.\nDie beiden Passwörter müssen übereinstimmen."));
    }

    @Test
    public void testRegister() {

    }

}
