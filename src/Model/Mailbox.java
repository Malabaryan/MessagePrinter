/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ParameterState;
import Controller.ParametersController;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ariel
 */
public class Mailbox {
    String id;
    ArrayList<Message> queue;
    ArrayList<Process> receiveprocess;

    public Mailbox(String id) {
        this.id = id;
        this.queue = new ArrayList<Message>();
        this.receiveprocess = new ArrayList<Process>();
    }
    
    public void addProcessReceive(Process process){
        this.receiveprocess.add(process);
        System.out.print(receiveprocess.size());
    }

    public String getId() {
        return id;
    }

    public ArrayList<Message> getQueue() {
        return queue;
    }

    public ArrayList<Process> getReceiveprocess() {
        return receiveprocess;
    }
    
    public void addMessage(Message message){
        queue.add(message);
        System.out.print(queue.size());
    }
    
    public Message nextMessage(){
        Message nextMessage = null;
        for (Message msg : queue){
            if (ParametersController.getQueueStrategy() == ParameterState.Queue_Priority){
                if (nextMessage == null || nextMessage.getPriority() < msg.getPriority()){
                    nextMessage = msg;
                }
            } else {
                nextMessage = msg;
                break;
            }
        }
        if (nextMessage != null){
            queue.remove(nextMessage);
        }
        System.out.print("Tamaño cola: " + queue.size());
        return nextMessage;
    }
    
    @Override
    public String toString(){
        return id;
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
