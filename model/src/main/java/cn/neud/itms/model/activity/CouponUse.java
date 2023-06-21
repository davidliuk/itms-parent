package cn.neud.itms.model.activity;

import cn.neud.itms.enums.CouponStatus;
import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * CouponUse
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "优惠券领取记录表")
@TableName("coupon_use")
public class CouponUse extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "购物券ID")
	@TableField("coupon_id")
	private Long couponId;

	@ApiModelProperty(value = "用户ID")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "订单ID")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "购物券状态")
	@TableField("coupon_status")
	private CouponStatus couponStatus;

	@ApiModelProperty(value = "领券时间")
	@TableField("get_time")
	private Date getTime;

	@ApiModelProperty(value = "使用时间")
	@TableField("using_time")
	private Date usingTime;

	@ApiModelProperty(value = "支付时间")
	@TableField("used_time")
	private Date usedTime;

	@ApiModelProperty(value = "过期时间")
	@TableField("expire_time")
	private Date expireTime;

}

