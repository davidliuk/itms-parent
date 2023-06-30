package cn.neud.itms.sys.client;

import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-sys")
public interface SysFeignClient {

    @GetMapping("/api/sys/dispatch/inner/{orderNo}")
    public void autoDispatch(@PathVariable String orderNo);

    @ApiOperation("任务单列表")
    @GetMapping("/admin/sys/workOrder/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            WorkOrderQueryVo workOrderQueryVo
    );
}
