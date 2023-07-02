package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "RegionStation")
@TableName("region_station")
public class RegionStation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地区id")
    @TableField("region_id")
    private Long regionId;

    @ApiModelProperty(value = " ")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private Long province;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private Long city;

    @ApiModelProperty(value = "区域")
    @TableField("district")
    private Long district;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private Double latitude;

    @ApiModelProperty(value = "门店照片")
    @TableField("store_path")
    private String storePath;

    @ApiModelProperty(value = "营业时间")
    @TableField("work_time")
    private String workTime;

    @ApiModelProperty(value = "营业状态")
    @TableField("work_status")
    private Integer workStatus;

}