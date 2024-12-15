package com.webtechproject.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Foreign key to User

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;  // Date of the progress report (daily summary)

    @Column(nullable = false)
    private double totalCaloriesBurned;  // Sum of calories burned from activities

    @Column(nullable = false)
    private double totalDistanceCovered;  // Total distance covered (in km)

    @Column(nullable = false)
    private int totalSteps;  // Total steps taken

    @Column(nullable = false)
    private int totalActiveMinutes;  // Total active minutes

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;  // Timestamp when the report was created

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date modifiedAt;


    // Constructors
    public ProgressReport() {
    }

    public ProgressReport(User user, Date date, int steps, double caloriesBurned, double distanceCovered, int activeMinutes, Date createdAt, Date modifiedAt) {
        this.user = user;
        this.date = date;
        this.totalSteps = steps;
        this.totalCaloriesBurned = caloriesBurned;
        this.totalDistanceCovered = distanceCovered;
        this.totalActiveMinutes = activeMinutes;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Getters and Setters
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSteps() {
        return totalSteps;
    }

    public void setSteps(int steps) {
        this.totalSteps = steps;
    }

    public double getCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.totalCaloriesBurned = caloriesBurned;
    }

    public double getDistanceCovered() {
        return totalDistanceCovered;
    }

    public void setDistanceCovered(double distanceCovered) {
        this.totalDistanceCovered = distanceCovered;
    }

    public double getActiveMinutes() {
        return totalActiveMinutes;
    }

    public void setActiveMinutes(int activeMinutes) {
        this.totalActiveMinutes = activeMinutes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

}
