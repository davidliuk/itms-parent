package cn.neud.itms.model.sys;

import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.base.BaseEntity;
import cn.neud.itms.model.order.OrderInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "WorkOrder")
@TableName("work_order")
public class WorkOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "配送员ID")
    @TableField("courier_id")
    private Long courierId;

    @ApiModelProperty(value = "仓库ID")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "分站ID")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "姓名")
    @TableField("station_name")
    private String stationName;

    @ApiModelProperty(value = "物流公司id")
    @TableField("logistics_id")
    private Long logisticsId;

    @ApiModelProperty(value = "物流公司名称")
    @TableField("logistics_name")
    private String logisticsName;

    @ApiModelProperty(value = "物流公司电话")
    @TableField("logistics_phone")
    private String logisticsPhone;

    @ApiModelProperty(value = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮编")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("district")
    private String district;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "配送类型")
    @TableField("work_type")
    //	private DeliveryType deliveryType;
    private WorkType workType;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private WorkStatus workStatus;

    @ApiModelProperty(value = "订单")
    @TableField(exist = false)
    private OrderInfo orderInfo;

}