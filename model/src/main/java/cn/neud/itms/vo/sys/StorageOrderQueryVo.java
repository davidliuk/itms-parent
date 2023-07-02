package cn.neud.itms.vo.sys;

import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.enums.WorkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StorageOrderQueryVo {

    @ApiModelProperty(value = "收货人姓名关键字")
    private String name;

    @ApiModelProperty(value = "配送员Id")
    private Long courierId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "仓库ID")
    private Long wareId;

    @ApiModelProperty(value = "分站ID")
    private Long stationId;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "任务单类型")
    private WorkType workType;

    @ApiModelProperty(value = "任务单状态")
    private WorkStatus workStatus;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

