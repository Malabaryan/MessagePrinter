/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Controller.MainController;
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
        MainController controller = new MainController();
        UiController uiController = new UiController(controller);
        
        /* TEST INFO*/
        
            Mailbox m = new Mailbox("First Mailbox");
            controller.addMailbox(m);
            controller.addProcess(new Model.Process("First Process"));
            
        /******************/
        
        
        uiController.showSetup();
    }
    
}
