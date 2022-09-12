package com.springsecurity.brewery.security.listeners;

import com.springsecurity.brewery.domain.security.LoginSuccess;
import com.springsecurity.brewery.domain.security.User;
import com.springsecurity.brewery.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationEvents {
    private final LoginSuccessRepository loginSuccessRepository;
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        log.debug("User Logged In OK.");
        if(event.getSource() instanceof UsernamePasswordAuthenticationToken){
            LoginSuccess.LoginSuccessBuilder builder = LoginSuccess.builder();
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
            if(token.getPrincipal() instanceof User){
                User user = (User) token.getPrincipal();
                builder.user(user);
                log.debug("User named logged in: "+ user.getUsername());
            }
            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.debug("Source ip: "+details.getRemoteAddress());
                builder.sourceIp(details.getRemoteAddress());
            }
            LoginSuccess loginSuccess = loginSuccessRepository.save(builder.build());
            log.debug("Login success saved. Id: "+loginSuccess.getId());
        }
    }
}
