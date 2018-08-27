/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.util;

import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Adrian Wilhelm
 */
public class SettingsTest {

    public SettingsTest() {
    }

    /**
     * Test of createApplicationSettings method, of class Settings.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateApplicationSettings() throws Exception {
        Settings.createApplicationSettings();
        assertTrue("File exists", new File(Settings.SETTINGS).exists());
    }

    /**
     * Test of setSetting method, of class Settings.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testSetSetting() throws Exception {
        Settings.setSetting("lastLogin", "jeffrey.smith@stud.bbbaden.ch");
        assertEquals("jeffrey.smith@stud.bbbaden.ch", Settings.getSetting("lastLogin"));
    }

    /**
     * Test of getSetting method, of class Settings.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetSetting() throws Exception {
        Settings.setSetting("lastLogin", "lucien.gygli@stud.bbbaden.ch");
        assertEquals("lucien.gygli@stud.bbbaden.ch", Settings.getSetting("lastLogin"));
    }

    /**
     * Test of initializeSettings method, of class Settings.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testInitializeSettings() throws Exception {
        // TODO
    }

    /**
     * Test of importSet method, of class Settings.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testImportSet() throws Exception {
        // TODO
    }

    /**
     * Test of getSets method, of class Settings.
     */
    @Test
    public void testGetSets() {
        // TODO
    }

}
