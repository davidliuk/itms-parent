package cn.neud.itms.model.sys;

import cn.neud.itms.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "验货单")
@TableName("check_order")
public class PurchaseOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库ID")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "商品sku编号")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "商品sku名字")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "商品sku图片")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "商品sku价格")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "商品购买的数量")
    @TableField("sku_num")
    private Integer skuNum;

    @ApiModelProperty(value = "供应商ID")
    @TableField("supplier_id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    @TableField("supplier_name")
    private Long supplierName;

}