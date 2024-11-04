package com.veniamin.taskplanner.filter;

import com.veniamin.taskplanner.constant.PathConstants;
import com.veniamin.taskplanner.exception.BadRequestException;
import com.veniamin.taskplanner.exception.errors.BadRequestError;
import com.veniamin.taskplanner.filter.jwt.JwtUtils;
import com.veniamin.taskplanner.model.User;
import com.veniamin.taskplanner.repository.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader("Refresh") != null) {
            String refreshToken = request.getHeader("Refresh");
            refreshToken = refreshToken.replace("Bearer", "");
            if (!jwtUtils.validateRefreshToken(refreshToken)) {
                throw new BadRequestException(BadRequestError.NOT_CORRECT_REFRESH_TOKEN);
            }

            String email = jwtUtils.getUserEmailFromToken(refreshToken);
            User user = userRepo.findByEmail(email).get();
            user.setRefreshToken(jwtUtils.generateRandomSequence());
            refreshToken = jwtUtils.generateRefreshToken(user);

            String accessToken = jwtUtils.generateToken(user);
            response.addHeader("Authorization", "Bearer " + accessToken);
            response.addHeader("Refresh", "Bearer " + refreshToken);

            userRepo.saveAndFlush(user);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().equals(PathConstants.AUTHORIZE_CONTROLLER_PATH + "/refreshToken");
    }

}
