package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.Activity;
import com.webtechproject.backend.model.Goal;
import com.webtechproject.backend.model.GymMembership;
import com.webtechproject.backend.model.Meal;
import com.webtechproject.backend.service.ActivityService;
import com.webtechproject.backend.service.GoalService;
import com.webtechproject.backend.service.GymMembershipService;
import com.webtechproject.backend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private GoalService goalService;

    @Autowired
    private GymMembershipService gymMembershipService;

    @Autowired
    private MealService mealService;  // Add MealService

    // Get a report with goals, activities, memberships, and meals
    @GetMapping
    public ResponseEntity<Report> getReport() {
        List<Activity> activities = activityService.getAllActivities();
        List<Goal> goals = goalService.getAllGoals();
        List<GymMembership> memberships = gymMembershipService.getAllMemberships();
        List<Meal> meals = mealService.getAllMeals();  // Fetch meals

        Report report = new Report(goals, activities, memberships, meals);  // Pass meals to the report
        return ResponseEntity.ok(report);
    }

    // Report DTO to bundle goals, activities, memberships, and meals together
    public static class Report {
        private List<Goal> goals;
        private List<Activity> activities;
        private List<GymMembership> memberships;
        private List<Meal> meals;  // Add meals list

        public Report(List<Goal> goals, List<Activity> activities, List<GymMembership> memberships, List<Meal> meals) {
            this.goals = goals;
            this.activities = activities;
            this.memberships = memberships;
            this.meals = meals;  // Initialize meals
        }

        public List<Goal> getGoals() {
            return goals;
        }

        public void setGoals(List<Goal> goals) {
            this.goals = goals;
        }

        public List<Activity> getActivities() {
            return activities;
        }

        public void setActivities(List<Activity> activities) {
            this.activities = activities;
        }

        public List<GymMembership> getMemberships() {
            return memberships;
        }

        public void setMemberships(List<GymMembership> memberships) {
            this.memberships = memberships;
        }

        public List<Meal> getMeals() {
            return meals;
        }

        public void setMeals(List<Meal> meals) {
            this.meals = meals;
        }
    }
}

