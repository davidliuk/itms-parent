package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.Receipt;
import cn.neud.itms.sys.service.ReceiptService;
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
@Api(tags = "回执单管理")
@RestController
@RequestMapping("/admin/sys/receipt")
//@CrossOrigin
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @ApiOperation("发票列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody Receipt receipt
    ) {
        Page<Receipt> pageParam = new Page<>(page, limit);
        IPage<Receipt> pageModel = receiptService.selectPage(pageParam, receipt);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Receipt receipt = receiptService.getById(id);
        return Result.ok(receipt);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Receipt receipt) {
        receiptService.save(receipt);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Receipt receipt) {
        receiptService.updateById(receipt);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        receiptService.removeById(id);
        return Result.ok(null);
    }

}

