/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set.rw;

import java.util.Comparator;

/**
 *
 * @author Adrian Wilhelm
 */
public class AccountIDComparator implements Comparator<Account> {

    @Override
    public int compare(Account o1, Account o2) {
        if (o1.getId() > o2.getId()) {
            return 1;
        } else if (o1.getId() < o2.getId()) {
            return -1;
        }
        return 0;
    }

}
