package com.veniamin.taskplanner.controller;

import com.veniamin.taskplanner.constant.PathConstants;
import com.veniamin.taskplanner.dto.request.RegisterReqDTO;
import com.veniamin.taskplanner.dto.request.UserAuthorizeReqDTO;
import com.veniamin.taskplanner.dto.responce.TokenRespDTO;
import com.veniamin.taskplanner.service.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.AUTHORIZE_CONTROLLER_PATH)
public class AuthorizeController {

    private final AuthorizeService authorizeService;

    @PostMapping("/login")
    public ResponseEntity<TokenRespDTO> authorizeUser(@Valid @RequestBody UserAuthorizeReqDTO userAuthorizeDTO) {
        return authorizeService.authorizeUser(userAuthorizeDTO);
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterReqDTO registerDTO, HttpServletRequest request) {
        authorizeService.registerUser(registerDTO, request);
    }

    @PostMapping("/verificateCode")
    public void sendVerificationCode(@RequestParam String email, HttpServletRequest request) {
        authorizeService.sendVerificationCode(email, request);
    }

    @PostMapping("/verification")
    public void verificateUser(@RequestParam(required = true) String email,
                               @RequestParam(required = true) String verificationToken,
                               HttpServletRequest request) {
        authorizeService.verificateUser(email, verificationToken);
    }
}
