一个简单的获取用户机器信息并且发送邮件的程序

工程基于springboot+maven搭建，jar包

收集信息的类为CollectInfo.java，
调用收集方法是在springboot入口程序ToolsApplication.java中调用
```$xslt
    @Bean
    public CollectInfo collectInfo(){
        return new CollectInfo();
    }
```
并且CollectInfo需要实现接口ApplicationRunner