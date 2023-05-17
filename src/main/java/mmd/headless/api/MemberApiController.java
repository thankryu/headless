package mmd.headless.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import mmd.headless.dto.MemberLogin;
import mmd.headless.dto.MemberRequest;
import mmd.headless.dto.ResultResponse;
import mmd.headless.service.MemberService;
import mmd.headless.util.InValidErrorHandlingUtil;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    InValidErrorHandlingUtil inValidErrorHandlingUtil = new InValidErrorHandlingUtil();

    @PostMapping("/new")
    @Operation(
            summary = "headless 회원 가입", description = "headless 회원 가입을 진행합니다." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입에 성공하셨습니다.")
            }
    )
    public ResultResponse newMember(@Valid @RequestBody MemberRequest form , Errors errors) throws Exception {

        inValidErrorHandlingUtil.errorHandler(errors);
        Long memberId = memberService.newMember(form);

        return new ResultResponse(memberId, "success");
    }

    @Operation(
            summary = "headless 로그인", description = "headless 로그인을 진행합니다." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그인에 성공하셨습니다.")
            }
    )
    @PostMapping("/login")
    public ResultResponse login(@Valid @RequestBody MemberLogin login, Errors errors) throws Exception{

        inValidErrorHandlingUtil.errorHandler(errors);
        return memberService.login(login);
    }
}