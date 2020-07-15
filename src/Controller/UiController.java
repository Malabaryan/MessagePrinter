/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Logger;
import Model.Log;
import Model.Logger;
import Ui.Help;
import Ui.Setup;
import Ui.Window;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Bryan Hernandez
 */
public class UiController {
    
    private Setup setup;
    private Window mainWindow;
    private MainController controller;
    private Logger logger;
    private Help help;
        

    public UiController(MainController controller) {
        this.controller = controller;
        setup = new Setup(this);
        help = new Help(this);
    }

    public Setup getSetup() {
        return setup;
    }

    public Window getMainWindow() {
        return mainWindow;
    }

    public Help getHelp() {
        return help;
    }
    
    public void showSetup() {
        this.setup.setVisible(true);
    }
    
    public void showHelp() {
        this.help.setVisible(true);
    }

    public void showMainWindow() {
        if(mainWindow == null){
            mainWindow = new Window(this);
        }
        this.mainWindow.setVisible(true);
    }

    public MainController getController() {
        return controller;
    }
    
    public Logger getLogger() {
        return logger;
    }

    public void sendCommand(String text) {
        this.controller.executeCommand(text);
    }

    public void updateTextField(JTextArea txt_selectedprocesses, String ID) {
        
        txt_selectedprocesses.setText(getLogOf(ID));
    }

    
    public String getLogOf(String processID) {
        ArrayList<Log> logs = new ArrayList();
        return Logger.getInstance().getProcessLogs(processID);
        
    }

    public void updateAll(JTextArea txt_allprocesses) {

        txt_allprocesses.setText(Logger.getInstance().getAllLogs());
        
    }

    public void startUpdateSimulation(int formatSize, int noProcesses, int spin_maxQueueLenght) {
        ParametersController.setQueueSize(spin_maxQueueLenght);
        ParametersController.setMessageLength(formatSize);

    }

    
}
