package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @BeforeAll
    void start() {

        usuarioRepository.deleteAll();
        usuarioRepository.save((new Usuario(0L, "Joao da Silva", "joao@email.com", "12345", "https://bityli.com/1B8Ax")));
        usuarioRepository.save((new Usuario(0L, "Manoela da Silva", "manu@email.com", "123456", "https://bityli.com/1B8Ax")));
        usuarioRepository.save((new Usuario(0L, "adriana da Silva", "adriana@email.com", "1234567", "https://bityli.com/1B8Ax")));
        usuarioRepository.save((new Usuario(0L, "Paulo da Silva", "Paulo@email.com", "12345678", "https://bityli.com/1B8Ax")));

    }

    @Test
    @DisplayName("Retorna um usuario")
    public void deveRetornarumUsuario() {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
        assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
    }

    @Test
    @DisplayName("Retorna tres usuarios")
    public void deveRetornartresUsuario() {

        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        assertEquals(3, listaDeUsuarios.size());
        assertTrue(listaDeUsuarios.get(0).getNome().equals("joao da Silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Manoela da Silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("Manoela da Silva"));


    }

@AfterAll
    public void end(){
        usuarioRepository.deleteAll();
}

}