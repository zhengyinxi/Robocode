package wang.xin.robocode.server.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by Xin on 2016/10/6.
 */
@Configuration
public class AppConfig implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    }

    @Bean
    @Autowired
    public EmbeddedServletContainerCustomizer servletContainerCustomizer(ServerProperties properties) {
        return container -> {
            if (container instanceof JettyEmbeddedServletContainerFactory) {
                JettyEmbeddedServletContainerFactory jettyContainer = (JettyEmbeddedServletContainerFactory) container;
                jettyContainer.addConfigurations(new AbstractConfiguration() {

                    @Override
                    public void configure(WebAppContext context) throws Exception {
                        properties.getContextParameters().forEach((k, v) -> context.setInitParameter(k, v));
                    }
                });
            }
        };
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToDisable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .featuresToDisable(SerializationFeature.WRITE_NULL_MAP_VALUES);
    }
}
