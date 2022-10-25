package kz.jaguars.hackathon.security.config;

import kz.jaguars.hackathon.security.filters.JwtAuthenticationFilter;
import kz.jaguars.hackathon.security.filters.JwtAuthorizationFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Profile("!noSecurity")
public class JwtSecurityConfig {
    UserDetailsService userDetailsServiceImpl;

    PasswordEncoder passwordEncoder;

    AuthenticationProvider refreshTokenAuthenticationProvider;
//Access to XMLHttpRequest at 'https://hackathon-2022-app.herokuapp.com/api/supervisor/registration/' from origin 'https://twitter-front-pi.vercel.app' has been blocked by CORS policy: Response to preflight request doesn't pass access control check:
// No 'Access-Control-Allow-Origin' header is present on the requested resource.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter,
                                                   JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        httpSecurity.cors().configurationSource(corsConfigurationSource());
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeRequests().antMatchers("/auth/token/**", "/", "/zebra-open-api",
                "/api/supervisor/registration/").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/api/products/**").authenticated();
        httpSecurity.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
//        httpSecurity.authorizeRequests().antMatchers("/supervisor/**", "products/**").hasAuthority("SUPERVISOR");


        httpSecurity.addFilter(jwtAuthenticationFilter);
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Autowired
    public void bindUserDetailsServiceAndPasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
        builder.authenticationProvider(refreshTokenAuthenticationProvider);
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    }

}
