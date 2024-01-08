package com.resumeApp.ResumeApp.repositories;

import com.resumeApp.ResumeApp.models.PersonalDetails;
import com.resumeApp.ResumeApp.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Integer> {

  Optional<PersonalDetails> findByUser(User user);
}
