package com.flexone.catchwiseserver;

import com.flexone.catchwiseserver.security.JWTProvider;
import com.flexone.catchwiseserver.service.UserDetailsServiceImpl;
import com.flexone.catchwiseserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Slf4j
@EnableAutoConfiguration
@SpringBootApplication
public class CatchwiseServerApplication {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTProvider jwtProvider;


    public static void main(String[] args) {
        SpringApplication.run(CatchwiseServerApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void populateSecurityContext() {
        List<String> sessionTokens = userService.fetchAllUserTokens();
        sessionTokens.forEach(token -> {
            String username = jwtProvider.getUsernameFromJwt(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });
        log.info("Security context populated");
    }

}
