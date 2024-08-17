package com.veniamin.taskplanner.service.impl;

import com.veniamin.taskplanner.configuration.RestTemplateConfig;
import com.veniamin.taskplanner.constant.IpAddressesConstant;
import com.veniamin.taskplanner.dto.responce.IpStackResponse;
import com.veniamin.taskplanner.exception.NotFoundException;
import com.veniamin.taskplanner.exception.errors.NotFoundError;
import com.veniamin.taskplanner.model.User;
import com.veniamin.taskplanner.model.UserSession;
import com.veniamin.taskplanner.repository.UserRepo;
import com.veniamin.taskplanner.repository.UserSessionRepository;
import com.veniamin.taskplanner.service.SessionService;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepo userRepository;

    private final UserSessionRepository userSessionRepository;

    private final RestTemplateConfig restTemplateConfig;

    @Value("${ipstack.access.key}")
    private String accessKey;


    @Transactional
    public UserSession saveNewSession(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NotFoundError.USER_NOT_FOUND));

        endOldSessions(userId);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uAgent = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(uAgent);
        OperatingSystem os = userAgent.getOperatingSystem();
        String ip = request.getRemoteAddr();
        String cityName = this.getCityFromIp(ip);
        if (cityName == null) {
            cityName = "";
        }

        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setIpAddress(ip);
        userSession.setCity(cityName);
        userSession.setUserAgent(uAgent);
        userSession.setOsName(os.getName());
        userSession.setDeviceType(os.getDeviceType().getName());
        userSession.setStartTime(LocalDateTime.now());
        userSession = userSessionRepository.save(userSession);
        return userSession;
    }

    @Transactional
    public void endOldSessions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NotFoundError.USER_NOT_FOUND));

        List<UserSession> userSessions = userSessionRepository.findByUserAndEndTime(user, null);
        LocalDateTime now = LocalDateTime.now();
        for (UserSession session : userSessions) {
            session.setEndTime(now);
            userSessionRepository.save(session);
        }
    }

    private String getCityFromIp(String ip) {
        String url = IpAddressesConstant.API_IPSTACK_URL + ip + IpAddressesConstant.ACCESS_KEY_GET_PARAMETER + accessKey;
        RestTemplate restTemplate = restTemplateConfig.getRestTemplate();
        return restTemplate.getForObject(url, IpStackResponse.class).getCity();
    }
}
