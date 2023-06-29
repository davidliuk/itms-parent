package cn.neud.itms.model.order;

import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.ProcessStatus;
import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员_id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty(value = "使用的优惠券")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty(value = "订单总额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "促销活动金额")
    @TableField("activity_amount")
    private BigDecimal activityAmount;

    @ApiModelProperty(value = "优惠券")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "原价金额")
    @TableField("original_total_amount")
    private BigDecimal originalTotalAmount;

    @ApiModelProperty(value = "运费")
    @TableField("freight_fee")
    private BigDecimal freightFee;

    @ApiModelProperty(value = "减免运费")
    @TableField("freight_fee_reduce")
    private BigDecimal freightFeeReduce;

    @ApiModelProperty(value = "可退款日期（签收后1天）")
    @TableField("refundable_time")
    private Date refundableTime;

    @ApiModelProperty(value = "支付方式【1->微信】")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty(value = "订单来源[0->小程序；1->H5]")
    @TableField("source_type")
    private Integer sourceType;

    @ApiModelProperty(value = "订单状态【0->待付款；1->待发货；2->待配送员收货；3->待用户收货，已完成；-1->已取消】")
    @TableField("order_status")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "进度状态")
    @TableField("process_status")
    private ProcessStatus processStatus;

    @ApiModelProperty(value = "物流公司id")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "物流公司名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "物流公司手机")
    @TableField("logistics_phone")
    private String logisticsPhone;

    @ApiModelProperty(value = "配送员id")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "配送员名称")
    @TableField("courier_name")
    private String courierName;

    @ApiModelProperty(value = "配送员手机")
    @TableField("courier_phone")
    private String courierPhone;

    @ApiModelProperty(value = "收货人姓名")
    @TableField("receiver_name")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    @TableField("receiver_phone")
    private String receiverPhone;

    @ApiModelProperty(value = "收货人邮编")
    @TableField("receiver_post_code")
    private String receiverPostCode;

    @ApiModelProperty(value = "省份/直辖市")
    @TableField("receiver_province")
    private String receiverProvince;

    @ApiModelProperty(value = "城市")
    @TableField("receiver_city")
    private String receiverCity;

    @ApiModelProperty(value = "区")
    @TableField("receiver_district")
    private String receiverDistrict;

    @ApiModelProperty(value = "详细地址")
    @TableField("receiver_address")
    private String receiverAddress;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("payment_time")
    private Date paymentTime;

    @ApiModelProperty(value = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty(value = "配送员提货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("take_time")
    private Date takeTime;

    @ApiModelProperty(value = "确认收货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("receive_time")
    private Date receiveTime;

    @ApiModelProperty(value = "订单备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "取消订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("cancel_time")
    private Date cancelTime;

    @ApiModelProperty(value = "取消订单原因")
    @TableField("cancel_reason")
    private String cancelReason;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "仓库名称")
    @TableField("ware_name")
    private Long wareName;

    @ApiModelProperty(value = "分站id")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "分站名称")
    @TableField("station_name")
    private Long stationName;

    @ApiModelProperty(value = "配送员佣金")
    @TableField("commission_amount")
    private BigDecimal commissionAmount;

    @ApiModelProperty(value = "订单项列表")
    @TableField(exist = false)
    private List<OrderItem> orderItemList;

}