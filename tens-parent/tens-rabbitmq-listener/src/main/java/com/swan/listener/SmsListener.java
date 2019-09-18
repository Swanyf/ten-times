package com.swan.listener;

import com.aliyuncs.exceptions.ClientException;
import com.swan.listener.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms_authcode")
public class SmsListener {

    @Autowired
    SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;   // 模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;   // 签名名称

    @RabbitHandler
    public void sendSmsMsg(Map<Object, Object> map) {
        Object mobile = map.get("mobile");
        Object authcode = map.get("authcode");
        System.out.println(mobile);
        System.out.println(authcode);
        try {
            smsUtil.sendSms(mobile.toString(), template_code, sign_name, "{code:" + authcode.toString() + "}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
