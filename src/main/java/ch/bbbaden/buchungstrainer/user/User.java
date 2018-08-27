/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.user;

import static ch.bbbaden.buchungstrainer.util.Settings.SETTINGS;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian Wilhelm
 */
public class User {

    private static final User USER = new User();

    private String mode = "noMode";
    private String email = "noEmail";

    private User() {
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User getInstance() {
        return USER;
    }

    public static void initializeUsers(File file) throws IOException {

        List<String> settings = new ArrayList<>();
        settings.add("lastteacherlogin=\n");
        settings.add("laststudentlogin=\n");

        Files.write(Paths.get(SETTINGS), settings, StandardCharsets.UTF_8);
    }

}
