package cn.neud.itms.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRegisterVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱账户")
    private String email;

    @ApiModelProperty(value = "验证码")
    private String code;

}
