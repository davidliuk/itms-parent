package cn.neud.itms.model.user;

import cn.neud.itms.enums.BillType;
import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "CourierBill")
@TableName("courier_bill")
public class CourierBill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送员ID")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "账单类型")
    @TableField("bill_type")
    private BillType billType;

    @ApiModelProperty(value = "业务编号")
    @TableField("business_no")
    private String businessNo;

    @ApiModelProperty(value = "交易时间")
    @TableField("bill_time")
    private Date billTime;

    @ApiModelProperty(value = "账单金额")
    @TableField("bill_amount")
    private BigDecimal billAmount;

    @ApiModelProperty(value = "账单编号")
    @TableField("bill_no")
    private String billNo;

    @ApiModelProperty(value = "账单描述")
    @TableField("bill_desc")
    private String billDesc;

    @ApiModelProperty(value = "交易前资金余额")
    @TableField("balance_before")
    private BigDecimal balanceBefore;

    @ApiModelProperty(value = "交易后资金余额")
    @TableField("balance_after")
    private BigDecimal balanceAfter;

    @ApiModelProperty(value = "账单状态")
    @TableField("bill_status")
    private Integer billStatus;

}