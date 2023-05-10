package mmd.headless.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import mmd.headless.enums.JoinType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class MemberRequest {

    @Schema(title = "사용자 이름", example = "홍길동")
    @NotEmpty(message = "회원 이름은 필수 입니다")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String username;

    @Schema(title = "사용자 이메일", example = "thankryu@gmail.com")
    @NotEmpty(message = "이메일은 필수 입니다")
    @Email
    private String email;

    @Schema(title = "비밀번호", example = "abcdefgd1!")
    @NotEmpty(message = "비밀번호는 필수입니다")
    private String password;

    @Schema(title = "비밀번호", example = "010-0000-0000")
    @NotEmpty(message = "전화번호는 필수입니다")
    private String phone;

    private JoinType joinType;
}