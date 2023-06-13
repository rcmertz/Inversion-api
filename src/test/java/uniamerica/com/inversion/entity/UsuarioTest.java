package uniamerica.com.inversion.entity;

import org.junit.jupiter.api.Test;
import uniamerica.com.inversion.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioTest {

    //** Teste de nome do Usuario **//
    @Test
    public void isNomeNotNull() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setNome("ass");
        assertTrue(UsuarioService.isNomeNotNull(usuario));
    }

    @Test
    public void isNomeCaracter() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setNome("sdad");
        assertTrue(UsuarioService.isNomeCaracter(usuario));
    }
    //****//

    //** Teste de telefone do Usuario **//
    @Test
    public void isTelefoneLength(){
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setTelefone("12345678912");
        assertTrue(UsuarioService.isTelefoneLength(usuario));
    }

    @Test
    public void isTelefoneNotNull(){
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setTelefone("SDA");
        assertTrue(UsuarioService.isTelefoneNotNull(usuario));
    }

    @Test
    public void isTelefoneCaracter() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setTelefone("sad");
        assertTrue(UsuarioService.isTelefoneCaracter(usuario));
    }

    @Test
    public void isTelefoneNumber() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setTelefone("22f2e2");
        assertTrue(UsuarioService.isTelefoneNumber(usuario));
    }
    //****//

    //** Teste de CPF do Usuario **//

    //Testa se CPF nao e null
    @Test
    public void isCpfNotNull() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setCpf("as");
        assertTrue(UsuarioService.isCpfNotNull(usuario));
    }

    //****//

    //Testa se CPF tem 11 caracter especial
    @Test
    public void isCpfMenor() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setCpf("12349567892");
        assertTrue(UsuarioService.isCpfMenor(usuario));
    }

    //Testa se CPF tem caracter especial
    @Test
    public void isCpfCaracter() {
        Usuario usuario = new Usuario();
        UsuarioService UsuarioService = new UsuarioService();

        usuario.setCpf("222222222222!");
        assertTrue(UsuarioService.isCpfCaracter(usuario));
    }
    
    
}
