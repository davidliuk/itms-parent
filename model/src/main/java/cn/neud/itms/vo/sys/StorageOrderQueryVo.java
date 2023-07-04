package cn.neud.itms.vo.sys;

import cn.neud.itms.enums.StorageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StorageOrderQueryVo {

    @ApiModelProperty(value = "仓库ID")
    private Long wareId;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "分站ID")
    private Long stationId;

    @ApiModelProperty(value = "分站名称")
    private String stationName;

    @ApiModelProperty(value = "供应商ID")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "商品sku编号")
    private Long skuId;

    @ApiModelProperty(value = "商品sku名字")
    private String skuName;

    @ApiModelProperty(value = "类型")
    private StorageType storageType;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

