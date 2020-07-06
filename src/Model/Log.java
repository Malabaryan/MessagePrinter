/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Bryan Hernandez
 */
public class Log {
    
    private Message msg;
    private Date timeStamp;
    private String text;
    private LogType type;

    public Log(Message msg, LogType type) {
        this.msg = msg;
        this.type = type;
        this.timeStamp = new Date();
    }

    public Log(String text, LogType type) {
        this.text = text;
        this.type = type;
        this.timeStamp = new Date();
    }


    public String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");   
        return formatter.format(timeStamp);
    }
    
    /**
     * Depends of logType, this method returns a String with the log to print.
     * @return 
     */
    public String printLog(){
        
        return "";
    }
    
}
