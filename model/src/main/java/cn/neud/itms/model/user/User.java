package cn.neud.itms.model.user;

import cn.neud.itms.enums.UserType;
import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "User")
@TableName("user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "用户类型（0:用户；1:配送员）")
    @TableField("user_type")
    private UserType userType;

    @ApiModelProperty(value = "会员头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "身份证号码")
    @TableField("id_no")
    private String idNo;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "小程序open id")
    @TableField("open_id")
    private String openId;

    @ApiModelProperty(value = "微信开放平台unionID")
    @TableField("union_id")
    private String unionId;

    @ApiModelProperty(value = "是否新用户")
    @TableField("is_new")
    private Integer isNew;

    @ApiModelProperty(value = "配送员信息")
    @TableField(exist = false)
    private CourierInfo courierInfo;

    @ApiModelProperty(value = "地址列表")
    @TableField(exist = false)
    private List<Address> addresses;
}
