package uniamerica.com.inversion.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.UsuarioRepository;

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
    public void insert(Usuario usuario){
        this.validarCadastro(usuario);
        this.usuarioRepository.save(usuario);
    }

    @Transactional
    public void update (Long id, Usuario usuario){
        if (id == usuario.getId()){
            this.usuarioRepository.save(usuario);
        }
        else{
            throw new RuntimeException("Falha ao Atualizar o Usuario");
        }
    }

    @Transactional
    public void desativar(Long id, Usuario usuario){
        if (id == usuario.getId()){
            this.usuarioRepository.desativar(usuario.getId());
        }else {
            throw new RuntimeException("Falha ao Desativar o Usuario");
        }
    }
    //** Validacao do Cliente **//

    //Valida se Nome do cliente nao e vazio ou nulo
    public Boolean isNomeNotNull(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
            throw new RuntimeException("O nome não foi fornecido, favor insira um nome valido.");
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
                    throw new RuntimeException("O nome fornecido não é valido, favor insira um nome sem caracter especial.");
                }
            }
        }
        return true;
    }

    public Boolean isCpfNotNull(Usuario usuario) {
        if (usuario.getCpf() != null || !usuario.getCpf().isEmpty()) {
            return true;
        } else {
            throw new RuntimeException("O CPF do Usuario nao foi fornecido, favor inserir um CPF.");
        }
    }
    public Boolean isCpfMenor(Usuario usuario) {
        if (usuario.getCpf().length() == 11) {
            return true;
        } else {
            throw new RuntimeException("CPF é inválido, precisar ter 11 caracteres.");
        }
    }
    public Boolean isCpfCaracter(Usuario usuario) {
        char[] charSearch = {'[', '@', '_', '!', '#', '$', '%', '^', '&', '*', '(', ')', '<', '>', '?', '/', '|', '}', '{', '~', ':', ']'};
        for (int i = 0; i < usuario.getCpf().length(); i++) {
            char chr = usuario.getCpf().charAt(i);
            for (int j = 0; j < charSearch.length; j++) {
                if (charSearch[j] == chr) {
                    throw new RuntimeException("O CPF fornecido não é válido, favor insira apenas números");
                }
            }
        }
        return true;
    }
    //Valida se Telefone do usuario nao e nulo ou vazio
    public Boolean isTelefoneNotNull(Usuario usuario) {
        if (usuario.getTelefone() == null) {
            throw new RuntimeException("O telefone não foi fornecido, favor insira um telefone");
        } else {
            return true;
        }
    }

    //Valida se Telefone tem menos de 11 caracter
    public Boolean isTelefoneMenor(Usuario usuario) {
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
                    throw new RuntimeException("O telefone fornecido nao e valido, possui caracteres especiais! ");
                }
            }
        }
        return true;
    }

    //Valida se os campos do cadastro do Usuario nao estao nulos
    public boolean campoCadastroNull(Usuario usuario){
        if(usuario.getNome()== null || usuario.getTelefone() == null
                || usuario.getCpf() == null) {
            return true;
        }else
            throw new RuntimeException("Não pode ser cadastrado com algum campo em branco.");
    }

    //Funcao para validar o cadastro do Usuario
    public boolean validarCadastro(Usuario usuario){
        if(this.isNomeNotNull(usuario) == true &&
                this.isNomeCaracter(usuario) == true &&
                this.isCpfCaracter(usuario) == true &&
                this.isCpfMenor(usuario) == true &&
                this.isTelefoneCaracter(usuario) == true &&
                this.isTelefoneMenor(usuario) == true &&
                this.isTelefoneNotNull(usuario) == true)
        {
            return true;
        }else{
            return false;
        }
    }

}
