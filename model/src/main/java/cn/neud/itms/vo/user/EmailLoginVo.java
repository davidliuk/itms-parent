package cn.neud.itms.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户邮件登录信息")
public class EmailLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱账户")
    private String email;

    @ApiModelProperty(value = "验证码")
    private String code;
}