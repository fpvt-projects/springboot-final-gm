package com.gm.activity.gmactivity.controller;

import com.gm.activity.gmactivity.model.Activity;
import com.gm.activity.gmactivity.repository.ActivityRepository;
import com.gm.payload.apipayload.ActivityRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("activity")
public class ActivityController {

    private ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @PostMapping
    public void logActivity(@RequestBody ActivityRequest request) {
        Activity activity = new Activity();
        activity.setAction(request.getAction());
        activity.setEmail(request.getEmail());
        activity.setCreatedAt(LocalDateTime.now());

        activityRepository.save(activity);
    }
}
