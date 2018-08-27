package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.*;
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
public class SetViewTest extends ApplicationTest {

    private final static String FXMLFILE = "/fxml/Set.fxml";
    private final static String TITLE = "#lblTitle";
    private final static String ERROR = "#lblError";
    private final static String BACKBUTTON = "#btnBack";
    private final static String CREATEBUTTON = "#btnCreate";
    private final static String OPENBUTTON = "#btnOpen";
    private final static String IMPORTBUTTON = "#btnImport";
    private final static String ACCOUNTBUTTON = "#btnAccount";
    private final static String EDITBUTTON = "#btnEditSet";
    private final static String EXPORTBUTTON = "#btnExport";
    private final static String DELETEBUTTON = "#btnDelete";
    private final static String LIST = "#listSets";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
    }

    // BACK
    @Test
    public void testBackButton() {
        clickOn(BACKBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Einloggen"));
    }

    // IMPORT
    @Test
    public void testImportButton() {
        // DO OR NOT DO: THAT IS THE QUESTION
    }

    // OPEN
    @Test
    public void testOpenButton() {
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(0);
        clickOn(OPENBUTTON);
        FxAssert.verifyThat(TITLE, NodeMatchers.hasText("Aufgabe X"));
    }

    @Test
    public void testOpenButtonWhenUnselectedTableContent() {
        FxAssert.verifyThat(OPENBUTTON, NodeMatchers.isDisabled());
    }

    @Test
    public void testOpenButtonWhenReUnselectedTableContent() {
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(0);
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(null);

        FxAssert.verifyThat(OPENBUTTON, NodeMatchers.isDisabled());
    }

    // DELETE
    @Test
    public void testDeleteButton() {
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(0);
        Set set = (Set) ((ListView) from(lookup(LIST)).query()).getItems().get(0);

        clickOn(DELETEBUTTON);
        Assert.assertEquals(false, ((ListView) from(lookup(LIST)).query()).getItems().contains(set));
    }

    @Test
    public void testDeleteButtonWhenUnselectedTableContent() {
        FxAssert.verifyThat(DELETEBUTTON, NodeMatchers.isDisabled());
    }

    @Test
    public void testDeleteButtonWhenReUnselectedTableContent() {
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(0);
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().clearSelection();

        FxAssert.verifyThat(DELETEBUTTON, NodeMatchers.isDisabled());
    }

    // CREATE
    @Test
    public void testCreateButton() {
        clickOn(CREATEBUTTON);
        ObservableList set = ((ListView) from(lookup(LIST)).query()).getItems();
        // Tester has to enter asdf (JOptionPane)
        Assert.assertEquals("asdf", ((Set) set.get(set.size() - 1)).getTitle());
    }

    // EXPORT
    @Test
    public void testExportButton() {
        // DO OR NOT DO: THAT IS THE QUESTION
    }

    @Test
    public void testExportButtonWhenUnselectedTableContent() {
        FxAssert.verifyThat(EXPORTBUTTON, NodeMatchers.isDisabled());
    }

    @Test
    public void testExportButtonWhenReUnselectedTableContent() {
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(0);
        ((ListView) from(lookup(LIST)).query()).getSelectionModel().select(null);

        FxAssert.verifyThat(EXPORTBUTTON, NodeMatchers.isDisabled());
    }

}
