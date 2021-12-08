package com.example.repository;

import com.example.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("select m from Message m where (to = ?1 or to is null) and roomId = ?2 order by local_date_time")
    List<Message> findForUserAndRoom(String username, int roomId);
}
