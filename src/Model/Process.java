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
    public Boolean waiting;
    public ArrayList<Message> messagessends;
    public ArrayList<Message> messagesreceives;

    public Process(String id) {
        this.id = id;
        this.state = false;
        this.waiting = false;
        this.messagessends = new ArrayList<Message>();
        this.messagesreceives = new ArrayList<Message>();
    }
    
    public void sendMessage_Aux(Message message){
        Logger logger = Logger.getInstance();
        if(this.state == true){
            logger.log(new Log("El proceso esta bloqueado", Log.Type.STATE,this.id));
            System.out.print("El proceso esta bloqueado \n");
        }
        else{
            ParameterState sync = ParametersController.getSyncronization_Send();
            ParameterState syncreceive = ParametersController.getSyncronization_Receive();
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
            
            ParameterState addressing = ParametersController.getAddressing_Send();
            if(addressing == ParameterState.Addr_Direct_Send){
                Process process = MainController.getInstance().getProcess(message.getDestinationID());
                if(process!=null){
                    if(syncreceive == ParameterState.Sync_Receive_ProofOfArrival && process.getWaiting()==true){
                        process.receiveMessage(this.id);
                    }
                }
            }
            else{
                Mailbox mailbox = MainController.getInstance().getMailbox(message.getDestinationID());
                if(mailbox!=null){
                    if(syncreceive == ParameterState.Sync_Receive_ProofOfArrival && mailbox.findWaiting()==true){
                        Process process = MainController.getInstance().getProcess(mailbox.findWaitingProcess());
                        if(process!=null){
                            process.receiveMessage(message.getDestinationID());
                        }
                    }
                }
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

                sendMessage_Aux(message);
            }
            else if(addressing == ParameterState.Addr_Indirect_Static || addressing == ParameterState.Addr_Indirect_Dynamic){

                boolean send = MainController.getInstance().sendMessageIndirect(message);
                if(send){
                    this.messagessends.add(message);
                    logger.log(new Log("Mensaje enviado", Log.Type.SEND,this.id));
                    System.out.print("Mensaje enviado \n");
                    sendMessage_Aux(message);
                }
                else{
                    logger.log(new Log("Mensaje no enviado", Log.Type.ERROR,this.id));
                    System.out.print("Mensaje enviado \n");
                    sendMessage_Aux(message);
                }
                
            }
        }
        else{
            logger.log(new Log("Mensaje no enviado", Log.Type.ERROR,this.id));
            System.out.print("El proceso esta bloqueado no puede enviar \n");
        }
    }
    
    public Message receiveMessage_Aux(String ID){
        ParameterState addressing = ParametersController.getAddressing_Receive();
        System.out.print(ParametersController.getAddressing_Receive().toString()+"  ");
        Message messagereturn = null;
        Logger logger = Logger.getInstance();
        if(addressing == ParameterState.Addr_Direct_Receive_Explicit){
            messagereturn = MainController.getInstance().receiveMessageDirectExplicit(ID,this.id);
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
        if(state==true){
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
                Message messagereturn = receiveMessage_Aux(ID);
                if(messagereturn==null){
                    this.waiting = true;
                    logger.log(new Log("El proceso esta esperando", Log.Type.STATE,this.id));
                    System.out.print("El proceso se quedo buscando recibiendo \n");
                }
                
                else{
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
            else{
                state = false;
                Message messagereturn = receiveMessage_Aux(ID);
                if(messagereturn!=null){
                    logger.log(new Log("Mensaje Recibido", Log.Type.RECIEVE,this.id));
                    System.out.print("El proceso no se ha bloqueado recibiendo \n");
                    if(syncsend == ParameterState.Sync_Send_Blocking){
                        MainController.getInstance().unlockprocess(messagereturn.getSourceID());
                        logger.log(new Log("Se ha desbloqueado el proceso", Log.Type.STATE,messagereturn.getSourceID()));
                        System.out.print("Se ha desbloqueado el proceso: " + messagereturn.getSourceID()+ "\n");
                    }
                }
                else{
                    logger.log(new Log("No hay mensajes por recibir", Log.Type.ERROR,this.id));
                    logger.log(new Log("El proceso no se ha bloqueado", Log.Type.STATE,this.id));
                    System.out.print("El proceso no se ha bloqueado \n");
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

    public Boolean getWaiting() {
        return waiting;
    }
    
    public void setWaiting(Boolean waiting) {
        this.waiting = waiting;
    }
    
    @Override
    public String toString(){
        return this.getId();
    }
}
