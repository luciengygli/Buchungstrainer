package ch.bbbaden.buchungstrainer.controller;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.util.FXMLUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.ListViewMatchers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adrian Wilhelm
 */
public class AccountCreateViewTest extends ApplicationTest {

    private Stage stage;

    private final static String FXMLFILE = "/fxml/AccountCreate.fxml";
    private final static String TITLE = "#lblTitle";

    private final static String CANCELBUTTON = "#btnCancel";
    private final static String ADDBUTTON = "#btnAdd";
    private final static String DELETEBUTTON = "#btnDelete";
    private final static String NEXTBUTTON = "#btnNext";
    private final static String UPDATEBUTTON = "#btnUpdate";

    private final static String ACCOUNTLIST = "#listAccount";

    private final static String IDTXT = "txtID";
    private final static String NAMETXT = "txtName";
    private final static String VALUETXT = "txtValue";

    @Override
    public void start(Stage stage) throws Exception {

        Set set = new Set("Test", "Tester", new Date(1234567891));
        Account accounts[] = {new Account(1000, "Bank"), new Account(3000, "VLL")};
        set.setAccountlist(Arrays.asList(accounts));
        Solution solutions[] = {
            new Solution(accounts[0], accounts[1], BigDecimal.ONE),
            new Solution(accounts[1], accounts[0], BigDecimal.TEN)
        };
        set.addTask(new Task("ASDF", Arrays.asList(solutions)));
        CurrentSet.getInstance().setSet(set);

        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFILE));
        FXMLUtil.changeScene(stage, loader);
    }

    @Test
    public void testInitialization() throws Exception {

        Set set = new Set("TestINIT", "INITTester", new SimpleDateFormat("ddMMyyyy").parse("01012000"));

        Account accounts[] = {new Account(1000, "Bank"), new Account(3000, "VLL")};
        set.setAccountlist(Arrays.asList(accounts));

        Solution solutions[] = {new Solution(accounts[0], accounts[1], BigDecimal.ONE),
            new Solution(accounts[1], accounts[0], BigDecimal.TEN)
        };
        set.addTask(new Task("ASDF", Arrays.asList(solutions)));

        CurrentSet.getInstance().setSet(set);

        start(stage);

        verifyThat(ACCOUNTLIST, ListViewMatchers.hasListCell(accounts[0]));
        verifyThat(ACCOUNTLIST, ListViewMatchers.hasListCell(accounts[0]));
    }

    @Test
    public void testAdd() {
        clickOn(ADDBUTTON);
        verifyThat(ACCOUNTLIST, ListViewMatchers.hasItems(3));
    }

    @Test
    public void testDelete() {
        ListView<Account> listview = lookup(ACCOUNTLIST).query();
        listview.selectionModelProperty().getValue().select(0);
        clickOn(DELETEBUTTON);
        listview.selectionModelProperty().getValue().select(0);
        clickOn(DELETEBUTTON);
        verifyThat(ACCOUNTLIST, ListViewMatchers.isEmpty());
    }

    @Test
    public void testCancel() {
        clickOn(CANCELBUTTON);
        verifyThat(TITLE, NodeMatchers.hasText("Sets"));
    }

    @Test
    public void testSelection() {
        from(lookup(ACCOUNTLIST)).nth(0);
        ListView<Account> listview = lookup(ACCOUNTLIST).query();
        listview.selectionModelProperty().getValue().select(0);
        verifyThat(IDTXT, NodeMatchers.hasText("1000"));
        verifyThat(NAMETXT, NodeMatchers.hasText("Bank"));
        verifyThat(VALUETXT, NodeMatchers.hasText("0"));
    }

    @Test
    public void testUpdate() {
        ListView<Account> listview = lookup(ACCOUNTLIST).query();
        listview.selectionModelProperty().getValue().select(0);

        clickOn(IDTXT).write("4000");
        clickOn(NAMETXT).write("Eigenkapital");
        clickOn(VALUETXT).write("4000");

//        verifyThat(ACCOUNTLIST, ListViewMatchers.hasListCell(new Account(4000, "Eigenkapital", 4000, 4000, "")));
    }

    @Test
    public void testNext() {
        ListView<Account> listview = lookup(ACCOUNTLIST).query();
        listview.selectionModelProperty().getValue().select(0);

        clickOn(IDTXT).write("4000");
        clickOn(NAMETXT).write("Eigenkapital");
        clickOn(VALUETXT).write("4000");

        clickOn(NEXTBUTTON);
    }
}
