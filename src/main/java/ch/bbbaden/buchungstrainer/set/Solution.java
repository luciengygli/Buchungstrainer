/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import ch.bbbaden.buchungstrainer.set.rw.Account;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Adrian Wilhelm
 */
public class Solution {

    private Account soll = new Account();
    private Account haben = new Account();
    private BigDecimal value = new BigDecimal(0);

    public Solution() {
    }

    public Solution(Account soll, Account haben, BigDecimal value) {
        this.soll = soll;
        this.haben = haben;
        this.value = value;
    }

    public Account getSoll() {
        return soll;
    }

    public void setSoll(Account soll) {
        this.soll = soll;
    }

    public Account getHaben() {
        return haben;
    }

    public void setHaben(Account haben) {
        this.haben = haben;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setSolution(Solution solution) {
        this.soll = solution.soll;
        this.haben = solution.haben;
        this.value = solution.value;
    }

    @Override
    public String toString() {
        return soll.toString().concat(" / ").concat(haben.toString()).concat(" ").concat(value.toString());
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
        final Solution other = (Solution) obj;
        if (!Objects.equals(this.soll, other.soll)) {
            return false;
        }
        if (!Objects.equals(this.haben, other.haben)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

}
