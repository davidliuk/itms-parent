package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Receipt")
@TableName("receipt")
public class Receipt extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "电话号码")
    @TableField("user_phone")
    private String userPhone;

    @ApiModelProperty(value = "分站ID")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "分站名称")
    @TableField("station_name")
    private String stationName;

    @ApiModelProperty(value = "分站电话号码")
    @TableField("station_phone")
    private String stationPhone;

    @ApiModelProperty(value = "订单ID")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "邮编")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "送货要求")
    @TableField("requirement")
    private String requirement;

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

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "用户满意度")
    @TableField("mark")
    private Integer mark;

    @ApiModelProperty(value = "反馈")
    @TableField("feedback")
    private String feedback;

    @ApiModelProperty(value = "是否要发票")
    @TableField("take_invoice")
    private Boolean takeInvoice;

}
