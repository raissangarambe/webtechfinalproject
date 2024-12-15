package com.webtechproject.backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mealId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Foreign key to User

    @Column(nullable = false)
    private String mealName;  // Name of the meal (e.g., Breakfast, Lunch, etc.)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date mealTime;  // Timestamp of when the meal was consumed

    @Column(nullable = false)
    private double calories;  // Calories consumed in the meal

    @Column(nullable = false)
    private double protein;  // Protein content in the meal (in grams)

    @Column(nullable = false)
    private double carbs;  // Carbohydrates content in the meal (in grams)

    @Column(nullable = false)
    private double fats;  // Fats content in the meal (in grams)

    @Column(nullable = false)
    private double fiber;  // Fiber content in the meal (in grams)

    @Column(nullable = false)
    private double sugar;  // Sugar content in the meal (in grams)

    @Column(nullable = false)
    private double vitamins;  // Vitamins content in the meal (in mg)

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;  // Timestamp when the meal entry was created

    // Constructors
    public Meal() {
    }

    public Meal(User user, String mealName, Date mealTime, double calories, double protein, double carbs, double fats, double fiber, double sugar, double vitamins, Date createdAt) {
        this.user = user;
        this.mealName = mealName;
        this.mealTime = mealTime;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
        this.sugar = sugar;
        this.vitamins = vitamins;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Date getMealTime() {
        return mealTime;
    }

    public void setMealTime(Date mealTime) {
        this.mealTime = mealTime;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getVitamins() {
        return vitamins;
    }

    public void setVitamins(double vitamins) {
        this.vitamins = vitamins;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
