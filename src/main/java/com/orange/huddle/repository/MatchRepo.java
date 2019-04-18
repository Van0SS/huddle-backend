package com.orange.huddle.repository;

import com.orange.huddle.model.ConfirmedMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepo extends CrudRepository<ConfirmedMatch, String>, SearchableByUsersConcat, JpaRepository<ConfirmedMatch, String> {
}

interface SearchableByUsersConcat {
    public List<ConfirmedMatch> findAllByUsersConcat(String users);
}
