package utils.mailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {

    private static final Logger log = LoggerFactory.getLogger(EmailUtil.class);
    protected Session session;

    /**
     * 获取系统环境
     */
    public static Properties getEmailProperties() {
        Properties prop = new Properties();
        return prop;
    }

    /**
     * 初始化 SMTP 服务
     * @param prop
     * @param host 发件人的邮箱的 SMTP 服务器地址
     * @param port 端口
     * @param ssl  是否使用SSL
     * @return
     */
    public static Properties initSMTP(Properties prop, String host, int port, boolean ssl) {
        prop.setProperty("mail.smtp.host", host);
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.smtp.port", String.valueOf(port));
        prop.setProperty("mail.smtp.ssl.enable", ssl ? "true" : "false");
        if (ssl) {
            prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        }
        return prop;
    }

    /**
     * 企业邮箱服务器
     * @param prop
     * @return
     */
    public static Properties setSMTP_qq(Properties prop) {
        return initSMTP(prop, "smtp.exmail.qq.com", 465, true);
    }

    /**
     *
     * @param prop
     * @param host
     * @return prop
     * @throws MessagingException
     */
    public static Properties setSMTP_sel(Properties prop, String host) throws MessagingException {
        if (host.contains("yingxiong.com")) {
            return initSMTP(prop, "smtp.exmail.qq.com", 465, true);
        } else if (host.contains("126.com") || host.equals("163.com")) { // 网易
            return initSMTP(prop, "smtp.126.com", 25, true);
        } else if (host.contains("sina.com")) { // 新浪
            return initSMTP(prop, "smtp.sina.com", 25, true);
        } else if (host.contains("qq.com")) { // 新浪
            return initSMTP(prop, "smtp.qq.com", 465, true);
        } else {
            throw new MessagingException("not support for host[" + host + "]");
        }
    }

    /**
     * 初始化session
     * @param prop
     * @return
     */
    public Session initSession(Properties prop){
        Session session = Session.getInstance(prop);
        // 在控制台显示Debug信息
        session.setDebug(true);
        this.session = session;
        return session;
    }

    /**
     * 创建一封邮箱
     * @param session
     * @param subject
     * @param content
     * @param sendMail
     * @param sendPersonName
     * @param receiveMail
     * @param receivePersonName
     * @return message
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String subject, String content, String sendMail, String sendPersonName, String receiveMail, String receivePersonName) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendPersonName, "UTF-8"));
        // To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receivePersonName, "UTF-8"));
        // Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // Content: 邮件正文
        message.setContent(content, "text/html;charset=UTF-8");
        // 设置发件时间
        message.setSentDate(new Date());
        // 保存设置
        message.saveChanges();
        return message;
    }

    /**
     * 创建一封邮箱带多附件
     * @param session
     * @param subject
     * @param content
     * @param sendMail
     * @param sendPersonName
     * @param receiveMail
     * @param receivePersonName
     * @return message
     * @throws Exception
     */
    public static MimeMessage createMimeMessage2(Session session, String subject, String content, String sendMail, String sendPersonName, String receiveMail, String receivePersonName, File ...file) throws Exception {
        MimeMessage message = new MimeMessage(session);
        // From: 发件人
        message.setFrom(new InternetAddress(sendMail, sendPersonName, "UTF-8"));
        // To: 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receivePersonName, "UTF-8"));
        // Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        //添加附件+正文
        MimeBodyPart body0 = new MimeBodyPart();
        body0.setContent(content, "text/html;charset=UTF-8");
        MimeMultipart mmPart = new MimeMultipart();
        mmPart.addBodyPart(body0);
        for (int i = 0; i < file.length; i++){
            MimeBodyPart body = new MimeBodyPart();
            body.setDataHandler(new DataHandler(new FileDataSource(file[i])));
            body.setFileName(MimeUtility.encodeText(file[i].getName()));
            mmPart.addBodyPart(body);
        }
        message.setContent(mmPart);
        // 设置发件时间
        message.setSentDate(new Date());
        // 保存设置
        message.saveChanges();
        return message;
    }


    /**
     * 发送邮件
     * @param message
     * @param sendAccount
     * @param sendPassword
     * @param receiver  用作日志输出
     * @return
     */
    public boolean send(MimeMessage message, String sendAccount, String sendPassword, String receiver) {
        try{
            Transport transport = session.getTransport();
            transport.connect(sendAccount, sendPassword);
            log.info("开始发送邮件····");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            log.info("邮件发送成功！ 发送者：" + sendAccount + ";接受者：" + receiver);
            return true;
        } catch (NoSuchProviderException e) {
            log.error("邮件发送失败！发送者：" + sendAccount + ";接受者：" + receiver + e);
            e.printStackTrace();
        } catch (MessagingException e) {
            log.error("邮件发送失败！发送者：" + sendAccount + ";接受者：" + receiver + e);
            e.printStackTrace();
        }
        return false;
    }

         public static void main(String[] args) throws Exception {
             /**
              * 企业邮箱发送模板
              */
//             EmailUtil emailUtil=new EmailUtil();
//             Properties properties = EmailUtil.getEmailProperties();
//             properties = EmailUtil.initSMTP(properties, "smtp.exmail.qq.com", 465, true);
//             MimeMessage message = EmailUtil.createMimeMessage(emailUtil.initSession(properties), "这是主题", "这是内容", "jie.zhang@yingxiong.com", "张杰","junhong.lin@yingxiong.com", "俊红");
//             boolean flag = emailUtil.send(message, "jie.zhang@yingxiong.com", "这里输入密码","");
//             if (flag){
//                 System.out.println("发送成功！");
//             }else{
//                 System.out.println("发送失败！");
//             }

             /**
              * qq邮箱发送模板
              */
//             EmailUtil emailUtil=new EmailUtil();
//             Properties properties = EmailUtil.getEmailProperties();
//             properties = EmailUtil.setSMTP_sel(properties, "qq.com");
//             MimeMessage message = EmailUtil.createMimeMessage(emailUtil.initSession(properties), "这是主题", "这是内容", "420964597@qq.com", "张杰","1585718943@qq.com", "a");
//             boolean flag = emailUtil.send(message, "420964597@qq.com", "这里输入密码", "");
//             if (flag){
//                 log.info("邮件发送成功！");
//             }else{
//                 log.error("邮件发送失败！");
//             }


         }


}
