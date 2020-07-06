/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Logger;
import Model.Log;
import Model.Process;
import UI.Setup;
import UI.Window;
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

    public UiController(MainController controller) {
        this.controller = controller;
        setup = new Setup(this);
        mainWindow = new Window(this);
        logger = new Logger();
    }

    public Setup getSetup() {
        return setup;
    }

    public Window getMainWindow() {
        return mainWindow;
    }
    
    public void showSetup() {
        this.setup.setVisible(true);
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
            fullString = fullString + "[" + log.getTimeStamp() + "]: " 
                    + log.getText() + " - " + log.getMsg().getSourceID() 
                    + " to " + log.getMsg().getDestinationID() + "\n";
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
            fullString = fullString + log.getText() + " --- " + log.getMsg().getSourceID() + " to " + log.getMsg().getDestinationID() + "\n";
        }
        txt_allprocesses.setText(fullString);
        
    }

    public void startSimulation(int formatSize, int noProcesses, int spin_maxQueueLenght) {
        for(int i = 0; i < noProcesses;i++){
            Process proceso = new Process(Integer.toString(i),this.controller.getMailbox());
            this.controller.AddProcess(proceso);
            this.controller.getMailbox().addListReceive(proceso);
            this.controller.getMailbox().addListSend(proceso);
        }
        ParametersController.setQueueSize(spin_maxQueueLenght);
        ParametersController.setMessageLength(formatSize);
        System.out.println(this.controller.getProcesses().size() + " es la cantidad de procesos");;
        for(String s:this.controller.getProcessesString())
            System.out.println(s);
     }
    
}
