package com.mth.resume_app.repositories;

import com.mth.resume_app.models.Experience;
import com.mth.resume_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

    Optional<Experience> findByUserAndId(User user, Integer id);
    List<Experience> findAllByUser(User user);
}
