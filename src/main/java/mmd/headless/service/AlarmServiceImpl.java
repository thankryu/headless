package mmd.headless.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.AlarmRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmServiceImpl implements AlarmService {

    private final RestTemplate restTemplate;

    @Override
    public void sendAlarm(AlarmRequest form) {

    }
}