/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.User;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Zisko
 */
public class UserXml {
    private Element root;
    private String fileLocation = "src//archivos//UserXml.xml";
    
    public UserXml() {
        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = null;
            doc = builder.build(fileLocation);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        }
    }
    
    private Element UsertoXmlElement(User nUser) {
        Element userE = new Element("User");
        
        Element username = new Element("Username");
        username.setText(nUser.getUsername());
        
        Element pass = new Element("Password");
        pass.setText(nUser.getPass());

        userE.addContent(username);
        userE.addContent(pass);
        
        return userE;
    }
    
     private User UserToObject(Element element) throws ParseException {
        User nUser = new User(element.getChildText("Username"), element.getChildText("Password")) { 
       };
       return nUser;
       }
     
    private boolean updateDocument() {
        try {
            XMLOutputter out = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
            FileOutputStream file = new FileOutputStream(fileLocation);
            out.output(root, file);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }
        
    public static Element buscar(List raiz, String username, String password) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (username.equals(e.getChild("Username").getText()) && password.equals(e.getChild("Password").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public User buscarUser(String username, String password) {
        Element aux = new Element("User");
        List Users = this.root.getChildren("User");
        while (aux != null) {
            
            aux = UserXml.buscar(Users, username, password);
            if (aux != null) {
                try {
                    return UserToObject(aux);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }
}
