package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.dao.SetDAO;
import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.user.User;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.jdom2.JDOMException;

public class SetController extends Controller {

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnOpen;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnExport;
    @FXML
    private Label lblTitle;
    @FXML
    private ListView<Set> listSets;
    @FXML
    private Label lblError;
    @FXML
    private Button btnAccount;
    @FXML
    private Button btnEditSet;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (User.getInstance().getMode().equals("student")) {
            ((AnchorPane) btnExport.getParent()).getChildren().remove(btnExport);
            ((AnchorPane) btnCreate.getParent()).getChildren().remove(btnCreate);
            ((AnchorPane) btnAccount.getParent()).getChildren().remove(btnAccount);
            ((AnchorPane) btnEditSet.getParent()).getChildren().remove(btnEditSet);
        }

        listSets.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Set>() {
            @Override
            public void changed(ObservableValue<? extends Set> observable, Set oldValue, Set newValue) {
                if (newValue != null) {
                    btnOpen.setDisable(!(newValue.getTasklist().size() >= 1));
                    btnAccount.setDisable(false);
                    btnEditSet.setDisable(!(newValue.getAccountlist().size() > 1));
                    btnExport.setDisable(!(newValue.getTasklist().size() >= 1));
                    btnDelete.setDisable(false);
                } else {
                    btnOpen.setDisable(true);
                    btnAccount.setDisable(true);
                    btnEditSet.setDisable(true);
                    btnExport.setDisable(true);
                    btnDelete.setDisable(true);
                }
            }
        });

        ObservableList list = FXCollections.observableArrayList();
        list.addAll(SetDAO.getSets());
        listSets.setItems(list);

        listSets.refresh();

        if (CurrentSet.getInstance().getSet() != null) {
            for (Set item : listSets.getItems()) {
                if (item.equals(CurrentSet.getInstance().getSet())) {
                    listSets.getSelectionModel().select(item);
                    CurrentSet.getInstance().setSet(null);
                }
            }
        }

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        User.getInstance().setEmail("noMail");
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.LOGIN);;
    }

    @FXML
    private void open(ActionEvent event) throws IOException {
        CurrentSet.getInstance().setSet(reloadSelectedSet());
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.TASKSTART);
    }

    @FXML
    private void editSet(ActionEvent event) throws IOException {
        CurrentSet.getInstance().setSet(listSets.getSelectionModel().getSelectedItem());
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.CREATESET);
    }

    @FXML
    private void editAccount(ActionEvent event) throws IOException {
        CurrentSet.getInstance().setSet(listSets.getSelectionModel().getSelectedItem());
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.CREATEACCOUNTLIST);
    }

    @FXML
    private void setImport(ActionEvent event) throws IOException, JDOMException, ParseException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Set", "*.set"));

        try {

            File file = chooser.showOpenDialog(new Stage());

            Set newSet = SetDAO.importSet(file);
            boolean isExisting = false;
            for (Set set : listSets.getItems()) {
                if (newSet.equals(set)) {
                    isExisting = true;
                }
            }
            if (isExisting == false) {
                listSets.getItems().add(newSet);
            }
        } catch (NullPointerException ex) {
            System.err.println("No such file");
        }

    }

    @FXML
    private void delete(ActionEvent event) throws IOException {

        Set set = listSets.getSelectionModel().getSelectedItem();

        // TODO Create our own Confirm Window
        int i = JOptionPane.showConfirmDialog(null, "Bist du sicher, dass du das " + set.getTitle(), "-Set löschen willst?", JOptionPane.YES_NO_OPTION);

        if (i == 0) {
            listSets.getItems().remove(set);
            SetDAO.cacheSet(set);
        }
    }

    @FXML
    private void create(ActionEvent event) throws IOException {

        // TODO Create our own Input Window
        String title = JOptionPane.showInputDialog("Gib den Set-Namen ein:");

        if (title != null) {
            Set newSet = new Set(title, User.getInstance().getEmail(), new Date(), new ArrayList<>());
            listSets.getItems().add(newSet);
            SetDAO.exportSet(new File(Settings.SETS + newSet.getFilename()), newSet);
        }
    }

    @FXML
    private void setExport(ActionEvent event) {

        DateFormat format = new SimpleDateFormat("ddMMYYYY");
        Set set = listSets.getSelectionModel().getSelectedItem();

        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName(set.getFilename());
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Set", ".set"));
        File file = chooser.showSaveDialog(new Stage());

        try {
            SetDAO.exportSet(file, set);
        } catch (IOException ex) {
            lblError.setText("Bitte wähle einen gültigen Pfad");
        } catch (NullPointerException ex) {

        }
    }

    private Set reloadSelectedSet() {
        Set set = listSets.getSelectionModel().getSelectedItem();
        return SetDAO.reloadSet(set);
    }
}
