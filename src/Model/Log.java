/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Bryan Hernandez
 */
public class Log {
   
    public enum Type {
        SEND,
        RECEIVE,
        ALERT,
        ACTION,
        UNSPECIFIED,
        STATE,
        ERROR
    }
    
    private String processId = "NA";
    private Type type = Type.UNSPECIFIED;
    private Date time;
    private String details;

    public Log(){
        this.details = "";
        this.time = new Date();
    }
    
    public Log(Type type){
        this.details = details;
        this.type = type;
        this.time = new Date();
    }
    
    public Log(Type type, String processId){
        this.type = type;
        this.processId = processId;
        this.time = new Date();
    }
    
    public Log(String details){
        this.details = details;
        this.time = new Date();
    }
    
    public Log(String details, Type type){
        this.details = details;
        this.type = type;
        this.time = new Date();
    }
    
    public Log(String details, Type type, String processId){
        this.details = details;
        this.type = type;
        this.processId = processId;
        this.time = new Date();
    }
    
    public String getProcessId() {
        return processId;
    }

    public Type getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    public String getDetails() {
        return details;
    }
    
    public String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("[HH:mm:ss]");   
        return formatter.format(getTime());
    }
        
    public String toString(boolean showProcessId){
        return getTimeStamp() 
                + (showProcessId && !processId.equals("NA") ?  
                "[" + getProcessId() + "][" : "[") 
                + getTypeString() + "] : " + details;
    }

    /*public Message getMsg() {
        return msg;
    }*/

    public String toString(){
        return toString(true);
    }

    private String getTypeString(){
        switch (getType()){
            case UNSPECIFIED:
                return "INFO";
            default:
                return(getType().name());
        }
    }    
}