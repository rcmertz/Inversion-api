package uniamerica.com.inversion.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import uniamerica.com.inversion.entity.Usuario;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Test
    public void insertUsuario() {

        Usuario usuario = new Usuario("Ricardo", "09050113923", "45999351660", "teste@gmail.com", "teste");

        this.usuarioRepository.save(usuario);

        Integer countUsuario = usuarioRepository.findAll().size();

        assertEquals(1, countUsuario);

    }

}
