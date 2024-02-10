package com.mth.resume_app.repositories;

import com.mth.resume_app.models.Education;
import com.mth.resume_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, Integer> {
    Optional<Education> findByUserAndId(User user, Integer id);
    List<Education> findAllByUser(User user);
}
