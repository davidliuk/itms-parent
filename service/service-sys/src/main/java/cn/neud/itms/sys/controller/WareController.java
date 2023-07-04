package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.model.sys.Ware;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.sys.service.TransferOrderService;
import cn.neud.itms.sys.service.WareService;
import cn.neud.itms.sys.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "区域仓库接口")
@RestController
@RequestMapping("/admin/sys/ware")
//@CrossOrigin
public class WareController {

    @Autowired
    private WareService wareService;

    @Autowired
    private TransferOrderService transferOrderService;

    @Autowired
    private WorkOrderService workOrderService;

    //查询所有仓库列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation("查询所有仓库列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Ware ware = wareService.getById(id);
        return Result.ok(ware);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Ware ware) {
        wareService.save(ware);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Ware ware) {
        wareService.updateById(ware);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        wareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        wareService.removeByIds(idList);
        return Result.ok(null);
    }

    // 调拨出库
    @ApiOperation("调拨出库")
    @GetMapping("/out/{workOrderId}")
    public Result out(@PathVariable Long workOrderId) {
        // 获取任务单
        WorkOrder workOrder = workOrderService.getById(workOrderId);
        workOrder.setWorkStatus(WorkStatus.OUT);
        workOrderService.updateById(workOrder);

//        // 生成调拨单
//        TransferOrder transferOrder = new TransferOrder();
//        transferOrder.setOutTime(new Date());
//        transferOrderService.save(transferOrder);

        // 生成库存单
        StorageOrder storageOrder = new StorageOrder();
        BeanUtils.copyProperties(workOrder, storageOrder);

        return Result.ok(null);
    }

}

