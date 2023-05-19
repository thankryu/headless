package mmd.headless.service;

import mmd.headless.dto.SmsRequest;

public interface AlarmService {

    void sendSMS(SmsRequest form) throws Exception;

}