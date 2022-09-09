package com.springsecurity.brewery.config;

import com.springsecurity.brewery.security.RestHeaderAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private ObjectPostProcessor<Object> oop;

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(restHeaderAuthFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable();
        http
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/h2-console/**").permitAll() // do not use in production
                        .antMatchers("/", "/login").permitAll()
                        .antMatchers("/beers/find", "/beers").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                        .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll()
                        .mvcMatchers("/brewery/breweries")
                            .hasAnyRole("ADMIN", "CUSTOMER")
                        .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries")
                            .hasAnyRole("ADMIN", "CUSTOMER")
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
        // h2 console config
        http
                .headers().frameOptions().sameOrigin();

        return http.build();
    }

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsManager(dataSource()));
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }

    @Bean
    DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

//    @Bean
//    UserDetailsManager userDetailsManager(DataSource dataSource) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails spring = User.builder()
//                .username("spring")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER", "ADMIN")
//                .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        users.createUser(spring);
//        users.createUser(admin);
//        return users;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
////        UserBuilder users = User.withDefaultPasswordEncoder();
////        UserDetails user = users
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
//
//        UserDetails admin =
//                User.builder()
//                        .username("spring")
//                        .password(passwordEncoder().encode("password"))
//                        .roles("ADMIN")
//                        .build();
//
//        UserDetails user =
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("password"))
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public AuthenticationManager authenticationManager()
//            throws Exception {
//        return new AuthenticationManagerBuilder(oop)
//                .userDetailsService(userDetailsManager(dataSource()))
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource()).passwordEncoder(passwordEncoder());
////        authenticationManagerBuilder.userDetailsService(userDetailsManager(dataSource())).passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/webjars/**", "/resources/**");
    }
}
