package cn.neud.itms.sys.api;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.sys.service.PurchaseOrderService;
import cn.neud.itms.sys.service.StorageOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sys")
public class SysApiController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private StorageOrderService storageOrderService;

    @PostMapping("inner/purchaseOrder")
    public Result savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrderService.save(purchaseOrder);

        return Result.ok(null);
    }

}
