package com.mth.resume_app.repositories;

import com.mth.resume_app.models.Info;
import com.mth.resume_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InfoRepository extends JpaRepository<Info, Integer> {

    Optional<Info> findByUser(User user);

}
