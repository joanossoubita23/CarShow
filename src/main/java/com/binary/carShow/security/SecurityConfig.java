package com.binary.carShow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Header ->Authentication : Basic username: password
    //body{
    // id;
    // make"toyota",
    //}
    //crf cross site request forgery(CSRF) attack
    //cors
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Everyone Should be able to GET
        return http
                .cors(Customizer.withDefaults())
                .csrf(c->c.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                       auth->auth.requestMatchers(HttpMethod.GET,"api/v1/car/*").hasAnyRole("USER","ADMIN")
                               .requestMatchers(HttpMethod.POST,"api/v1/car/*").hasRole("ADMIN")
                               .anyRequest()
                               .authenticated()
                )
                .build();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }




//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails admin= User.builder()
//                .username("admin")
//                .password(bCryptPasswordEncoder().encode("adminPass"))
//                .roles("ADMIN")
//                .build();
//        var user=User.builder()
//                .username("user")
//                .password(bCryptPasswordEncoder().encode("UserPass"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);

   // }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
