package cl.duoc.ferremasapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FERREMAS API")
                        .version("1.0")
                        .description("API para la gestión completa del sistema FERREMAS, incluyendo usuarios, productos, pedidos, pagos, mensajes, integraciones con WebPay y Banco Central.")
                );
    }
}
