package com.webtechproject.backend.repository;
import com.webtechproject.backend.model.Activity;
import com.webtechproject.backend.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser_Uid(Long userId); //Find activities by user ID
    // Count activities for a specific user
    long countByUser(User user);


    @Query("SELECT a FROM Activity a WHERE " +
            "LOWER(a.activityType) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.user.firstName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Activity> findBySearchQuery(@Param("query") String query);

}
