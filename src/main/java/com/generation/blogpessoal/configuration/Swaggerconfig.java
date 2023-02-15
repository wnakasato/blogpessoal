package com.generation.blogpessoal.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swaggerconfig {

    @Bean
    public OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Projeto blog pessoal")
                        .description("Projeto Blog Pessoal Generation")
                        .version("V 1.0")
                        .license(new License()
                                .name("Generation Brasil")
                                .url("http://brazil.generation.org/"))
                                .contact(new Contact()
                                        .name("Conteudo Generation")
                                        .url("http://github.com.br/conteudoGeneration")
                                .email("conteudo.generation@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("http://github.com/conteudoGeneration"));
    }

    @Bean
    public OpenApiCustomizer custumerGlobalHeaderOpenAPICustomizer() {

        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse("200", createApiResponse("sucesso"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido"));
                apiResponses.addApiResponse("400", createApiResponse("Erro Requisicao"));
                apiResponses.addApiResponse("401", createApiResponse("Acesso Nao Autorizado"));
                apiResponses.addApiResponse("404", createApiResponse("Objeto nao encontrado"));
                apiResponses.addApiResponse("500", createApiResponse("erro na aplicacao"));

            }));

        };

    }

    private ApiResponse createApiResponse(String message){
        return new ApiResponse().description(message);
    }

}
