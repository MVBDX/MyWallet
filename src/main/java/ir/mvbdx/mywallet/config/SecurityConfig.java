package ir.mvbdx.mywallet.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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