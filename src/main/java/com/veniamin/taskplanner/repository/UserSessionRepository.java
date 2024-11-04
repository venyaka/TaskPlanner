package com.veniamin.taskplanner.repository;

import com.veniamin.taskplanner.model.User;
import com.veniamin.taskplanner.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    List<UserSession> findByUserAndEndTime(User user, LocalDateTime localDateTime);

    UserSession findFirstByUserOrderByStartTimeDesc(User user);

}
