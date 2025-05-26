package cl.duoc.ferremasapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebPayConfig {

    @Value("${webpay.api.key}")
    private String apiKey;
    
    @Value("${webpay.commerce.code}")
    private String commerceCode;
    
    @Value("${webpay.api.url}")
    private String apiUrl;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public String getCommerceCode() {
        return commerceCode;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }
}