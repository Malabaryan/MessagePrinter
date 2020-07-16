/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Log;
import Model.Logger;
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
    
    public Process getProcess(String ID){
        for(Process p:processes){
            if(p.getId().equals(ID)){
                return p;
            }
        }
        return null;
    }
    
    public void addMailbox(Mailbox mailbox){
        mailboxes.add(mailbox);
        System.out.println("Amount of Mailboxes: " + mailboxes.size());
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
  
    public void executeCommand(String text) {
        String[] commands = text.split("\n");
        Message msg;
        for(String str : commands) {
            String[] subString = str.split("[()]");
            String[] parameters = subString[1].split(",");
            if (subString[0].equals("create")){
                switch(parameters.length){
                    case 1:
                        Process process = new Process(parameters[0]);
                        this.addProcess(process);
                        Logger.getInstance().log(new Log("Proceso Creado id: "+parameters[0]));
                        break;
                    default:
                        Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
                        break;
                }
            } else if (subString[0].equals("send")){
                switch(parameters.length){
                    case 3:
                        //SEND COMMAND send(Destination, Source, Message)
                        msg = new Message(parameters[0], parameters[1], parameters[2]);
                        Process process = getProcess(parameters[0]);
                        process.sendMessage(msg);
                        break;
                    case 4:
                        //SEND COMMAND send(Destination, Source, Message, Priority)
                        msg = new Message(parameters[0], parameters[1], parameters[2]);
                        msg.setPriority(Integer.getInteger(parameters[0]));
                        Process process2 = getProcess(parameters[0]);
                        process2.sendMessage(msg);
                        break;
                    default:
                        Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
                        break;
                }
            } else if (subString[0].equals("receive")){
                switch(parameters.length){
                    case 1:
                        Process process3 = getProcess(parameters[0]);
                        process3.receiveMessage(parameters[0]);
                        break;
                    case 2:
                        Process process4 = getProcess(parameters[1]);
                        process4.receiveMessage(parameters[1]);
                    default:
                        Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
                        break;
                }
                //RECIEVE COMMAND
            } else {
                //INVALID COMMAND
            }
            
        }
    }
    
    public void unlockprocess(String ID){
        for(Process p:processes){
            if(p.getId().equals(ID)){
                p.setState(false);
            }
        }
    }
}
