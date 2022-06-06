package com.gm.activity.gmactivity.repository;

import com.gm.activity.gmactivity.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivityRepository extends CrudRepository<Activity, String> {
}
