/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dominio.Chat;
import dominio.User;
import java.util.ArrayList;
import javax.swing.JFrame;
import persistencia.ChatXml;
import persistencia.UserXml;

/**
 *
 * @author Zisko
 */
public class Controlador {
    JFrame jframe;
    UserXml datosuser = new UserXml();
    ChatXml datoschat = new ChatXml();

    public Controlador(JFrame jframe) {
        this.jframe = jframe;
    }
    
    public Boolean login(User user){
        try{
            User users = datosuser.buscarUser(user.getUsername(), user.getPass());
            return users != null;
        } catch (Exception e){
            System.out.println("Error al buscar el usuario. "+e);
            return false;
        }
    }
    
    public void addQuestions(String question, String answer){
        int ultid;
        try{
            ultid = datoschat.buscarId();
            Chat chat = new Chat(ultid+1, question, answer);
            datoschat.agregarChat(chat);
        } catch (Exception ex) {
            System.out.println("Error al agregar pregunta y respuesta. " + ex);
        }    
    }
    
    public String sendQuestion(String question){
        String result = "";
        int count = 1;
        try{
            ArrayList<Chat> answers = datoschat.buscarChat(question);
            int size = answers.size();
            int random = (int)(Math.random()*size+1);
            for (Chat chat : answers){
                if(count == random){
                    result = chat.getAnswer();
                }
                count++;
            }
            return (result.substring(0, 1).toUpperCase() + result.substring(1));
        } catch (Exception e){
            System.out.println("Error en responder. "+ e);
            return "";
        }      
    }
    
}
