/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ParametersController;

/**
 *
 * @author Bryan Hernandez
 */
public class Message {
    private MessageType type;
    private String destinationID;
    private String sourceID;
    private int messageLenght;
    private String controlInformation;
    private String messageContent;
    private int priority;

    public Message() {
        destinationID = "empty";
        sourceID = "empty";
    }
    
    public Message(String messageContent) {
        this.messageContent = messageContent;
        this.sourceID = "";
        this.destinationID = "";
    }

    public Message(String destinationID, String sourceID, String messageContent) {
        this.destinationID = destinationID;
        this.sourceID = sourceID;
        this.messageContent = messageContent;
    }
    
    public Message(MessageType type, String destinationID, String sourceID, String controlInformation, String messageContent) {
        this.type = type;
        this.destinationID = destinationID;
        this.sourceID = sourceID;
        this.controlInformation = controlInformation;
        this.messageContent = messageContent;
        this.priority = 0;
        checkMessageLenght();
    }
    
    public Message(MessageType type, String destinationID, String sourceID, String controlInformation, String messageContent, int priority) {
        this.type = type;
        this.destinationID = destinationID;
        this.sourceID = sourceID;
        this.controlInformation = controlInformation;
        this.messageContent = messageContent;
        this.priority = priority;
        checkMessageLenght();
    }

    
    
    private void checkMessageLenght(){
        if(ParametersController.getMessageLength() > 0){
            messageContent = messageContent.substring(0,  
                    Math.min(messageContent.length(), 
                            ParametersController.getMessageLength()));
        }
    }

    public String getDestinationID() {
        return destinationID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public int getPriority() {
        return priority;
    }

    public void setDestinationID(String destinationID) {
        this.destinationID = destinationID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }
    
    
}
