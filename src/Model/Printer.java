/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Ui.Window;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Bryan Hernandez
 */
public class Printer {
    
    private String ID;
    private int pCount = 1;
    ArrayList<Process> receiveprocess;
    ArrayList<Message> queue;

    public Printer(String ID) {
        this.ID = ID;
        receiveprocess = new ArrayList();
        queue = new ArrayList();
    }

    public String getID() {
        return ID;
    }
    
    public void addProcessReceive(Process process){
        this.receiveprocess.add(process);
    }
    
    public void addMessage(Message message){
        queue.add(message);
    }
    
    @Override
    public String toString(){
        return this.getID();
    }
    
    public String[][] queueToTable(){
        if (queue.isEmpty()){
            return new String[][] {};
        }
        String[][] contents = new String [queue.size()][2];
        for (int i = 0; i < queue.size(); i++){
            contents[i][0] = ((Message) queue.toArray()[i]).getSourceID();
            contents[i][1] = ((Message) queue.toArray()[i]).getMessageContent();
        }
        return contents;
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
    
    public void print(){
        String fileToSave = "C:\\Printer\\" + ID + "_" + (pCount++) + ".txt";
        if (!queue.isEmpty())
        try {
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(fileToSave));

            Message msg = queue.get(0);
            queue.remove(0);

            writer.write(msg.getMessageContent());

            writer.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
