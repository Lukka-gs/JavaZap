package br.senac.rj.msn.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {
    private String text;
    private User sender;
    private LocalDateTime timestamp;

    public Message(User sender, String text, LocalDateTime timestamp) {
        this.text = text;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getTimestamp() { return timestamp; }

    public String getFormattedTimestamp() {
        DateTimeFormatter formatTimestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(formatTimestamp);
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + sender.getUserName() + ": " + text;
    }

}

