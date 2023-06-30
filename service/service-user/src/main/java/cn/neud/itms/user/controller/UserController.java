package cn.neud.itms.user.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.model.user.User;
import cn.neud.itms.user.service.AddressService;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.UserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("客户管理控制器")
@RestController
@RequestMapping("/admin/user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    // 1 用户列表
    @ApiOperation("用户列表")
    @GetMapping("{current}/{limit}")
    public Result list(@PathVariable Long current,
                       @PathVariable Long limit,
                       UserQueryVo userQueryVo) {
        Page<User> pageParam = new Page<User>(current, limit);
        IPage<User> pageModel = userService.selectPageUser(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        User user = userService.getById(id);
        user.setAddresses(addressService.getAddressListByUserId(id));
        return Result.ok(user);
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody User user) {
        //获取输入的密码
        String password = user.getPassword();
        //对输入密码进行加密 MD5
        String passwordMD5 = MD5.encrypt(password);
        //设置到user对象里面
        user.setPassword(passwordMD5);
        //调用方法添加
        userService.save(user);
        return Result.ok(null);
    }

    //4 修改用户(不能修改密码)，传入的密码值为null的时候不会改密码
    @ApiOperation("修改用户")
    @PutMapping("update")
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        return Result.ok(null);
    }

}
