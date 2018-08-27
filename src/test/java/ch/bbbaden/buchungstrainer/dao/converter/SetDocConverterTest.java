/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao.converter;

import ch.bbbaden.buchungstrainer.set.Set;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;

/**
 *
 * @author Adrian Wilhelm
 */
public class SetDocConverterTest {

    public SetDocConverterTest() {
    }

    /**
     * Test of getSet method, of class SetDocConverter.
     */
    @Test
    public void testGetSet() throws Exception {
    }

    /**
     * Test of getDoc method, of class SetDocConverter.
     */
    @Test
    public void testGetDoc() throws ParseException {
        Set set = new Set("Test", "Tester", new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000"));

    }

}
