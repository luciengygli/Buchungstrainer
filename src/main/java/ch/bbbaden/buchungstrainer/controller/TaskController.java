package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Response;
import ch.bbbaden.buchungstrainer.set.Responses;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class TaskController extends Controller {

    // BASE für Scenen-Wechsel
    @FXML
    private AnchorPane base;
    // TITLE für universelle Title
    @FXML
    private Label lblTitle;

    // CANCEL um die Übung abzubrechen
    @FXML
    private Button btnCancel;
    // NEXT für nächste Aufgabe / Aufgaben beenden
    @FXML
    private Button btnNext;

    // Label zur Beschreibung des Tasks
    @FXML
    private Label lblTask;

    // CONTENTBOX zum hinzufügen von Lösungsfelder
    @FXML
    private VBox contentBox;

    // ERROR um Fehler bei den Buchungen anzuzeigen
    @FXML
    private Label lblError;

    private int tasksFinished = 0;

    private Set set;
    private List<Task> taskList;
    private List<Account> accountlist;

    private Task currentTask;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.set = CurrentSet.getInstance().getSet();

        taskList = set.getTasklist();
        accountlist = set.getAccountlist();

        currentTask = taskList.get(tasksFinished);

        loadTask(currentTask);
    }

    @FXML
    private void next(ActionEvent event) throws IOException {
        createResponses();

        boolean entriesAreValid;
        try {
            entriesAreValid = responsesAreCorrect(CurrentSet.getInstance().getResponses().get(tasksFinished));
        } catch (ArrayIndexOutOfBoundsException ex) {
            entriesAreValid = false;
        }

        if (isFinished() && entriesAreValid) {
            super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.BILANZINCOME);
        } else if (entriesAreValid) {
            deleteBoxes();

            tasksFinished++;
            currentTask = taskList.get(tasksFinished);

            loadTask(currentTask);

        } else {
            lblError.setText("Bitte füllen Sie alle Felder korrekt aus.");
        }
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

    private void loadTask(Task task) {
        lblTitle.setText("Aufgabe " + (tasksFinished + 1));
        lblTask.setText(task.getTask());
        addBox(currentTask);
        lblError.setText("");
    }

    private void addBox(Task task) {

        int index = 0;
        for (Solution solution : task.getSolutions()) {

            HBox hBox = new HBox();
            hBox.setId("response" + index);

            ComboBox<Account> comboboxSoll = new ComboBox<>();
            Label slash = new Label("/");
            ComboBox<Account> comboboxHaben = new ComboBox<>();
            TextField textboxValue = new TextField();

            comboboxSoll.getItems().setAll(accountlist);
            comboboxHaben.getItems().setAll(accountlist);

            hBox.getChildren().add(comboboxSoll);
            hBox.getChildren().add(slash);
            hBox.getChildren().add(comboboxHaben);
            hBox.getChildren().add(textboxValue);

            comboboxSoll.setPromptText("Soll");
            comboboxHaben.setPromptText("Haben");
            textboxValue.setPromptText("0.00");

            comboboxSoll.setPrefWidth(200);
            comboboxHaben.setPrefWidth(200);

            HBox.setMargin(comboboxSoll, new Insets(0, 5, 0, 0));
            HBox.setMargin(slash, new Insets(0, 5, 0, 5));
            HBox.setMargin(comboboxHaben, new Insets(0, 10, 0, 5));
            HBox.setMargin(textboxValue, new Insets(0, 0, 0, 10));

            textboxValue.alignmentProperty().set(Pos.CENTER_RIGHT);
            hBox.alignmentProperty().set(Pos.CENTER);

            contentBox.getChildren().add(hBox);

            VBox.setMargin(hBox, new Insets(20, 0, 0, 0));

            index++;
        }
    }

    public void deleteBoxes() {
        contentBox.getChildren().clear();
    }

    private boolean isFinished() {
        return tasksFinished == taskList.size() - 1;
    }

    private void createResponses() {
        Responses responses = new Responses();

        contentBox.getChildren().forEach((node) -> {
            HBox hBox = (HBox) node;

            ComboBox soll = (ComboBox) hBox.getChildren().get(0);
            ComboBox haben = (ComboBox) hBox.getChildren().get(2);
            TextField value = (TextField) hBox.getChildren().get(3);

            Response r = new Response((Account) soll.getValue(), (Account) haben.getValue(), convert(value));
            responses.getResponses().add(r);

        });

        if (CurrentSet.getInstance().getResponses().size() > tasksFinished) {
            CurrentSet.getInstance().getResponses().remove(tasksFinished);
        }
        CurrentSet.getInstance().getResponses().add(responses);

    }

    private BigDecimal convert(TextField value) {
        BigDecimal bd = new BigDecimal(-1);
        try {
            bd = new BigDecimal(value.getText());
        } catch (NumberFormatException ex) {
        }
        return bd;
    }

    private boolean responsesAreCorrect(Responses responses) {
        return responses.getResponses().stream().noneMatch((r) -> (!responseIsCorrect(r)));
    }

    private boolean responseIsCorrect(Response response) {
        if (response.getSoll() == null) {
            return false;
        }
        if (response.getHaben() == null) {
            return false;
        }
        return response.getAmount().compareTo(new BigDecimal(0)) != -1;
    }

}
