package com.emerline.ijl01_10.utils;

import java.io.Serializable;

/**
 * Created by dzmitry.karayedau on 30-Apr-17.
 */
public class Message implements Serializable {
    private String command;
    private String sender;
    private String destination;
    private String author;
    private String messageBody;

    public Message(String command, String sender, String destination, String author, String messageBody) {
        this.command = command;
        this.sender = sender;
        this.destination = destination;
        this.author = author;
        this.messageBody = messageBody;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getCommand() {
        return command;
    }

    public String getSender() {
        return sender;
    }

    public String getDestination() {
        return destination;
    }

}
