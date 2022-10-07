package ir.mvbdx.mywallet.config;

import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {

        String[] staticResources = {"/css/**", "/images/**", "/fonts/**", "/scripts/**"};

        http
//                .sessionManagement().maximumSessions(1).expiredUrl("/expired").maxSessionsPreventsLogin(false).and()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/invalid").and()
                .authorizeRequests()
                .antMatchers(staticResources).permitAll()
                .antMatchers("/login", "/register", "/result").permitAll()
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers("/api/users/").hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/api/users/new").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/users/edit/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/users/**", "/api/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .httpBasic().and()
                .formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/index", true)
                .and()
                .logout().permitAll()
                .logoutUrl("/logout")
                .and()
                .csrf().disable()
                .headers().frameOptions().disable()
        ;
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