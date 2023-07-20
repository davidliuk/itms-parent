package cn.neud.itms.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttrGroupQueryVo {

    private Long id;

    @ApiModelProperty(value = "组名")
    private String name;

    @ApiModelProperty(value = "组名")
    private String remark;

}

