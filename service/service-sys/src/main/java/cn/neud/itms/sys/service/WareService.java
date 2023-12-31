package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.Ware;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
public interface WareService extends IService<Ware> {

    IPage<Ware> selectPage(Page<Ware> pageParam, Ware ware);
}
