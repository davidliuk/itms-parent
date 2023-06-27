package cn.neud.itms.model.activity;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * ActivitySku
 * </p>
 *
 * @author David
 */
@Data
@ApiModel(description = "ActivitySku")
@TableName("activity_sku")
public class ActivitySku extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id ")
    @TableField("activity_id")
    private Long activityId;

    @ApiModelProperty(value = "sku_id")
    @TableField("sku_id")
    private Long skuId;

//	@TableField(exist = false)
//	private SkuInfo skuInfo;

}
