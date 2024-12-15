package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.User;
import com.webtechproject.backend.repository.ActivityRepository;
import com.webtechproject.backend.repository.GoalRepository;
import com.webtechproject.backend.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/summary")

    public ResponseEntity<Map<String, Object>> getDashboardSummary(@AuthenticationPrincipal User user) {
        
        Map<String, Object> summary = new HashMap<>();

        // Count activities
        long activityCount = activityRepository.countByUser(user);

        // Count goals (completed and failed)
        long completedGoals = goalRepository.countByUserAndStatus(user, "COMPLETED");
        long failedGoals = goalRepository.countByUserAndStatus(user, "FAILED");

        // Count meals
        long mealCount = mealRepository.countByUser(user);

        // Adding to response map
        summary.put("activities", activityCount);
        summary.put("completedGoals", completedGoals);
        summary.put("failedGoals", failedGoals);
        summary.put("meals", mealCount);

        return ResponseEntity.ok(summary);
    }
}
