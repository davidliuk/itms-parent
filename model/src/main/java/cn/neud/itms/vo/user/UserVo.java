package cn.neud.itms.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "用户登录信息")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "账户")
    private String username;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "介绍")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "小程序open id")
    private String openId;

    @ApiModelProperty(value = "是否新用户")
    private Integer isNew;

    @ApiModelProperty(value = "当前登录用户分站id")
    private Long stationId;

    @ApiModelProperty(value = "仓库id")
    private Long wareId;

}