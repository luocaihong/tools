package com.mogul.tools.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mogul.model.PropertiesConfig;

/**
 * 基于spring-boot的邮件发送功能
 * @author luocaihong
 * @date 2018-06-05
 */
@Component
public class MailUtil {
	private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private PropertiesConfig config;
	
	/**
	 * 发送简单邮件
	 * @param to 收件人
	 * @param subject 标题
	 * @param content 内容
	 */
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(config.getMailFromUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
	}
	
	/**
	 * 发送html格式邮件
	 * @param to 收件人
	 * @param subject 标题
	 * @param content 内容
	 */
	public void sendHtmlMail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();

	    try {
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(config.getMailFromUsername());
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

			// 可以防止被当作垃圾邮件
	        helper.setCc(config.getMailCcUser());

	        mailSender.send(message);
	        logger.info("html邮件发送成功");
	    } catch (MessagingException e) {
	        logger.error("发送html邮件时发生异常！", e);
	    }
	}
	
	/**
	 * 发送带附件的邮件
	 * @param to 收件人
	 * @param subject 标题
	 * @param content 内容
	 * @param filePath 附件路径
	 */
	public void sendAttachmentsMail(String to, String subject, String content, String filePath){
	    MimeMessage message = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(config.getMailFromUsername());
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource file = new FileSystemResource(new File(filePath));
	        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	        
	        
	        helper.addAttachment(fileName, file);

	        mailSender.send(message);
	        logger.info("带附件的邮件已经发送。");
	    } catch (MessagingException e) {
	        logger.error("发送带附件的邮件时发生异常！", e);
	    }
	}
	
	/**
	 * 发送带静态资源的邮件
	 * @param to 收件人
	 * @param subject 标题
	 * @param content 内容
	 * @param rscPath 静态资源路径
	 * @param rscId 静态资源id <img src=\'cid:" + rscId + "\' >
	 */
	public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
	    MimeMessage message = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(config.getMailFromUsername());
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, true);

	        FileSystemResource res = new FileSystemResource(new File(rscPath));
	        helper.addInline(rscId, res);

	        mailSender.send(message);
	        logger.info("嵌入静态资源的邮件已经发送。");
	    } catch (MessagingException e) {
	        logger.error("发送嵌入静态资源的邮件时发生异常！", e);
	    }
	}
}
