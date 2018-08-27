/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.set.rw.AccountIDComparator;
import ch.bbbaden.buchungstrainer.set.rw.Type;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
public class BilanceAndIncomeController extends Controller implements Initializable {

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblTitle;
    @FXML
    private ListView<Account> listAccounts;
    @FXML
    private Button btnCorrect;
    @FXML
    private ListView<Account> listActives;
    @FXML
    private ListView<Account> listPassives;
    @FXML
    private ListView<Account> listEfforts;
    @FXML
    private ListView<Account> listReturns;
    @FXML
    private Label lblAccount;
    @FXML
    private TextField txtValue;
    @FXML
    private ComboBox<Type> comCategorie;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblError;
    @FXML
    private Label lblAB;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        List<Account> accounts = getCopyOfAccounts();

        listAccounts.setItems(FXCollections.observableArrayList(accounts));
        comCategorie.setItems(FXCollections.observableArrayList(getTypes()));

        List<ListView<Account>> listviews = new ArrayList<>();

        listviews.add(listActives);
        listviews.add(listEfforts);
        listviews.add(listPassives);
        listviews.add(listReturns);

        listAccounts.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
                if (newValue != null) {
                    lblAccount.setText(newValue.toString());
                    lblAB.setText(newValue.getAb().toString());
                    txtValue.setText(newValue.getValue().toString());
                    comCategorie.setValue(newValue.getType());
                } else {
                    lblAccount.setText("");
                    lblAB.setText("");
                    txtValue.setText("");
                    comCategorie.setValue(null);
                }
            }
        });

        listAccounts.setCellFactory((ListView<Account> param) -> {
            return new ListCell<Account>() {

                final PseudoClass USED_PSEUDO_CLASS = PseudoClass.getPseudoClass("used");

                @Override
                protected void updateItem(Account account, boolean empty) {
                    super.updateItem(account, empty);

                    if (empty || account == null) {
                        setText("");
                    } else {
                        String text = account.toString().concat(" ").concat(account.getValue().toString());

                        if (account.getType() != null && account.getType() != Type.TYPE) {
                            pseudoClassStateChanged(USED_PSEUDO_CLASS, !isSelected());
                            text = text.concat(" in ").concat(account.getType().toString());
                        } else {
                            pseudoClassStateChanged(USED_PSEUDO_CLASS, false);
                        }
                        setText(text);
                    }

                }
            };
        });

        listviews.forEach((ListView<Account> listview) -> {

            listview.setCellFactory((ListView<Account> param) -> {

                return new ListCell<Account>() {
                    @Override
                    protected void updateItem(Account account, boolean empty) {
                        super.updateItem(account, empty);

                        if (empty || account == null) {
                            setText("");
                        } else {
                            setText(account.toString().concat(" ").concat(account.getValue().toString()));
                        }
                    }
                };
            });
        });

    }

    private List<Type> getTypes() {
        List<Type> types = new ArrayList<>(Arrays.asList(Type.values()));
        return types;
    }

    private List<Account> getCopyOfAccounts() {

        ArrayList<Account> accounts = new ArrayList<>();

        CurrentSet.getInstance().getSet().getAccountlist().forEach((Account account) -> {

            Account a = new Account();
            a.setId(account.getId());
            a.setName(account.getName());
            a.setAb(account.getAb());
            a.setValue(account.getValue());
            accounts.add(a);
        });

        return accounts;
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        // TODO Create our own Confirm Window
        int i = JOptionPane.showConfirmDialog(null, "Bist du sicher, dass du die Übung abbrechen willst?", "Übungen abbrechen", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {

            CurrentSet.getInstance().setSet(null);
            CurrentSet.getInstance().setBilanceAndIncomeAnswers(null);
            CurrentSet.getInstance().setSolutions(null);
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
        }
    }

    @FXML
    private void correct(ActionEvent event) throws IOException {

        if (answersAreComplete(listAccounts.getItems())) {
            lblError.setText("");
            CurrentSet.getInstance().setBilanceAndIncomeAnswers(listAccounts.getItems());
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.STATS);
        } else {
            lblError.setText("Bitte weisen Sie alle Konten dem Kontenplan zu!");
        }

    }

    private boolean answersAreComplete(List<Account> accounts) {
        return accounts.stream().noneMatch((account) -> (account.getType() == null || account.getType() == Type.TYPE));
    }

    @FXML
    private void saveCategorie(ActionEvent event) {

        // ERROR IF SELECTED "Categorie" (ArrayIndexOutOfBounds)
        // Select
        Account account = listAccounts.getSelectionModel().getSelectedItem();

        if (account.getType() != null) {
            // Delete
            switch (account.getType()) {
                case ACTIVES:
                    listActives.getItems().remove(account);
                    break;
                case PASSIVES:
                    listPassives.getItems().remove(account);
                    break;
                case EFFORT:
                    listEfforts.getItems().remove(account);
                    break;
                case EARNING:
                    listReturns.getItems().remove(account);
                    break;
                default:
            }
        }

        // Edit
        try {
            account.setValue(new BigDecimal(txtValue.getText()));
        } catch (NumberFormatException ex) {
            txtValue.setText("");
        }

        account.setType(comCategorie.getValue());

        if (account.getType() != null) {
            // Move
            switch (account.getType()) {
                case ACTIVES:
                    listActives.getItems().add(account);
                    listActives.getItems().sort(new AccountIDComparator());
                    break;
                case PASSIVES:
                    listPassives.getItems().add(account);
                    listPassives.getItems().sort(new AccountIDComparator());
                    break;
                case EFFORT:
                    listEfforts.getItems().add(account);
                    listEfforts.getItems().sort(new AccountIDComparator());
                    break;
                case EARNING:
                    listReturns.getItems().add(account);
                    listReturns.getItems().sort(new AccountIDComparator());
                    break;
                default:
            }
        }

        // Update
        listAccounts.refresh();
    }

}
