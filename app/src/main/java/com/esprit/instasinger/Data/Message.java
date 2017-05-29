package com.esprit.instasinger.Data;

/**
 * Created by bechirkaddech on 12/28/16.
 */

public class Message {
    private String fromId;
    private String toId;
    private String text;
    private String time ;


    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFromId() {

        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public Message(String fromId, String toId, String text, String time) {

        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
        this.time = time;
    }

    public Message() {

    }
}
