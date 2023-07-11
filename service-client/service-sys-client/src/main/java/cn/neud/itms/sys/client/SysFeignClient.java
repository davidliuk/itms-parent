package cn.neud.itms.sys.client;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-sys")
public interface SysFeignClient {

    @GetMapping("/api/sys/dispatch/inner/{orderNo}")
    public void autoDispatch(@PathVariable String orderNo);

    @PostMapping("/api/sys/inner/purchaseOrder")
    public Result savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder);

    @PostMapping("/api/sys/inner/storageOrder")
    public Result saveStorageOrder(@RequestBody StorageOrder storageOrder);

    @ApiOperation("任务单列表")
    @GetMapping("/admin/sys/workOrder/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody WorkOrderQueryVo workOrderQueryVo
    );

    @GetMapping("/api/sys/dispatch/inner/returnOrder")
    void returnOrder(String orderNo);
}
