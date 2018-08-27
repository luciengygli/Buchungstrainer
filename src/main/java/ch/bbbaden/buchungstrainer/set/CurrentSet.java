/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import ch.bbbaden.buchungstrainer.set.rw.Account;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian Wilhelm
 */
public class CurrentSet {

    private static final CurrentSet CURRENTSET = new CurrentSet();
    private Set set;
    private List<Task> answers = new ArrayList<>();
    private List<Response> response = new ArrayList<>();
    private List<Responses> responses = new ArrayList<>();
    private List<Solution> solution = new ArrayList<>();
    private List<Solutions> solutions = new ArrayList<>();
    private List<Account> bilanceAndIncomeAnswers = new ArrayList<>();

    public CurrentSet(Set set) {
        this.set = set;
    }

    private CurrentSet() {
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public Set getSet() {
        return set;
    }

    public List<Task> getAnswers() {
        return answers;
    }

    public List<Response> getResponse() {
        return response;
    }

    public List<Responses> getResponses() {
        return responses;
    }

    public List<Solution> getSolution() {
        return solution;
    }

    public List<Solutions> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solutions> solutions) {
        this.solutions = solutions;
    }

    public List<Account> getBilanceAndIncomeAnswers() {
        return bilanceAndIncomeAnswers;
    }

    public void setBilanceAndIncomeAnswers(List<Account> bilanceAndIncomeAnswers) {
        this.bilanceAndIncomeAnswers = bilanceAndIncomeAnswers;
    }

    public static CurrentSet getInstance() {
        return CURRENTSET;
    }
}
