package cn.neud.itms.model.user;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CourierInfo")
@TableName("courier_info")
public class CourierInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "分站id")
    @TableField("station_id")
    private Long stationId;

    @ApiModelProperty(value = "分站名称")
    @TableField("station_name")
    private String stationName;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "身份证")
    @TableField("id_no")
    private String idNo;

    @ApiModelProperty(value = "身份证图片路径")
    @TableField("id_no_url1")
    private String idNoUrl1;

    @ApiModelProperty(value = "身份证图片路径")
    @TableField("id_no_url2")
    private String idNoUrl2;

    @ApiModelProperty(value = "任务数")
    @TableField("work_num")
    private Integer workNum;

}