package pl.salary.calculator.service.webTarget;

import lombok.Data;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Data
@Configuration
public class RateWebTarget {

    @Value("${exchange.rates.url}")
    private String url;

    @Value("${webTarget.timeout}")
    private Integer timeout;

    private ClientConfig clientConfig() {
        return new ClientConfig()
                .property(ClientProperties.CONNECT_TIMEOUT, timeout)
                .property(ClientProperties.READ_TIMEOUT, timeout);
    }

    @Bean
    public WebTarget RateWebTarget() {
        return ClientBuilder
                .newClient()
                .target(url);
    }
}