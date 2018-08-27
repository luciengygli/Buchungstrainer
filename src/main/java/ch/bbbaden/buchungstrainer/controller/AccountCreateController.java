/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.dao.SetDAO;
import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.set.rw.Type;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Adrian Wilhelm
 */
public class AccountCreateController extends Controller {

    private Account lastAddedAccount = null;
    private static final Logger LOG = Logger.getLogger(AccountCreateController.class.getName());

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Account> listAccount;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnNext;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtValue;
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lblErrorID;
    @FXML
    private Label lblErrorName;
    @FXML
    private Label lblErrorValue;
    @FXML
    private ComboBox<Type> comType;
    @FXML
    private CheckBox checkMinus;
    @FXML
    private Label lblErrorTyp;
    @FXML
    private Label lblError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LOG.info("Initialize AccountCreateView");

        Account account = new Account();
        account.setId(4999);
        account.setAb(new BigDecimal(0));
        account.setValue(new BigDecimal(0));
        account.setName("Gewinn (Bilanz)");
        account.setType(Type.PASSIVES);

        if (!CurrentSet.getInstance().getSet().getAccountlist().contains(account)) {
            CurrentSet.getInstance().getSet().getAccountlist().add(account);
        }

        account = new Account();
        account.setId(5999);
        account.setAb(new BigDecimal(0));
        account.setValue(new BigDecimal(0));
        account.setName("Erfolg (ER)");
        account.setType(Type.EFFORT);

        if (!CurrentSet.getInstance().getSet().getAccountlist().contains(account)) {
            CurrentSet.getInstance().getSet().getAccountlist().add(account);
        }

        listAccount.getItems().addAll(CurrentSet.getInstance().getSet().getAccountlist());

        listAccount.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {

                if (oldValue != null) {
                    clearTextboxs();
                }

                comType.setItems(FXCollections.observableArrayList(Arrays.asList(Type.values())));

                if (newValue != null) {
                    writeToTextbox(newValue);
                }

                manageDisabeling(newValue == null);

            }

            private void manageDisabeling(boolean disabled) {
                btnDelete.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()) || accountIsUsedInTasks(listAccount.getSelectionModel().getSelectedItem()));
                btnUpdate.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
                txtID.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
                txtName.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
                txtValue.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
                comType.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
                checkMinus.setDisable(disabled || isPredefinedAccount(listAccount.getSelectionModel().getSelectedItem()));
            }

            private void writeToTextbox(Account account) {
                txtID.setText("" + account.getId());
                txtName.setText(account.getName());
                txtValue.setText("" + account.getValue());
                comType.getSelectionModel().select(account.getType());
                checkMinus.setSelected(account.isMinusAccount());
            }

            private void clearTextboxs() {
                txtID.clear();
                txtName.clear();
                txtValue.clear();
                comType.setItems(FXCollections.emptyObservableList());
            }

            private boolean accountIsUsedInTasks(Account account) {
                return CurrentSet.getInstance().getSet().getTasklist().stream().anyMatch((task) -> (task.getSolutions().stream().anyMatch((solution) -> (solution.getSoll().equals(account) || solution.getHaben().equals(account)))));
            }

            private boolean isPredefinedAccount(Account account) {
                if (account != null && (account.getId() == 4999 || account.getId() == 5999)) {
                    return true;
                }
                return false;
            }
        });

        comType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Type>() {
            @Override
            public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
                checkMinus.setDisable(newValue == null);
            }
        });

        if (listAccount.getItems().isEmpty()) {
            btnNext.setDisable(true);
        }

    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        // TODO Create our own Confirm Window
        int i = JOptionPane.showConfirmDialog(null, "Bist du sicher, dass du alle Änderungen verwerfen willst?", "Kontenerstellung verlassen", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            LOG.log(Level.INFO, "Delete changes and change view");
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
        }
    }

    @FXML
    private void add(ActionEvent event) {

        LOG.log(Level.INFO, "Add new account");

        btnAdd.setDisable(true);
        Account newAccount = new Account();
        listAccount.getItems().add(newAccount);
        sortList();
        lastAddedAccount = newAccount;

        listAccount.getSelectionModel().select(newAccount);

        btnNext.setDisable(true);
    }

    @FXML
    private void delete(ActionEvent event) {

        Account account = listAccount.getSelectionModel().getSelectedItem();

        boolean deleteIsPossible = true;
        for (Task task : CurrentSet.getInstance().getSet().getTasklist()) {
            for (Solution solution : task.getSolutions()) {
                if (solution.getSoll().equals(account) || solution.getHaben().equals(account)) {
                    deleteIsPossible = false;
                }
            }
        }

        if (deleteIsPossible) {

            lblError.setText("");

            LOG.log(Level.INFO, "Delete account: {0}", account);

            listAccount.getItems().remove(account);

            if (listAccount.getItems().size() > 0) {
                if (lastAddedAccount == null) {

                }
            } else {
                btnAdd.setDisable(false);
            }
            btnNext.setDisable(!listIsComplete());
        } else {
            lblError.setText("Could not delete Account because it's used in one of the tasks");
        }
    }

    @FXML
    private void next(ActionEvent event) throws IOException {

        LOG.log(Level.INFO, "Override old accountlist and set file and change view");

        Set set = CurrentSet.getInstance().getSet();
        set.setAccountlist(listAccount.getItems());

        SetDAO.exportSet(new File(Settings.SETS + set.getFilename()), set);

        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
    }

    @FXML
    private void update(ActionEvent event) {

        Account account = listAccount.getSelectionModel().getSelectedItem();

        LOG.log(Level.INFO, "Updating selected account: ID = {0}, name = {1}, value = {2}", new Object[]{account.getId(), account.getName(), account.getValue()});

        Integer id = 0;
        String name = "";
        BigDecimal value = new BigDecimal(0);

        try {
            id = Integer.parseInt(txtID.getText());
            lblErrorID.setText("");
        } catch (NumberFormatException ex) {
            lblErrorID.setText("Bitte geben Sie eine gültige ID ein");
            LOG.log(Level.WARNING, "User entered illegal ID: {0}", txtID.getText());
        }

        name = txtName.getText().replace("<", "&lt;").replace(">", "&gt;");
        if (name.equals("")) {
            lblErrorName.setText("Bitte geben Sie einen Namen ein");
        } else {
            lblErrorName.setText("");
        }

        try {
            value = new BigDecimal(txtValue.getText());
            lblErrorValue.setText("");
        } catch (NumberFormatException ex) {
            lblErrorValue.setText("Bitte geben Sie eine gültige Zahl ein");
            LOG.log(Level.WARNING, "User entered illegal value: {0}", txtValue.getText());
        }

        if (id != 0 && !name.equals("") && value != null) {

            for (Account item : listAccount.getItems()) {
                if (item == account) {
                    continue;
                }
                if (item.getId() == id) {
                    lblErrorID.setText("Bitte verwenden Sie einen neue Account-ID");
                    id = null;
                    break;
                } else {
                    lblErrorID.setText("");
                }
                if (item.getName().equals(name)) {
                    lblErrorName.setText("Bitte verwenden Sie einen neuen Account-Name");
                    LOG.log(Level.WARNING, "User used an already used Name. Name: {0}", name);
                    name = "Konto";
                } else {
                    lblErrorName.setText("");
                }
            }

            account.setId(id);
            account.setName(name);
            account.setAb(value);
            account.setValue(value);
            account.setType(comType.getValue());
            account.setIsMinusAccount(checkMinus.isSelected());

            LOG.log(Level.INFO, "Account was edited. New Account: ID = {0}, name = {1}, value = {2}", new Object[]{account.getId(), account.getName(), account.getValue()});
        } else {
            LOG.log(Level.WARNING, "Account was not edited. Variables: ID = {0}, name = {1}, value = {2}", new Object[]{id, name, value});
        }

        sortList();
        listAccount.refresh();

        btnAdd.setDisable(!(lastAddAccountIsComplete()));
        btnNext.setDisable(!listIsComplete());
    }

    private void sortList() {
        listAccount.getItems().sort((Account o1, Account o2) -> {
            if (o1.getId() < o2.getId()) {
                return -1;
            } else if (o1.getId() > o2.getId()) {
                return 1;
            }
            return 0;
        });
    }

    private boolean listIsComplete() {

        if (!(listAccount.getItems().size() > 1)) {
            return false;
        }

        for (Account item : listAccount.getItems()) {
            if (!(item.getId() > 0)) {
                return false;
            }
            if (item.getName().equals("")) {
                return false;
            }
            if (item.getType() == null || item.getType() == Type.TYPE) {
                return false;
            }
        }
        return true;
    }

    private boolean lastAddAccountIsComplete() {
        if (lastAddedAccount == null) {
            return true;
        }
        if (lastAddedAccount.getId() > 0) {
            lblErrorID.setText("");
            if (!lastAddedAccount.getName().equals("")) {
                lblErrorName.setText("");
                if (lastAddedAccount.getType() != null) {
                    lblErrorTyp.setText("");
                    return true;
                } else {
                    lblErrorTyp.setText("Bitte wählen Sie einen Kontentypen aus!");
                }
            } else {
                lblErrorName.setText("Bitte geben Sie dem Konto einem Namen!");
            }
        } else {
            lblErrorID.setText("Bitte geben Sie dem Konto eine ID!");
        }
        return false;
    }

}
