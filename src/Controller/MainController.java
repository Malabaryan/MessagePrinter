/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import Model.Process;
import Model.Mailbox;
import Model.Message;
/**
 *
 * @author Bryan Hernandez
 */
public class MainController {

    
    

    static MainController maincontroller;
    static ArrayList<Process> processes;
    static ArrayList<Mailbox> mailboxes;
    static ArrayList<Message> messagespostsend;

    public MainController() {
        processes = new ArrayList<Process>();
        mailboxes = new ArrayList<Mailbox>();
        messagespostsend = new ArrayList<Message>();
    }
    
    public static MainController getInstance() {
        if(maincontroller == null){
            maincontroller = new MainController(); 
        }
        return maincontroller;
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    public ArrayList<Mailbox> getMailboxes() {
        return mailboxes;
    }
    
    public Mailbox getMailbox(String ID){
        for(Mailbox m:mailboxes){
            if(m.getId().equals(ID)){
                return m;
            }
        }
        return null;
    }
    
    public void addMailbox(Mailbox mailbox){
        mailboxes.add(mailbox);
        System.out.print(mailboxes.size()+ "\n");
    }
    
    public void addProcess(Process process){
        processes.add(process);
    }
    
    public void sendMessageDirect(Message message){
        messagespostsend.add(message);
        System.out.print(messagespostsend.size()+ "\n");
    }
    
    public void sendMessageIndirect(Message message){
        Mailbox mailbox = getMailbox(message.getDestinationID());
        mailbox.addMessage(message);
    }
    
    public Message receiveMessageDirectExplicit(String ID){
        for(int i=0;i<messagespostsend.size();i++){
            if(messagespostsend.get(i).getSourceID().equals(ID)){
                Message message = messagespostsend.get(i);
                messagespostsend.remove(i);
                System.out.print(messagespostsend.size() + "\n");
                return message;
            }
        }
        return null;
    }
    
    public Message receiveMessageDirectImplicit(String ID){
        for(int i=0;i<messagespostsend.size();i++){
            if(messagespostsend.get(i).getDestinationID().equals(ID)){
                Message message = messagespostsend.get(i);
                messagespostsend.remove(i);
                System.out.print(messagespostsend.size() + "\n");
                return message;
            }
        }
        return null;
    }
    
    public Message receiveMessageIndirect(String ID){
        Mailbox mailbox = getMailbox(ID);
        System.out.print(" Va por aca \n");
        return mailbox.nextMessage();
    }
    
    public void unlockprocess(String ID){
        for(Process p:processes){
            if(p.getId().equals(ID)){
                p.setState(false);
            }
        }
    }
}
