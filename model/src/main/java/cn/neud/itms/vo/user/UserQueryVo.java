package cn.neud.itms.vo.user;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class UserQueryVo {
	
	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "身份证号码")
	private String idNo;

	@ApiModelProperty(value = "性别")
	private String gender;

	@ApiModelProperty(value = "电话号码")
	private String phone;
}
