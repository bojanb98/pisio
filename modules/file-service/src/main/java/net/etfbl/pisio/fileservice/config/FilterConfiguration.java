package net.etfbl.pisio.fileservice.config;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.authfilter.TokenValidationService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class FilterConfiguration {

    private final TokenValidationService tokenValidationService;

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> loggingFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(tokenValidationService));
        registrationBean.addUrlPatterns("/file/*");

        return registrationBean;
    }

}
