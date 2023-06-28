package cn.neud.itms.model.product;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "RegionStation")
@TableName("region_station")
public class Supplier extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String regionName;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮编")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区域")
    @TableField("district")
    private String district;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private Double latitude;

    @ApiModelProperty(value = "营业时间")
    @TableField("work_time")
    private String workTime;

    @ApiModelProperty(value = "营业状态")
    @TableField("work_status")
    private Integer workStatus;

}