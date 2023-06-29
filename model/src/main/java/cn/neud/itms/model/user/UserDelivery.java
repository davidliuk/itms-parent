package cn.neud.itms.model.user;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "UserDelivery")
@TableName("user_delivery")
public class UserDelivery extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "配送员id")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "是否默认")
    @TableField("is_default")
    private Integer isDefault;

}