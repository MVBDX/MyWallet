package ir.mvbdx.mywallet.config;

import ir.mvbdx.mywallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/expired")
                .maxSessionsPreventsLogin(false)
                .and()

                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invalid")
                .and()

                .httpBasic()
                .and()

                .authorizeRequests()
                .antMatchers("/welcome").permitAll()
                .and()

                .authorizeRequests()
//                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .antMatchers("/api/users/").hasAnyRole("ADMIN", "MANAGER", "USER")
                .antMatchers("/api/users/new").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/users/edit/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/users/**", "/api/user/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin().permitAll()
                .defaultSuccessUrl("/index", true)
                .and()

                .logout().permitAll()
                .logoutUrl("/logout")
                .and()

                .csrf().disable()
                .headers().frameOptions().disable()
        ;
    }
}