package cn.neud.itms.vo.product;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkuWareQueryVo implements Serializable {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "商品名称")
    private String skuName;

    @ApiModelProperty(value = "仓库")
    private Long wareId;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "销量")
    private Integer sale;

//    @ApiModelProperty(value = "sku类型")
//    private Integer skuType;
//
//    @ApiModelProperty(value = "更新的库存数量")
//    private Integer stockNum;

}

