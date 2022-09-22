package ir.mvbdx.mywallet;

import ir.mvbdx.mywallet.config.CustomAuditAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class MyWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWalletApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new CustomAuditAware();
    }
}
