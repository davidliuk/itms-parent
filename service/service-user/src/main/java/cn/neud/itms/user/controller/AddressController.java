package cn.neud.itms.user.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.user.Address;
import cn.neud.itms.user.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/api/user/address")
//@CrossOrigin
public class AddressController {

    @Autowired
    private AddressService addressService;

    //平台属性列表方法
    //根据平台属性分组id查询
//    url: `${api_name}/${groupId}`,
//    method: 'get'
    @ApiOperation("根据用户id查询")
    @GetMapping("{userId}")
    public Result list(@PathVariable Long groupId) {
        List<Address> list = addressService.getAddressListByUserId(groupId);
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return Result.ok(address);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Address address) {
        addressService.save(address);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Address address) {
        addressService.updateById(address);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        addressService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        addressService.removeByIds(idList);
        return Result.ok(null);
    }
}

