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
    }
    
    public boolean processBelongs(String ID){
        for(Process p:receiveprocess){
            System.out.print(p.getId()+",");
            if(p.getId().equals(ID)){
                return true;
            }
        }
        return false;
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
        return nextMessage;
    }
    
    @Override
    public String toString(){
        return id;
    }
    
    public boolean findWaiting(){
        for(Process p:receiveprocess){
            if(p.getWaiting()){
                return true;
            }
        }
        return false;
    }
    
    public String findWaitingProcess(){
        for(Process p:receiveprocess){
            if(p.getWaiting()==true){
                return p.getId();
            }
        }
        return null;
    }
    
    public int getSize(){
        return queue.size();
    }
    
    public String getMessages(){
        String messages2print = "";
        for(Message m: queue){
            messages2print = messages2print 
                    + "[C: " + m.getMessageContent() 
                    + "][S: " + m.getSourceID() 
                    + "][D: " + m.getDestinationID()
                    + "][P: " + m.getPriority()
                    + "]\n";
        }
        return messages2print;
    }
    
}
