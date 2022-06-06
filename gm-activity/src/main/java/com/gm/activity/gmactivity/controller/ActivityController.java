package com.gm.activity.gmactivity.controller;

import com.gm.activity.gmactivity.model.Activity;
import com.gm.activity.gmactivity.repository.ActivityRepository;
import com.gm.payload.apipayload.ActivityRequest;
import com.gm.payload.apipayload.GetAllActivitiesResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    @GetMapping
    public GetAllActivitiesResponse getAllActivities() {
        GetAllActivitiesResponse response = new GetAllActivitiesResponse();
        response.setCurrentActivityCount(activityRepository.count());
        response.setActivities( new ArrayList<>());

        activityRepository.findAll().forEach(activity -> {
            ActivityRequest activityRequest = new ActivityRequest();
            activityRequest.setEmail(""+activity.getEmail());
            activityRequest.setAction(activity.getAction());

            response.getActivities().add(activityRequest);
        });

        return response;
    }
}
