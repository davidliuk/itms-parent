package cn.neud.itms.cart.api;

import cn.neud.itms.activity.client.ActivityFeignClient;
import cn.neud.itms.cart.service.CartInfoService;
import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.order.CartInfo;
import cn.neud.itms.vo.order.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private CartInfoService cartInfoService;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    //1 根据skuId选中
    @SaUserCheckLogin
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked) {
        //获取userId
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        //调用方法
        cartInfoService.checkCart(userId, skuId, isChecked);
        return Result.ok(null);
    }

    //2 全选
    @SaUserCheckLogin
    @GetMapping("checkAllCart/{isChecked}")
    public Result checkAllCart(@PathVariable Integer isChecked) {
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.checkAllCart(userId, isChecked);
        return Result.ok(null);
    }

    //3 批量选中
    @SaUserCheckLogin
    @PostMapping("batchCheckCart/{isChecked}")
    public Result batchCheckCart(@RequestBody List<Long> skuIdList,
                                 @PathVariable Integer isChecked) {
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.batchCheckCart(skuIdList, userId, isChecked);
        return Result.ok(null);
    }

    /**
     * 查询带优惠卷的购物车
     *
     * @return
     */
    @SaUserCheckLogin
    @GetMapping("activityCartList")
    public Result activityCartList() {
        // 获取用户Id
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);

        OrderConfirmVo orderTradeVo = activityFeignClient.findCartActivityAndCoupon(cartInfoList, userId);
        return Result.ok(orderTradeVo);
    }

    //购物车列表
    @SaUserCheckLogin
    @GetMapping("cartList")
    public Result cartList() {
        //获取userId
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        List<CartInfo> cartInfoList = cartInfoService.getCartList(userId);
        return Result.ok(cartInfoList);
    }

    //添加商品到购物车
    //添加内容：当前登录用户id，skuId，商品数量
    @SaUserCheckLogin
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        //获取当前登录用户Id
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.addToCart(userId, skuId, skuNum);
        return Result.ok(null);
    }

    //根据skuId删除购物车
    @SaUserCheckLogin
    @DeleteMapping("deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId) {
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.deleteCart(skuId, userId);
        return Result.ok(null);
    }

    //清空购物车
    @SaUserCheckLogin
    @DeleteMapping("deleteAllCart")
    public Result deleteAllCart() {
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.deleteAllCart(userId);
        return Result.ok(null);
    }

    //批量删除购物车 多个skuId
    @SaUserCheckLogin
    @DeleteMapping("batchDeleteCart")
    public Result batchDeleteCart(@RequestBody List<Long> skuIdList) {
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        cartInfoService.batchDeleteCart(skuIdList, userId);
        return Result.ok(null);
    }

    //获取当前用户购物车选中购物项

    /**
     * 根据用户Id 查询购物车列表
     *
     * @param userId
     * @return
     */
    @SaUserCheckLogin
    @GetMapping("inner/getCartCheckedList/{userId}")
    public List<CartInfo> getCartCheckedList(@PathVariable("userId") Long userId) {
        return cartInfoService.getCartCheckedList(userId);
    }
}
