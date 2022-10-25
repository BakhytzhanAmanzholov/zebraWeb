package kz.jaguars.hackathon;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.jaguars.hackathon.models.CoffeeHouse;
import kz.jaguars.hackathon.services.CoffeeService;
import kz.jaguars.hackathon.services.StaffService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;

@SpringBootApplication
public class HackathonApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    public static void main(String[] args) {
        SpringApplication.run(HackathonApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(CoffeeService coffeeService) {
        return args -> {
            coffeeService.save(
                    CoffeeHouse.builder()
                            .id(1L)
                            .address("Kaba")
                            .bestProducts(new HashSet<>())
                            .profit(0)
                            .expenses(0)
                            .countSales(0)
                            .shortName("Ka")
                            .marginality(0)
                            .salesVolume(0.0)
                            .staffs(new HashSet<>())
                            .workingHours("0-0")
                            .averageBill(0.0)

                            .build()
            );

        };
    }

}
