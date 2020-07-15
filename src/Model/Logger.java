/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Kenneth
 */
public class Logger {
    private static Logger instance;
    private ArrayList<Log> logs;
    
    private Logger(){
        logs = new ArrayList<>();
    }
    
    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(Log log) {
        this.logs.add(log);
    }
    
    public String getAllLogs(){
        String logString = "";
        for(Log log : logs){
            logString += log.toString(true) + "\n";
        }
        return logString;
    }
    
    public String getProcessLogs(String processId){
        String logString = "";
        for(Log log : logs){
            if (log.getProcessId().equals(processId))
                logString += log.toString(false) + "\n";
        }
        return logString;
    }
}
