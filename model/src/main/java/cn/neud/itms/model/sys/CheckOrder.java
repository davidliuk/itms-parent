package cn.neud.itms.model.sys;

import cn.neud.itms.enums.CheckStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.base.BaseEntity;
import cn.neud.itms.model.order.OrderInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "验货单")
@TableName("check_order")
public class CheckOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "任务单ID")
    @TableField("work_order_id")
    private Long workOrderId;

    @ApiModelProperty(value = "订单")
    @TableField(exist = false)
    private OrderInfo orderInfo;

    @ApiModelProperty(value = "仓库ID")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "分站ID")
    @TableField("station_id")
    private Long stationId;

//    @ApiModelProperty(value = "商品sku编号")
//    @TableField("sku_id")
//    private Long skuId;
//
//    @ApiModelProperty(value = "商品sku名字")
//    @TableField("sku_name")
//    private String skuName;
//
//    @ApiModelProperty(value = "商品sku图片")
//    @TableField("img_url")
//    private String imgUrl;
//
//    @ApiModelProperty(value = "商品sku价格")
//    @TableField("sku_price")
//    private BigDecimal skuPrice;
//
//    @ApiModelProperty(value = "商品购买的数量")
//    @TableField("sku_num")
//    private Integer skuNum;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private CheckStatus status;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private WorkType type;

    @ApiModelProperty(value = "出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("out_time")
    private Date outTime;

    @ApiModelProperty(value = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("in_time")
    private Date inTime;

}