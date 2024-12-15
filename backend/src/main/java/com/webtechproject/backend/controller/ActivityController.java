package com.webtechproject.backend.controller;
import com.webtechproject.backend.model.Activity;
import com.webtechproject.backend.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/{userId}")
    public ResponseEntity<Activity> createActivity(@PathVariable Long userId, @RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.createActivity(userId, activity));
    }


    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

}
