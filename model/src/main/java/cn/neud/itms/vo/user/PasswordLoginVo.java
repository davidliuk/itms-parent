package cn.neud.itms.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录信息")
public class PasswordLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}