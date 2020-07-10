
import Controller.MainController;
import Controller.ParameterState;
import Controller.ParametersController;
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
        ParametersController.getInstance().setAddressing_Send(ParameterState.Addr_Direct_Send);
        ParametersController.getInstance().setAddressing_Receive(ParameterState.Addr_Direct_Receive_Explicit);
        ParametersController.getInstance().setSyncronization_Send(ParameterState.Sync_Send_Blocking);
        ParametersController.getInstance().setSyncronization_Receive(ParameterState.Sync_Receive_Blocking);
        
        Mailbox m1 = new Mailbox("m1");
        MainController.getInstance().addMailbox(m1);
        
        Message message = new Message();
        message.setDestinationID("2");
        Process p1 = new Process("1");
        Process p2 = new Process("2");
        
        p1.sendMessage(message);
        
        p2.receiveMessage("1");
    }
    
}
