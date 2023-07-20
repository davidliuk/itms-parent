package cn.neud.itms.vo.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryQueryVo {

    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    private Integer status;

}

