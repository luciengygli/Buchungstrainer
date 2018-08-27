/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Adrian Wilhelm
 */
public class LoginDAOTest {

    public LoginDAOTest() {
    }

    /**
     * Test of check method, of class LoginDAO.
     */
    @Test
    public void testCheck() {
        Assert.assertTrue(LoginDAO.check("student", "lucien.gygli@stud.bbbaden.ch", "asdfghjkl"));
    }

    /**
     * Test of addUser method, of class LoginDAO.
     */
    @Test
    public void testAddUser() throws IOException {
        RegisterDAO.addUser("student", "lorenz.tschumi@hotmail.com", "asdfghjkl");
        Assert.assertTrue(LoginDAO.check("student", "lucien.gygli@stud.bbbaden.ch", "asdfghjkl"));
    }

}
