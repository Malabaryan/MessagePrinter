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
    public ArrayList<Message> messagessends;
    public ArrayList<Message> messagesreceives;

    public Process(String id) {
        this.id = id;
        this.state = false;
        this.messagessends = new ArrayList<Message>();
        this.messagesreceives = new ArrayList<Message>();
    }
    
    public void sendMessage_Aux(){
        Logger logger = Logger.getInstance();
        if(this.state == true){
            logger.log(new Log("El proceso esta bloqueado", Log.Type.STATE,this.id));
            System.out.print("El proceso esta bloqueado \n");
        }
        else{
            ParameterState sync = ParametersController.getSyncronization_Send();
            if(sync == ParameterState.Sync_Send_Blocking){
                state = true;
                logger.log(new Log("El proceso quedo bloqueado", Log.Type.STATE,this.id));
                System.out.print("El proceso se ha bloqueado \n");
            }
            else{
                state = false;
                logger.log(new Log("El proceso no quedo bloqueado", Log.Type.STATE,this.id));
                System.out.print("El proceso no se ha bloqueado \n");
            }
        }
    }
    
    public void sendMessage(Message message){
        message.setSourceID(this.id);
        ParameterState addressing = ParametersController.getAddressing_Send();
        Logger logger = Logger.getInstance();
        if(!this.state){
            if(addressing == ParameterState.Addr_Direct_Send){
                MainController.getInstance().sendMessageDirect(message);
                this.messagessends.add(message);
                logger.log(new Log("Mensaje enviado", Log.Type.SEND,this.id));
                System.out.print("Mensaje enviado \n");

                sendMessage_Aux();
            }
            else if(addressing == ParameterState.Addr_Indirect_Static || addressing == ParameterState.Addr_Indirect_Dynamic){

                MainController.getInstance().sendMessageIndirect(message);
                this.messagessends.add(message);
                logger.log(new Log("Mensaje enviado", Log.Type.SEND,this.id));
                System.out.print("Mensaje enviado \n");
                sendMessage_Aux();
            }
        }
        else{
            logger.log(new Log("El proceso "+this.id+" esta bloqueado"));
            System.out.print("El proceso esta bloqueado no puede enviar \n");
        }
    }
    
    public Message receiveMessage_Aux(String ID){
        ParameterState addressing = ParametersController.getAddressing_Receive();
        System.out.print(ParametersController.getAddressing_Receive().toString()+"  ");
        Message messagereturn = null;
        Logger logger = Logger.getInstance();
        if(addressing == ParameterState.Addr_Direct_Receive_Explicit){
            messagereturn = MainController.getInstance().receiveMessageDirectExplicit(ID);
        }
        else if(addressing == ParameterState.Addr_Direct_Receive_Implicit){
            messagereturn = MainController.getInstance().receiveMessageDirectImplicit(this.id);
        }
        else if(addressing == ParameterState.Addr_Indirect_Static || addressing == ParameterState.Addr_Indirect_Dynamic){
            if(MainController.getInstance().getMailbox(ID).processBelongs(this.id)){
                messagereturn = MainController.getInstance().receiveMessageIndirect(ID);
            }
            else{
                logger.log(new Log("El proceso "+this.id+" no pertenece a la lista de MailBox"));
                System.out.print("El proceso no pertenece a la lista del Mailbox");
            }
        }
        return messagereturn;
    }
    
    public void receiveMessage(String ID){
        Logger logger = Logger.getInstance();
        if(this.state){
            logger.log(new Log("El proceso esta bloqueado no puede recibir", Log.Type.STATE,this.id));
            System.out.print("El proceso esta bloqueado no puede recibir \n");
        } else {
            ParameterState sync = ParametersController.getSyncronization_Receive();
            ParameterState syncsend = ParametersController.getSyncronization_Send();
            if(sync == ParameterState.Sync_Receive_Blocking){
                state = true;
                System.out.print("El proceso se ha bloqueado recibiendo \n");
                logger.log(new Log("El proceso esta bloqueado", Log.Type.STATE,this.id));
                Message messagereturn = receiveMessage_Aux(ID);
                if(messagereturn==null){
                    this.state = true;
                    logger.log(new Log("El proceso se quedo bloqueado no puede recibir", Log.Type.STATE,this.id));
                    System.out.print("El proceso se quedo bloqueado recibiendo \n");
                }
                else{
                    this.state = false;
                    this.messagesreceives.add(messagereturn);
                    logger.log(new Log("El proceso esta desbloqueado", Log.Type.STATE,this.id));
                    System.out.print("El proceso se ha desbloqueado recibiendo \n");
                    logger.log(new Log("Mensaje Recibido", Log.Type.RECIEVE,this.id));
                    System.out.print("El mensaje ha sido recibido \n");
                    if(syncsend == ParameterState.Sync_Send_Blocking){
                        MainController.getInstance().unlockprocess(messagereturn.getSourceID());
                        logger.log(new Log("Se ha desbloqueado el proceso", Log.Type.STATE,messagereturn.getSourceID()));
                        System.out.print("Se ha desbloqueado el proceso: " + messagereturn.getSourceID()+ "\n");
                    }
                }
            }
            else if(sync == ParameterState.Sync_Receive_ProofOfArrival){
                boolean a = true;
                while(a){
                    Message messagereturn = receiveMessage_Aux(ID);
                    if(messagereturn==null){
                        a = true;
                        System.out.print("El proceso se quedo buscando recibiendo \n");
                    }
                    else{
                        a = false;
                        this.messagesreceives.add(messagereturn);
                        logger.log(new Log("Mensaje Recibido", Log.Type.RECIEVE,this.id));
                        System.out.print("El proceso si lo encontro recibiendo \n");
                        if(syncsend == ParameterState.Sync_Send_Blocking){
                            MainController.getInstance().unlockprocess(messagereturn.getSourceID());
                            logger.log(new Log("Se ha desbloqueado el proceso", Log.Type.STATE,messagereturn.getSourceID()));
                            System.out.print("Se ha desbloqueado el proceso: " + messagereturn.getSourceID()+ "\n");
                        }
                    }
                }
            }
            else{
                state = false;
                Message messagereturn = receiveMessage_Aux(ID);
                logger.log(new Log("Mensaje Recibido", Log.Type.RECIEVE,this.id));
                System.out.print("El proceso no se ha bloqueado recibiendo \n");
                if(syncsend == ParameterState.Sync_Send_Blocking){
                    MainController.getInstance().unlockprocess(messagereturn.getSourceID());
                    logger.log(new Log("Se ha desbloqueado el proceso", Log.Type.STATE,messagereturn.getSourceID()));
                    System.out.print("Se ha desbloqueado el proceso: " + messagereturn.getSourceID()+ "\n");
                }
            }
        }
    }

    public String getId() {
        return id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
    
    @Override
    public String toString(){
        return this.getId();
    }
}
