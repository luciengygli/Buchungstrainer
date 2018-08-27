/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.set;

import ch.bbbaden.buchungstrainer.set.rw.Account;
import java.math.BigDecimal;
import java.math.BigInteger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jeffrey
 */
public class SolutionTest {

    public SolutionTest() {
    }

    @Test
    public void testGetSoll() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        Solution solution = new Solution(accountSoll, accountHaben, BigDecimal.ONE);
        assertEquals(accountSoll, solution.getSoll());
    }

    @Test
    public void testSetSoll() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        Solution solution = new Solution();
        solution.setSoll(accountSoll);
        assertEquals(accountSoll, solution.getSoll());
    }

    @Test
    public void testGetHaben() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        Solution solution = new Solution(accountSoll, accountHaben, BigDecimal.ONE);
        assertEquals(accountHaben, solution.getHaben());
    }

    @Test
    public void testSetHaben() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        Solution solution = new Solution();
        solution.setHaben(accountHaben);
        assertEquals(accountHaben, solution.getHaben());
    }

    @Test
    public void testGetValue() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        BigDecimal accountValue = new BigDecimal(BigInteger.ONE);
        Solution solution = new Solution(accountSoll, accountHaben, BigDecimal.ONE);
        assertTrue(accountValue.equals(solution.getValue().toString()));
    }

    @Test
    public void testSetValue() {
        Account accountSoll = new Account();
        Account accountHaben = new Account();
        Solution solution = new Solution();
        BigDecimal accountValue = new BigDecimal(BigInteger.ONE);
        solution.setValue(accountValue);
        assertTrue(accountValue.equals(solution.getValue().toString()));
    }
}
