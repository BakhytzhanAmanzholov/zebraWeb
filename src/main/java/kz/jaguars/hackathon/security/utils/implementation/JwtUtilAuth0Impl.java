package kz.jaguars.hackathon.security.utils.implementation;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kz.jaguars.hackathon.models.Account;
import kz.jaguars.hackathon.security.config.JwtSecurityConfig;
import kz.jaguars.hackathon.security.details.UserDetailsImpl;
import kz.jaguars.hackathon.security.utils.JwtUtil;
import com.auth0.jwt.JWTVerifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;

import java.nio.charset.StandardCharsets;
import java.util.*;


@Component
@ConditionalOnBean(value = JwtSecurityConfig.class)
public class JwtUtilAuth0Impl implements JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRES_TIME = 30 * 60 * 1000; // THIRTY MINUTES

    private static final long REFRESH_TOKEN_EXPIRES_TIME = 24 * 60 * 60 * 1000; //TWENTY FOUR HOURS

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Map<String, String> generateTokens(String subject, String authority, String issuer) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

        String accessToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_TIME))
                .withClaim("role", authority)
                .withIssuer(issuer)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRES_TIME))
                .withClaim("role", authority)
                .withIssuer(issuer)
                .sign(algorithm);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    @Override
    public Authentication buildAuthentication(String token) {
        ParsedToken parsedToken = parse(token);

        return new UsernamePasswordAuthenticationToken(new UserDetailsImpl(
                Account.builder()
                        .role(Account.Role.valueOf(parsedToken.getRole()))
                        .email(parsedToken.getEmail())
                        .build()
        ), null,
                Collections.singleton(new SimpleGrantedAuthority(parsedToken.getRole())));
    }

    private ParsedToken parse(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));

        JWTVerifier jwtVerifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        String email = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("role").asString();

        return ParsedToken.builder()
                .role(role)
                .email(email)
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class ParsedToken {
        private String email;
        private String role;
    }

}

