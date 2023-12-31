package cn.neud.itms.vo.order;

import cn.neud.itms.vo.user.AddressVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderSubmitVo {

    @ApiModelProperty(value = "使用预生产订单号防重")
    private String orderNo;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "库房id")
    private Long wareId;

    @ApiModelProperty(value = "分站id")
    private Long stationId;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "下单选中的优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "地址")
    private AddressVo address;

//	@ApiModelProperty("购买的sku信息")
//	private List<Long> skuIdList;
}

