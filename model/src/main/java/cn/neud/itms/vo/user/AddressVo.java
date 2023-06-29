package cn.neud.itms.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * UserAddress
 * </p>
 *
 * @author David
 */
@Data
@ApiModel(description = "用户地址")
public class AddressVo {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "仓库id")
    private Long wareId;

    @ApiModelProperty(value = "区域ID")
    @TableField("region_id")
    private Long regionId;

    @ApiModelProperty(value = "分站id")
    private Long stationId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    @ApiModelProperty(value = "是否默认")
    @TableField("is_default")
    private Integer isDefault;
}
