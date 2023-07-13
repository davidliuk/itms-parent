package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Ware")
@TableName("ware")
public class Ware extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "管理员ID")
    @TableField("admin_id")
    private Long adminId;

    @ApiModelProperty(value = "管理员名称")
    @TableField("admin_name")
    private String adminName;

    @ApiModelProperty(value = "管理员邮箱")
    @TableField("admin_email")
    private String adminEmail;

    @ApiModelProperty(value = "管理员ID")
    @TableField("region_id")
    private Long regionId;

    @ApiModelProperty(value = "省code")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "城市code")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区域code")
    @TableField("district")
    private String district;

    @ApiModelProperty(value = "详细地址")
    @TableField("detail_address")
    private String detailAddress;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

}