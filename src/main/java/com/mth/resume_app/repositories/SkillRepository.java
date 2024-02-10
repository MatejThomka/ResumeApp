package com.mth.resume_app.repositories;

import com.mth.resume_app.models.Skill;
import com.mth.resume_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<List<Skill>> findAllByUser(User user);
    Optional<Skill> findByUserAndId(User user, Integer id);
}
