package com.veniamin.taskplanner.service;

import com.veniamin.taskplanner.dto.request.RegisterReqDTO;
import com.veniamin.taskplanner.dto.request.UserAuthorizeReqDTO;
import com.veniamin.taskplanner.dto.responce.TokenRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthorizeService {

    ResponseEntity<TokenRespDTO> authorizeUser(UserAuthorizeReqDTO userAuthorizeDTO);

    void registerUser(@Valid RegisterReqDTO registerDTO, HttpServletRequest request);

    void sendVerificationCode(String email, HttpServletRequest request);

    void verificateUser(String email, String verificationToken);

}
