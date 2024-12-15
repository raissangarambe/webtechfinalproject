package com.webtechproject.backend.service;
import com.webtechproject.backend.model.Meal;
import com.webtechproject.backend.model.User;
import com.webtechproject.backend.repository.MealRepository;
import com.webtechproject.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new meal
    public Meal createMeal(Long userId, Meal meal) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        meal.setUser(userOptional.get());
        meal.setCreatedAt(new Date());
        return mealRepository.save(meal);
    }

    // Retrieve all meals
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    // Retrieve meals by user ID
    public List<Meal> getMealsByUserId(Long userId) {
        return mealRepository.findByUser_Uid(userId);
    }

    // Retrieve a specific meal by ID
    public Meal getMealById(Long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found with ID: " + mealId));
    }

    // Update an existing meal
    public Meal updateMeal(Long mealId, Meal updatedMeal) {
        Meal existingMeal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("Meal not found with ID: " + mealId));

        existingMeal.setMealName(updatedMeal.getMealName());
        existingMeal.setMealTime(updatedMeal.getMealTime());
        existingMeal.setCalories(updatedMeal.getCalories());
        existingMeal.setProtein(updatedMeal.getProtein());
        existingMeal.setCarbs(updatedMeal.getCarbs());
        existingMeal.setFats(updatedMeal.getFats());
        existingMeal.setFiber(updatedMeal.getFiber());
        existingMeal.setSugar(updatedMeal.getSugar());
        existingMeal.setVitamins(updatedMeal.getVitamins());

        return mealRepository.save(existingMeal);
    }

    // Delete a meal
    public void deleteMeal(Long mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new IllegalArgumentException("Meal not found with ID: " + mealId);
        }
        mealRepository.deleteById(mealId);
    }
}
