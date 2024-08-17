package com.veniamin.taskplanner.service.impl;

import com.veniamin.taskplanner.dto.request.RegisterReqDTO;
import com.veniamin.taskplanner.dto.request.UserAuthorizeReqDTO;
import com.veniamin.taskplanner.dto.responce.TokenRespDTO;
import com.veniamin.taskplanner.exception.BadRequestException;
import com.veniamin.taskplanner.exception.NotFoundException;
import com.veniamin.taskplanner.exception.errors.AuthorizedError;
import com.veniamin.taskplanner.exception.errors.BadRequestError;
import com.veniamin.taskplanner.exception.errors.NotFoundError;
import com.veniamin.taskplanner.filter.jwt.JwtUtils;
import com.veniamin.taskplanner.model.User;
import com.veniamin.taskplanner.repository.UserRepo;
import com.veniamin.taskplanner.service.AuthorizeService;
import com.veniamin.taskplanner.utils.LogsUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizeServiceImpl implements AuthorizeService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final MailServiceImpl mailService;

    private final LogsUtils logsUtils;

    private final SessionServiceImpl sessionService;

    private final JwtUtils jwtUtils;

    private static final Logger loggerAuth = LoggerFactory.getLogger("authLogger");


    @Override
    public ResponseEntity<TokenRespDTO> authorizeUser(UserAuthorizeReqDTO userAuthorizeDTO) {
        String userEmail = userAuthorizeDTO.getEmail();
        String userPassword = userAuthorizeDTO.getPassword();
        Optional<User> userOptional = userRepo.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new BadCredentialsException(AuthorizedError.USER_WITH_THIS_EMAIL_NOT_FOUND.getMessage());
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            throw new BadCredentialsException(AuthorizedError.NOT_CORRECT_PASSWORD.getMessage());
        }
        checkUserCanAuthorize(user);
        user.setRefreshToken(jwtUtils.generateRandomSequence());
        String jwtToken = jwtUtils.generateToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        userRepo.saveAndFlush(user);

        TokenRespDTO tokenDTO = new TokenRespDTO();
        tokenDTO.setAccessToken("Bearer " + jwtToken);
        tokenDTO.setRefreshToken("Bearer " + refreshToken);

        logsUtils.log(loggerAuth, "Authorize user - " + userEmail);

        sessionService.saveNewSession(user.getId());

        return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public void registerUser(@Valid RegisterReqDTO registerDTO, HttpServletRequest request) {
        if (userRepo.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new BadRequestException(BadRequestError.USER_ALREADY_EXISTS);
        }
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setSurname(registerDTO.getSurname());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setIsEmailVerificated(Boolean.FALSE);
        user.setToken(generateValidatingToken());
        userRepo.save(user);

        mailService.sendUserVerificationMail(user, request);

        logsUtils.log(loggerAuth, "Register user - " + registerDTO.getEmail());
    }

    @Override
    public void sendVerificationCode(String email, HttpServletRequest request) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException(NotFoundError.USER_NOT_FOUND));
        if (user.getIsEmailVerificated()) {
            throw new BadRequestException(BadRequestError.USER_ALREADY_VERIFICATED);
        }
        mailService.sendUserVerificationMail(user, request);

        logsUtils.log(loggerAuth, "Repeate send verificate code to email - " + user.getEmail());
    }

    @Override
    @Transactional
    public void verificateUser(String email, String verificationToken) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(NotFoundError.USER_NOT_FOUND);
        }
        User user = optionalUser.get();

        if (user.getIsEmailVerificated()) {
            throw new BadRequestException(BadRequestError.USER_ALREADY_VERIFICATED);
        }

        if (null == user.getToken() || !user.getToken().equals(verificationToken)) {
            throw new BadRequestException(BadRequestError.NOT_CORRECT_VERIFICATION_CODE);
        }

        user.setToken(null);
        user.setIsEmailVerificated(Boolean.TRUE);
        userRepo.save(user);

        logsUtils.log(loggerAuth, "User verification - " + email);
    }


    private void checkUserCanAuthorize(User user) {
        if (user.getIsDeleted().equals(Boolean.TRUE)) {
            throw new AccessDeniedException(AuthorizedError.USER_IS_DELETED.getMessage());
        }
        if (!user.getIsEmailVerificated()) {
            throw new AccessDeniedException(AuthorizedError.USER_NOT_VERIFY.getMessage());
        }
        if (user.getIsActive().equals(Boolean.FALSE)) {
            throw new AccessDeniedException(AuthorizedError.USER_IS_NOT_ACTIVE.getMessage());
        }
    }

    private String generateValidatingToken() {
        return RandomStringUtils.randomAlphanumeric(50);
    }
}
