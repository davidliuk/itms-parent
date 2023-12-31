//
//
package cn.neud.itms.vo.acl;

import cn.neud.itms.model.acl.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户查询实体
 * </p>
 *
 * @author David
 * @since 2019-11-08
 */
@Data
@ApiModel(description = "用户查询实体")
public class AdminQueryVo extends Admin {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String name;

}
