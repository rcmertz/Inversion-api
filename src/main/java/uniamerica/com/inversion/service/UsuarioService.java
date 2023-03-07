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
            throw new RuntimeException("Falha ao Atualizar o usuario");
        }
    }
}
