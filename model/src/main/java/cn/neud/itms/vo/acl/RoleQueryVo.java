//
//
package cn.neud.itms.vo.acl;

import cn.neud.itms.model.acl.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色查询实体
 * </p>
 *
 * @author David
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色查询实体")
public class RoleQueryVo extends Role {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

}

