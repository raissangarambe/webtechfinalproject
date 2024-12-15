package com.webtechproject.backend.controller;
import com.webtechproject.backend.model.Meal;
import com.webtechproject.backend.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    // Create a new meal
    @PostMapping("/{userId}")
    public ResponseEntity<Meal> createMeal(@PathVariable Long userId, @RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.createMeal(userId, meal));
    }

    // Retrieve all meals
    @GetMapping
    public ResponseEntity<List<Meal>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    // Retrieve meals by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Meal>> getMealsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(mealService.getMealsByUserId(userId));
    }

    // Retrieve a specific meal by ID
    @GetMapping("/{mealId}")
    public ResponseEntity<Meal> getMealById(@PathVariable Long mealId) {
        return ResponseEntity.ok(mealService.getMealById(mealId));
    }

    // Update an existing meal
    @PutMapping("/{mealId}")
    public ResponseEntity<Meal> updateMeal(@PathVariable Long mealId, @RequestBody Meal meal) {
        return ResponseEntity.ok(mealService.updateMeal(mealId, meal));
    }

    // Delete a meal
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }
}
