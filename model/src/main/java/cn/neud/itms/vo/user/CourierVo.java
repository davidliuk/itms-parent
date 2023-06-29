package cn.neud.itms.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Courier")
public class CourierVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "分站id")
	private Long stationId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "手机号码")
	private String phone;

	@ApiModelProperty(value = "身份证")
	private String idNo;

	@ApiModelProperty(value = "身份证图片路径")
	private String idNoUrl1;

	@ApiModelProperty(value = "身份证图片路径")
	private String idNoUrl2;

}