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
import Model.Printer;
/**
 *
 * @author Bryan Hernandez
 */
public class MainController {

    
    

    static MainController maincontroller;
    
    static ArrayList<Process> processes;
    static ArrayList<Mailbox> mailboxes;
    static ArrayList<Printer> printers;
    
    static ArrayList<Message> messagespostsend;


    private MainController() {
        processes = new ArrayList<Process>();
        mailboxes = new ArrayList<Mailbox>();
        printers = new ArrayList<Printer>();
        
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

    public ArrayList<Printer> getPrinters() {
        return printers;
    }
    
    public void printAll(){
        for (Printer printer : printers){
            printer.print();
        }
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
    
    public Printer getPrinter(String ID){
        for(Printer m: printers){
            if(m.getID().equals(ID)){
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
    }
    
    public void addPrinter(Printer printer){
        printers.add(printer);
    }
    
    public void addProcess(Process process){
        processes.add(process);
    }
    
    public void sendMessageDirect(Message message){
        messagespostsend.add(message);
    }
    
    public boolean sendMessageIndirect(Message message){
        Mailbox mailbox = getMailbox(message.getDestinationID());
        if(mailbox.getSize()>=ParametersController.getQueueSize()){
            Logger logger = Logger.getInstance();
            logger.log(new Log("El tamaño de la cola llego al limite", Log.Type.ERROR,mailbox.getId()));
            return false;
        }
        else{
            mailbox.addMessage(message);
            return true;
        }
    }
    
    public Message receiveMessageDirectExplicit(String IDS, String IDD){
        for(int i=0;i<messagespostsend.size();i++){
            if(messagespostsend.get(i).getSourceID().equals(IDS) && messagespostsend.get(i).getDestinationID().equals(IDD)){
                Message message = messagespostsend.get(i);
                messagespostsend.remove(i);
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
                return message;
            }
        }
        return null;
    }
    
    public Message receiveMessageIndirect(String ID){
        Mailbox mailbox = getMailbox(ID);
        return mailbox.nextMessage();
    }

    public ArrayList<Message> getMessagespostsend() {
        return messagespostsend;
    }
  
    public void executeCommand(String text) {
        String[] commands = text.split("\n");
        Message msg;
        for(String str : commands) {
            if (commands.length < 1) {
                Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
                break;
            }
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
                        msg = new Message(parameters[1], parameters[0], parameters[2]);
                        Process process = getProcess(parameters[0]);
                        process.sendMessage(msg);
                        break;
                    case 4:
                        //SEND COMMAND send(Destination, Source, Message, Priority)
                        msg = new Message(parameters[1], parameters[0], parameters[2]);
                        msg.setPriority(Integer.parseInt(parameters[3]));
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
                        Process process4 = getProcess(parameters[0]);
                        process4.receiveMessage(parameters[1]);
                        break;
                    default:
                        Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
                        break;
                }
            } else {
                Logger.getInstance().log(new Log("Comando no valido: " + str, Log.Type.ERROR));
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
