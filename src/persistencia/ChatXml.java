/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Chat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Zisko
 */
public class ChatXml {
    private Element root;
   
    private String fileLocation = "src//archivos//ChatXml.xml";
    
    public ChatXml() {
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
    
    private Element ChattoXmlElement(Chat nChat) {
        Element chatE = new Element("Chat");
        
        Element id = new Element("Id");
        id.setText(Integer.toString(nChat.getId()));
        
        Element question = new Element("Question");
        question.setText(nChat.getQuestion());
        
        Element answer = new Element("Answer");
        answer.setText(nChat.getAnswer());

        chatE.addContent(id);
        chatE.addContent(question);
        chatE.addContent(answer);

        return chatE;
    }
    
     private Chat ChatToObject(Element element) throws ParseException {
        Chat nChat = new Chat(Integer.parseInt(element.getChildText("Id")), element.getChildText("Question"), element.getChildText("Answer")) { 
       };
       return nChat;
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
    
    public void agregarChat(Chat nChat) {
        root.addContent(ChattoXmlElement((Chat) nChat));
        updateDocument();
    }
    
    public ArrayList<Chat> todosLosArchivos(String user) {
        ArrayList<Chat> resultado = new ArrayList<Chat>();     
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                if(user.equals(xmlElem.getChildText("Username"))){
                    resultado.add(ChatToObject(xmlElem));  
                }                                  
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
        
   public static Element buscar(List raiz, String question) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (question.equals(e.getChild("Question").getText())) {
                return e;
            }
        }
        return null;
    }
   
    public ArrayList<Chat> buscarChat(String question) {
        ArrayList<Chat> resultado = new ArrayList<Chat>();     
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                if(question.equals(xmlElem.getChildText("Question"))){
                    resultado.add(ChatToObject(xmlElem));  
                }                                  
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    public int buscarId(){
        int id = 0;
        try {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(fileLocation);
        id = document.getRootElement().getChildren().size();
        return id;
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
            return id;
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
            return id;
        }    
    }
    
}
