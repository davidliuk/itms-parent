package cn.neud.itms.vo.order;

import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.OrderType;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderInfoQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会员_id")
    private Long userId;

    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "订单类型")
    @TableField("order_type")
    private OrderType orderType;

    @ApiModelProperty(value = "物流公司名称")
    private String logisticsName;

    @ApiModelProperty(value = "配送员名称")
    private String courierName;

}
