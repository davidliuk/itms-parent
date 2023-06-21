package cn.neud.itms.vo.product;

import cn.neud.itms.model.product.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SkuInfoVo extends SkuInfo {

	@ApiModelProperty(value = "海报列表")
	private List<SkuPoster> skuPosterList;

	@ApiModelProperty(value = "属性值")
	private List<SkuAttrValue> skuAttrValueList;

	@ApiModelProperty(value = "图片")
	private List<SkuImage> skuImageList;

	@ApiModelProperty(value = "各仓库库存信息")
	private List<SkuWare> skuWareList;

}
