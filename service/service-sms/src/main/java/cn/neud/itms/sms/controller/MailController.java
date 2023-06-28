package cn.neud.itms.sms.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.sms.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "发送邮件验证码")
@RestController
@RequestMapping("/api/sms/mail")
public class MailController {

    @Resource
    private MailService mailService;

    @ApiOperation("邮箱获取验证码")
    @GetMapping("code/{email}")
    public Result loginByEmail(@RequestBody String email) {
        // 效验数据
        return Result.ok(mailService.emailLoginValidate(email));
    }

}
