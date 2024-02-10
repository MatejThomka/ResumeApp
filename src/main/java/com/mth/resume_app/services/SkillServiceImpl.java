package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.exceptions.SkillException;
import com.mth.resume_app.exceptions.UserException;
import com.mth.resume_app.models.Skill;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.SkillDTO;
import com.mth.resume_app.repositories.SkillRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final UserExtraction extraction;

    /**
     * Retrieves and returns a list of skill data transfer objects (DTOs) associated with the specified user.
     * It fetches the user by their username, then retrieves all skill entities associated with that user.
     * If no skills are found, it throws a SkillException with the message "There is nothing!".
     *
     * @param username The username of the user for whom skill details are being retrieved.
     * @return A list of SkillDTOs representing the user's skill details.
     * @throws ResumeAppException If there is an issue with retrieving user information.
     * @throws SkillException If no skills are found for the user.
     */
    @Override
    public List<SkillDTO> show(String username) throws ResumeAppException {
        User user = extraction.findUserByUsername(username);

        List<Skill> skillList = skillRepository.findAllByUser(user).orElseThrow(() -> new UserException("Problem with user!"));

        if (skillList.isEmpty()) throw new SkillException("There is nothing!");

        return skillList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates or updates a skill for the specified user based on the provided SkillDTO.
     * It retrieves the user by their username, then fetches or initializes the skill entity based on the provided ID.
     * If the skill does not have an associated user, it assigns the user to the skill.
     * Finally, it calls the saveSkill method to persist the changes and returns the resulting SkillDTO.
     *
     * @param username  The username of the user for whom the skill is being created or updated.
     * @param skillDTO  The SkillDTO containing information about the skill to be created or updated.
     * @return The SkillDTO representing the created or updated skill.
     * @throws ResumeAppException If there is an issue with retrieving user information.
     */
    @Override
    public SkillDTO createOrUpdate(String username, SkillDTO skillDTO) throws ResumeAppException {
        User user = extraction.findUserByUsername(username);

        Skill skill = skillRepository.findByUserAndId(user, skillDTO.getId()).orElse(new Skill());

        if (skill.getUser() == null) skill.setUser(user);

        return saveSkill(skill, skillDTO);
    }

    /**
     * Deletes the skill entry with the specified ID associated with the given username.
     * It fetches the user by username and then retrieves the skill entity based on the provided ID.
     * If the skill entry is found, it is deleted from the repository.
     *
     * @param username The username of the user associated with the skill entry.
     * @param id       The ID of the skill entry to be deleted.
     * @throws ResumeAppException If the skill entry is not found.
     */
    @Override
    public void delete(String username, Integer id) throws ResumeAppException {
        User user = extraction.findUserByUsername(username);

        Skill skill = skillRepository.findByUserAndId(user, id).orElseThrow(() -> new SkillException("Skill not exist!"));

        skillRepository.delete(skill);
    }

    /**
     * Converts a Skill entity to its corresponding SkillDTO representation.
     * This method takes a Skill entity as input and creates a new SkillDTO object
     * with the same ID, name, and level as the input skill. It then returns the newly created SkillDTO.
     *
     * @param skill The Skill entity to be converted to SkillDTO.
     * @return The SkillDTO representation of the input Skill entity.
     */
    private SkillDTO convertToDTO(Skill skill) {
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .level(skill.getLevel())
                .build();
    }

    /**
     * Saves the skill entity based on the provided SkillDTO.
     * It updates the name and level attributes of the existing skill entity or creates a new one
     * based on the information in the SkillDTO. After saving the changes to the repository,
     * it creates a new SkillDTO representation with the updated or newly created skill attributes
     * and returns it.
     *
     * @param skill     The existing or new skill entity to be updated or created.
     * @param skillDTO  The SkillDTO containing the updated or new skill details.
     * @return The SkillDTO representation of the updated or newly created skill entity.
     */
    private SkillDTO saveSkill(Skill skill, SkillDTO skillDTO) {
        skill.setName(skillDTO.getName());
        skill.setLevel(skillDTO.getLevel());

        skillRepository.save(skill);

        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .level(skill.getLevel())
                .build();
    }
}
