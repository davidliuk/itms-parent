package cn.neud.itms.model.user;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "CourierAccount")
@TableName("courier_account")
public class CourierAccount extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "配送员ID")
	@TableField("courier_id")
	private Long courierId;

	@ApiModelProperty(value = "总收益, 可能有部分余额因为订单未结束而不能提现")
	@TableField("total_amount")
	private BigDecimal totalAmount;

	@ApiModelProperty(value = "可提现余额")
	@TableField("available_amount")
	private BigDecimal availableAmount;

	@ApiModelProperty(value = "冻结余额")
	@TableField("frozen_amount")
	private BigDecimal frozenAmount;

}