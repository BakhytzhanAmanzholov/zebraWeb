package kz.jaguars.hackathon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://twitter-front-pi.vercel.app")
                .allowedMethods("PUT", "DELETE", "POST", "GET")
                .allowedOriginPatterns("*")
//                .allowedHeaders("Authorization", "Requestor-Type", "header3")
//                .exposedHeaders("header1", "header2")
                .allowCredentials(true).maxAge(3600);
    }
}