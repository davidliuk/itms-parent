package cn.neud.itms.vo.activity;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SeckillQueryVo {
	
	@ApiModelProperty(value = "活动标题")
	private String title;

	@ApiModelProperty(value = "上下线状态")
	private Integer status;


}

