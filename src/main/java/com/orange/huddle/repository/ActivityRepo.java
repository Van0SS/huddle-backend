package com.orange.huddle.repository;

import com.orange.huddle.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepo extends CrudRepository<Activity, String> {
}
