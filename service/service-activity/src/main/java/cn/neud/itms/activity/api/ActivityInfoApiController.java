package cn.neud.itms.activity.api;

import cn.neud.itms.activity.service.ActivityInfoService;
import cn.neud.itms.activity.service.CouponInfoService;
import cn.neud.itms.model.activity.CouponInfo;
import cn.neud.itms.vo.order.CartInfoVo;
import cn.neud.itms.vo.order.OrderConfirmVo;
import cn.neud.itms.model.order.CartInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activity")
public class ActivityInfoApiController {

    @Autowired
    private ActivityInfoService activityInfoService;

    @Autowired
    private CouponInfoService couponInfoService;

    //获取购物车里面满足条件优惠卷和活动的信息
    @PostMapping("inner/findCartActivityAndCoupon/{userId}")
    public OrderConfirmVo findCartActivityAndCoupon(@RequestBody List<CartInfo> cartInfoList,
                                                    @PathVariable("userId") Long userId) {
        return activityInfoService.findCartActivityAndCoupon(cartInfoList, userId);
    }

    @ApiOperation(value = "根据skuId列表获取促销信息")
    @PostMapping("inner/findActivity")
    public Map<Long, List<String>> findActivity(@RequestBody List<Long> skuIdList) {
        return activityInfoService.findActivity(skuIdList);
    }

    @ApiOperation("根据skuID获取营销数据和优惠卷")
    @GetMapping("inner/findActivityAndCoupon/{skuId}/{userId}")
    public Map<String, Object> findActivityAndCoupon(@PathVariable Long skuId,
                                                     @PathVariable Long userId) {
        return activityInfoService.findActivityAndCoupon(skuId, userId);
    }

    //获取购物车对应规则数据
    @PostMapping("inner/findCartActivityList")
    public List<CartInfoVo> findCartActivityList(@RequestBody List<CartInfo> cartInfoList) {
        return activityInfoService.findCartActivityList(cartInfoList);
    }

    //获取购物车对应优惠卷
    @PostMapping("inner/findRangeSkuIdList/{couponId}")
    public CouponInfo findRangeSkuIdList(@RequestBody List<CartInfo> cartInfoList,
                                         @PathVariable("couponId") Long couponId) {
        return couponInfoService.findRangeSkuIdList(cartInfoList, couponId);
    }

    //更新优惠卷使用状态
    @GetMapping("inner/updateCouponInfoUseStatus/{couponId}/{userId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(@PathVariable("couponId") Long couponId,
                                             @PathVariable("userId") Long userId,
                                             @PathVariable("orderId") Long orderId) {
        couponInfoService.updateCouponInfoUseStatus(couponId, userId, orderId);
        return true;
    }
}
