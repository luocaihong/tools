package com.mogul.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:config.properties")
public class PropertiesConfig {
	
	/********************邮件发送相关配置**********************/
	// 一天最多发送的数量
	@Value("${email.maxCount}")
	private String emailMaxCount;
	
	// 一次发送的数量
	@Value("${email.onceCount}")
	private String emailOnceCount;
	
	// 发送一次休息的时间 秒
	@Value("${email.sleepTime}")
	private String emailSleepTime;
	
	// 邮件发送的内容，包含占位符
	@Value("email.content")
	private String emailContent;
	
	// 邮件内容描述，占位符1
	@Value("${email.desc}")
	private String emailDesc;
	
	// 邮件内容链接地址，占位符2
	@Value("${email.href.host}")
	private String emailHrefHost;
	
	// 邮件内容链接端口，占位符3
	@Value("${email.href.port}")
	private String emailHrefPort;

	// 邮件发送者名称
	@Value("${mail.from.username}")
	private String mailFromUsername;

	// 邮件接收者邮箱
	@Value("${mail.to.user}")
	private String mailToUser;

    // 邮件抄送邮箱
    @Value("${mail.cc.user}")
    private String mailCcUser;

	public String getEmailMaxCount() {
		return emailMaxCount;
	}

	public void setEmailMaxCount(String emailMaxCount) {
		this.emailMaxCount = emailMaxCount;
	}

	public String getEmailOnceCount() {
		return emailOnceCount;
	}

	public void setEmailOnceCount(String emailOnceCount) {
		this.emailOnceCount = emailOnceCount;
	}

	public String getEmailSleepTime() {
		return emailSleepTime;
	}

	public void setEmailSleepTime(String emailSleepTime) {
		this.emailSleepTime = emailSleepTime;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	public String getEmailDesc() {
		return emailDesc;
	}

	public void setEmailDesc(String emailDesc) {
		this.emailDesc = emailDesc;
	}

	public String getEmailHrefHost() {
		return emailHrefHost;
	}

	public void setEmailHrefHost(String emailHrefHost) {
		this.emailHrefHost = emailHrefHost;
	}

	public String getEmailHrefPort() {
		return emailHrefPort;
	}

	public void setEmailHrefPort(String emailHrefPort) {
		this.emailHrefPort = emailHrefPort;
	}

	public String getMailFromUsername() {
		return mailFromUsername;
	}

	public void setMailFromUsername(String mailFromUsername) {
		this.mailFromUsername = mailFromUsername;
	}

	public String getMailToUser() {
		return mailToUser;
	}

	public void setMailToUser(String mailToUser) {
		this.mailToUser = mailToUser;
	}

    public String getMailCcUser() {
        return mailCcUser;
    }

    public void setMailCcUser(String mailCcUser) {
        this.mailCcUser = mailCcUser;
    }
}
