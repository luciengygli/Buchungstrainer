/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao;

import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Adrian Wilhelm
 */
public class LoginDAO {

    public static boolean check(String usermode, String email, String password) {

        List<Element> list = getUserDoc().getRootElement().getChild(usermode + "s").getChildren("user");
        // Für jeden User in der Liste
        for (Element userElement : list) {

            // Wenn die Email einer Email in der Liste entspricht ...
            if (userElement.getChildText("email").equals(email)) {
                // ... und das Passwort übereinstimmt

                if (checkPassword(password, userElement.getChildText("password"))) {
                    // Wird zurückgegen, dass der User gefunden wurde
                    return true;
                }
            }
        }

        return false;
    }

    private static Document getUserDoc() {
        try {
            String path = Settings.USERS + "userdata.xml";
            return new SAXBuilder().build(path);

        } catch (JDOMException ex) {
            System.err.println("JDOM Exception");
        } catch (IOException ex) {
            System.err.println("ERROR 404: File not found");
        }
        return null;
    }

    private static boolean checkPassword(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }

}
