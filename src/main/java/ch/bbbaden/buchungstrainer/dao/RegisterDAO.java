/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.buchungstrainer.dao;

import ch.bbbaden.buchungstrainer.util.Settings;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Adrian Wilhelm
 */
public class RegisterDAO {

    public static void addUser(String usermode, String email, String password) throws IOException {

        Element newUser = new Element("user");
        newUser.addContent(new Element("email").setText(email));
        newUser.addContent(new Element("password").setText(hash(password)));

        Document doc = getUserDoc();
        doc.getRootElement().getChild(usermode + "s").addContent(newUser);

        output(doc);

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

    private static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());

    }

    public static void generateUserdataFile() throws IOException {
        Document doc = new Document(new Element("roles"));
        doc.getRootElement().addContent(new Element("students"));
        doc.getRootElement().addContent(new Element("teachers"));
        output(doc);
    }

    private static void output(Document doc) throws IOException {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        String path = Settings.USERS + "userdata.xml";
        File file = new File(path);
        file.createNewFile();
        out.output(doc, new FileWriter(file));
    }

}
