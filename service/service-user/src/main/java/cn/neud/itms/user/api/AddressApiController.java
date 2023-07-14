package cn.neud.itms.user.api;

import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.user.Address;
import cn.neud.itms.user.service.AddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/api/user/address")
//@CrossOrigin
public class AddressApiController {

    @Autowired
    private AddressService addressService;

    @ApiOperation("获取登录用户所有地址信息")
    @GetMapping()
    @SaUserCheckLogin
    public Result addressGet() {
        return Result.ok(addressService.getAddressListByUserId(StpUserUtil.getLoginIdAsLong()));
    }

    @ApiOperation("设置默认地址")
    @GetMapping("default/{addressId}")
    @SaUserCheckLogin
    public Result addressGet(Long addressId) {
        // 把原来的默认地址改为非默认
        addressService.setAllUnDefault();
        // 把当前地址改为默认
        Address address = new Address();
        address.setId(addressId);
        address.setIsDefault(1);
        addressService.updateById(address);
        return Result.ok(null);
    }

    //平台属性列表方法
    //根据平台属性分组id查询
//    url: `${api_name}/${userId}`,
//    method: 'get'
//    @SaUserCheckLogin
//    @ApiOperation("根据用户id查询")
//    @GetMapping("")
//    public Result list() {
//        long userId = StpUserUtil.getLoginIdAsLong();
//        List<Address> list = addressService.getAddressListByUserId(userId);
//        return Result.ok(list);
//    }

    @ApiOperation(value = "获取收货地址")
    @GetMapping("{id}")
    public Result get(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return Result.ok(address);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Address address) {
        if (addressService.count(new LambdaQueryWrapper<Address>().eq(Address::getUserId, address.getUserId())) == 0) {
            address.setIsDefault(1);
        }
        addressService.save(address);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Address address) {
        if (address.getIsDefault().equals(1)) {
            addressService.setAllUnDefault();
        }
        addressService.updateById(address);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("{id}")
    public Result remove(@PathVariable Long id) {
        addressService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        addressService.removeByIds(idList);
        return Result.ok(null);
    }
}

