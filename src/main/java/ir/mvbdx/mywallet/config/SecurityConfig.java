package ir.mvbdx.mywallet.config;

import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder,
                                                       CustomerService customerService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customerService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String[] staticResources = {"/css/**", "/images/**", "/fonts/**", "/scripts/**", "/webjars/**",
                "/login", "/register", "/result"};

        http.csrf().disable()
                // .sessionManagement().maximumSessions(1).expiredUrl("/expired").maxSessionsPreventsLogin(false).and()
                // .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/invalid").and()
                .authorizeRequests((requests) -> requests
                        .requestMatchers(staticResources).permitAll()
                        // .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                        .requestMatchers("/api/users/").hasAnyRole("ADMIN", "MANAGER", "USER")
                        .requestMatchers("/api/users/new").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/users/edit/**").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/api/users/**", "/api/user/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin((form) ->
                        form.loginPage("/login").permitAll().defaultSuccessUrl("/index", true))
                .logout((logout) -> logout.permitAll().logoutUrl("/logout"))
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .httpBasic().and()
                .headers().frameOptions().disable()
        ;
        return http.build();
    }
    /*@Configuration
    @Order(1)
    public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("USER")
                    .and()
                    .httpBasic()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
        }
    }

    @Configuration
    @Order(2)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            String[] staticResources = {"/css/**", "/images/**", "/fonts/**", "/scripts/**"};

            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(staticResources).permitAll()
                    .antMatchers("/login", "/register", "/result").permitAll()
                    .and()
                    .formLogin()
                    .and()
                    .logout().permitAll()
            ;
        }
    }*/
}