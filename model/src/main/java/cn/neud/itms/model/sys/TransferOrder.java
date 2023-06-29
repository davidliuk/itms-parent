package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "调拨单")
@TableName("transfer_order")
public class TransferOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单ID")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "仓库ID")
	@TableField("ware_id")
	private Long wareId;

	@ApiModelProperty(value = "分站ID")
	@TableField("station_id")
	private Long stationId;

	@ApiModelProperty(value = "分站名称")
	@TableField("station_name")
	private Long stationName;

	@ApiModelProperty(value = "物流公司id")
	@TableField("logistics_id")
	private Long logisticsId;

	@ApiModelProperty(value = "物流公司名称")
	@TableField("logistics_name")
	private String logisticsName;

	@ApiModelProperty(value = "物流公司电话")
	@TableField("logistics_phone")
	private String logisticsPhone;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;
	
	@ApiModelProperty(value = "出库时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("out_time")
	private Date outTime;
	
	@ApiModelProperty(value = "入库时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField("in_time")
	private Date inTime;

}