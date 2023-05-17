package mmd.headless.service;

import mmd.headless.dto.MemberLogin;
import mmd.headless.dto.MemberRequest;
import mmd.headless.dto.ResultResponse;

public interface MemberService {

    Long newMember(MemberRequest form) throws Exception;

    ResultResponse login(MemberLogin login) throws Exception;
}