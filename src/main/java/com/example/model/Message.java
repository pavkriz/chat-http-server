package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @Column(name="from_username")
    private String from;
    @Column(name="to_username")
    private String to;
    private LocalDateTime localDateTime;
    private int roomId;

    public Message() {}

    public Message(String text, String from, String to, LocalDateTime localDateTime, int roomId) {
        this.text = text;
        this.from = from;
        this.to = to;
        this.localDateTime = localDateTime;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
