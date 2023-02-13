package com.generation.blogpessoal.Controller;

import com.generation.blogpessoal.Service.UsuarioService;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {

        usuarioRepository.deleteAll();

        usuarioRepository.deleteAll();
        usuarioService.cadastrarUsuario(new Usuario(0L, "root@root.com", "rootroot", "", ""));

    }

    @Test
    @DisplayName("Cadastrar um Usuario")
    public void deveCriarumUsuario() {
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Paulo Antunes", "paulo Antunes@email.com.br",
                "123456", "https://bityli.com/1B8Ax"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getUsuario());


    }

    @Test
    @DisplayName("Nao deve permitir duplicacao de usuario")
    public void naoDeveduplicarUsuario() {

        usuarioService.cadastrarUsuario(new Usuario(0L, "Maria da Silva", "maria_Silva@email.com",
                "12345", "https://bityli.com/1B8Ax"));

        HttpEntity<Usuario> corpoRequisicao = new Usuario(0L, "marcos da silva", "marcos_silva@email.com.br",
                "12345678", "https://bityli.com/1B8Ax"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());

    }

        @Test
        @DisplayName("Atualizar um Usuario")

        public void deveAtualizarUsuario () {

            Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,"Juliana Andre",
                    "JulianaAndre", "12345", "https://bityli.com/1B8Ax"));

            Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(), "Juliana Andre","JulianaAndre@email.com.br",
                    "Juliana123", "https://bityli.com/1B8Ax");

            HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

            ResponseEntity<Usuario> corpoResposta = testRestTemplate
                    .withBasicAuth("root@root.com", "rootroot")
                    .exchange("/usuarios/atualizar", HttpMethod.PUT,corpoRequisicao, Usuario.class);

            assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
            assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
            assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getUsuario());

        }

    @Test
    @DisplayName("Listar todos Usuarios")

    public void deveMostrarTodosUsuarios () {

        usuarioService.cadastrarUsuario(new Usuario(0L,"Sabrina",
                "SabrinaDoidona", "12345", "https://bityli.com/1B8Ax"));

        usuarioService.cadastrarUsuario(new Usuario(0L,"Ricardo",
                "RicardoDoidona", "12345", "https://bityli.com/1B8Ax"));


        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null,String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }

    }





