package mmd.headless.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mmd.headless.dto.ResultResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

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
    public ResultResponse sendConfirm(@RequestHeader("headLess") String headerValue, @RequestBody ConfirmRequestDto dto){

        log.info("headerValue = "+headerValue);

        return new ResultResponse("", "success");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ConfirmRequestDto{

        @Schema(title = "비밀번호", example = "010-0000-0000")
        @NotEmpty(message = "전화번호는 필수입니다")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$",message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
        private String phone;
    }

}