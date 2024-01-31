package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.EmailDTO;
import com.mth.resume_app.models.dtos.PasswordDTO;
import com.mth.resume_app.models.dtos.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO credentials(String username) throws ResumeAppException;
    UserDTO updateCredentials(String username, UserDTO userDTO) throws ResumeAppException;
    void passwordChange(String username, PasswordDTO passwordDTO) throws ResumeAppException;
    void emailChange(String username, EmailDTO emailDTO) throws ResumeAppException;
    void deleteAccount(String username, PasswordDTO passwordDTO) throws ResumeAppException;
    List<UserDTO> showAllUsers() throws ResumeAppException;
}
