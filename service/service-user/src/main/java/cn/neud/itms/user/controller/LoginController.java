package cn.neud.itms.user.controller;

import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.common.utils.JwtHelper;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.enums.UserType;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.model.acl.Role;
import cn.neud.itms.model.user.User;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.user.utils.ConstantPropertiesUtil;
import cn.neud.itms.user.utils.HttpClientUtils;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import cn.neud.itms.vo.user.PasswordLoginVo;
import cn.neud.itms.vo.user.UserLoginVo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api("登录控制器")
@RestController
@RequestMapping("/api/user/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 用户微信授权登录
    @ApiOperation(value = "账户密码登录")
    @PostMapping("/login}")
    public Result login(@RequestBody PasswordLoginVo loginVo) {
        // 操作user表
        User user = userService.getUserByUserName(loginVo.getUsername());
        if (user == null || !Objects.equals(user.getPassword(), MD5.encrypt(loginVo.getPassword()))) {
            return Result.fail(null);
        }

        //5 根据userId查询提货点和配送员信息
        // 提货点  user表  user_delivery表
        // 配送员    courier表
        AddressVo addressVo = userService.getAddressByUserId(user.getId());

        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserLoginVo userLoginVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(RedisConstant.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userLoginVo,
                        RedisConstant.USERKEY_TIMEOUT,
                        TimeUnit.DAYS);
        StpUserUtil.login(user.getId());
        //8 需要数据封装到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("addressVo", addressVo);
        return Result.ok(map);
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
