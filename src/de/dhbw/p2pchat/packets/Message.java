package de.dhbw.p2pchat.packets;

import java.util.Date;

public class Message extends Packet{
    String content;
    Date dateSent;

    public Message(String content) {
        this.content = content;
        this.dateSent = new Date();
    }

    public String getContent() {
        return content;
    }

    public Date getDateSent() {
        return dateSent;
    }
}
