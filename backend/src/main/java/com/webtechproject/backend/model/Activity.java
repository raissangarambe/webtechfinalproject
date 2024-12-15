package com.webtechproject.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Foreign key to User

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private double caloriesBurned;

    @Transient
    private double duration;  // Duration calculated automatically from startTime and endTime

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Intensity intensity;


    // Constructors
    public Activity() {
        // Default constructor
    }

    public Activity(User user, ActivityType activityType, LocalDateTime startTime, LocalDateTime endTime, double distance, double caloriesBurned, Intensity intensity) {
        this.user = user;
        this.activityType = activityType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distance = distance;
        this.caloriesBurned = caloriesBurned;
        this.intensity = intensity;

        // Automatically calculate duration
        this.duration = calculateDuration();
    }


    // Getter and Setter for duration (calculated)
    public double getDuration() {
        return calculateDuration();
    }

    private double calculateDuration() {
        if (startTime != null && endTime != null) {
            return (double) java.time.Duration.between(startTime, endTime).toMinutes();  // Duration in minutes
        }
        return 0;
    }

    // Getters and Setters
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.duration = calculateDuration();  // Recalculate duration whenever startTime changes
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        this.duration = calculateDuration();  // Recalculate duration whenever endTime changes
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }


    // Enum for Activity Type
    public enum ActivityType {
        RUNNING,
        WALKING,
        CYCLING,
        SWIMMING,
        HIKING,
        WORKOUTS,
        YOGA
    }

    // Enum for Intensity Level
    public enum Intensity {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    }

}
