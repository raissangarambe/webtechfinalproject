package com.webtechproject.backend.service;
import com.webtechproject.backend.repository.ActivityRepository;
import com.webtechproject.backend.repository.GoalRepository;
import com.webtechproject.backend.repository.MealRepository;
import com.webtechproject.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final ActivityRepository activityRepository;
    private final GoalRepository goalRepository;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public SearchService(ActivityRepository activityRepository, GoalRepository goalRepository,
                         MealRepository mealRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.goalRepository = goalRepository;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    public Map<String, List<?>> searchAll(String query) {
        Map<String, List<?>> results = new HashMap<>();

        results.put("activities", activityRepository.findBySearchQuery(query));
        results.put("goals", goalRepository.findBySearchQuery(query));
        results.put("meals", mealRepository.findBySearchQuery(query));
        results.put("users", userRepository.findBySearchQuery(query));

        return results;
    }
}
