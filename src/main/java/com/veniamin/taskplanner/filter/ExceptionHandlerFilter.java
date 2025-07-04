package com.veniamin.taskplanner.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veniamin.taskplanner.dto.responce.BusinessExceptionRespDTO;
import com.veniamin.taskplanner.exception.AuthorizeException;
import com.veniamin.taskplanner.exception.BadRequestException;
import com.veniamin.taskplanner.exception.NotFoundException;
import com.veniamin.taskplanner.exception.RestExceptionHandler;
import com.veniamin.taskplanner.utils.LogsUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final String ENCODE = "UTF-8";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private final RestExceptionHandler exceptionHandler;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding(ENCODE);
        response.setContentType(CONTENT_TYPE);
        try {
            filterChain.doFilter(request, response);
        } catch (NotFoundException e) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(e, request, response);
            response.setStatus(404);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));

        } catch (AccessDeniedException e) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(e, request, response);
            response.setStatus(403);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } catch (AuthenticationException e) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(e, request, response);
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } catch (AuthorizeException e) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(e, request, response);
            response.setStatus(401);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } catch (BadRequestException e) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(e, request, response);
            response.setStatus(400);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        } catch (Throwable throwable) {
            BusinessExceptionRespDTO responseBody = exceptionHandler.handleExceptions(throwable, request, response);
            response.setStatus(500);
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        }

    }

}
