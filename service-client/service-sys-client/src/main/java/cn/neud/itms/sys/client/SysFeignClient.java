package cn.neud.itms.sys.client;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-sys")
public interface SysFeignClient {

    @GetMapping("/api/sys/dispatch/inner/{orderNo}")
    void autoDispatch(@PathVariable String orderNo);

    @PostMapping("/api/sys/inner/purchaseOrder")
    Result savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder);

    @PostMapping("/api/sys/inner/storageOrder")
    Result saveStorageOrder(@RequestBody StorageOrder storageOrder);

    @ApiOperation("任务单列表")
    @PostMapping("/admin/sys/workOrder/{page}/{limit}")
    Result listWorkOrder(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody WorkOrderQueryVo workOrderQueryVo
    );

    @GetMapping("/api/sys/dispatch/inner/returnOrder/{orderNo}")
    void returnOrder(@PathVariable String orderNo);

    @GetMapping("/admin/sys/storageOrder/{storageOrderId}")
    StorageOrder getStorageOrder(@PathVariable Long storageOrderId);

    @GetMapping("/admin/sys/purchaseOrder/{purchaseOrderId}")
    PurchaseOrder getPurchaseOrder(@PathVariable Long purchaseOrderId);

    @PutMapping("/admin/sys/purchaseOrder")
    Result updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder);
}
