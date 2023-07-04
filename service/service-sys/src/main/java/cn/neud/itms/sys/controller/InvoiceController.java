package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.TransferOrder;
import cn.neud.itms.sys.service.TransferOrderService;
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
@Api(tags = "发票管理")
@RestController
@RequestMapping("/admin/sys/invoice")
//@CrossOrigin
public class InvoiceController {

    @Autowired
    private TransferOrderService invoiceService;

    @ApiOperation("发票列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            TransferOrder invoice
    ) {
        Page<TransferOrder> pageParam = new Page<>(page, limit);
        IPage<TransferOrder> pageModel = invoiceService.selectPage(pageParam, invoice);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        TransferOrder invoice = invoiceService.getById(id);
        return Result.ok(invoice);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody TransferOrder invoice) {
        invoiceService.save(invoice);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody TransferOrder invoice) {
        invoiceService.updateById(invoice);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        invoiceService.removeById(id);
        return Result.ok(null);
    }

}

