package com.veniamin.taskplanner.utils;

import com.veniamin.taskplanner.model.User;
import com.veniamin.taskplanner.repository.UserRepo;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LogsUtils {

    @Autowired
    private UserRepo userRepo;

    public void log(Logger logger, String message) {
        putUserId();
        ThreadContext.put("id", UUID.randomUUID().toString());
        logger.info(message);
    }

    public void log(Logger logger, String message, Throwable throwable) {
        putUserId();
        ThreadContext.put("id", UUID.randomUUID().toString());
        logger.error(message, throwable);
    }

    private void putUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && !"anonymousUser".equals(authentication.getName())) {
            User user = userRepo.findByEmail(authentication.getName()).get();
            ThreadContext.put("userId", user.getId().toString());
        }
    }
}
