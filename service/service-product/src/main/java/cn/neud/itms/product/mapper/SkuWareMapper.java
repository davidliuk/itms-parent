package cn.neud.itms.product.mapper;

import cn.neud.itms.model.product.SkuWare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * spu属性值 Mapper 接口
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
public interface SkuWareMapper extends BaseMapper<SkuWare> {

    @MapKey("id")
    Map<String, Object> selectStockByIds(@Param("list") Long[] ids);

}
