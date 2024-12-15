package com.webtechproject.backend.service;
import com.webtechproject.backend.model.Goal;
import com.webtechproject.backend.model.User;
import com.webtechproject.backend.repository.GoalRepository;
import com.webtechproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new goal
    public Goal createGoal(Long userId, Goal goal) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        goal.setUser(userOptional.get());
        goal.setCreatedAt(new Date());
        return goalRepository.save(goal);
    }

    // Retrieve all goals
    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    // Retrieve goals by user ID
    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUser_Uid(userId);
    }

    // Retrieve a specific goal by ID
    public Goal getGoalById(Long goalId) {
        return goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + goalId));
    }


    // Update an existing goal
    public Goal updateGoal(Long goalId, Goal updatedGoal) {
        Goal existingGoal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + goalId));

        existingGoal.setGoalType(updatedGoal.getGoalType());
        existingGoal.setTargetValue(updatedGoal.getTargetValue());
        existingGoal.setCurrentValue(updatedGoal.getCurrentValue());
        existingGoal.setStartDate(updatedGoal.getStartDate());
        existingGoal.setEndDate(updatedGoal.getEndDate());
        existingGoal.setStatus(updatedGoal.getStatus());

        return goalRepository.save(existingGoal);
    }

    // Delete a goal
    public void deleteGoal(Long goalId) {
        if (!goalRepository.existsById(goalId)) {
            throw new IllegalArgumentException("Goal not found with ID: " + goalId);
        }
        goalRepository.deleteById(goalId);
    }

    // Calculate progress for a specific goal
    public double calculateProgress(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new IllegalArgumentException("Goal not found with ID: " + goalId));

        if (goal.getTargetValue() == 0) {
            throw new IllegalArgumentException("Target value cannot be zero.");
        }

        return (goal.getCurrentValue() / goal.getTargetValue()) * 100; // Progress as a percentage
    }

    // Retrieve goals by status
    public List<Goal> getGoalsByStatus(String status) {
        return goalRepository.findByStatus(status);
    }

    // Retrieve goals within a date range
    public List<Goal> getGoalsWithinDateRange(Date startDate, Date endDate) {
        return goalRepository.findByStartDateAfterAndEndDateBefore(startDate, endDate);
    }
}
