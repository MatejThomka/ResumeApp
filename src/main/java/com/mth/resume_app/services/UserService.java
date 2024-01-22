package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.dtos.EmailDTO;
import com.mth.resume_app.models.dtos.PasswordDTO;
import com.mth.resume_app.models.dtos.UserDTO;

public interface UserService {

    UserDTO credentials() throws ResumeAppException;
    UserDTO updateCredentials(UserDTO userDTO) throws ResumeAppException;
    void passwordChange(PasswordDTO passwordDTO) throws ResumeAppException;
    void emailChange(EmailDTO emailDTO) throws ResumeAppException;
    void deleteAccount(String email, PasswordDTO passwordDTO) throws ResumeAppException;
}
