package cn.itcast.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//创建Springapplication应用对象
		SpringApplication springApplication=new SpringApplication(Application.class);
		
		//关闭横幅
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}
}
