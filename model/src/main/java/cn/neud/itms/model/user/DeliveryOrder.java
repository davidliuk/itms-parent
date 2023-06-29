package cn.neud.itms.model.user;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "DeliveryOrder")
@TableName("delivery_order")
public class DeliveryOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "配送员ID")
	@TableField("courier_id")
	private Long courierId;

	@ApiModelProperty(value = "分站ID")
	@TableField("station_id")
	private Long stationId;

	@ApiModelProperty(value = "订单ID")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "配送类型")
	@TableField("delivery_type")
//	private DeliveryType deliveryType;
	private Integer deliveryType;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;
}