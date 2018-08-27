/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Adrian Wilhelm
 */
public class Task {

    private String task = "";
    private List<Solution> solutions = new ArrayList<>();

    public Task() {
    }

    public Task(String task, List<Solution> solutions) {
        this.task = task;
        this.solutions = solutions;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    @Override
    public String toString() {
        if (task.equals("")) {
            return solutions.size() + " Buchungen, noTask";
        }
        return solutions.size() + " Buchungen, " + task.replace("\n", " ");
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Task other = (Task) obj;
        if (!Objects.equals(this.task, other.task)) {
            return false;
        }
        return Objects.equals(this.solutions, other.solutions);
    }

}
