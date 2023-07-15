package cn.neud.itms.user.api;

import cn.neud.itms.common.auth.AuthContextHolder;
import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.common.utils.JwtHelper;
import cn.neud.itms.enums.UserType;
import cn.neud.itms.model.user.User;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.user.utils.ConstantPropertiesUtil;
import cn.neud.itms.user.utils.HttpClientUtils;
import cn.neud.itms.vo.user.UserVo;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/user/weixin")
public class WeixinApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    //用户微信授权登录
    @ApiOperation(value = "微信登录获取openid(小程序)")
    @GetMapping("/wxLogin/{code}")
    public Result loginWx(@PathVariable String code) {
        //1 得到微信返回code临时票据值
        //2 拿着code + 小程序id + 小程序秘钥 请求微信接口服务
        //// 使用HttpClient工具请求
        //小程序id
        String wxOpenAppId = ConstantPropertiesUtil.WX_OPEN_APP_ID;
        //小程序秘钥
        String wxOpenAppSecret = ConstantPropertiesUtil.WX_OPEN_APP_SECRET;
        //get请求
        //拼接请求地址+参数
        /// 地址?name=value&name1=value1
        StringBuffer url = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/jscode2session")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&js_code=%s")
                .append("&grant_type=authorization_code");
        String tokenUrl = String.format(url.toString(),
                wxOpenAppId,
                wxOpenAppSecret,
                code);
        //HttpClient发送get请求
        String result = null;
        try {
            result = HttpClientUtils.get(tokenUrl);
        } catch (Exception e) {
            throw new ItmsException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILED);
        }

        //3 请求微信接口服务，返回两个值 session_key 和 openid
        //// openId是你微信唯一标识
        JSONObject jsonObject = JSONObject.parseObject(result);
        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");

//        openid = "odo3j4q2KskkbbW-krfE-cAxUnzU1";
        //4 添加微信用户信息到数据库里面
        //// 操作user表
        //// 判断是否是第一次使用微信授权登录：如何判断？openId
        User user = userService.getUserByOpenId(openid);
        if (user == null) {
            user = new User();
            user.setOpenId(openid);
            user.setNickName(openid);
            user.setAvatar("");
            user.setUserType(UserType.USER);
            user.setIsNew(0);
            userService.save(user);
        }

        //5 根据userId查询提货点和配送员信息
        ////提货点  user表  user_delivery表
        ////配送员    courier表
//        CourierAddressVo courierAddressVo = userService.getCourierAddressByUserId(user.getId());

        //6 使用JWT工具根据userId和userName生成token字符串
        String token = JwtHelper.createToken(user.getId(), user.getNickName());

        //7 获取当前登录用户信息，放到Redis里面，设置有效时间
        UserVo userVo = userService.getUserLoginVo(user.getId());
        redisTemplate.opsForValue()
                .set(RedisConstant.USER_LOGIN_KEY_PREFIX + user.getId(),
                        userVo,
                        RedisConstant.USER_KEY_TIMEOUT,
                        TimeUnit.DAYS);

        //8 需要数据封装到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
//        map.put("courierAddressVo", courierAddressVo);
        return Result.ok(map);
    }

    @PostMapping("/auth/updateUser")
    @ApiOperation(value = "更新用户昵称与头像")
    public Result updateUser(@RequestBody User user) {
        //获取当前登录用户id
        User user1 = userService.getById(AuthContextHolder.getUserId());
        //把昵称更新为微信用户
        user1.setNickName(user.getNickName().replaceAll("[ue000-uefff]", "*"));
        user1.setAvatar(user.getAvatar());
        userService.updateById(user1);
        return Result.ok(null);
    }
}
