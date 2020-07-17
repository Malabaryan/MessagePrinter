/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Bryan Hernandez
 */
public class Printer {
    
    private String ID;
    ArrayList<Process> receiveprocess;
    ArrayList<Message> queue;

    public Printer(String ID) {
        this.ID = ID;
        receiveprocess = new ArrayList();
    }

    public String getID() {
        return ID;
    }
    
    public void addProcessReceive(Process process){
        this.receiveprocess.add(process);
        System.out.print(receiveprocess.size());
    }
    
    public void addMessage(Message message){
        queue.add(message);
        System.out.print(queue.size());
    }
    
    @Override
    public String toString(){
        return this.getID();
    }
    
    public String getMessages(){
        String messages2print = "";
        for(Message m: queue){
            messages2print = messages2print 
                    + "C: " + m.getMessageContent() 
                    + ",S: " + m.getSourceID() 
                    + ",D: " + m.getDestinationID()
                    + "\n";
        }
        return messages2print;
    }
}
