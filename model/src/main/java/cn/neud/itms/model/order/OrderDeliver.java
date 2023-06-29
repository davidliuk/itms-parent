package cn.neud.itms.model.order;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "OrderDeliver")
@TableName("order_deliver")
public class OrderDeliver extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送日期")
    @TableField("deliver_date")
    private Date deliverDate;

    @ApiModelProperty(value = "配送员id")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "物流公司id")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "物流公司名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "物流公司电话")
    @TableField("logistics_phone")
    private String logisticsPhone;

    @ApiModelProperty(value = "状态（0：默认，1：已发货，2：配送员收货）")
    @TableField("status")
    private Integer status;

}