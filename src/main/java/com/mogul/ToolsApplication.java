package com.mogul;

import com.mogul.tools.business.CollectInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }

    @Bean
    public CollectInfo collectInfo(){
        return new CollectInfo();
    }
}
