package com.mth.resume_app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String interestsOrHobbies;
    String personalSummary;
    String linkedin;
    String gitHub;
    String facebook;

    @OneToOne
    User user;
}
