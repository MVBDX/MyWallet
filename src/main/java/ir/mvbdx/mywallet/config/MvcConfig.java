package ir.mvbdx.mywallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "ir.mvbdx.mywallet")
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    /*@Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver cr = new CookieLocaleResolver();
        cr.setCookieName("mywallet");
        cr.setDefaultLocale(Locale.ENGLISH);
        return cr;
    }

    @Bean
	public InternalResourceViewResolver viewResolver() {
		return new InternalResourceViewResolver(
				"/WEB-INF/pages/",
				".jsp");
	}

	//load properties files on change of language code
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource rm = new ReloadableResourceBundleMessageSource();
		rm.setBasename("classpath:messages");
		rm.setDefaultEncoding("UTF-8");
		return rm;
	}*/

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
