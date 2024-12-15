package com.webtechproject.backend.service;
import com.webtechproject.backend.model.Activity;
import com.webtechproject.backend.model.Activity.ActivityType;
import com.webtechproject.backend.model.Activity.Intensity;
import com.webtechproject.backend.model.User;
import com.webtechproject.backend.repository.ActivityRepository;
import com.webtechproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new activity
    public Activity createActivity(Long userId, Activity activity) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        activity.setUser(userOptional.get());

        return activityRepository.save(activity);
    }

    // Retrieve all activities
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    // Retrieve activities by user ID
    public List<Activity> getActivitiesByUserId(Long userId) {
        return activityRepository.findByUser_Uid(userId);
    }


    // Retrieve activity by ID
    public Activity getActivityById(Long activityId) {
        return activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));
    }

    // Update an activity
    public Activity updateActivity(Long activityId, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found with ID: " + activityId));

        existingActivity.setActivityType(updatedActivity.getActivityType());
        existingActivity.setStartTime(updatedActivity.getStartTime());
        existingActivity.setEndTime(updatedActivity.getEndTime());
        existingActivity.setDistance(updatedActivity.getDistance());
        existingActivity.setCaloriesBurned(updatedActivity.getCaloriesBurned());
        existingActivity.setIntensity(updatedActivity.getIntensity());

        // Duration is recalculated automatically in the model
        return activityRepository.save(existingActivity);
    }

    // Delete an activity
    public void deleteActivity(Long activityId) {
        if (!activityRepository.existsById(activityId)) {
            throw new IllegalArgumentException("Activity not found with ID: " + activityId);
        }
        activityRepository.deleteById(activityId);
    }

    // Retrieve activities by type and intensity (example of advanced filtering)
    public List<Activity> getActivitiesByTypeAndIntensity(ActivityType activityType, Intensity intensity) {
        return activityRepository.findAll().stream()
                .filter(activity -> activity.getActivityType() == activityType && activity.getIntensity() == intensity)
                .toList();
    }

    // Retrieve activities within a date range
    public List<Activity> getActivitiesWithinDateRange(LocalDateTime start, LocalDateTime end) {
        return activityRepository.findAll().stream()
                .filter(activity -> (activity.getStartTime().isAfter(start) || activity.getStartTime().isEqual(start)) &&
                        (activity.getEndTime().isBefore(end) || activity.getEndTime().isEqual(end)))
                .toList();
    }


}
