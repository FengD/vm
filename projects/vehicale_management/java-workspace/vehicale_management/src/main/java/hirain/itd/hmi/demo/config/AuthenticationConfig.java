package hirain.itd.hmi.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hirain.itd.hmi.demo.config.interceptor.CarAuthentication;

@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**");
	}

	@Bean
	public CarAuthentication authenticationInterceptor() {
		return new CarAuthentication();
	}
}