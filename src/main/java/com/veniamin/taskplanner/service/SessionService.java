package com.veniamin.taskplanner.service;

import com.veniamin.taskplanner.model.UserSession;

public interface SessionService {

    UserSession saveNewSession(Long userId);

}
