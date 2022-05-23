package de.dhbw.p2pchat.packets;

import java.util.Date;

public class Message extends Packet{
    String content;
    Date dateSent;


    String senderName;

    public Message(String content, String senderName) {
        this.content = content;
        this.dateSent = new Date();
        this.senderName = senderName;
    }

    public String getContent() {
        return content;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public String getSenderName() {
        return senderName;
    }
}
