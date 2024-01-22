package com.mth.resume_app.services;

import com.mth.resume_app.exceptions.ResumeAppException;
import com.mth.resume_app.models.User;
import com.mth.resume_app.models.dtos.UserDTO;

public interface UserService {

    UserDTO credentials() throws ResumeAppException;
    UserDTO updateCredentials(User user) throws ResumeAppException;
}
