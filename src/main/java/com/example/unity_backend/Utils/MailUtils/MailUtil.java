package com.example.unity_backend.Utils.MailUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.unity_backend.Utils.LogUtils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Date;
@Service
public class MailUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    private void checkMail(String to,String subject,String text) {
        if (to.isEmpty()) {
            throw new RuntimeException("邮件收信人不能为空");
        }
        if (subject.isEmpty()) {
            throw new RuntimeException("邮件主题不能为空");
        }
        if (text.isEmpty()) {
            throw new RuntimeException("邮件内容不能为空");
        }
    }

    public JSONObject sendTextMailMessage(String to, String subject, String text) {
        checkMail(to, subject, text);
        JSONObject json = new JSONObject();
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人 1或多个
            mimeMessageHelper.setTo(to.split(","));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            LogUtil.showDebug("发送邮件成功：" + sendMailer + "->" + to);
            json.put("send_status", "success");
        } catch (MessagingException e) {
            e.printStackTrace();
            LogUtil.showDebug("发送邮件失败：" + e.getMessage());
            json.put("send_status", "fail");
            json.put("err", e.getMessage());
        }
        return json;
    }

}
