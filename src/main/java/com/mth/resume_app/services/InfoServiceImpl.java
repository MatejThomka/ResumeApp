package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.InfoException;
import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.Info;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.InfoDTO;
import com.mth.resume_app.repositories.InfoRepository;
import com.mth.resume_app.security.UserExtraction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;
    private final UserExtraction extraction;

    /**
     * Retrieves information about the user based on the provided username.
     *
     * @param username The username of the user for whom information is being retrieved.
     * @return An InfoDTO object containing interests or hobbies, personal summary, LinkedIn, GitHub, and Facebook URLs.
     * @throws ResumeAppException If there is an issue with retrieving user information.
     */
    @Override
    public InfoDTO show(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        Info info = infoRepository.findByUser(user).orElseThrow(() -> new InfoException("Missing info!"));

        return InfoDTO.builder()
                .interestsOrHobbies(info.getInterestsOrHobbies())
                .personalSummary(info.getPersonalSummary())
                .linkedin(info.getLinkedin())
                .gitHub(info.getGitHub())
                .facebook(info.getFacebook())
                .build();
    }

    /**
     * Creates or updates user information based on the provided InfoDTO for the specified username.
     *
     * @param username The username of the user for whom information is being created or updated.
     * @param infoDTO The InfoDTO containing the user information to be created or updated.
     * @return An InfoDTO object representing the created or updated user information.
     * @throws ResumeAppException If there is an issue with retrieving user information or saving data.
     */
    @Override
    public InfoDTO createOrUpdate(String username, InfoDTO infoDTO) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        Info info = infoRepository.findByUser(user).orElse(new Info());

        if (info.getUser() == null) info.setUser(user);

        info.setInterestsOrHobbies(infoDTO.getInterestsOrHobbies());
        info.setPersonalSummary(infoDTO.getPersonalSummary());
        info.setLinkedin(infoDTO.getLinkedin());
        info.setGitHub(infoDTO.getGitHub());
        info.setFacebook(infoDTO.getFacebook());

        infoRepository.save(info);

        return InfoDTO.builder()
                .interestsOrHobbies(info.getInterestsOrHobbies())
                .personalSummary(info.getPersonalSummary())
                .linkedin(info.getLinkedin())
                .gitHub(info.getGitHub())
                .facebook(info.getFacebook())
                .build();
    }

    /**
     * Deletes the user information associated with the specified username.
     *
     * @param username The username of the user whose information is to be deleted.
     * @throws ResumeAppException If there is an issue with retrieving user information or deleting data.
     */
    @Override
    public void delete(String username) throws ResumeAppException {

        User user = extraction.findUserByUsername(username);

        Info info = infoRepository.findByUser(user).orElseThrow(() -> new InfoException("Missing info!"));

        infoRepository.delete(info);
    }
}
