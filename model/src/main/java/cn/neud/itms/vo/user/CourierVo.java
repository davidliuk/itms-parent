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
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "分站id")
	@TableField("station_id")
	private Long stationId;

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

}