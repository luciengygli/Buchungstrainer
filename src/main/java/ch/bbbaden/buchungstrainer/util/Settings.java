/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.util;

import ch.bbbaden.buchungstrainer.dao.RegisterDAO;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Adrian Wilhelm
 */
public class Settings {

    public static final String SETTINGS = System.getenv("APPDATA") + "\\.buchungstrainer\\settings.txt";
    public static final String CACHE = System.getenv("APPDATA") + "\\.buchungstrainer\\cache\\";
    public static final String SETS = System.getenv("APPDATA") + "\\.buchungstrainer\\sets\\";
    public static final String USERS = System.getenv("APPDATA") + "\\.buchungstrainer\\users\\";

    public static void createApplicationSettings() throws IOException {

        File file = new File(SETTINGS);
        if (!file.exists()) {
            System.out.println("Application settings are not existing");

            System.out.println("Create application settings directory");
            new File(System.getenv("APPDATA") + "\\.buchungstrainer").mkdir();

            System.out.println("Create application settings file: " + SETTINGS);
            file.createNewFile();
            System.out.println("Initialize the settings file settings");
            initializeSettings(file);
        }

        file = new File(USERS + "userdata.xml");
        if (!file.exists()) {
            System.out.println("Userdata is not existing");

            System.out.println("Create userdata directory");
            new File(USERS).mkdir();

            System.out.println("Create userdata file: " + USERS + "userdata.xml");
            RegisterDAO.generateUserdataFile();
        }

        new File(SETS).mkdir();

    }

    public static List<File> getSets() {
        return Arrays.asList(new File(SETS).listFiles());
    }

    public static void setSetting(String settingName, String setting) throws IOException {
        List<String> newLines = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(SETTINGS), StandardCharsets.UTF_8)) {
            if (line.contains(settingName)) {
                if (line.substring(line.indexOf("=")).length() <= 2) {
                    newLines.add(line + setting);
                } else {
                    newLines.add(line.replace(line.substring(line.indexOf("=") + 1), setting));
                }
            } else {
                newLines.add(line);
            }
        }
        Files.write(Paths.get(SETTINGS), newLines, StandardCharsets.UTF_8);

    }

    public static String getSetting(String settingName) {

        try {
            for (String line : Files.readAllLines(Paths.get(SETTINGS), StandardCharsets.UTF_8)) {
                if (line.contains(settingName)) {
                    return line.substring(line.indexOf("=") + 1);
                }
            }
        } catch (IOException ex) {
            System.err.println("Problem with the settings file. \nRecreate the file...");
            File file = new File(SETTINGS);
            try {
                file.createNewFile();
                initializeSettings(file);
            } catch (IOException ex1) {
                System.err.println("Recreating file failed");
            }
        }
        return "";
    }

    public static void initializeSettings(File file) throws IOException {

        List<String> settings = new ArrayList<>();
        settings.add("lastteacherlogin=\n");
        settings.add("laststudentlogin=\n");

        Files.write(Paths.get(SETTINGS), settings, StandardCharsets.UTF_8);
    }

}
