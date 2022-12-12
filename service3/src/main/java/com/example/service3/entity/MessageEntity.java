package com.example.service3.entity;


import java.time.LocalDateTime;

public class MessageEntity {

    Integer id;
    Integer sessionId;
    LocalDateTime mc1Timestamp;
    LocalDateTime mc2Timestamp;
    LocalDateTime mc3Timestamp;
    LocalDateTime endTimestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getMc1Timestamp() {
        return mc1Timestamp;
    }

    public void setMc1Timestamp(LocalDateTime mc1Timestamp) {
        this.mc1Timestamp = mc1Timestamp;
    }

    public LocalDateTime getMc2Timestamp() {
        return mc2Timestamp;
    }

    public void setMc2Timestamp(LocalDateTime mc2Timestamp) {
        this.mc2Timestamp = mc2Timestamp;
    }

    public LocalDateTime getMc3Timestamp() {
        return mc3Timestamp;
    }

    public void setMc3Timestamp(LocalDateTime mc3Timestamp) {
        this.mc3Timestamp = mc3Timestamp;
    }

    public LocalDateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(LocalDateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", mc1Timestamp=" + mc1Timestamp +
                ", mc2Timestamp=" + mc2Timestamp +
                ", mc3Timestamp=" + mc3Timestamp +
                ", endTimestamp=" + endTimestamp +
                '}';
    }
}
