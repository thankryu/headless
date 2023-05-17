package mmd.headless.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberLogin {

    @Schema(title = "이메일", example = "thankryu@gmail.com")
    @NotEmpty(message = "이메일은 필수 입니다")
    @Email
    public String email;

    @Schema(title = "비밀번호", example = "1q2w3e4r5t")
    @NotEmpty(message = "비밀번호는 필수입니다")
    public String password;
}