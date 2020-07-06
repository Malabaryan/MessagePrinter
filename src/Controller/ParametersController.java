/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Bryan Hernandez
 */
public class ParametersController {
    private static ParametersController parametersController;
    
    private static ParameterState syncronization_Send;
    private static ParameterState syncronization_Receive;
    private static ParameterState addressing_Send;
    private static ParameterState addressing_Receive;
    private static ParameterState format;
    private static ParameterState queueStrategy;
    
    private static int queueSize = -1;
    private static int messageLength = -1;

    private ParametersController() {
    }

    public static ParametersController getInstance() {
        if(parametersController == null){
            parametersController = new ParametersController(); 
        }
        return parametersController;
    }
 
    public static ParameterState getSyncronization_Send() {
        if (syncronization_Send == null)
            syncronization_Send = ParameterState.Sync_Send_Blocking;
        return syncronization_Send;
    }
    
    public static ParameterState getSyncronization_Receive() {
        if (syncronization_Receive == null)
            syncronization_Receive = ParameterState.Sync_Receive_Blocking;
        return syncronization_Receive;
    }


    public static ParameterState getAddressing_Send() {
        if (addressing_Send == null)
            addressing_Send = ParameterState.Addr_Direct_Send;
        return addressing_Send;
    }
    
    public static ParameterState getAddressing_Receive() {
        if (addressing_Receive == null)
            addressing_Receive = ParameterState.Addr_Indirect_Static;
        return addressing_Receive;
    }

    public static ParameterState getFormat() {
        if (format == null)
            format = ParameterState.Form_Content;
        return format;
    }

    public static ParameterState getQueueStrategy() {
        if (queueStrategy == null)
            queueStrategy = ParameterState.Queue_FIFO;
        return queueStrategy;
    }
    
    public static int getMessageLength(){
        return messageLength;
    }
    
    public static int getQueueSize(){
        return queueSize;
    }

    public static void setSyncronization_Send(ParameterState syncronization_Send) {
        ParametersController.syncronization_Send = syncronization_Send;
    }

    public static void setSyncronization_Receive(ParameterState syncronization_Receive) {
        ParametersController.syncronization_Receive = syncronization_Receive;
    }
    

    public static void setAddressing_Send(ParameterState addressing) {
        ParametersController.addressing_Send = addressing;
    }
    
    public static void setAddressing_Receive(ParameterState addressing) {
        ParametersController.addressing_Receive = addressing;
    }

    public static void setFormat(ParameterState format) {
        ParametersController.format = format;
    }

    public static void setQueueStrategy(ParameterState queueStrategy) {
        ParametersController.queueStrategy = queueStrategy;
    }
    
    public static void setMessageLength(int length){
        ParametersController.messageLength = length;
    }
    
    public static void setQueueSize(int size){
        ParametersController.queueSize = size;
    }
}
