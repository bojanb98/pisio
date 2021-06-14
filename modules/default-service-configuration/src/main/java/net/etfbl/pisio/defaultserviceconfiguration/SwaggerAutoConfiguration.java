package net.etfbl.pisio.defaultserviceconfiguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Docket apiConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalRequestParameters(globalParameterList())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private List<RequestParameter> globalParameterList() {
        return Collections.singletonList(new RequestParameterBuilder()
                .name("Authorization")
                .description("Authorization Bearer param")
                .in(ParameterType.HEADER)
                .required(false)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING))).build());
    }
}
