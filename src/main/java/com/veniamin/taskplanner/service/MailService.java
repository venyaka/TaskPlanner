package com.veniamin.taskplanner.service;

import com.veniamin.taskplanner.model.User;
import jakarta.servlet.http.HttpServletRequest;

public interface MailService {

    void sendUserVerificationMail(User user, HttpServletRequest request);

    void sendPasswordRestoreMail(User user, HttpServletRequest request);
}
