package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.Invoice;
import cn.neud.itms.sys.service.InvoiceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "发票单管理")
@RestController
@RequestMapping("/admin/sys/invoice")
//@CrossOrigin
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation("发票列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            Invoice invoice
    ) {
        Page<Invoice> pageParam = new Page<>(page, limit);
        IPage<Invoice> pageModel = invoiceService.selectPage(pageParam, invoice);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Invoice invoice = invoiceService.getById(id);
        return Result.ok(invoice);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Invoice invoice) {
        invoiceService.save(invoice);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Invoice invoice) {
        invoiceService.updateById(invoice);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        invoiceService.removeById(id);
        return Result.ok(null);
    }

}

