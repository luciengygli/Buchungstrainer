/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao.converter;

import ch.bbbaden.buchungstrainer.set.CurrentSet;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.set.Solution;
import ch.bbbaden.buchungstrainer.set.Solutions;
import ch.bbbaden.buchungstrainer.set.Task;
import ch.bbbaden.buchungstrainer.set.rw.Account;
import ch.bbbaden.buchungstrainer.set.rw.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author Adrian Wilhelm
 */
public class SetDocConverter {

    private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Set getSet(Document doc) throws ParseException {

        Element root = doc.getRootElement();

        int code = Integer.parseInt(root.getAttributeValue("code"));

        String title = root.getChild("created").getChildText("title");
        String email = root.getChild("created").getChildText("email");
        Date datum = DATEFORMAT.parse(root.getChild("created").getChildText("datum"));

        Set set = new Set(title, email, datum);
        set.setCode(code);

        // Accountlist
        set.setAccountlist(new ArrayList<Account>());
        if (!root.getChild("accounts").getChildren("account").isEmpty()) {
            for (Element elementA : root.getChild("accounts").getChildren("account")) {
                Account newAccount = new Account();
                newAccount.setId(Integer.parseInt(elementA.getChildText("id")));
                newAccount.setName(elementA.getChildText("name"));
                newAccount.setAb(new BigDecimal(elementA.getChildText("value")));
                newAccount.setValue(newAccount.getAb());
                newAccount.setType(Type.valueOf(elementA.getChildText("type")));
                newAccount.setIsMinusAccount(elementA.getChildText("isMinusAccount").equals("true"));
                set.getAccountlist().add(newAccount);
            }
        }

        // Tasklist
        set.setTasklist(new ArrayList<Task>());
        Solutions solutions = new Solutions();
        if (!root.getChild("tasks").getChildren("task").isEmpty()) {
            for (Element element : root.getChild("tasks").getChildren("task")) {
                Task task = new Task();
                task.setTask(element.getChildText("problem"));

                // Solutions for each task
                for (Element elementS : element.getChild("solutions").getChildren("solution")) {
                    Solution solution = new Solution();
                    solution.setSoll(set.getAccount(elementS.getChildText("soll")));
                    solution.setHaben(set.getAccount(elementS.getChildText("haben")));
                    solution.setValue(new BigDecimal(elementS.getChildText("value")));

                    task.getSolutions().add(solution);
                    solutions.getSolutions().add(solution);
                }
                set.addTask(task);
            }
        }

        CurrentSet.getInstance().getSolutions().add(solutions);

        return set;
    }

    public static Document getDoc(Set set) {
        Document doc = new Document(new Element("set"));
        Element root = doc.getRootElement();

        root.setAttribute("code", String.valueOf(set.getCode()));

        root.addContent(new Element("created"));
        root.addContent(new Element("tasks"));
        root.addContent(new Element("accounts"));

        // Created
        root.getChild("created").addContent(new Element("title").setText(set.getTitle()));
        root.getChild("created").addContent(new Element("email").setText(set.getCreator()));
        root.getChild("created").addContent(new Element("datum").setText(DATEFORMAT.format(set.getCreatedOn())));

        // Tasks
        if (!set.getTasklist().isEmpty()) {
            for (Task task : set.getTasklist()) {

                Element taskElement = new Element("task");
                taskElement.addContent(new Element("problem").setText(task.getTask()));
                taskElement.addContent(new Element("solutions"));

                // Solutions
                task.getSolutions().stream().map((solution) -> {

                    Element solutionElement = new Element("solution");
                    solutionElement.addContent(new Element("soll").setText(solution.getSoll().getName()));
                    solutionElement.addContent(new Element("haben").setText(solution.getHaben().getName()));
                    solutionElement.addContent(new Element("value").setText(solution.getValue().toString()));

                    return solutionElement;
                }).forEachOrdered((solutionElement) -> {
                    taskElement.getChild("solutions").addContent(solutionElement);
                });
                root.getChild("tasks").addContent(taskElement);
            }
        }

        // Accountlist
        if (!set.getAccountlist().isEmpty()) {
            for (Account account : set.getAccountlist()) {
                Element accountElement = new Element("account");
                accountElement.addContent(new Element("id").setText("" + account.getId()));
                accountElement.addContent(new Element("name").setText(account.getName()));
                accountElement.addContent(new Element("value").setText(account.getAb().toString()));
                accountElement.addContent(new Element("type").setText(account.getType().name()));
                accountElement.addContent(new Element("isMinusAccount").setText("" + account.isMinusAccount()));

                root.getChild("accounts").addContent(accountElement);
            }
        }

        return doc;
    }

}
