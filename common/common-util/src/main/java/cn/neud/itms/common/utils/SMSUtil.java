package cn.neud.itms.common.utils;

import com.apistd.uni.Uni;
import com.apistd.uni.UniException;
import com.apistd.uni.UniResponse;
import com.apistd.uni.sms.UniMessage;
import com.apistd.uni.sms.UniSMS;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮箱发送工具类
 */
public class SMSUtil {

    public static void main(String[] args) {
        sendMessage("15038237816", 2);
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

    private static final String ACCESS_KEY_ID = "QsZsmBYtsgj5mgJV8BEb3wdv3HKcPpTEXu4eDTADrTkqbR4KQ";
    private static final String ACCESS_KEY_SECRET = "your access key secret";

    public static String sendMessage(String phone, int minute) {
        // 初始化
//        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET); // 若使用简易验签模式仅传入第一个参数即可
        Uni.init(ACCESS_KEY_ID);
        String code = createCode();
        // 设置自定义参数 (变量短信)
        Map<String, String> templateData = new HashMap<String, String>();
        templateData.put("code", code);
        templateData.put("ttl", String.valueOf(minute));

        // 构建信息
        UniMessage message = UniSMS.buildMessage()
                .setTo(phone)
                .setSignature("UniSMS")
                .setTemplateId("pub_verif_ttl3")
                .setTemplateData(templateData);

        // 发送短信
        try {
            UniResponse res = message.send();
            System.out.println(res);
        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }

        return code;
    }
}
