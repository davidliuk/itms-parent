package cn.neud.itms.user.api;

import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.utils.JwtHelper;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.common.utils.MailUtil;
import cn.neud.itms.enums.UserType;
import cn.neud.itms.model.user.User;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.user.service.AddressService;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api("用户接口控制器")
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 用户密码登录
    @ApiOperation(value = "账户密码登录")
    @PostMapping("login/password")
    public Result passwordLogin(@RequestBody PasswordLoginVo loginVo) {
        // 操作user表
        User user = userService.getUserByUserName(loginVo.getUsername());
        if (user == null || !Objects.equals(user.getPassword(), MD5.encrypt(loginVo.getPassword()))) {
            return Result.fail(null);
        }

        // 5 根据userId查询提货点和配送员信息
        // 提货点  user表  user_delivery表
        // 配送员    courier表
        AddressVo addressVo = userService.getAddressByUserId(user.getId());

        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserVo userVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(RedisConstant.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userVo,
                        RedisConstant.USER_KEY_TIMEOUT,
                        TimeUnit.DAYS);
        StpUserUtil.login(user.getId());
        //8 需要数据封装到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("addressVo", addressVo);
        return Result.ok(map);
    }

    // 用户邮件发送
    @ApiOperation(value = "邮件验证码发送")
    @PostMapping("email/send")
    public Result emailCode(@RequestBody EmailLoginVo loginVo) {
        String email = loginVo.getEmail();
        String code = MailUtil.sendMail(email);
        redisTemplate.opsForValue()
                .set(RedisConstant.LOGIN_CODE_KEY + email,
                        code,
                        RedisConstant.LOGIN_CODE_TTL,
                        TimeUnit.MINUTES);
        return Result.ok(null);
    }

    // 用户邮件登录
    @ApiOperation(value = "邮件验证码登录")
    @PostMapping("login/email")
    public Result emailLogin(@RequestBody EmailLoginVo loginVo) {
        // 操作user表
        User user = userService.getUserByEmail(loginVo.getEmail());
        if (!Objects.equals(redisTemplate.opsForValue().get(RedisConstant.LOGIN_CODE_KEY + loginVo.getEmail()), loginVo.getCode())) {
            return Result.fail(null);
        }
        // 初次登录用户注册账户
        if (user == null) {
            user = new User();
            user.setEmail(loginVo.getEmail());
            user.setNickName("邮箱用户" + loginVo.getEmail());
            user.setAvatar("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

        //5 根据userId查询提货点和配送员信息
        // 提货点  user表  user_delivery表
        // 配送员    courier表
        AddressVo addressVo = userService.getAddressByUserId(user.getId());

        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserVo userVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(RedisConstant.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userVo,
                        RedisConstant.USER_KEY_TIMEOUT,
                        TimeUnit.DAYS);
        StpUserUtil.login(user.getId());
        //8 需要数据封装到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("addressVo", addressVo);
        return Result.ok(map);
    }

    // 用户注册
    @ApiOperation("用户注册")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterVo userRegisterVo) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterVo, user);
        // 获取输入的密码
        String password = userRegisterVo.getPassword();
        // 对输入密码进行加密 MD5
        String passwordMD5 = MD5.encrypt(password);
        // 设置到user对象里面
        user.setPassword(passwordMD5);
        user.setNickName("邮箱用户" + userRegisterVo.getEmail());
        user.setAvatar("");
        user.setUserType(UserType.USER);
        user.setIsNew(0);
        // 调用方法添加
        userService.save(user);
        return Result.ok(null);
    }

    // 用户注册
    @ApiOperation("用户登出")
    @PostMapping("logout")
    @SaUserCheckLogin
    public Result logout() {
        redisTemplate.delete(RedisConstant.USER_LOGIN_KEY_PREFIX + StpUserUtil.getLoginIdAsLong());
        StpUserUtil.logout();
        return Result.ok(null);
    }

    //4 修改用户信息，传入的密码值为null的时候不会改密码
    @ApiOperation("修改用户信息")
    @PutMapping("")
    public Result update(@RequestBody User user) {
        // 获取输入的密码
        String password = user.getPassword();
        if (!StringUtils.isEmpty(password)) {
            // 对输入密码进行加密 MD5
            String passwordMD5 = MD5.encrypt(password);
            user.setPassword(passwordMD5);
        }
        userService.updateById(user);
        return Result.ok(null);
    }

//    @ApiOperation("获取所有地址信息")
//    @GetMapping("address")
//    @SaUserCheckLogin
//    public Result addressAll() {
//        return Result.ok(addressService.getAddressListByUserId(StpUserUtil.getLoginIdAsLong()));
//    }
//
//    @ApiOperation("获取地址信息")
//    @GetMapping("address/{id}")
//    public Result addressGet(@PathVariable Long id) {
//        return Result.ok(addressService.getById(id));
//    }
//
//    @ApiOperation("增加地址信息")
//    @PostMapping("address")
//    public Result addressAdd(@RequestBody Address address) {
//        addressService.save(address);
//        return Result.ok(null);
//    }
//
//    @ApiOperation("修改地址信息")
//    @PutMapping("address")
//    public Result addressUpdate(@RequestBody Address address) {
//        addressService.updateById(address);
//        return Result.ok(null);
//    }
//
//    @ApiOperation(value = "删除地址信息")
//    @DeleteMapping("address/{id}")
//    public Result remove(@PathVariable Long id) {
//        addressService.removeById(id);
//        return Result.ok(null);
//    }
}
