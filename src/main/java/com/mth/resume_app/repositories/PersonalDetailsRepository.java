package com.mth.resume_app.repositories;


import com.mth.resume_app.models.PersonalDetails;
import com.mth.resume_app.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails, Integer> {

  Optional<PersonalDetails> findByUser(User user);
}
