package cn.neud.itms.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "OrderDeliver")
public class OrderDeliverVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deliverDate;

    @ApiModelProperty(value = "配送员id")
    private Long courierId;

    @ApiModelProperty(value = "物流公司id")
    private Long logisticsId;

    @ApiModelProperty(value = "物流公司名称")
    private String logisticsName;

    @ApiModelProperty(value = "物流公司电话")
    private String logisticsPhone;

    @ApiModelProperty(value = "状态（0：默认，1：已发货，2：配送员收货）")
    private Integer status;

}