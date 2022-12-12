package com.example.service1.repositary;

import com.example.service1.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    MessageEntity findBySessionId(Integer sessionId);

    Optional<MessageEntity> findFirstByOrderByIdDesc();
}
