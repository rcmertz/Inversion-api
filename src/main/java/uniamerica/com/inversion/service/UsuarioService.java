package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.UsuarioRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findById(Long id){
        return this.usuarioRepository.findById(id).orElse(new Usuario());
    }

    public Page<Usuario> listAll(Pageable pageable){
        return this.usuarioRepository.findAll(pageable);
    }

    @Transactional
    public Usuario insert(Usuario usuario){
        if (this.validarRequest(usuario) &&
            validarCpfEmailExistente(usuario)) {
            this.usuarioRepository.save(usuario);
            return usuario;
        }else {
            throw new RuntimeException("Falha ao Cadastrar o Usuario");
        }
    }

    public Usuario findByEmail(String username) {
        return usuarioRepository.findByEmail(username);
    }

    @Transactional
    public void update (Long id, Usuario usuario){
        if (id == usuario.getId() && this.validarRequest(usuario)){
            this.usuarioRepository.save(usuario);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar o Usuario");
        }
    }

    @Transactional
    public void desativar(Long id, Usuario usuario){
        if (id == usuario.getId() && this.validarRequest(usuario)){
            this.usuarioRepository.desativar(usuario.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar o Usuario");
        }
    }
    //** Validacao do Usuario **//

    //Valida se Nome do usuario nao e vazio ou nulo
    public Boolean isNomeNotNull(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new RuntimeException("O nome não foi fornecido, favor insira um nome de usuario valido.");
        } else {
            return true;
        }
    }
    public Boolean isNomeCaracter(Usuario usuario) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < usuario.getNome().length(); i++) {
            char chr = usuario.getNome().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O nome fornecido não é valido, favor insira um nome de usuario sem caracter especial.");
                }
            }
        }
        return true;
    }

    //** VALIDAÇÕES USUARIO **//

    //Valida se o CPF nao e nulo
    public Boolean isCpfNotNull(Usuario usuario) {
        if (usuario.getCpf() != null || !usuario.getCpf().isEmpty()) {
            return true;
        } else {
            throw new RuntimeException("O CPF do Usuario nao foi fornecido, favor inserir um CPF.");
        }
    }

    //Valida se ja existe o usuario no banco pelo CPF
    public boolean validarCpfEmailExistente(Usuario usuario) {
        // Verifica se o CPF já está cadastrado
        Usuario usuarioPorCpf = usuarioRepository.findByCpf(usuario.getCpf());
        if (usuarioPorCpf != null) {
            throw new RuntimeException("CPF ou Email já cadastrados, tente novamente.");
        }
        // Verifica se o email já está cadastrado
        Usuario usuarioPorEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioPorEmail != null) {
            throw new RuntimeException("CPF ou Email já cadastrados, tente novamente.");
        }
        return true;
    }

    //Valida se o CPF tem 11 caracteres
    public Boolean isCpfMenor(Usuario usuario) {
        if (usuario.getCpf().length() == 11) {
            return true;
        } else {
            throw new RuntimeException("CPF é inválido, precisa ter 11 caracteres.");
        }
    }

    //Valida se o CPF chegou com caracteres especiais
    public Boolean isCpfCaracter(Usuario usuario) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < usuario.getCpf().length(); i++) {
            char chr = usuario.getCpf().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O CPF fornecido não é válido, favor inserir apenas números");
                }
            }
        }
        return true;
    }
    //Valida se Telefone do usuario nao e nulo ou vazio
    public Boolean isTelefoneNotNull(Usuario usuario) {
        if (usuario.getTelefone() == null) {
            throw new RuntimeException("O telefone do usuario não foi fornecido, favor inserir um telefone");
        } else {
            return true;
        }
    }

    // Valida se o telefone informado possui letras. //
    public Boolean isTelefoneNumber(Usuario usuario){
        char[] charSearch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for (int i = 0; i < usuario.getTelefone().length(); i++) {
            char chr = usuario.getTelefone().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    return true;
                }
            }
        }
        throw new RuntimeException("O telefone contem letras.");
    }

    //Valida se Telefone tem menos de 11 caracter
    public Boolean isTelefoneLength(Usuario usuario) {
        if (usuario.getTelefone().length() == 11) {
            return true;
        } else {
            throw new RuntimeException("Telefone do usuario é invalido, precisa possuir 11 numeros.");
        }
    }

    //Valida Caracter Especial do Telefone
    public Boolean isTelefoneCaracter(Usuario usuario) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < usuario.getTelefone().length(); i++) {
            char chr = usuario.getTelefone().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O telefone fornecido do usuario nao e valido, possui caracteres especiais! ");
                }
            }
        }
        return true;
    }

    //Valida se os campos do cadastro do Usuario nao estao nulos
//    public boolean campoCadastroNull(Usuario usuario){
//        if(usuario.getNome()== null || usuario.getTelefone() == null
//                || usuario.getCpf() == null || usuario.getEmail() == null || usuario.getSenha() == null) {
//            throw new RuntimeException("O usuario não pode ser cadastrado com algum campo em branco.");
//        }else
//            return true;
//    }

    //Funcao para validar o cadastro do Usuario
    public boolean validarRequest(Usuario usuario){
        return this.isNomeNotNull(usuario) &&
                this.isNomeCaracter(usuario) &&
                this.isCpfCaracter(usuario) &&
                this.isCpfMenor(usuario) &&
                this.isTelefoneCaracter(usuario) &&
                this.isTelefoneLength(usuario) &&
                this.isTelefoneNotNull(usuario) &&
                this.isTelefoneNumber(usuario) &&
                this.isCpfNotNull(usuario);
    }

}
