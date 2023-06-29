package cn.neud.itms.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮箱发送工具类
 */
public class MailUtil {

    private static final Properties prop = new Properties() {{
        // 开启debug调试，以便在控制台查看
        this.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        this.setProperty("mail.host", "smtp.163.com");
        // 发送服务器需要身份验证
        this.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        this.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        // 创建验证码
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            // 关闭/开启SSL加密，否则会失败
            this.put("mail.smtp.ssl.enable", "false");
            this.put("mail.smtp.ssl.socketFactory", sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }};

    /**
     * 发送验证码
     *
     * @param receiveMail
     * @throws Exception
     */
    public static String sendMail(String receiveMail) {
        String verifyCode = createCode();
        //创建验证码
        try {
            // 创建session
            Session session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com，qq使用：smtp.qq.com
            ts.connect("smtp.163.com", "ideatms@163.com", "RVTXABKEYGJEDXYN");
            // 创建邮件
            Message message = createSimpleMail(session, receiveMail, verifyCode);
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return verifyCode;
    }

    public static String createCode() {
        //  获取6位随机验证码（英文）
        String[] letters = new String[]{
                "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
                "A", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }

        //获取6位随机验证码（中文），根据项目需要选择中英文
//        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        return stringBuilder.toString();
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String receiveMail, String verifyCode) throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress("ideatms@163.com"));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMail));
        // 邮件的标题
        message.setSubject("ITMS 验证码");
        // 邮件的文本内容
        message.setContent("你好！<br/>" +
                "感谢您使用 ITMS - IDEA物流管理系统 。<br/>" +
                "你的登录邮箱为：" + receiveMail + "。请回填如下6位验证码：<br/>" +
                verifyCode + "<br/>验证码有效期为：2 分钟。如非本人操作，请忽略！请勿回复此邮箱", "text/html;charset=UTF-8");

        // 返回创建好的邮件对象
        return message;
    }
}
