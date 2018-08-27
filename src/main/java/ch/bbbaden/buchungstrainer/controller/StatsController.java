/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Response;
import ch.bbbaden.buchungstrainer.set.Responses;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lucien Gygli
 */
public class StatsController extends Controller {

    @FXML
    private AnchorPane base;
    @FXML
    private ListView<Task> listTasks;
    @FXML
    private Label lblNr;
    @FXML
    private Label lblFrage;
    @FXML
    private Button btnAbschliessen;
    @FXML
    private VBox boxResponse;
    @FXML
    private VBox boxSolution;

    private List<Task> tasks;
    private List<Responses> responses;
    private List<Solution> solutions;
    private List<VBox> boxesR;
    private List<VBox> boxesS;

    private Set set;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        set = CurrentSet.getInstance().getSet();
        tasks = set.getTasklist();
        responses = CurrentSet.getInstance().getResponses();
        boxesR = new ArrayList<>();
        boxesS = new ArrayList<>();

        ObservableList list = FXCollections.observableArrayList(tasks);
        listTasks.setItems(list);
        listTasks.refresh();

        addResponseLabel(null);
        addSolutionLabel(null);

        listTasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {

                deleteBookings();

                if (newValue == null) {
                    lblNr.setText("Keine Auswahl");
                    lblFrage.setText("Keine Auswahl");

                    addResponseLabel(null);
                    addSolutionLabel(null);

                } else {
                    solutions = newValue.getSolutions();

                    lblNr.setText(listTasks.getItems().indexOf(newValue) + 1 + "");
                    lblFrage.setText(newValue.getTask());

                    addBookings(newValue);
                }
            }
        });
    }

    @FXML
    public void finish() throws IOException {
        responses.clear();
        super.changeScene((Stage) base.getScene().getWindow(), FXMLUtil.STATSBAI);
    }

    public void addBookings(Task newValue) {

        Responses rs = responses.get(listTasks.getItems().indexOf(newValue));

        rs.getResponses().forEach((response) -> {

            Label r = addResponseLabel(response);
            Label s = addSolutionLabel(solutions.get(rs.getResponses().indexOf(response)));

            check(r, s);
        });

    }

    private Label addResponseLabel(Response response) {

        Label r = new Label();

        if (response != null) {
            r.setText(response.getSoll().toString().concat(" / ").concat(response.getHaben().toString().concat(" ").concat(response.getAmount().toString())));
        } else {
            r.setText("Keine Auswahl");
        }

        boxResponse.getChildren().add(r);
        VBox.setMargin(r, new Insets(10, 0, 0, 0));
        r.setFont(Font.font("System", 18));

        if (response != null) {
            boxesR.add(boxResponse);
        }

        return r;
    }

    private Label addSolutionLabel(Solution solution) {

        Label s = new Label();

        if (solution != null) {
            s.setText(solution.toString());
        } else {
            s.setText("Keine Auswahl");
        }

        boxSolution.getChildren().add(s);
        VBox.setMargin(s, new Insets(10, 0, 0, 0));
        s.setFont(Font.font("System", 18));

        if (solution != null) {
            boxesS.add(boxSolution);
        }

        return s;
    }

    public void deleteBookings() {
        boxSolution.getChildren().clear();
        boxResponse.getChildren().clear();
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
