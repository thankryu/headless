package mmd.headless.service;

import mmd.headless.dto.MemberRequest;

public interface MemberService {

    Long newMember(MemberRequest form);
}