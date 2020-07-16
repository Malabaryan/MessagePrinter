/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.MainController;
import Controller.ParameterState;
import Controller.ParametersController;
import Controller.UiController;
import Model.Mailbox;

/**
 *
 * @author Bryan Hernandez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        UiController uiController = new UiController(MainController.getInstance());
        
        /* TEST INFO*/
        
        ParametersController.setAddressing_Receive(ParameterState.Addr_Direct_Receive_Explicit);

            
        /******************/
        
        
        uiController.showSetup();
    }
    
}
