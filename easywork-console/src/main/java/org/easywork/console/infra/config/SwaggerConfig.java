package org.easywork.console.infra.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger配置
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EasyWork Console API")
                        .description("EasyWork通用管理后台API文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("EasyWork Team")
                                .email("contact@easywork.org")
                                .url("https://github.com/easywork-org/easywork-console")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                )
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("开发环境"),
                        new Server().url("https://api.easywork.org").description("生产环境")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("EasyWork Console Documentation")
                        .url("https://docs.easywork.org")
                )
                // 添加安全配置
                .addSecurityItem(new SecurityRequirement().addList("Bearer Token"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer Token",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("请输入JWT Token")
                        )
                );
    }
}