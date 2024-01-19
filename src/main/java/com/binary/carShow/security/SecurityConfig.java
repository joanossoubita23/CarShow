package com.binary.carShow.security;

import com.binary.carShow.exception.AuthEntrypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Autowired
    private AuthEntrypoint authEntrypoint;
    @Autowired
    private AuthenticationFilter authenticationFilter;
    @Autowired
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
                       auth-> {
                           try {
                               auth
                                       .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                       .requestMatchers(HttpMethod.GET, "api/v1/car/*").hasAnyRole("USER", "ADMIN")
                                       .requestMatchers(HttpMethod.POST, "api/v1/car/*").hasRole("ADMIN")
                                       .anyRequest()
                                       .authenticated()
                                       .and()
                                       .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                       .exceptionHandling((ex) -> ex.authenticationEntryPoint(authEntrypoint));

                           } catch (Exception e) {
                               throw new RuntimeException(e);
                           }
                       }
                )
                .build();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
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
