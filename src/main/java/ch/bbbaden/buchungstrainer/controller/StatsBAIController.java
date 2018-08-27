/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.set.rw.Type;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lucien Gygli
 */
public class StatsBAIController extends Controller implements Initializable {

    @FXML
    private AnchorPane base;
    @FXML
    private Label lblTitle;
    @FXML
    private ListView<Account> listActives;
    @FXML
    private ListView<Account> listPassives;
    @FXML
    private ListView<Account> listEfforts;
    @FXML
    private ListView<Account> listEarnings;
    @FXML
    private Label lblKonto;
    @FXML
    private Label lblIA;
    @FXML
    private Label lblRA;
    @FXML
    private Button btnFinish;

    private List<Account> solutions;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        solutions = generateRightSolutions();

        List<Account> answers = CurrentSet.getInstance().getBilanceAndIncomeAnswers();

        answers.stream().forEach((Account account) -> {
            switch (account.getType()) {
                case ACTIVES:
                    listActives.getItems().add(account);
                    break;
                case PASSIVES:
                    listPassives.getItems().add(account);
                    break;
                case EFFORT:
                    listEfforts.getItems().add(account);
                    break;
                case EARNING:
                    listEarnings.getItems().add(account);
                    break;
                default:
            }
        });

        List<ListView<Account>> listviews = new ArrayList<>();
        listviews.add(listActives);
        listviews.add(listPassives);
        listviews.add(listEfforts);
        listviews.add(listEarnings);

        listviews.forEach((ListView<Account> list) -> {

            list.setCellFactory((ListView<Account> param) -> {
                return new ListCell<Account>() {

                    final PseudoClass RIGHT_PSEUDO_CLASS = PseudoClass.getPseudoClass("right");
                    final PseudoClass WRONG_PSEUDO_CLASS = PseudoClass.getPseudoClass("wrong");

                    @Override
                    protected void updateItem(Account account, boolean empty) {
                        super.updateItem(account, empty);

                        if (empty || account == null) {
                            setText("");
                        } else {
                            String text = account.toString().concat(" ").concat(account.getValue().toString());

                            pseudoClassStateChanged(RIGHT_PSEUDO_CLASS, isRight(account));
                            pseudoClassStateChanged(WRONG_PSEUDO_CLASS, !isRight(account));

                            setText(text);
                        }

                    }

                    private boolean isRight(Account account) {
                        for (Account solution : solutions) {
                            if (account.equals(solution)) {
                                if (account.getType() == solution.getType() && account.getValue().equals(solution.getValue())) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                        return false;
                    }
                };
            });

            list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
                @Override
                public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {

                    if (newValue != null) {

                        disselectOthers(newValue);

                        lblKonto.setText(newValue.toString());
                        lblIA.setText(newValue.getValue().toString() + ", " + newValue.getType().toString());

                        solutions.stream().filter((solution) -> (solution.equals(newValue))).forEachOrdered((solution) -> {
                            lblRA.setText(solution.getValue().toString() + ", " + solution.getType().toString());
                        });
                    }

                }

                private void disselectOthers(Account account) {
                    switch (account.getType()) {
                        case ACTIVES:
                            listPassives.getSelectionModel().clearSelection();
                            listEfforts.getSelectionModel().clearSelection();
                            listEarnings.getSelectionModel().clearSelection();
                            break;
                        case PASSIVES:
                            listActives.getSelectionModel().clearSelection();
                            listEfforts.getSelectionModel().clearSelection();
                            listEarnings.getSelectionModel().clearSelection();
                            break;
                        case EFFORT:
                            listActives.getSelectionModel().clearSelection();
                            listPassives.getSelectionModel().clearSelection();
                            listEarnings.getSelectionModel().clearSelection();
                            break;
                        case EARNING:
                            listActives.getSelectionModel().clearSelection();
                            listPassives.getSelectionModel().clearSelection();
                            listEfforts.getSelectionModel().clearSelection();
                            break;
                        default:
                    }
                }
            });

        });
    }

    private List<Account> generateRightSolutions() {

        List<Account> accounts = CurrentSet.getInstance().getSet().getAccountlist();

        List<Task> tasks = CurrentSet.getInstance().getSet().getTasklist();

        tasks.forEach((Task task) -> {

            task.getSolutions().forEach(((Solution solution) -> {

                accounts.forEach((Account account) -> {

                    if (solution.getSoll().equals(account)) {
                        switch (account.getType()) {
                            case ACTIVES:
                                if (account.isMinusAccount()) {
                                    account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                } else {
                                    account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                }
                                break;
                            case PASSIVES:
                                if (account.isMinusAccount()) {
                                    account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                } else {
                                    account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                }
                                break;
                            case EFFORT:
                                account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                break;
                            case EARNING:
                                account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                break;
                            default:
                        }
                    }

                    if (solution.getHaben().equals(account)) {
                        switch (account.getType()) {
                            case ACTIVES:
                                if (account.isMinusAccount()) {
                                    account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                } else {
                                    account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                }
                                break;
                            case PASSIVES:
                                if (account.isMinusAccount()) {
                                    account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                } else {
                                    account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                }
                                break;
                            case EFFORT:
                                account.setValue(account.getValue().subtract(new BigDecimal(solution.getValue().doubleValue())));
                                break;
                            case EARNING:
                                account.setValue(account.getValue().add(new BigDecimal(solution.getValue().doubleValue())));
                                break;
                            default:
                        }
                    }

                });

            }));

        });

        double value = 0.0;
        for (Account account : accounts) {
            if (account.getType() == Type.EARNING) {
                value -= account.getValue().intValue();
            } else if (account.getType() == Type.EFFORT) {
                value += account.getValue().intValue();
            }
        }

        accounts.get(accounts.indexOf(new Account(4999, "Gewinn (Bilanz)"))).setValue(new BigDecimal(value));
        accounts.get(accounts.indexOf(new Account(5999, "Erfolg (ER)"))).setValue(new BigDecimal(value));

        return accounts;
    }

    public void finish() throws IOException {
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
    }

    public void check(Label answer, Label solution) {
        if (answer.getText().equals(solution.getText())) {
            answer.setTextFill(Color.GREEN);
        } else {
            answer.setTextFill(Color.RED);
        }
        solution.setTextFill(Color.GREEN);
    }

}
