package cn.neud.itms.vo.user;

import cn.neud.itms.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserQueryVo {

    @ApiModelProperty(value = "账户")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型（0:用户；1:配送员）")
    private UserType userType;

    @ApiModelProperty(value = "身份证号码")
    private String idNo;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;
}
