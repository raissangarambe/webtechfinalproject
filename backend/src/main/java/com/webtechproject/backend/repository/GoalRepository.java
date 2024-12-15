package com.webtechproject.backend.repository;
import com.webtechproject.backend.model.Goal;
import com.webtechproject.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser_Uid(Long userId);  // Find goals by user ID
    List<Goal> findByStatus(String status);  // Find goals by status (active/completed/failed)

    List<Goal> findByStartDateAfterAndEndDateBefore(Date startDate, Date endDate);
    @Query("SELECT g FROM Goal g WHERE " +
            "LOWER(g.goalType) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(g.status) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Goal> findBySearchQuery(@Param("query") String query);

    // Count goals for a specific user
    long countByUser(User user);

    // Count goals for a specific user with a given status
    long countByUserAndStatus(User user, String status);
}

