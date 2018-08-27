/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import ch.bbbaden.buchungstrainer.set.rw.Account;
import java.math.BigDecimal;

/**
 *
 * @author Lucien Gygli
 */
public class Response {

    private Account soll;
    private Account haben;
    private BigDecimal amount;

    public Response(Account soll, Account haben, BigDecimal amount) {
        this.soll = soll;
        this.haben = haben;
        this.amount = amount;
    }

    public Account getSoll() {
        return soll;
    }

    public Account getHaben() {
        return haben;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
