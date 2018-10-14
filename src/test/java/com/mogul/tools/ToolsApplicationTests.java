package com.mogul.tools;

import com.mogul.model.PropertiesConfig;
import com.mogul.tools.email.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolsApplicationTests {
    @Autowired
    private PropertiesConfig config;

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void contextLoads() {
//        mailUtil.sendSimpleMail("827302653@qq.com", "主题：约战", "明天决战紫禁之巅");
        mailUtil.sendHtmlMail("827302653@qq.com", "主题：HTML邮件", config.getEmailContent());
    }

    @Test
    public void getHomePath(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        System.out.println(com.getPath());
    }

    @Test
    public void getIp() throws UnknownHostException {
        System.out.println("本机IP：" + getLocalHostIP());
        System.out.println("本地主机名字为：" + getLocalHostName());

        String[] localIP = getAllLocalHostIP();
        for (int i = 0; i < localIP.length; i++) {
            System.out.println(localIP[i]);
        }

        InetAddress baidu = InetAddress.getByName("www.baidu.com");
        System.out.println("baidu : " + baidu);
        System.out.println("baidu IP: " + baidu.getHostAddress());
        System.out.println("baidu HostName: " + baidu.getHostName());
    }

    /**
     * 获取本机的IP
     * @return Ip地址
     */
    public static String getLocalHostIP() {
        String ip;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**返回 IP 地址字符串（以文本表现形式）*/
            ip = addr.getHostAddress();
        } catch(Exception ex) {
            ip = "";
        }

        return ip;
    }

    /**
     * 或者主机名：
     * @return
     */
    public static String getLocalHostName() {
        String hostName;
        try {
            /**返回本地主机。*/
            InetAddress addr = InetAddress.getLocalHost();
            /**获取此 IP 地址的主机名。*/
            hostName = addr.getHostName();
        }catch(Exception ex){
            hostName = "";
        }

        return hostName;
    }

    /**
     * 获得本地所有的IP地址
     * @return
     */
    public static String[] getAllLocalHostIP() {

        String[] ret = null;
        try {
            /**获得主机名*/
            String hostName = getLocalHostName();
            if(hostName.length()>0) {
                /**在给定主机名的情况下，根据系统上配置的名称服务返回其 IP 地址所组成的数组。*/
                InetAddress[] addrs = InetAddress.getAllByName(hostName);
                if(addrs.length>0) {
                    ret = new String[addrs.length];
                    for(int i=0 ; i< addrs.length ; i++) {
                        /**.getHostAddress()   返回 IP 地址字符串（以文本表现形式）。*/
                        ret[i] = addrs[i].getHostAddress();
                    }
                }
            }

        }catch(Exception ex) {
            ret = null;
        }

        return ret;
    }

}
