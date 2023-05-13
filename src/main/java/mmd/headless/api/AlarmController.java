package mmd.headless.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import mmd.headless.dto.AlarmRequest;
import mmd.headless.dto.ResultResponse;
import mmd.headless.service.AlarmService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(
            summary = "알림톡 발송", description = "알림톡을 발송합니다." ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "알림톡을 발송합니다.")
            }
    )
    @PostMapping("/sendAlarm")
    public ResultResponse sendAlarm(@Valid @RequestBody AlarmRequest form , Errors errors){

        alarmService.sendAlarm(form);

        return new ResultResponse("", "success");
    }
}