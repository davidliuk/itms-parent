package cn.neud.itms.vo.order;

import cn.neud.itms.model.activity.CouponInfo;
import cn.neud.itms.vo.user.CourierAddressVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * OrderDetailActivity
 * </p>
 *
 * @author David
 */
@Data
public class OrderConfirmVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预生产订单号")
	private String orderNo;

	@ApiModelProperty(value = "用户对应的配送员地址")
	private CourierAddressVo courierAddressVo;
	
	@ApiModelProperty(value = "购物项列表")
	private List<CartInfoVo> carInfoVoList;

	@ApiModelProperty(value = "订单优惠券列表")
    private List<CouponInfo> couponInfoList;

	@ApiModelProperty(value = "促销优惠金额")
	private BigDecimal activityReduceAmount;

	@ApiModelProperty(value = "优惠券优惠金额")
	private BigDecimal couponReduceAmount;

	@ApiModelProperty(value = "购物车原始总金额")
	private BigDecimal originalTotalAmount;

	@ApiModelProperty(value = "最终总金额")
	private BigDecimal totalAmount;

}

