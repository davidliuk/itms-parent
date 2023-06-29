//
//
package cn.neud.itms.model.acl;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色权限
 * </p>
 *
 * @author David
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色权限")
@TableName("role_permission")
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "roleId")
    @TableField("role_id")
    private Long roleId;

    @ApiModelProperty(value = "permissionId")
    @TableField("permission_id")
    private Long permissionId;

}
