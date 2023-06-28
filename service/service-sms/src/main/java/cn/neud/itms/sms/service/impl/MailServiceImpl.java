package cn.neud.itms.sms.service.impl;

import cn.neud.itms.common.utils.MailUtil;
import cn.neud.itms.sms.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public String emailLoginValidate(String email) {
        return MailUtil.sendMail(email);
    }

}
