package com.springsecurity.brewery.bootstrap;

import com.springsecurity.brewery.domain.security.Authority;
import com.springsecurity.brewery.domain.security.User;
import com.springsecurity.brewery.repositories.security.AuthorityRepository;
import com.springsecurity.brewery.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private void loadSecurityData() {
        Authority admin = authorityRepository.save(Authority.builder().authority("ROLE_ADMIN").build());
        Authority userRole = authorityRepository.save(Authority.builder().authority("ROLE_USER").build());
        Authority customer = authorityRepository.save(Authority.builder().authority("ROLE_CUSTOMER").build());

        userRepository.save(User.builder()
                .username("spring")
                .password(passwordEncoder.encode("password"))
                .authority(admin)
                .build());

        userRepository.save(User.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .authority(userRole)
                .build());

        userRepository.save(User.builder()
                .username("scott")
                .password(passwordEncoder.encode("tiger"))
                .authority(customer)
                .build());

        log.debug("Users Loaded: " + userRepository.count());
    }

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
        }
    }
}
