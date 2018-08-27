/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.dao.SetDAO;
import ch.bbbaden.buchungstrainer.set.*;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Adrian Wilhelm
 */
public class SetCreateController extends Controller {

    @FXML
    private AnchorPane base;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<Task> listTask;
    @FXML
    private Button btnSave;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblError;
    @FXML
    private VBox contentBox;
    @FXML
    private TextArea txtTask;
    @FXML
    private TextField txtValue;
    @FXML
    private Button btnTaskAdd;
    @FXML
    private Button btnTaskDelete;
    @FXML
    private Button btnSolutionAdd;
    @FXML
    private Button btnSolutionDelete;
    @FXML
    private ListView<Solution> listSolutions;
    @FXML
    private ComboBox<Account> comSoll;
    @FXML
    private ComboBox<Account> comHaben;
    @FXML
    private AnchorPane setPane;
    @FXML
    private BorderPane taskPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Initialize Tasklist
        Set cSet = CurrentSet.getInstance().getSet();
        if (!cSet.getTasklist().isEmpty()) {
            List<Task> tasks = cSet.getTasklist();

            listTask.getItems().setAll(tasks);
        }

        // Initialize Account Comboboxes
        if (!cSet.getAccountlist().isEmpty()) {
            comSoll.getItems().setAll(cSet.getAccountlist());
            comHaben.getItems().setAll(cSet.getAccountlist());
        }

        // <editor-fold defaultstate="collapsed" desc="ChangeListeners">
        listTask.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {

                taskPane.setDisable(newValue == null);
                btnTaskDelete.setDisable(newValue == null);
                listSolutions.getItems().clear();

                comSoll.setDisable(true);
                comHaben.setDisable(true);
                txtValue.setDisable(true);

                if (newValue != null) {
                    lblTitle.setText("Aufgabe " + (listTask.getSelectionModel().getSelectedIndex() + 1));
                    txtTask.setText(newValue.getTask());
                    listSolutions.getItems().addAll(newValue.getSolutions());
                } else {
                    lblTitle.setText("Aufgaben");
                    txtTask.clear();
                    listSolutions.getItems().clear();
                }
                manageDisableing();

                listSolutions.refresh();
                listTask.refresh();
            }
        });
        listSolutions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Solution>() {
            @Override
            public void changed(ObservableValue<? extends Solution> observable, Solution oldValue, Solution newValue) {

                comSoll.setDisable(newValue == null);
                comHaben.setDisable(newValue == null);
                txtValue.setDisable(newValue == null);

                comSoll.getSelectionModel().clearSelection();
                comHaben.getSelectionModel().clearSelection();
                txtValue.clear();

                if (newValue != null) {
                    comSoll.getSelectionModel().select(newValue.getSoll());
                    comHaben.getSelectionModel().select(newValue.getHaben());
                    txtValue.setText(newValue.getValue().toString());
                } else {
                }
                manageDisableing();

                listSolutions.refresh();
                listTask.refresh();
            }
        });
        txtTask.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    try {
                        listTask.getSelectionModel().getSelectedItem().setTask(newValue);
                        listTask.refresh();
                    } catch (Exception e) {
                    }
                }
                manageDisableing();
            }
        });
        comSoll.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
                if (newValue != null) {
                    if (comHaben.getValue() != null) {
                        if (comHaben.getValue().equals(newValue)) {
                            if (!newValue.equals(new Account()) && !newValue.equals(new Account())) {
                                if (oldValue.equals(new Account())) {
                                    comSoll.getSelectionModel().clearSelection();
                                } else {
                                    comSoll.getSelectionModel().select(oldValue);
                                }
                                lblError.setText("Bitte verwenden Sie unterschiedliche Konten");
                            } else {
                                lblError.setText("");
                                listSolutions.getSelectionModel().getSelectedItem().setSoll(newValue);
                            }
                        } else {
                            lblError.setText("");
                            listSolutions.getSelectionModel().getSelectedItem().setSoll(newValue);
                        }
                    } else {
                        lblError.setText("");
                        listSolutions.getSelectionModel().getSelectedItem().setSoll(newValue);
                    }
                    listSolutions.refresh();
                }
                manageDisableing();
            }
        });
        comHaben.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
                if (newValue != null) {
                    if (comSoll.getValue() != null) {
                        if (comSoll.getValue().equals(newValue) && !newValue.equals(new Account())) {
                            if (!newValue.equals(new Account())) {
                                if (oldValue.equals(new Account())) {
                                    comHaben.getSelectionModel().clearSelection();
                                } else {
                                    comHaben.getSelectionModel().select(oldValue);
                                }
                                lblError.setText("Bitte verwenden Sie unterschiedliche Konten");
                            } else {
                                lblError.setText("");
                                listSolutions.getSelectionModel().getSelectedItem().setHaben(newValue);
                            }
                        } else {
                            lblError.setText("");
                            listSolutions.getSelectionModel().getSelectedItem().setHaben(newValue);
                        }
                    } else {
                        lblError.setText("");
                        listSolutions.getSelectionModel().getSelectedItem().setHaben(newValue);
                    }
                    listSolutions.refresh();
                }
                manageDisableing();
            }
        });
        txtValue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (listSolutions.getSelectionModel().getSelectedItem() != null) {
                    try {
                        if (!newValue.contains("-") && !newValue.contains("+")) {
                            BigDecimal bd = new BigDecimal(newValue);
                            listSolutions.getSelectionModel().getSelectedItem().setValue(bd);
                            listSolutions.refresh();
                        } else {
                            throw new Exception();
                        }

                    } catch (Exception e) {
                        txtValue.setText(listSolutions.getSelectionModel().getSelectedItem().getValue().toString());
                    }
                }
                manageDisableing();
            }
        });
        // </editor-fold>

    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        // TODO Create our own Confirm Window
        int i = JOptionPane.showConfirmDialog(null, "Bist du sicher, dass du alle Änderungen verwerfen willst?", "Aufgabenerstellung verlassen", JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
        }
    }

    @FXML
    private void addTask(ActionEvent event) {

        listTask.getItems().add(new Task());
        listTask.refresh();

        manageDisableing();
    }

    @FXML
    private void deleteTask(ActionEvent event) {
        Task task = listTask.getSelectionModel().getSelectedItem();
        listTask.getItems().remove(task);

        manageDisableing();
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        Set set = CurrentSet.getInstance().getSet();
        set.setTasklist(listTask.getItems());

        SetDAO.exportSet(new File(Settings.SETS + set.getFilename()), set);

        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.SET);
    }

    @FXML
    private void deleteSolution(ActionEvent event) {
        Solution solution = listSolutions.getSelectionModel().getSelectedItem();
        listSolutions.getItems().remove(solution);

        listTask.getSelectionModel().getSelectedItem().setSolutions(listSolutions.getItems());
        listTask.refresh();

        manageDisableing();
    }

    @FXML
    private void addSolution(ActionEvent event) {
        Solution solution = new Solution();
        listSolutions.getItems().add(solution);
        listTask.getSelectionModel().getSelectedItem().getSolutions().add(solution);

        listTask.refresh();

        manageDisableing();
    }

    private void manageDisableing() {

        // TASKPANE
        taskPane.setDisable(listTask.getSelectionModel().getSelectedItem() == null);
        // BUTTONS
        buttonConfiguration();

    }

    private void buttonConfiguration() {

        // BUTTON btnSolutionAdd
        boolean solutionsAreComplete = true;
        if (listSolutions.getItems().size() > 0) {
            for (Solution item : listSolutions.getItems()) {
                if (!isSolutionComplete(item)) {
                    solutionsAreComplete = false;
                }
            }
        }

        Set cSet = CurrentSet.getInstance().getSet();
        Set set = new Set(cSet.getTitle(), cSet.getCreator(), cSet.getCreatedOn(), listTask.getItems(), cSet.getAccountlist());
        set.setTasklist(listTask.getItems());

        // BUTTON btnSolutionAdd
        btnSolutionAdd.setDisable(!solutionsAreComplete);

        // BUTTON btnSolutionDelete
        btnSolutionDelete.setDisable(listSolutions.getItems().size() < 1 || listSolutions.getSelectionModel().getSelectedItem() == null);

        // BUTTON btnTaskDelete
        btnTaskDelete.setDisable(listTask.getSelectionModel().getSelectedItem() == null);

        // BUTTON btnSave
        btnSave.setDisable(!isSetComplete(set));
    }

    private boolean isSolutionComplete(Solution solution) {
        if (solution == null) {
            return false;
        }
        if (solution.getSoll().equals(new Account())) {
            return false;
        }
        if (solution.getHaben().equals(new Account())) {
            return false;
        }

        return !solution.getValue()
                .equals(new BigDecimal(0));
    }

    private boolean isTaskComplete(Task task) {
        if (task == null) {
            return false;
        }
        if (task.getSolutions().size() < 1) {
            return false;
        }
        if (task.getTask().length() < 1) {
            return false;
        }
        for (Solution solution : task.getSolutions()) {
            if (!isSolutionComplete(solution)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSetComplete(Set set) {
        if (set.getTasklist().size() < 1) {
            return false;
        }
        for (Task task : set.getTasklist()) {
            if (!isTaskComplete(task)) {
                return false;
            }
        }
        return true;
    }

}
