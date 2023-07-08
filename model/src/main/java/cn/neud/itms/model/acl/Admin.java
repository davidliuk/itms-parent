package cn.neud.itms.model.acl;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author David
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "用户")
@TableName("admin")
public class Admin extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "分站id")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<String> roles;

    @ApiModelProperty(value = "权限列表")
    @TableField(exist = false)
    private List<String> permissions;
}
