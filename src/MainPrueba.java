
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
        ParametersController.getInstance().setSyncronization_Receive(ParameterState.Sync_Receive_Blocking);
        
        Mailbox m1 = new Mailbox("m1");
        MainController.getInstance().addMailbox(m1);
        
        Message message = new Message();
        message.setDestinationID("2");
        
        Message message2 = new Message();
        message.setDestinationID("m1");
        
        Process p1 = new Process("1");
        Process p2 = new Process("2");
        
        m1.addProcessReceive(p2);
        
        MainController.getInstance().addProcess(p1);
        MainController.getInstance().addProcess(p2);
        
        p1.sendMessage(message);
        
        System.out.print("Tamaño de la cola: "+ m1.getQueue().size());
        
        p2.receiveMessage("m1");
        
        //p1.sendMessage(message2);
        
        System.out.print(Logger.getInstance().getAllLogs());
    }
    
}
