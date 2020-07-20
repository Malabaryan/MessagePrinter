
import Controller.MainController;
import Controller.ParameterState;
import Controller.ParametersController;
import Model.Logger;
import Model.Mailbox;
import Model.Message;
import Model.Process;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ariel
 */
public class MainPrueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainController.getInstance();
        Logger.getInstance();
        
        ParametersController.getInstance().setAddressing_Send(ParameterState.Addr_Indirect_Dynamic);
        ParametersController.getInstance().setAddressing_Receive(ParameterState.Addr_Indirect_Dynamic);
        ParametersController.getInstance().setSyncronization_Send(ParameterState.Sync_Send_Blocking);
        ParametersController.getInstance().setSyncronization_Receive(ParameterState.Sync_Receive_ProofOfArrival);
        ParametersController.setQueueSize(2);
        
        Mailbox m1 = new Mailbox("m1");
        MainController.getInstance().addMailbox(m1);
        
        Message message = new Message();
        message.setDestinationID("m1");
        
        Message message2 = new Message();
        message2.setDestinationID("m1");
        
        Process p1 = new Process("1");
        Process p2 = new Process("2");
        Process p3 = new Process("3");
        Process p4 = new Process("4");
        
        MainController.getInstance().getMailbox("m1").addProcessReceive(p1);
        MainController.getInstance().getMailbox("m1").addProcessReceive(p2);
        MainController.getInstance().getMailbox("m1").addProcessReceive(p3);
        MainController.getInstance().getMailbox("m1").addProcessReceive(p4);
        
        MainController.getInstance().addProcess(p1);
        MainController.getInstance().addProcess(p2);
        MainController.getInstance().addProcess(p3);
        MainController.getInstance().addProcess(p4);
        
        p1.sendMessage(message);
        p2.sendMessage(message);
        p3.sendMessage(message);
        
        p3.receiveMessage("m1");
        
        p2.receiveMessage("m1");
        
        p1.sendMessage(message2);
        
        p3.receiveMessage("m1");
        
        p2.receiveMessage("m1");
        
        
        //System.out.print("1 Tamaño listilla: "+ MainController.getInstance().getMessagespostsend().size() + "\n");
        
        //p2.receiveMessage("1");
        
        //System.out.print("2 Tamaño listilla: "+ MainController.getInstance().getMessagespostsend().size() + "\n");
        
        //p1.sendMessage(message2);
        
        
        
        //MainController.getInstance().getProcess("2").receiveMessage("1");
        
        //p1.sendMessage(message2);
        
        System.out.print(Logger.getInstance().getAllLogs());
    }
    
}
