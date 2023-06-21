package cn.neud.itms.model.user;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CourierUser")
@TableName("courier_user")
public class CourierUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "配送员ID")
	@TableField("courier_id")
	private String courierId;

	@ApiModelProperty(value = "userId")
	@TableField("user_id")
	private Long userId;

}