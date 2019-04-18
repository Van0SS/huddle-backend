package com.orange.huddle.repository;

import com.orange.huddle.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepo extends CrudRepository<Preferences, String>, JpaRepository<Preferences, String> {
}
