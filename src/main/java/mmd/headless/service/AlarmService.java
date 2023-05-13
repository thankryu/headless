package mmd.headless.service;

import mmd.headless.dto.AlarmRequest;

public interface AlarmService {

    void sendAlarm(AlarmRequest form);

}