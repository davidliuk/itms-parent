package cn.neud.itms.model.product;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "SkuWare")
@TableName("sku_ware")
public class SkuWare extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类id")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "仓库")
	@TableField("ware_id")
	private Long wareId;
	
	@ApiModelProperty(value = "库存")
	@TableField("stock")
	private Integer stock;

	@ApiModelProperty(value = "锁定库存")
	@TableField("lock_stock")
	private Integer lockStock;

	@ApiModelProperty(value = "预警库存")
	@TableField("low_stock")
	private Integer lowStock;

	@ApiModelProperty(value = "销量")
	@TableField("sale")
	private Integer sale;
}