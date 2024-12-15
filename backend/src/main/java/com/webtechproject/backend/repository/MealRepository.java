package com.webtechproject.backend.repository;
import com.webtechproject.backend.model.Meal;
import com.webtechproject.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    List<Meal> findByUser_Uid(Long userId);  // Find meals by user ID

    @Query("SELECT m FROM Meal m WHERE " +
            "LOWER(m.mealName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Meal> findBySearchQuery(@Param("query") String query);

    // Count meals for a specific user
    long countByUser(User user);
}
