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
        mainWindow = new Window(this);
        logger = new Logger();
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
        String fullString = "";
        for(Log log: getLogOf(ID)){
            fullString = fullString + log.printLog();
        }
        txt_selectedprocesses.setText(fullString);
    }

    
    public ArrayList<Log> getLogOf(String processID) {
        ArrayList<Log> logs = new ArrayList();
        for(Log log: this.logger.getLogger()){
            if(log.getMsg().getDestinationID().equals(processID)){
                if(log.getMsg().getDestinationID()  == null || log.getMsg().getSourceID()  == null){
                    logs.add(log);
                }
                else{
                    logs.add(log);
                }
            }
        }
        return logs;
    }

    public void updateAll(JTextArea txt_allprocesses) {
        String fullString = "";
        for(Log log: logger.getLogger()){
            fullString = fullString + log.printLog();
        }
        txt_allprocesses.setText(fullString);
        
    }

    public void startUpdateSimulation(int formatSize, int noProcesses, int spin_maxQueueLenght) {
        ParametersController.setQueueSize(spin_maxQueueLenght);
        ParametersController.setMessageLength(formatSize);
    }

    
}
