package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "Invoice")
@TableName("invoice")
public class Invoice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "配送员ID")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "配送员名称")
    @TableField("courier_name")
    private String courierName;

    @ApiModelProperty(value = "分站ID")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;
}