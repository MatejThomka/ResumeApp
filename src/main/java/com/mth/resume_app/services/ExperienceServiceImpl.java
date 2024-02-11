package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ExperienceException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.YearException;
import com.mth.resume_app.models.Experience;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.ExperienceDTO;
import com.mth.resume_app.repositories.ExperienceRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final UserExtraction extraction;
    private final ExperienceRepository experienceRepository;

    @Override
    public List<ExperienceDTO> show(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        List<Experience> experienceList = experienceRepository.findAllByUser(user);

        if (experienceList.isEmpty()) throw new ExperienceException("Nothing inside!");

        return experienceList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExperienceDTO createOrUpdate(String username,
                                        ExperienceDTO experienceDTO)
            throws ResumeAppException {

        checkDate(experienceDTO.getDateFrom(), experienceDTO.getDateTill());

        User user = extraction.findUserByUsername(username);

        Experience experience = experienceRepository.findByUserAndId(user, experienceDTO.getId()).orElse(new Experience());

        experience.setUser(user);

        return saveExperience(experience, experienceDTO);
    }

    @Override
    public void delete(String username, Integer id) throws ResumeAppException{

        User user = extraction.findUserByUsername(username);

        Experience experience = experienceRepository.findByUserAndId(user, id).orElseThrow(() -> new ExperienceException("Experience with this ID " + id + " missing!"));

        experienceRepository.delete(experience);
    }

    private ExperienceDTO convertToDTO(Experience experience) {
        return ExperienceDTO.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .employer(experience.getEmployer())
                .country(experience.getCountry())
                .city(experience.getCity())
                .dateFrom(experience.getDateFrom())
                .dateTill(experience.getDateTill())
                .description(experience.getDescription())
                .build();
    }

    private ExperienceDTO saveExperience(Experience experience,
                                         ExperienceDTO experienceDTO) {

        experience.setTitle(experienceDTO.getTitle());
        experience.setEmployer(experienceDTO.getEmployer());
        experience.setCountry(experienceDTO.getCountry());
        experience.setCity(experienceDTO.getCity());
        experience.setDateFrom(experienceDTO.getDateFrom());
        experience.setDateTill(experienceDTO.getDateTill());
        experience.setDescription(experienceDTO.getDescription());

        experienceRepository.save(experience);

        return ExperienceDTO.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .employer(experience.getEmployer())
                .country(experience.getCountry())
                .city(experience.getCity())
                .dateFrom(experience.getDateFrom())
                .dateTill(experience.getDateTill())
                .description(experience.getDescription())
                .build();
    }

    private void checkDate(String dateFrom, String dateTill) throws YearException {

        if (dateTill != null) {
            if (Integer.parseInt(dateFrom.substring(0, 3)) > Integer.parseInt(dateTill.substring(0,3))) throw new YearException("Your date from is higher than date till!");

            if (Integer.parseInt(dateFrom.substring(0,3)) > LocalDate.now().getYear() ||
                    Integer.parseInt(dateTill.substring(0,3)) > LocalDate.now().getYear()) throw new YearException("Wrong year!");
        }

        if (Integer.parseInt(dateFrom.substring(0,3)) > LocalDate.now().getYear()) throw new YearException("Wrong year!");

    }
}
