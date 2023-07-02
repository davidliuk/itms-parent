package cn.neud.itms.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourierQueryVo {

    @ApiModelProperty(value = "分站ID")
    private Long stationId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号")
    private String idNo;

}

