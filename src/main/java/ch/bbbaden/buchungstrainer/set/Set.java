/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import ch.bbbaden.buchungstrainer.set.rw.Account;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Adrian Wilhelm
 */
public class Set {

    private int code = System.identityHashCode(this);
    private Date createdOn = new Date();
    private String creator = "NoCreator";
    private String title = "NoTitle";
    private List<Task> tasklist = new ArrayList<>();
    private List<Account> accountlist = new ArrayList<>();

    public Set(String title, String creator, Date createdOn, List<Task> tasklist, List<Account> acountlist) {
        this.createdOn = createdOn;
        this.creator = creator;
        this.title = title;
        this.tasklist = tasklist;
        this.accountlist = acountlist;
    }

    public Set(String title, String creator, Date createdOn, List<Task> tasklist) {
        this.createdOn = createdOn;
        this.creator = creator;
        this.title = title;
        this.tasklist = tasklist;
    }

    public Set(String title, String creator, Date createdOn) {
        this.createdOn = createdOn;
        this.creator = creator;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public String getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasklist() {
        return tasklist;
    }

    public void setTasklist(List<Task> tasklist) {
        this.tasklist = tasklist;
    }

    public void addTask(Task task) {
        this.tasklist.add(task);
    }

    public void removeTask(Task task) {
        this.tasklist.remove(task);
    }

    public int getIndex(Task t) {
        int i = 0;
        for (Task task : tasklist) {
            if (task.equals(t)) {
                return i;
            }
            i += 1;
        }
        return -1;
    }

    public List<Account> getAccountlist() {
        return accountlist;
    }

    public void setAccountlist(List<Account> accountlist) {
        this.accountlist = accountlist;
    }

    public Account getAccount(String accountname) {
        for (Account account : accountlist) {
            if (account.getName().equals(accountname)) {
                return account;
            }
        }
        return null;
    }

    public String getFilename() {
        return new SimpleDateFormat("ddMMyyyy").format(getCreatedOn()) + "_" + String.valueOf(code) + "_"
                + creator.substring(0, creator.indexOf("@")).replace('.', '_') + "_"
                + title.toLowerCase()
                + ".set";
    }

    @Override
    public String toString() {
        return title + ": "
                + (tasklist.size()) + " Aufgaben zum lösen,"
                + " erstellt am " + new SimpleDateFormat("dd. MMMM YYYY").format(createdOn)
                + " von " + creator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.code;
        hash = 79 * hash + Objects.hashCode(this.createdOn);
        hash = 79 * hash + Objects.hashCode(this.creator);
        hash = 79 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Set other = (Set) obj;
        if (this.code != other.code) {
            return false;
        }
        if (!Objects.equals(this.creator, other.creator)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.createdOn, other.createdOn)) {
            return false;
        }
        return true;
    }

}
