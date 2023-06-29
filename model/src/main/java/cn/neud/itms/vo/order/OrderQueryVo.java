package cn.neud.itms.vo.order;

import cn.neud.itms.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderQueryVo {


    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "收货人信息")
    private String receiver;

    @ApiModelProperty(value = "订单状态")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "配送员id")
    private Long courierId;

    @ApiModelProperty(value = "仓库id")
    private Long wareId;

    @ApiModelProperty(value = "创建时间")
    private String createTimeBegin;
    private String createTimeEnd;

}

