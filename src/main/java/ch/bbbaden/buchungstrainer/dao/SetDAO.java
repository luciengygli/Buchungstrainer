/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao;

import ch.bbbaden.buchungstrainer.dao.converter.SetDocConverter;
import ch.bbbaden.buchungstrainer.set.Set;
import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Adrian Wilhelm
 */
public class SetDAO {

    private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Set importSet(File file) throws IOException, JDOMException, ParseException {

        String newFilename = Settings.SETS + file.getName();
        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(newFilename), StandardCopyOption.REPLACE_EXISTING);

        SAXBuilder sax = new SAXBuilder();
        Document doc;

        try (FileReader reader = new FileReader(file)) {
            doc = sax.build(reader);
        }

        return SetDocConverter.getSet(doc);
    }

    public static void exportSet(File file, Set set) throws IOException {
        file.createNewFile();

        XMLOutputter out = new XMLOutputter();
        out.setFormat(Format.getPrettyFormat());

        FileWriter fw = new FileWriter(file);
        out.output(SetDocConverter.getDoc(set), fw);

        fw.close();
    }

    public static List<Set> getSets() {
        List<Set> sets = new ArrayList<>();
        for (File file : Settings.getSets()) {

            try {
                sets.add(SetDocConverter.getSet(new SAXBuilder().build(file)));
            } catch (ParseException | JDOMException | IOException | NullPointerException ex) {
                System.err.println("File could not be converted to set: " + file.getAbsolutePath());
            }
        }
        return sets;
    }

    public static Set reloadSet(Set set1) {
        for (Set set : getSets()) {
            if (set.equals(set1)) {
                return set;
            }
        }
        return null;
    }

    public static boolean setIsSaved(Set set) {
        for (Set set1 : getSets()) {
            if (set.equals(set1)) {
                return true;
            }
        }
        return false;
    }

    public static void cacheSet(Set set) throws IOException {
        Files.move(Paths.get(Settings.SETS + set.getFilename()), Paths.get(Settings.CACHE + set.getFilename()), StandardCopyOption.REPLACE_EXISTING);
    }

}
