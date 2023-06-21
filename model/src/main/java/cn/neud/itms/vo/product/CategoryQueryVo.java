package cn.neud.itms.vo.product;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class CategoryQueryVo {
	
	@ApiModelProperty(value = "分类名称")
	private String name;

}

