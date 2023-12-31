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
 * 角色
 * </p>
 *
 * @author David
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "角色")
@TableName("role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "角色编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

}
