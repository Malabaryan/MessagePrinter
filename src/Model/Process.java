/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.MainController;
import Controller.ParameterState;
import Controller.ParametersController;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class Process {
    public String id;
    public Boolean state;
    //public ArrayList<Message> messagessends;
    //public ArrayList<Message> messagesreceives;

    public Process(String id) {
        this.id = id;
        this.state = false;
    }
    
    public void sendMessage_Aux(){
        if(this.state == true){
            System.out.print("El proceso esta bloqueado");
        }
        else{
            ParameterState sync = ParametersController.getSyncronization_Send();
            if(sync == ParameterState.Sync_Send_Blocking){
                state = true;
                System.out.print("El proceso se ha bloqueado");
            }
            else{
                state = false;
                System.out.print("El proceso no se ha bloqueado");
            }
        }
    }
    
    public void sendMessage(Message message){
        message.setSourceID(this.id);
        ParameterState addressing = ParametersController.getAddressing_Send();
        if(addressing == ParameterState.Addr_Direct_Send){
            MainController.getInstance().sendMessageDirect(message);
            System.out.print("Mensaje enviado");
            sendMessage_Aux();
        }
        else if(addressing == ParameterState.Addr_Indirect_Static || addressing == ParameterState.Addr_Indirect_Dynamic){
            
            MainController.getInstance().sendMessageIndirect(message);
            System.out.print("Mensaje enviado");
            sendMessage_Aux();
        }
    }
    
    public Message receiveMessage_Aux(String ID){
        ParameterState addressing = ParametersController.getAddressing_Receive();
        System.out.print(ParametersController.getAddressing_Receive().toString()+"  ");
        Message messagereturn = null;
        if(addressing == ParameterState.Addr_Direct_Receive_Explicit){
            messagereturn = MainController.getInstance().receiveMessageDirectExplicit(ID);
        }
        else if(addressing == ParameterState.Addr_Direct_Receive_Implicit){
            messagereturn = MainController.getInstance().receiveMessageDirectImplicit(this.id);
        }
        else if(addressing == ParameterState.Addr_Indirect_Static || addressing == ParameterState.Addr_Indirect_Dynamic){
            messagereturn = MainController.getInstance().receiveMessageIndirect(ID);
        }
        return messagereturn;
    }
    
    public void receiveMessage(String ID){
        if(this.state == true){
            System.out.print("El proceso esta bloqueado");
        }
        else{
            ParameterState sync = ParametersController.getSyncronization_Receive();
            if(sync == ParameterState.Sync_Receive_Blocking){
                state = true;
                System.out.print(" El proceso se ha bloqueado recibiendo ");
                Message messagereturn = receiveMessage_Aux(ID);
                if(messagereturn==null){
                    this.state = true;
                    System.out.print("El proceso se quedo bloqueado recibiendo");
                }
                else{
                    this.state = false;
                    System.out.print("El proceso se ha desbloqueado recibiendo");
                }
            }
            else if(sync == ParameterState.Sync_Receive_ProofOfArrival){
                boolean a = true;
                while(a){
                    Message messagereturn = receiveMessage_Aux(ID);
                    if(messagereturn==null){
                        a = true;
                        System.out.print("El proceso se quedo buscando recibiendo");
                    }
                    else{
                        a = false;
                        System.out.print("El proceso si lo encontro recibiendo");
                    }
                }
            }
            else{
                state = false;
                System.out.print("El proceso no se ha bloqueado recibiendo");
            }
        }
    }
}
