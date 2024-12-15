package com.webtechproject.backend.controller;
import com.webtechproject.backend.model.Goal;
import com.webtechproject.backend.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/{userId}")
    public ResponseEntity<Goal> createGoal(@PathVariable Long userId, @RequestBody Goal goal) {
        return ResponseEntity.ok(goalService.createGoal(userId, goal));
    }

    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        return ResponseEntity.ok(goalService.getAllGoals());
    }

    @GetMapping("/{goalId}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.getGoalById(goalId));
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long goalId, @RequestBody Goal goal) {
        return ResponseEntity.ok(goalService.updateGoal(goalId, goal));
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{goalId}/progress")
    public ResponseEntity<Double> getProgress(@PathVariable Long goalId) {
        return ResponseEntity.ok(goalService.calculateProgress(goalId));
    }
}