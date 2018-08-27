/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.user;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Adrian Wilhelm
 */
public class UserTest {

    private final User user = User.getInstance();

    public UserTest() {
    }

    /**
     * Test of getMode method, of class User.
     */
    @Test
    public void testGetMode() {
        assertEquals(user.getMode(), "noMode");
    }

    /**
     * Test of setMode method, of class User.
     */
    @Test
    public void testSetMode() {
        user.setMode("teacher");
        assertEquals(user.getMode(), "teacher");
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        assertEquals(user.getEmail(), "noEmail");
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        user.setEmail("adrian.wilhelm@stud.bbbaden.ch");
        assertEquals(user.getEmail(), "adrian.wilhelm@stud.bbbaden.ch");
    }

    /**
     * Test of getInstance method, of class User.
     */
    @Test
    public void testIsInstanceOf() {
        assertTrue(User.getInstance() instanceof User);
    }

    /**
     * Test of getInstance method, of class User.
     */
    @Test
    public void testGetInstance() {
        assertEquals(User.getInstance(), User.getInstance());
    }

}
