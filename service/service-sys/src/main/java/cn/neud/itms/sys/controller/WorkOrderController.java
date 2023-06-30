package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.RegionWare;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.sys.service.RegionWareService;
import cn.neud.itms.sys.service.WorkOrderService;
import cn.neud.itms.vo.sys.RegionWareQueryVo;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "任务单管理")
@RestController
@RequestMapping("/admin/sys/workOrder")
//@CrossOrigin
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("任务单列表")
    @GetMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            WorkOrderQueryVo workOrderQueryVo
    ) {
        Page<WorkOrder> pageParam = new Page<>(page, limit);
        IPage<WorkOrder> pageModel = workOrderService.selectPage(pageParam, workOrderQueryVo);
        return Result.ok(pageModel);
    }

}

