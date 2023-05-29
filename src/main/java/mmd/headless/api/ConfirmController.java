package mmd.headless.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.ResultResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/confirm")
@Slf4j
public class ConfirmController {

    @Operation(
            summary = "인증 요청", description = "인증을 요청합니다" ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "인증에 성공하였습니다.")
            }
    )
    @PostMapping("/sendConfirm")
    public ResultResponse sendConfirm(@RequestHeader("headLess") String headerValue){

        log.info("headerValue = "+headerValue);

        return new ResultResponse("", "success");
    }
}