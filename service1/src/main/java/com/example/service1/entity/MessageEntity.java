package com.example.service1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @Column(name = "session_id", nullable = false)
    Integer sessionId;

    @Column(name = "mc1_timestamp", columnDefinition = "TIMESTAMP NOT NULL")
    LocalDateTime mc1Timestamp;

    @Column(name = "mc2_timestamp", columnDefinition = "TIMESTAMP NOT NULL")
    LocalDateTime mc2Timestamp;

    @Column(name = "mc3_timestamp", columnDefinition = "TIMESTAMP NOT NULL")
    LocalDateTime mc3Timestamp;

    @Column(name = "end_timestamp", columnDefinition = "TIMESTAMP NOT NULL")
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
        return "MessageEntity{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", mc1Timestamp=" + mc1Timestamp +
                ", mc2Timestamp=" + mc2Timestamp +
                ", mc3Timestamp=" + mc3Timestamp +
                ", endTimestamp=" + endTimestamp +
                '}';
    }
}
