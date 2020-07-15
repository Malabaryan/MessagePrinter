/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author ariel
 */
public class Mailbox {
    String id;
    Queue<Message> queue;
    ArrayList<Process> receiveprocess;

    public Mailbox(String id) {
        this.id = id;
        this.queue = new LinkedList<Message>();
        this.receiveprocess = new ArrayList<Process>();
    }
    
    public void addProcessReceive(Process process){
        this.receiveprocess.add(process);
        System.out.print(receiveprocess.size());
    }

    public String getId() {
        return id;
    }

    public Queue<Message> getQueue() {
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
        Message messagereturn = queue.poll();
        System.out.print("Tamaño cola: "+queue.size());
        return messagereturn;
    }
    
}
